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

package engcalculator.function.embedded.interval;

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvValueTolerance extends FunctionPrefix {
    private final static String HELP = "... (a%b) returns (a, b).\nIt computes the value and tolerance on every interval of a list of intervals.";
    private final static String[] EXAMPLE = {"0_10, 5_10"};
    private final static String[] RESULT = {"5, 100, 7.5, 33.333"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    public ItvValueTolerance() {
        super ("interval", "ValueTolerance", (byte) 1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        Interval a = input.getFirst();
        ListIntervals result = new ListIntervals(new IntervalPoint(a.getValue()));
        result.add(new IntervalPoint(a.getTolerance()));
        return result;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        List<MeasurementUnit> mu = new LinkedList<MeasurementUnit> ();
        mu.add(input.getMeasurementUnit());
        mu.add(MeasurementUnit.PURE);
        return mu;
    }
       
}
