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

package engcalculator.reactiveExpression;


import engcalculator.expression.Expression;
import engcalculator.function.Function;
import engcalculator.interval.ListIntervals;
import engcalculator.function.variable.FunctionVariable;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ReactiveExpression {
    private final static ReactiveExpressionStorage REACTIVE_EXPRESSION_STORAGE = new ReactiveExpressionStorage();
    
    public static void addChangedVariable (String v) {
        REACTIVE_EXPRESSION_STORAGE.addChangedVariable(v);
    }
    
    public static ListIntervals addReactiveExpression (ReactiveExpression re) throws Exception {
        return REACTIVE_EXPRESSION_STORAGE.addReactiveExpression(re);
    }
    
    public static boolean isUsedAsInput (String var) {
        return REACTIVE_EXPRESSION_STORAGE.isUsedAsInput(var);
    }    
    
    public static boolean delReactiveExpression (String output) {
        return REACTIVE_EXPRESSION_STORAGE.delReactiveExpression(output);
    }    
    
    public static String computeReactiveExpressions (boolean all) throws Exception{   
        return REACTIVE_EXPRESSION_STORAGE.computeReactiveExpressions(all);
    }    

    public static LinkedList<ReactiveExpression> getReactiveExpressions() {
        return REACTIVE_EXPRESSION_STORAGE.getReactiveExpressions();
    }

    public static ReactiveExpression getReactiveExpression(String name) {
        return REACTIVE_EXPRESSION_STORAGE.getReactiveExpression(name);
    }
    
    public static String getReactiveExpressionSummary () {
        return REACTIVE_EXPRESSION_STORAGE.getReactiveExpressionSummary();
    }
    
    public static void initialize() {
        REACTIVE_EXPRESSION_STORAGE.initialize();
    }
    
    private final Expression expression;
    private final VariableSet inputs;
    private final String output;
    private final String body;
    
    public ReactiveExpression(String output, String body) throws Exception {    
        this (output, body, null);
    }

    public ReactiveExpression(String output, String body, LinkedList<String> extraVars) throws Exception {
        expression = new Expression(body, null);
        expression.evaluate(null); //looking for exception in the definition
        LinkedList<String> inputVars = expression.listOfVariables();
        if (extraVars != null) inputVars.addAll(extraVars);
        if (inputVars.size() == 0) Function.throwNewCalculusException ("At least one input variable should be declared in the recurrent expression "+body+" if it not the case use assignment '=' operator instead.");
        inputs = new VariableSet(inputVars);
        if (inputs.contains(output)) Function.throwNewCalculusException ("The reactive expression cannot be added because it is recursive: "+body);
        this.output = output;
        this.body = body;
    }

    public String getOutput() {
        return output;
    }

    VariableSet getInputs() {
        return inputs;
    }
    
    FunctionVariable compute () throws Exception {
        FunctionVariable var = new FunctionVariable (output, expression.evaluate(null), false, "Reactive Expression: "+toString());

        if (! FunctionVariable.addFunction(var)) Function.throwNewCalculusException ("There is a variable already defined with the same name of the defined reactive exression: "+output);
        return var;
    }
    
    @Override
    public String toString () {
        return new StringBuilder (output).append(" === '").append(body).append('\'').toString();
    }
}
