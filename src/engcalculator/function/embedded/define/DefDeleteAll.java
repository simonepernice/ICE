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
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefDeleteAll extends FunctionPrefix {
    private final static String HELP = "... would delete all the functions and variables without a lock.\nUsed in the middle of a session would delete all the function defined by the users (which are not locked by default)..";
    private final static String[] EXAMPLES = {"a=5,b=2;defineDeleteAll;a,b"};
    private final static String[] RESULTS = {"'a','b'"};
    
    public DefDeleteAll () {
        super("define", "DeleteAll", (byte) 0, null, false, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals(new IntervalLiteral(Function.deleteAll()));
    }

}
