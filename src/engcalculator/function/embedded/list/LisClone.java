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

import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisClone extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd(new DomainIntervalPoint(), new DomainInteger(), new DomainIntervalPositive()), new DomainInterval());

    private final static String HELP = "... (number of clones, interval) returns (interval, interval, interval, ..).\nIt generates a new list containing the given number of the input interval.";
    private final static String[] EXAMPLE = {"3, 4_1+2*I","4,{1,2,3}"};
    private final static String[] RESULT = {"(4_1+2*I, 4_1+2*I, 4_1+2*I)","{1,2,3},{1,2,3},{1,2,3},{1,2,3}"};

    public LisClone() {        
        super("list", "Clone", (byte) 2, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int s = (int) input.getFirst().getValue();

        ListIntervals result = new ListIntervals ();

        for (int i = 0; i<s; ++i) {
            result.add(input.get(1));
        }

        return result;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }     

}
