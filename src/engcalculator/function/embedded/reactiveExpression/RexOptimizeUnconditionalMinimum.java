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

package engcalculator.function.embedded.reactiveExpression;

import engcalculator.domain.*;
import engcalculator.function.prefix.FunctionPrefixByExpression;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class RexOptimizeUnconditionalMinimum extends FunctionPrefixByExpression {    
    private final static String[] VARS = {"out", "in"};
    private final static String[] GROUPS = {"optimization", "literal"};
    private static final String EXP = "optimizationUnconditionalMinimum(reactiveExpressionDefineFunction(defineLambdaGetName (0), in, out), literalToExpression(listFlatten(in)))";
    
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReactiveExpression(), new DomainIntervalList(new DomainFunctionVariable ()));

    private final static String HELP = "... ($outputReactiveExpression, {$inputVar1, .., $inputVarn}) find the $outputReactiveExpression minimum changing the $inputVar1 to $inputVarn.\nAt beginning point it takes the current value of inputVar1, .., inputVarn. It is the same of optimizationUnconditionalMinimum executed on a function. Consider reactive expressions are slower than functions.";
    private final static String[] EXAMPLE = {"aj3 = 5 , aj4 = 11 , $aj5 === 'abs((aj3 - 4)*( aj4 - 10))'; reactiveExpressionOptimizeUnconditionalMinimum($aj5, {$aj3, $aj4})"};
    private final static String[] RESULT = {"4, 10"};
    
    public RexOptimizeUnconditionalMinimum () {
        super("reactiveExpression", "OptimizeUnconditionalMinimum", (byte) 2, DOMAIN, false, HELP, EXAMPLE, RESULT, EXP, true, VARS, GROUPS);
    }

    
}
