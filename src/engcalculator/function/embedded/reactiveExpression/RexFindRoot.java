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
public final class RexFindRoot extends FunctionPrefixByExpression {    
    private final static String[] VARS = {"out", "in", "range", "yval"};
    private final static String[] GROUPS = {"equation", "define"};
    private static final String EXP = "equationFindRoot(reactiveExpressionDefineFunction(defineLambdaGetName(0) , {in}, out),range,yval)";
    
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReactiveExpression(), new DomainFunctionVariable (), new DomainIntervalReal (), new DomainIntervalReal ());

    private final static String HELP = "... ($re, $variable, interval, point) in order to set $re to the same value of point, changes variable in the given interval.\nIt is the same of equationFindRoot on a function. Consider reactive expressions are slower than functions.";
    private final static String[] EXAMPLE = {"a1=5,a2==='a1+4';reactiveExpressionFindRoot($a2, $a1, 0_10, 10)"};
    private final static String[] RESULT = {"6 "};
    
    public RexFindRoot () {
        super("reactiveExpression", "FindRoot", (byte) 4, DOMAIN, false, HELP, EXAMPLE, RESULT, EXP, true, VARS, GROUPS);
    }

    
}
