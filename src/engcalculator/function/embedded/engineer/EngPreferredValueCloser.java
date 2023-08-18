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

import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.embedded.interval.ItvByRelativeToleranceBuilder;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngPreferredValueCloser extends FunctionPrefix {
    private final static String HELP = "... (value%tolerance) returns the closer couple of values between the provided one (and tolerance).\nIt returns the list of the two closer preferred values from standard IEC 60063.";
    private final static String[] EXAMPLE = {"40%25"};
    private final static String[] RESULT = {"33%20, 47%20"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    private final static ItvByRelativeToleranceBuilder TOLERANCE = new ItvByRelativeToleranceBuilder();

    public EngPreferredValueCloser() {
        super ("engineer", "PreferredValueCloser", (byte) 1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute (ListIntervals interList) throws Exception {
        Interval inter = interList.getFirst();      
        return new ListIntervals().append(EngPreferred.getFloorPreferred(inter)).append(EngPreferred.getCeilingPreferred(inter));

    }
    
        @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        LinkedList<MeasurementUnit>  result = new LinkedList<MeasurementUnit> ();
        result.add(input.getMeasurementUnit());
        result.add(input.getMeasurementUnit());
        return result;
    }

}
