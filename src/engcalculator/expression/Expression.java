/*
 *     ICE (Interval Calculator for Engineer) is a programmable calculator working on intervals.
 *     Copyright (C) 2009  Simone Pernice
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package engcalculator.expression;

import engcalculator.AsynchronousOutput;
import engcalculator.expression.token.TokenList;
import engcalculator.expression.token.Tokenizer;
import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.interval.ListIntervals;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.workFlow.WorkFlow;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Expression {
    public final static int MAXGOSUBDEPTH = 100;
    private final static LinkedList<ListIntervals> previousResults = new LinkedList<ListIntervals> ();
    public final static int MAXPREVIOUSRESULTSTORE = 5;
    protected final static boolean DEBUG = false;
            
    protected static boolean stop = false;
    protected static boolean debug = false;
    protected static AsynchronousOutput trace = null;

    public static void setDebug(boolean debug) {
        Expression.debug = debug;
        stop = debug;
    }
    
    public static void setTrace (AsynchronousOutput trace) {
        Expression.trace = trace;
    }    
    
    public static ListIntervals  getResult (int i) throws CalculusException {
        if (i >= MAXPREVIOUSRESULTSTORE) Function.throwNewCalculusException("The given index "+i+" is outside the maximum number of previous result stored: "+MAXPREVIOUSRESULTSTORE);        
        if (i < 0 || i >= previousResults.size()) Function.throwNewCalculusException("The given index "+i+" is outside the range of previous computed intervals 0 to"+previousResults.size());        
        return previousResults.get(i);
    }
                
    protected final Tokenizer tokenizer;
    protected String[] expression;
    protected Interpeter[] interpeter;
    protected int size;
    
    private int nextLine;
    private LabelsStorage labelsStorage;
    private LinkedList<Integer> goSubRows;

    protected Expression () {
        tokenizer = new Tokenizer();        
        labelsStorage = new LabelsStorage ();
        goSubRows = new LinkedList<Integer>();
    }

    public Expression (String expression, LinkedList<String> varfunc) throws Exception {
        this();
        initializer (new String[] {expression}, varfunc);
    }
    
    public Expression (String[] expression, LinkedList<String> varfunc) throws Exception {
        this();
        initializer (expression, varfunc);
    }
    
    protected final void initializer (String[] expression, LinkedList<String> varfunc) throws Exception {
        this.expression = expression;           
        size = expression.length;
        interpeter = new Interpeter[size];
        int row=0;
        for (String exp : expression) {
            Interpeter in = getInterpeter (tokenizer.tokenize(varfunc, exp));       
            String name = in.isALabel();
            if (name != null) labelsStorage.addLabel(new Label(name, row));
            interpeter[row++] = in; 
        }
    }
    
    protected Interpeter getInterpeter (TokenList exp) {
        return new Interpeter(exp);
    } 
    
    public LinkedList<String> getParametersName() {
        return tokenizer.getParametersName();
    }

    public String getExpression() {
        StringBuilder result = new StringBuilder();
        if (size>1) result.append("multiple line expression:\n");
        for (String s : expression) result.append(s).append("\n");
        return result.toString();
    }        

    public ListIntervals evaluate(HashMap<String, FunctionPrefix> parameters ) throws Exception {
        if (DEBUG) System.out.println("Evaluating interpreted expression: "+expression[nextLine]+" with parameters: "+parameters);
        
        tokenizer.setParameters(parameters);        
        
        stop = debug;
        
        nextLine = 0;
        
        previousResults.clear();
        
        return continueEvaluate();
    }
    
    public ListIntervals continueEvaluate() throws Exception {                
        while (isEvaluationInProgress()) {
            Interpeter in = interpeter[nextLine++];            
            
            previousResults.push(in.parse());
            while (previousResults.size() > MAXPREVIOUSRESULTSTORE) previousResults.removeLast();
            
            if (trace != null) {
                int prevLine = nextLine-1;
                trace.printOnOutputAreaInBold("Trace line ");
                trace.printOnOutputAreaInBold(Integer.toString(prevLine));
                trace.printOnOutputAreaInBold(">> ");
                trace.printOnOutputAreaInBold(expression[prevLine]);
                trace.printOnOutputAreaInBold("\n");
                trace.printOnOutputAreaInBold("Result >> ");
                if (previousResults.size() > 0 && previousResults.peek() != null) trace.printOnOutputAreaInBold(previousResults.peek().toString());
                trace.printOnOutputAreaInBold("\n");
                if (in.getExpressionWorkFlow() != null && in.getExpressionWorkFlow().getWorkFlow() != null) {
                    trace.printOnOutputAreaInBold("Work Flow Change >> ");
                    trace.printOnOutputAreaInBold(in.getExpressionWorkFlow().getWorkFlow().toString());
                    trace.printOnOutputAreaInBold("\n");
                }
                trace.printOnOutputAreaInBold("\n");
            }            
            
            changeWorkFlow (in.getExpressionWorkFlow());

            if (stop) break;
        }

        return previousResults.peek();
    }
    
    public boolean isEvaluationInProgress () {
        return nextLine < size;
    }
    
    private void changeWorkFlow (Interval iwf) throws Exception {                
        WorkFlow wf;
        if (iwf != null && (wf = iwf.getWorkFlow()) != null) {
            switch (wf.getNextExpression()) {
                case GOSUB_LABEL_EX:
                    goSubRows.push(nextLine);
                    if (goSubRows.size() > MAXGOSUBDEPTH) Function.throwNewCalculusException("Too many annidated GoSub, the maximum is "+MAXGOSUBDEPTH);
                case GOTO_LABEL_EX:
                    String l = wf.getLabel();
                    int row = labelsStorage.getRow(l);
                    if (row != -1) nextLine = row+1;
                    else Function.throwNewCalculusException("GoTo without an existing label.");
                    break;                              
                case RETURN_EX:
                    if (goSubRows.size() < 1) Function.throwNewCalculusException("Return without a GoSub");
                    nextLine = goSubRows.pop();
                    break;                        
                case GOTO_BEGIN_EX:
                    nextLine = 0;
                    break;                    
                case GOTO_BEFORE_PREV_EX:
                    nextLine -= 3;
                    if (nextLine < 0) Function.throwNewCalculusException("GoToBeforePrevious expression without a before previous expression.");
                    break;
                case GOTO_PREV_EX:
                    nextLine -= 2;
                    if (nextLine < 0) Function.throwNewCalculusException("GoToPrevious expression without a previous expression.");
                    break;
                case GOTO_THIS_EX:
                    nextLine -= 1;
                    break;                       
                case GOTO_NEXT_EX:
                    break;
                case GOTO_AFTER_NEXT_EX:
                    nextLine += 1;
                    if (nextLine >= size) Function.throwNewCalculusException("GoToAfterNext expression without a next expression.");
                    break;
                case GOTO_END_EX:
                    previousResults.push(new ListIntervals(new IntervalReal(iwf)));
                    nextLine = size;
                    break;
                default:
                    throw new RuntimeException ("Internal error trying to find the proper control path in Expression.continueEvaluate");
            }
        }        
    }

    public LinkedList<String> listOfDuplicatedIntervals () {
        LinkedList<String> res = new LinkedList<String>();
        for (int i=0; i<size; ++i) res.addAll(interpeter[i].listOfDuplicatedIntervals ());
        return res;
    }

    public LinkedList<String> listOfDuplicatedVariables() {
        LinkedList<String> res = new LinkedList<String>();
        for (int i=0; i<size; ++i) res.addAll(interpeter[i].listOfDuplicatedVariables ());
        return res;        
    }
    
    public LinkedList<String> listOfVariables() {
        LinkedList<String> res = new LinkedList<String>();
        for (int i=0; i<size; ++i) res.addAll(interpeter[i].listOfVariables ());
        return res;         
    }

    @Override
    public String toString(){
        return "Interpreted expression: "+Arrays.toString(expression);
    }

    public void stopCalculus() {
        if (interpeter != null && nextLine-1<interpeter.length && nextLine >= 1) interpeter[nextLine-1].stopCalculus();
        stop = true;
    }
}
