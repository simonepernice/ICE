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

import engcalculator.function.Function;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import java.util.List;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefShowAllFunctions extends FunctionPrefix {
    private final static String HELP = "... () returns the list of all functions (prefix and inifx) and variables defined.";
    private final static String[] EXAMPLE = {";defineFunctionShowAll ? (literalRegularExpressionMatch('optimiz.*',defineFunctionShowAll ))"};
    private final static String[] RESULT = {"'optimizationUnconditionalMinimum'"};
    
    public DefShowAllFunctions() {
        super("define", "FunctionShowAll", (byte) 0, null, false, true, HELP, EXAMPLE, RESULT);
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals result = new ListIntervals();
        List<String> funcs = Function.getFunctionSymbols(true);
        for (String f : funcs)
            result.add(new IntervalLiteral(f));
        return result;
    }
}
