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

package engcalculator.function.embedded.setList;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SLsExtract extends FunctionPrefix {
    private final static String HELP = "... (list) returns the sub list which is a proper set.\nBasically a proper list set does not have any duplicate, therefore it removes them.";
    private final static String[] EXAMPLE = {"1, 2, 3, 4, 5, 6, 4, 5"};
    private final static String[] RESULT = {"1, 2, 3, 6, 4, 5"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalComplex());

    public SLsExtract() {
        super ("setList", "Extract", (byte) -1, INTERVALDOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int sm1 = input.size()-1;
        ListIntervals result = new ListIntervals ();
        if (sm1 < 0) return result;
        
        for (int i=0; i<sm1; ++i) {
            Iterator<Interval> ii = input.iterator();
            int j = 0;
            while (j < i) {
                ++j;
                ii.next();
            }
            Interval p = ii.next();
            boolean add = true;
            while (ii.hasNext()) {
                if (p.equals(ii.next())) {
                    add = false;
                    break;
                }
            }
            if (add) result.append(p);
        }
        
        result.add(input.getLast()); //the loop does not go through the last (so its not added) moreover it cannot be a duplicate (because is always tested with previous elements which would not be included)
        
        return result;
    }
}
