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

import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefSummaryUserDefinition extends FunctionPrefix {
    private final static String HELP = "... () returns the summary of all functions (prefix and inifx) and variables defined by the user.\nTo choice if the function is defined by the user it check it is not locked and it doen not begin by 'answer'.";
    private final static String[] EXAMPLE = null;
    private final static String[] RESULT = null; 
    
    public DefSummaryUserDefinition() {
        super("define", "SummaryUserDefinition", (byte) 0, null, false, true, HELP, EXAMPLE, RESULT);
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        StringBuilder result = new StringBuilder();
        result.append('\n');
        
        for (FunctionPrefix f : FunctionPrefix.getPrefixFunctions()) 
            if (! f.isLocked()) {
                result.append(f.getSymbol()).append(" => ").append(f.getBasicHelpMessage().replace('\n', '.')).append('\n');
            }
        
        for (FunctionInfix f : FunctionInfix.getInfixFunctions())
            if (! f.isLocked()) {
                result.append(f.getSymbol()).append(" => ").append(f.getBasicHelpMessage().replace('\n', '.')).append('\n');
            }

        for (FunctionVariable v : FunctionVariable.getVariables())
            if (! v.isLocked() && !v.getSymbol().startsWith("answer")) {
                result.append(v.getSymbol()).append(" => ").append(v.getValue().toString()).append('\n');
            }

        return new ListIntervals(new IntervalLiteral(result.toString()));
    }
}
