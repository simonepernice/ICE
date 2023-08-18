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

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvExtremes extends FunctionPrefix {
    private final static String HELP = "... (A_B) return (A, B).\nIt basically returns a list with the first and second end point of an interval.";    
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());
    private final static String[] EXAMPLE = {"1%4"};
    private final static String[] RESULT = {"(0.960, 1.04)"};


    public ItvExtremes() {
        super ("interval", "Extremes", (byte) 1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        Interval a = input.getFirst();
        ListIntervals result = new ListIntervals();
        result.append (new IntervalPoint(a.getLeft()));
        result.append (new IntervalPoint(a.getRight()));
        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        List<MeasurementUnit> mu = new LinkedList<MeasurementUnit> ();
        mu.add(input.getFirst().getMeasurementUnit());
        return mu;
    }    

}
