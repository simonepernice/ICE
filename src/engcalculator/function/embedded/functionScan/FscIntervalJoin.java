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
package engcalculator.function.embedded.functionScan;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FscIntervalJoin extends FunctionPrefix {

    private final static String HELP = "... (list) returns the smallest inteval containing all the given inputs.\nIt is useful if calculations were executed with interval splitted to go back to interval form.";
    private final static String[] EXAMPLE = {"1, 4, 2_7, 3, 5, 6","1,3,4,5","r1 = 10k%10, r1s  = functionScanIntervalSplitRandom r1, r2 = 20k%5, r2s = functionScanIntervalSplitRandom r2; r1/(r1+r2) >> functionScanIntervalJoin (r1s/(r1s+r2s)), 1/(1+r2/r1) >> functionScanIntervalJoin (r1s/(r1s+r2s)), r1/(r1+r2) >> 1/(1+r2/r1)"};
    private final static String[] RESULT = {"1_7","1_5", "true, true, true"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalReal());

    public FscIntervalJoin() {
        super("functionScan", "IntervalJoin", (byte) -1, INTERVALDOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final Interval in = input.getFirst();
        double min = in.getLeft();
        double max = in.getRight();
        
        for (Interval i : input) {
            if (i.getLeft() < min) min = i.getLeft();
            if (i.getRight() > max) max = i.getRight();
        }
        
        return new ListIntervals(new IntervalReal(min, max));
                
    }
}
