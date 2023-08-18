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

package engcalculator.function.embedded.plot;

import engcalculator.domain.*;
import engcalculator.function.prefix.FunctionPrefixByExpression;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PltBodePhase extends FunctionPrefixByExpression {

    private static final String EXP = "plotStandardLogarithmic( defineLambdaFunction( literalCharJoin('complexArg(', f, ' $x )/convertDegreeToRadiant ' ) ), n, i)";
    private static final String HELP = "... ($f, n, i) computes plotStandardLogarithmic(defineLambdaFunction('complexArg (f ($x) ) '), n, i) returning the plottable matrix.";
    private final static String[] VARS = {"f", "n", "i"};
    private final static String[] GROUPS = {"literal","convert"};

    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix (1), new DomainIntervalPoint(), new DomainIntervalReal());
    
    private final static String[] EXAMPLE = {"$sin, 5, 1_1k"};    
    private final static String[] RESULT = {"plotStandardLogarithmic(defineLambdaFunction('complexArg( sin $x)/convertDegreeToRadiant'),5, 1_1k)"};
    
    
    public PltBodePhase () {
        super("plot", "BodePhase", (byte) 3, DOMAIN, false, HELP, EXAMPLE, RESULT, EXP, true, VARS, GROUPS);
    }
    
}
