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

package engcalculator.function.embedded.program;

import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ProTimeMs extends FunctionPrefix {
    private final static long START_TIME = System.currentTimeMillis();
    private final static String HELP = "... () returns the current time in milliseconds.\nNote that while the unit of time of the return value is a millisecond, the granularity of the value depends on the underlying operating system and may be large. For example, many operating systems measure time in units of tens of milliseconds. This function may be useful for profiling: to measure the execution time of other functions.";	
    private final static String[] EXAMPLE = {"t=programTimeMs();programTimeMs()-t>=0"};
    private final static String[] RESULT = {"1"};
    
    public ProTimeMs() {
        super("program", "TimeMs", (byte) 0, null, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals ( new IntervalPoint (System.currentTimeMillis()-START_TIME));
    }
}
