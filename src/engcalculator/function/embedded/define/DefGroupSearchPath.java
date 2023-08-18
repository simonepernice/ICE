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

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.function.Function;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefGroupSearchPath extends FunctionPrefix {

    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... ('group1', 'group2', 'group3', ..) will automatically search prefix and variable on all the given groups.\nFor instance if the seach group path is 'define' the call 'function (5)' will look for function and if not found for definefunction. If the input is '' the current search group is returned. If the input is 'CLEARGROUPNAME' the search pat is cleared.";
    private final static String[] EXAMPLE = {"defineGroupSearchPath('information');abt=About();defineGroupSearchPath('CLEARGROUPNAME');abt"};
    private final static String[] RESULT = {"informationAbout()"};

    public DefGroupSearchPath() {
        super("define", "GroupSearchPath", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }    

    @Override
    @SuppressWarnings("empty-statement")
    public ListIntervals _compute(ListIntervals input) throws Exception {
        if (input.size() == 1 && input.getFirst().getName().length() == 0) ;//do nothing
        else if (input.size() == 1  &&  input.getFirst().getName().equals("CLEARGROUPNAME")) Function.getGroupSearchPath().getGroupPath().clear();
        else Function.getGroupSearchPath().setGroupPath(input);
        return Function.getGroupSearchPath().getGroupPathAsListIntervals();
    }
}
