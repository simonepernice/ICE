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
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisIncremental extends FunctionPrefix {
    private final static AriSum SUM = new AriSum ();
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalPoint(), new DomainInteger (), new DomainIntervalPositive()), new DomainIntervalReal(), new DomainIntervalReal());

    private final static String HELP = "... (noi, increment, start) generates a list of noi intervals from start with given increment.";
    private final static String[] EXAMPLE = {"4, 2, 10"};
    private final static String[] RESULT = {"(10, 12, 14, 16)"};

    public LisIncremental() {
        super("list", "Incremental", (byte) 3, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int s = (int) input.getFirst().getValue();

        final ListIntervals increment = input.subList(1, 2);
        ListIntervals result = new ListIntervals ();
        ListIntervals start = input.subList(2, 3);

        for (int i = 0; i<s; ++i) {
            result.add(start.getFirst());
            start = SUM._compute(start, increment);
        }
        return result;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        List<MeasurementUnit> mul = new LinkedList<MeasurementUnit> ();
        mul.add(input.getMeasurementUnit());
        return mul;
    }
    
    

}
