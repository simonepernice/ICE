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

package engcalculator.function.embedded.statistic;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.embedded.setList.SLsSort;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalReal;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaPercentile extends FunctionPrefix {
    private final static SLsSort SORT_SET_LIST = new SLsSort();
    private final static StaAverage AVERAGE = new StaAverage();
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    private final static String HELP = "... (list) returns the list with the precentile (0, 25th, 50th or median, 75th, 100th) of the given list of values.";
    private final static String[] EXAMPLE = {"5,4,3,1","5,4,3,1,2"};
    private final static String[] RESULT = {"1,2,3.5,4.5,5","1,2,3,4,5"};


    public StaPercentile() {
        super ("statistic", "Percentile", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals sortedInput = SORT_SET_LIST._compute(input);
        ListIntervals result = new ListIntervals();
        final int s = sortedInput.size()-1;
        for (int p = 0; p <= 4; ++p) {
            int i;
            boolean integerIndex;
            if (p == 2) {
                i = s / 2;
                integerIndex = s % 2 == 0;
            } else {
                i = (s * p) / 4;
                integerIndex = (s * p) % 4 == 0;
            }
            if (integerIndex) result.add(new IntervalReal(sortedInput.get(i)));
            else result.add(AVERAGE._compute(sortedInput.subList(i, i+2)).getFirst()); 
        }
    
        return result;
    }
}
