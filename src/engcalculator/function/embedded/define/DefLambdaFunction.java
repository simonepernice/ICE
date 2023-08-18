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
package engcalculator.function.embedded.define;

import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class DefLambdaFunction extends FunctionPrefix {
    private final static String[] EXAMPLES = {";plotStandard(defineLambdaFunction('2*$x+2'), 10, 1_10)",";defineLambdaFunction('2*$x+2', 1 .. 4)",";plotStandard(defineLambdaFunction({listShuffle({1 .. 10}, {4 .. 22 .. 2}) # 2}), 10, 1_10)",";defineLambdaFunction({listShuffle({1 .. 10}, {4 .. 22 .. 2}) # 2},1,10)","($g, $x, $y)= 'x + 2* y + 5';plotStandard(defineLambdaFunction({2, $x, $g}), 4, 1_4)"};
    private final static String[] RESULTS = {"listShuffle({1 .. 10}, {4 .. 22 .. 2}) # 2", "4 .. 10 .. 2", "listShuffle({1 .. 10}, {4 .. 22 .. 2}) # 2","4,22","listShuffle({1 .. 4}, {9 .. 15 .. 2}) # 2"};
    private final static String HELP = "... ('expression of $a, $b, .., $z) creates an unnamed function as expression(a, b, .., z).\nLambda functions are temporary functions which are automatically deleted at the end of the current expression execution. They are useful when it is necessary to create a function for some purpose but it is not required to save the function itself in the enviroment. The typical example is to plot a function. ... has a syntax designed to create quickly a function. If ... input is a literal, it creates a new function extrapolatin all the variables as the identifier beginning with $ ordered in alphabetical order: ...('$b+2*$a') is like (defineLambdaGetName, $a, $b)='b+2*a'. If ... input is a {sub-list} cointaining a matrix it defines an interpolation lambda function, i.e.: ... ({[[1,2],[3,4]]}) is equivalent to getLambdaName := [[1,2],[3,4]]. If ... input is a { sub-list }, it defines a sub argument input function, i.e. ... ({2,$x,$f}) is equivalent to (getLambdaName, 2, $x) #= $f. For more complex definitions it is required to use getLambdaName and the proper function. As default ... returns the temporary function name to be used by other functions. However if a list of intervals follows, the functions just create is applied to the list and the result is returned.";
    
    private final static DefFunctionVariable FUNCTION_VARIABLE_DEFINITION = new DefFunctionVariable ();
    private final static DefFunctionSubArgument FUNCTION_SUB_ARGUMENT_DEFINITION = new DefFunctionSubArgument();
    private final static DefFunctionInterpolation FUNCTION_INTERPOLATION_DEFINITION = new DefFunctionInterpolation();

    public DefLambdaFunction() {
        super("define", "LambdaFunction", (byte) -1, new DomainList(), false, true, HELP, EXAMPLES, RESULTS);
    }    
    
    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        Interval first = input.getFirst();
        ListIntervals result = null;
        if (first.isIntervalList() && first.getListIntervals().isMatrix()) {
            result = FUNCTION_INTERPOLATION_DEFINITION.compute(DefLambdaGetName.getNextLambdaFunctionName(),first.getListIntervals());
        } else if (first.isIntervalList()) {
            final int sm1 = first.getListIntervals().size()-1;
            
            ListIntervals ls = DefLambdaGetName.getNextLambdaFunctionName();
            ls.addAll(first.getListIntervals().subList(0, sm1));
            
            ListIntervals rs = first.getListIntervals().subList(sm1, sm1+1);
            result = FUNCTION_SUB_ARGUMENT_DEFINITION.compute(ls, rs);
        } else if (first.isIntervalLiteral()) {
            String definition = input.getFirst().getName();
            
            LinkedList<String> variables = new LinkedList<String>();
            
            StringBuilder var = new StringBuilder ();
            boolean inVar = false;
            for (char c : definition.toCharArray()) {
                if (! inVar && c != '$') continue;
                if (c == '$') {
                    inVar = true;
                    continue;
                }
                if (Character.isLetterOrDigit(c)) {
                    var.append(c);
                    continue;
                }
                if (! variables.contains(var.toString())) variables.add(var.toString());
                var.setLength(0);
                inVar = false;
            }
            if (var.length() > 0) if (! variables.contains(var.toString())) variables.add(var.toString());
            
            if (variables.size() == 0) throwNewCalculusException ("It was not defined any variable in the input function definition: '"+definition+"', the variable should follow the symbol $");
            
            Collections.sort(variables);
            
            ListIntervals left = DefLambdaGetName.getNextLambdaFunctionName();
            for (String v : variables)
                left.add(new IntervalLiteral(v));
            
            ListIntervals right = new ListIntervals(new IntervalLiteral(definition.replace("$", "")));
            
            result =  FUNCTION_VARIABLE_DEFINITION.compute (left, right);
        } else {
            throwNewCalculusException ("It was not possible to understand the input: "+input);
        }
        final int s = input.size();
        if (s == 1) return result;
        return FunctionPrefix.getFunction(result.getFirst().getName()).compute(input.subList(1, s));
    }
    
}

