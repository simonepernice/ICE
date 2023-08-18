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

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefFunctionCompiled extends DefFunctionVariable {
    private final static String HELP = "($f, $x1, .., $xn) ... 'expression' to define the following compiled expression f(x1, .., xn) = expression(x1, .., xn).\nIt is used to create compiled functions which are at least 10% faster then interpreted functions defined with '='. Ex.: ($f,$x,$y)='x+y' sets the function f(x,y) = x+y, ('f','x'='x,2*x' set the function f which gets the parameter x and returns a list of two elements (x,2*x). The compiled functions do not support control flow neither ';'. The compiled function parameters type and size are not checked at run time, which may rise exceptions. Compiled function supports an optimization, which to work requires the variable to be at the end of the expression (i.e.: ('f','x')...'1+2+3+4+5+6+7+8+9*x' is 25% faster than its interpreted function, while ('f','x')...'9*x+1+2+3+4+5+6+7+8' is just 15% faster). Please note when a function is defined all other variables involved (with exception of parameters) must be known because their value is stored (i.e.: ('f','x')='2*a*x', if you later change the variable 'a' value, that will not affect f(x)). '...'  behaves exactly like '=' for variable definition.";
    
    private final static String[] EXAMPLESLEFT = {"($n,$x,$y)_='2*x+5*y';n(5,6)"};
    private final static String[] EXAMPLESRIGHT = {""};
    private final static String[] RESULTS = {"40"};

    public DefFunctionCompiled() {
        super ("_=",HELP, EXAMPLESLEFT, EXAMPLESRIGHT, RESULTS);
    }

    @Override
    public boolean isCompilatedFunction() {
        return true;
    }

}
