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

import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisInteger extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    private final static String HELP = "... (A_B) build a list of integer number between the floor of interval min (A) and the floor of interval max (B).";
    private final static String[] EXAMPLE = {"2_5","4_3"};
    private final static String[] RESULT = {"2, 3, 4, 5","4,3"};

    public LisInteger() {
        super("list", "Integer", (byte) 1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return _computeIntervals (input.getFirst());
    }
    
    public ListIntervals _computeIntervals (Interval input) throws Exception {
        int begin = (int) Math.floor(input.getLeft());
        final int end = (int) Math.floor(input.getRight());

        ListIntervals result = new ListIntervals();
            if (begin <= end)
                for (; begin <= end; ++begin)
                    result.add(new IntervalPoint (begin));
            else
                for (; end <= begin; --begin)
                    result.add(new IntervalPoint (begin));

        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws MeasurementUnitException {      
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(input.getMeasurementUnit());
        return res;        
    }      

}
