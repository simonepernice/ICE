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
package engcalculator.function.embedded.list;

import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisCombination extends FunctionPrefix {

    private final static DomainList DOMAIN = new DomainList(new DomainIntervalList());
    private final static String HELP = "... ({a1, a2, ..}, {b1, b2, ..}, {c1, c2, ..}) returns all the possible combinations (a1, b1, c1, a1, b1, c2, ..).\nIt returns  give a list of sub-list it provides a list containing all the possible combination (taking one element from each sub-list) of the given sub-lists.";
    private final static String[] EXAMPLE = {"{1,2},{3,4},{5,6}"};
    private final static String[] RESULT = {"1,3,5,1,3,6,1,4,5,1,4,6,2,3,5,2,3,6,2,4,5,2,4,6"};

    public LisCombination() {
        super("list", "Combination", (byte) -1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals result = new ListIntervals();
        final int s = input.size();
        int[] len = new int[s];
        int[] i = new int[s];

        int j;
        for (j = 0; j < s; ++j) {
            len[j] = input.get(j).getListIntervals().size();
        }

        do {
            for (int k = 0; k < s; ++k) {
                result.append(input.get(k).getListIntervals().get(i[k]));
            }

            j = s;
            do {
                if (j < s) {
                    i[j] = 0;
                }
                --j;
                if (j < 0) {
                    break;
                }
                i[j]++;
            } while (i[j] >= len[j]);

        } while (j >= 0);

        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }      
}
