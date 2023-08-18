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

package engcalculator;

import engcalculator.function.CalculusException;
import engcalculator.interval.ListIntervals;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.expression.Expression;
import engcalculator.expression.ParsingException;
import engcalculator.function.embedded.define.DefLambdaGetName;
import engcalculator.function.embedded.multilineExpression.MleStatement;
import engcalculator.function.embedded.system.LoadGroup;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.reactiveExpression.ReactiveExpression;
import engcalculator.function.variable.FunctionVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class Calculator {    
    private final static int MAXANSWER = 99;
    private final static int MAXEXPECTEDNUMBEROFLINES = 20;
    private final String MULTIPLEEXPRESSIONSTATEMENT = MleStatement.MULTILINEEXPSTATEMENT;    
    private static int nextStep ;
    
    public static ArrayList<String> splitInLines (String text, ArrayList<String> result) {
        int i;
        while ((i = text.indexOf("\n")) != -1) {
            if (i > 0) result.add(text.substring(0, i));
            if (i+2 > text.length()) {
                text = null;
                break;
            }
            text = text.substring(i+1);
        }
        if (text != null && ! text.equals("\n") ) result.add(text);
        return result;
    }    

    public static int getNextStep() {
        return nextStep;
    }
            
    private int step = -1;
    private Expression expressionTokenized;
    
    private ArrayList<String> expressionString;
    private int expressionSPointer, expressionSSize;
    
    private boolean stop, debug;
    private StringBuilder answer;
    
    private AsynchronousOutput postponedOutput;
    
    private static StringBuilder warnings;   
    private boolean isDoingMultExp, isDoingMultLit;
    
    public Calculator (boolean coldStart, AsynchronousOutput postponedOutput) {
        expressionString = new ArrayList<String>(MAXEXPECTEDNUMBEROFLINES);
        
        answer = new StringBuilder ();
        debug = false;
        isDoingMultExp = false;
        isDoingMultLit = false;
        
        this.postponedOutput = postponedOutput;        

        if (coldStart || step < 0) {
            try {
                FunctionPrefix.initialize();
                FunctionInfix.initialize();
                FunctionVariable.initialize();
                LoadGroup.initialize ();                
                ParameterManager.initialize();
                nextStep = step = 1;
                LoadGroup.loadGroup("BASE");
            } catch (Exception e) {
                System.out.println (e.getMessage());
            }
        }
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
        Expression.setDebug(debug);
        stop = debug;
    }
    
    public void setTrace (boolean trace) {
        if (trace) Expression.setTrace(postponedOutput);
        else Expression.setTrace(null);
    }    
//
//    public int getExpressionPointer() {
//        return expressionSPointer;
//    }
//
//    public void setExpressionPointer(int expressionPointer) {
//        if (debug) this.expressionSPointer = expressionPointer;
//    }        
//
//    public void setExpressionPointerRelative(int expressionPointerDelta) {
//        if (debug) this.expressionSPointer += expressionPointerDelta;
//    }        
    
    private String expToTokenize;
    private boolean evaluationInProgress;
    
    public String compute (String exp) {
        expToTokenize = exp;
        if (!( isDoingMultExp || isDoingMultLit)) expressionSPointer = 0;
        splitInLines(exp);
        stop = debug;
        evaluationInProgress = false;
        
        return continueCompute ();
    }
    
    public String continueCompute () {
                
        ListIntervals result;
                
        while (expressionSSize > expressionSPointer || evaluationInProgress) {            
            answer.setLength(0);

            try {
                if (! evaluationInProgress) expressionTokenized = tokenizeNewRows();
                
                if (expressionTokenized == null) {                    
                    if (isDoingMultLit || isDoingMultExp) printBold(">> ",expToTokenize,"\n");
                    return null;
                }
                
                if (! evaluationInProgress) result = expressionTokenized.evaluate(null);
                else  result = expressionTokenized.continueEvaluate();
                
                evaluationInProgress = expressionTokenized.isEvaluationInProgress();
                
                if (! evaluationInProgress) printRegular("Expression",Integer.toString(getStep()),"> ",expressionTokenized.getExpression());

                DefLambdaGetName.delAllLambdaFunctions();            

                LinkedList<String> dup = expressionTokenized.listOfDuplicatedIntervals();
                if (dup.size() != 0) addWarning("Warning: there may be duplicated variables containing intervals in some input sub expression, which may lead to overestimation of the result. Try to rewrite the input sub expressions avoiding the following duplications for a smaller result: ".concat(dup.toString()));

                converResultToString(result, answer);

                String re = ReactiveExpression.computeReactiveExpressions(false);
                if (re != null && re.length() > 0) answer.append(re);       

                if (postponedOutput != null) {                
                    String war = Calculator.getAndCleanWarnings();
                    if (war != null) printBold(war,"\n");       

                    printBold(answer.toString(), "\n\n");
                }        

            } catch (CalculusException ce) {
                printBold(">> ",expressionTokenized.getExpression(),"\n");
                printBold ("There was an error trying to execute the input expression.\n",ce.getMessage(),"\n\n");
            } catch (ParsingException pe) {
                printBold(">> ",expressionTokenized.getExpression(),"\n");
                printBold ("There was an error trying to parse the input expression.\n",pe.getMessage(),"\n\n");
            } catch (Exception ex) {
                printBold(">> ",expressionTokenized.getExpression(),"\n");
                ex.printStackTrace(System.out);
                String error = "There was an internal error\nPlease send this ICE version, the current history and expression, and the following stack trace:\n"+Arrays.toString(ex.getStackTrace())+"\n\n";
                printBold(error);
                return error;
            }        
            
            if (stop) break;
        }
        
        return answer.toString();
    }  
    
    private void printBold (String... out) {
        if (postponedOutput != null) {
            for (String s : out) postponedOutput.printOnOutputAreaInBold(s);
        }        
    }

    private void printRegular (String... out) {
        if (postponedOutput != null) 
            for (String s : out) postponedOutput.printOnOutputAreaInRegular(s);        
    }
    
    private void converResultToString (ListIntervals result, StringBuilder Answer) throws Exception {
        if (result == null || result.size() == 0) {
            answer.append("No answer returned");
        } else if (result.getName() != null) {
            answer.append(result.toString());
        } else {
            FunctionVariable.addFunction(new FunctionVariable ("answerLast", result,  false, "It contains last unnamed answer provided by the kernel."));
            FunctionVariable answerFV = new FunctionVariable ("answer"+step, result,  false, "");
            ++step;
            if (step > MAXANSWER) step = 1;
            nextStep = step;
            FunctionVariable.addFunction(answerFV);
            answer.append(answerFV.getValue().toString());
        }        
    }
       
    private ArrayList<String> expMultipleLine = null;
    private StringBuilder litMultipleLine= null;
    
    private Expression tokenizeNewRows () throws Exception {
        String expSingleLine = null;                 
        
        while (expressionSPointer < expressionSSize) {
            expSingleLine = expressionString.get(expressionSPointer++);

            if (expSingleLine.equals(MULTIPLEEXPRESSIONSTATEMENT)) {//multipe line expressione
                if (isDoingMultExp) {
                    isDoingMultExp = false;
                    return new Expression (expMultipleLine.toArray(new String[0]) , null);
                } else {
                    isDoingMultExp = true;
                    if (expMultipleLine == null) expMultipleLine = new ArrayList<String> (MAXEXPECTEDNUMBEROFLINES);                    
                    else expMultipleLine.clear();
                }                
            } else if (expSingleLine.length() > 1 && expSingleLine.substring(expSingleLine.length()-2).equals("'+") ) {//multiple line literal
                if (litMultipleLine == null || litMultipleLine.length() == 0) litMultipleLine = new StringBuilder().append(expSingleLine.substring(0, expSingleLine.length()-2));
                else litMultipleLine.append(expSingleLine.substring(1, expSingleLine.length()-2));
                litMultipleLine.append('\n');
                isDoingMultLit = true;
            } else if (isDoingMultLit) {
                litMultipleLine.append(expSingleLine.substring(1));
                expSingleLine = litMultipleLine.toString();
                litMultipleLine.setLength(0);
                if (isDoingMultExp) expMultipleLine.add(expSingleLine);
                isDoingMultLit = false;
                if (! isDoingMultExp) break;
                else expMultipleLine.add(expSingleLine);
            } else if (isDoingMultExp) {
                expMultipleLine.add(expSingleLine);
            } else {
                break;
            }                               
        }
        
        if (isDoingMultExp || isDoingMultLit) return null; //the multipleExpression or literal needs to finish
        
        return new Expression (expSingleLine, null);                
    }
    
    private void splitInLines (String text) {
        if (!(isDoingMultExp || isDoingMultLit)) expressionString.clear();
        splitInLines(text, expressionString);
        expressionSSize = expressionString.size();
    }
    
    public int getStep() {
        return step;
    }

    void stopCalculus() {
        expressionTokenized.stopCalculus();
        stop = true;
        isDoingMultExp = isDoingMultLit = false;
    }

    public static String getAndCleanWarnings() {
        if (warnings == null) return null;
        String result = warnings.toString();
        warnings = null;
        return result;
    }

    public static void addWarning (String warning) {
        if (warnings == null) warnings = new StringBuilder ();
        warnings.append(warning);
        warnings.append("\n\n");
    }
}
