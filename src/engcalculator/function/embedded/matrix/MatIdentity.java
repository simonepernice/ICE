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

import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatIdentity extends FunctionPrefix {
    private final static String HELP = "... (size) builds an identity matrix of required size.";
    private final static String[] EXAMPLE = {"2"};
    private final static String[] RESULT = {"(1,0,0,1)#2"};
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalPoint(), new DomainInteger()));

    public MatIdentity() {
        super("matrix", "Identity", (byte) 1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int cs = (int) input.getFirst().getValue();
        ListIntervalsMatrix result = new ListIntervalsMatrix (cs);
        for (int c=0; c<cs; ++c)
            for (int r=0; r<cs; ++r)
                if (r == c) result.add(new IntervalPoint(1));
                else result.add(new IntervalPoint(0));
        return result;

    }

}
