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

package engcalculator.function.embedded.matrix;

import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatDiagonal extends FunctionPrefix {
    private final static String HELP = "... (list) builds a diagonal matrix with given list in its diagonal.";
    private final static String[] EXAMPLE = {"1,2"};
    private final static String[] RESULT = {"(1,0,0,2)#2"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public MatDiagonal() {
        super("matrix", "Diagonal", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int cs = (int) input.size();
        ListIntervalsMatrix result = new ListIntervalsMatrix (cs);
        int i = 0;
        for (int c=0; c<cs; ++c)
            for (int r=0; r<cs; ++r)
                if (r == c) result.add(input.get(i++));
                else result.add(new IntervalPoint(0));
        return result;

    }

}
