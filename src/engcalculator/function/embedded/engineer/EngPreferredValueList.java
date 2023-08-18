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

package engcalculator.function.embedded.engineer;

import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngPreferredValueList extends FunctionPrefix {
    private final static String HELP = "... (beginInterval_endInterva, tolerance) returns the preferred valued from begin to end intervals with the given tolerance.\nThe input tolerances should be the same. The list of intervals begin with the preferred value before begin and ends with the preferred value after the end. If the order begin, end is the opposit the list will be provided in the reverse order.";
    private final static String[] EXAMPLE = {"10k_50k,20"};
    private final static String[] RESULT = {"(10k,15k,22k,33k,47k,68k)%20"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal(), new DomainIntervalPoint());

    public EngPreferredValueList() {
        super ("engineer", "PreferredValueList", (byte) 2, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws Exception {
        return input.getMeasurementUnitList();
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final double begin = input.getFirst().getLeft();
        final double end = input.getFirst().getRight();
        final double tol = input.getLast().getValue();

        EngPreferredIteratorInterval i = new EngPreferredIteratorInterval(begin, end, tol);
        ListIntervals result = new ListIntervals ();
        while (i.hasNext()) result.append(i.next());
        return result;
    }
    
}
