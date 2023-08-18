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


import engcalculator.expression.token.TokenList;
import engcalculator.expression.token.Tokenizer;
import engcalculator.expression.token.Token;
import engcalculator.interval.Interval;
import engcalculator.interval.workFlow.WorkFlow;
import engcalculator.interval.ListIntervals;
import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.function.embedded.list.LisInternalIntervalBuilder;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.embedded.matrix.MatInternalBuilder;
import engcalculator.interval.workFlow.WorkFlowSubExpression;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class Interpeter {
    private final static boolean DEBUG = false;
    private final static MatInternalBuilder MATRIXBUILDER = new MatInternalBuilder();
    private final static LisInternalIntervalBuilder INTERVALLISTBUILDER = new LisInternalIntervalBuilder();

    private final TokenList tokenizedExpression;
    private boolean keepRunning, repeatingNext;
    private Interval expressionWorkFlow;

    public LinkedList<String> listOfDuplicatedIntervals() {
        LinkedList<Token> partialResult = new LinkedList<Token>();
        LinkedList<Token> result = new LinkedList<Token>();

        for (int i = 0; i < tokenizedExpression.size(); ++i) {
            Token t1 = tokenizedExpression.get(i);

            String ter = t1.getSymbol();
            if (ter != null && ",;".contains(ter)) {//the analysis is performed in sub expressions
                result.addAll(partialResult);
                partialResult.clear();
                continue;
            }

            if (result.contains(t1)) continue; //already found to be duplicated

            ListIntervals il1;
            try {
                il1 = t1.getListInterval();
            } catch (Exception e) {
                continue;
            }
            if (il1 != null && il1.isMadeByRealIntervals() && ! partialResult.contains(t1)) {
                for (int j=i+1; j < tokenizedExpression.size(); ++j) {
                    Token t2 =  tokenizedExpression.get(j);

                    ter = t2.getSymbol();
                    if (ter!= null && ",;".contains(ter))  break; //the analysis is performed in sub expressions

                    ListIntervals il2;
                    try {
                        il2 =t2.getListInterval();
                    } catch (Exception e) {
                        continue;
                    }
                    if (il2 != null && il2.equals (il1)) {
                        partialResult.add(t1);
                        break;
                    }
                }

            }
        }

        result.addAll(partialResult);

        LinkedList<String> stringResult = new LinkedList<String>();
        for (Token t: partialResult)
            stringResult.add(t.getSymbol());
       return stringResult;
    }

    public LinkedList<String> listOfDuplicatedVariables() {
        LinkedList<Token> partialResult = new LinkedList<Token>();
        LinkedList<Token> result = new LinkedList<Token>();

        for (int i = 0; i < tokenizedExpression.size(); ++i) {
            Token t1 = tokenizedExpression.get(i);

            String ter = t1.getSymbol();
            if (ter != null && ",;".contains(ter) ) { //the analysis is performed in sub expressions
                result.addAll(partialResult);
                partialResult.clear();
                continue;
            }

            if (result.contains(t1)) continue; //already found to be duplicated
            
            if (t1.isVariable() && isNotPointInterval(t1) && ! partialResult.contains(t1)) {
                String s = t1.getSymbol();
                if (s == null) continue;
                for (int j=i+1; j < tokenizedExpression.size(); ++j) {
                    Token t2 = tokenizedExpression.get(j);

                    ter = t2.getSymbol();
                    if (ter != null && ",;".contains(ter)) break; //the analysis is performed in sub expressions

                    if (t2.isVariable() && s.equals(t2.getSymbol())) {
                        partialResult.add(t1);
                        break;
                    }
                }
            }
        }

        result.addAll(partialResult);

        LinkedList<String> stringResult = new LinkedList<String>();
        for (Token t: partialResult)
            stringResult.add(t.getSymbol());

       return stringResult;
    }
    
    public LinkedList<String> listOfVariables() {
        LinkedList<String> result = new LinkedList<String>();
        
        for (int i=0; i<tokenizedExpression.size(); ++i) {
            Token t1 = tokenizedExpression.get(i);
            if (t1 != null && t1.isVariable()) result.add(t1.getSymbol());
        }
        
        return result;
    }   

    //Numeric Interval and literal evaluator.
    private ListIntervals interval() throws Exception {
        ListIntervals i = tokenizedExpression.get().getListInterval();
        if (i == null) i = new ListIntervals ();//throw new ParsingException ("It was expected an interval or literal at "+tokenizedExpression.notUnderstoodToString());
        else tokenizedExpression.nextToken();
        return i;
    }
    
    // Curly Brackets solver.
    private ListIntervals curlyBracket() throws Exception {
        if (! tokenizedExpression.isInList()) return new ListIntervals ();

        if( "{".equals(tokenizedExpression.get().getTerminal()) ) {
            ListIntervals answer;

            tokenizedExpression.nextToken();
            answer = computeInfixFunctionAtPriority(0);

            if (! (tokenizedExpression.isInList() )|| ! "}".equals (tokenizedExpression.get().getTerminal() ) ) throw new ParsingException ("The curly {} brackets does not match at "+tokenizedExpression.notUnderstoodToString());
            tokenizedExpression.nextToken();

            return computePrefixFunction(INTERVALLISTBUILDER, answer);//that is required to have compiled function working on square brackets
        } else {
            return interval();
        }
    }    

    // Square Brackets solver.
    private ListIntervals squareBracket() throws Exception {
        if (! tokenizedExpression.isInList()) return new ListIntervals ();

        if( "[".equals(tokenizedExpression.get().getTerminal()) ) {
            ListIntervals answer;

            tokenizedExpression.nextToken();
            answer = computeInfixFunctionAtPriority(0);

            if (! (tokenizedExpression.isInList() )|| ! "]".equals (tokenizedExpression.get().getTerminal() ) ) throw new ParsingException ("The square [] brackets does not match at "+tokenizedExpression.notUnderstoodToString());
            tokenizedExpression.nextToken();

            return computePrefixFunction(MATRIXBUILDER, answer);//that is required to have compiled function working on square brackets
        } else {
            return curlyBracket();
        }
    }

    // Round Brackets solver.
    private ListIntervals roundBracket() throws Exception {
        if (! tokenizedExpression.isInList()) return new ListIntervals ();

        if( "(".equals(tokenizedExpression.get().getTerminal()) ) {
            ListIntervals answer;

            tokenizedExpression.nextToken();
            answer = computeInfixFunctionAtPriority(0);

            if (! (tokenizedExpression.isInList() )|| ! ")".equals (tokenizedExpression.get().getTerminal() ) ) throw new ParsingException ("The round brackets () does not match at "+tokenizedExpression.notUnderstoodToString());
            tokenizedExpression.nextToken();

            return answer;
        } else {
            return squareBracket();
        }
    }

    //Compute prefix function
    private ListIntervals computePrefixFunction () throws Exception {
        ListIntervals answer = null;
        FunctionPrefix fun;

        if(tokenizedExpression.isInList() && (fun = tokenizedExpression.get().getFunctionPrefix()) != null) {
            tokenizedExpression.nextToken();
            answer = computePrefixFunction(fun, computePrefixFunction () );
        } else  {
            answer = roundBracket ();
        }
        return answer;
    }

    //Compute infix function of given priority
    private ListIntervals computeInfixFunctionAtPriority (int priority) throws Exception {
        ListIntervals accumulator;

        if (priority > FunctionInfix.getMaxPriority()) {
            accumulator = computePrefixFunction ();
        } else {
            accumulator = computeInfixFunctionAtPriority(priority+1);
            while( tokenizedExpression.isInList() ) {
                FunctionInfix fun = tokenizedExpression.get().getFunctionInfix();

                if (fun != null && fun.getPriority() == priority) {
                    tokenizedExpression.nextToken();
                    ListIntervals rightOperand;
                    if (FunctionInfix.isLeftAssociative(priority)) rightOperand = computeInfixFunctionAtPriority (priority+1);
                    else rightOperand = computeInfixFunctionAtPriority (priority);
                    accumulator = computeInfixFunction (fun, accumulator, rightOperand);
                } else break;
            }
        }
        return accumulator;
    }

    // expression solver.
    private ListIntervals subExpression () throws Exception {
        boolean firstLoop = true;
        repeatingNext = false;
        ListIntervals answer=null;

        while (tokenizedExpression.isInList() && keepRunning) {
            if (firstLoop) firstLoop = false;
            else retokenize();

            answer = computeInfixFunctionAtPriority (0);

            answer = changeWorkFlow(answer);
        }

        return answer;
    }

    public ListIntervals parse() throws Exception {
        if (DEBUG) {
            System.out.println("Parsing tokenized expression: "+tokenizedExpression.toString());
        }

        tokenizedExpression.reset();
        expressionWorkFlow = null;

        ListIntervals result;
        keepRunning = true;
        try  {
            result = subExpression ();
        } catch (CalculusException ce) {
            Function.throwNewCalculusException (tokenizedExpression.notUnderstoodToString()+ce.getMessage());
            return null;
        }
        if (tokenizedExpression.isInList()) throw new ParsingException ("It was not possible to understand "+tokenizedExpression.get()+" in the ending part of the expression: "+tokenizedExpression.notUnderstoodToString()+" The partial result is: "+result);
        return result;
    }
    
    public Interval getExpressionWorkFlow () {
        return expressionWorkFlow;
    }

    public Interpeter(TokenList tokenizedExpression) {
        this.tokenizedExpression = tokenizedExpression;
    }
    
    public String isALabel () {
        return tokenizedExpression.isALabel();
    }

    public void stopCalculus () {
        keepRunning = false;
    }

    protected ListIntervals computePrefixFunction(FunctionPrefix function, ListIntervals argument) throws Exception {
        return function.compute( argument);
    }

    protected ListIntervals computeInfixFunction(FunctionInfix function, ListIntervals argument1, ListIntervals argument2) throws Exception {
        return function.compute(argument1, argument2);
    }
    
    protected Interval findWorkFlow (ListIntervals answer) throws Exception {
        Interval flowAnswer = null;
        if (answer != null) {
            int indexFound = -1;
            Iterator<Interval> ansIt = answer.iterator();
            int index = 0;
            while (ansIt.hasNext()) {
                Interval ans = ansIt.next();
                if (ans != null && ans.getWorkFlow() != null && ans.getWorkFlow().getNextSubExpression() != WorkFlowSubExpression.NEXT_SUBEX) {
                    if (indexFound > -1) throw new ParsingException ("It was found more than one flow control instruction in the same sub expression.") ;
                    else {
                        indexFound = index;
                        flowAnswer = ans;
                    }
                }
                ++index;
            }
            if (indexFound > -1) answer.remove(indexFound);
        }
        return flowAnswer;
    }

    protected ListIntervals changeWorkFlow (ListIntervals answer) throws Exception {
        Interval flowAnswer = findWorkFlow(answer);
        WorkFlow wf;
        if (flowAnswer != null && (wf=flowAnswer.getWorkFlow()) != null &&  wf.getNextSubExpression() == WorkFlowSubExpression.RETURN_THIS_SUBEX) {
            tokenizedExpression.goToEnd();
            return answer;
        }

        if (tokenizedExpression.isInList())
            if (";".equals(tokenizedExpression.get().getTerminal())) answer = null; //by default after a ';' there is not any answer
            else throw new ParsingException ("It was not possible to understand "+tokenizedExpression.get()+" in the ending part of the expression: "+tokenizedExpression.notUnderstoodToString()+" The partial result is: "+answer); //if I am in the list but not at ';' there is something not understood

        if (repeatingNext) {
            tokenizedExpression.previousSubExpression();
            repeatingNext = false;
        } else if (flowAnswer != null && (wf=flowAnswer.getWorkFlow()) != null) {
            switch (wf.getNextSubExpression()) {
                case NEXT_SUBEX_THEN_THIS_SUBEX:
                    repeatingNext = true;
                    tokenizedExpression.nextToken();
                    break;
                case NEXT_SUBEX:
                    tokenizedExpression.nextToken();
                    break;
                case SKIP_NEXT_SUBEX:
                    tokenizedExpression.nextToken();
                    tokenizedExpression.nextSubExpression();
                    break;
                case PREVIOUS_SUBEX:
                    tokenizedExpression.previousSubExpression();
                    break;
                case THIS_SUBEX:
                    tokenizedExpression.thisSubExpression();
                    break;
                case STOP_SUBEX:
                    tokenizedExpression.goToEnd();
                    break;
                case FLOW_EX:
                    expressionWorkFlow = flowAnswer;
                    break;
                default:
                    throw new RuntimeException ("Internal error trying to find the proper control path in Parser.subExpression.");
            }
        } else tokenizedExpression.nextToken();

        return answer;
    }

    private boolean isNotPointInterval(Token t1) {
        ListIntervals val;
        try {
            val = t1.getListInterval();
        } catch (Exception e) {
            return true;
        }
        if (val != null)
            for (Interval i : val)
                if (! i.isIntervalPoint()) return true;
        return false;
    }

    void retokenize() {
        Tokenizer.retokenize(tokenizedExpression);
    }

}
