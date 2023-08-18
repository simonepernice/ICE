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

package engcalculator.function.prefix;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionPrefixSingleInputSingleOutput extends FunctionPrefix {
    public FunctionPrefixSingleInputSingleOutput(java.lang.String group, String name, DomainList domain, boolean expandsToLists, boolean locked, String help, String[] examples, String[] results) {
        super(group, name, (byte) 1, domain, expandsToLists, locked, help, examples, results);
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals (_computeIntervals(input.getFirst()));
    }

    public abstract Interval _computeIntervals (Interval input) throws Exception;

    @Override
    public final List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws Exception {
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit>  ();
        for (Interval i : input)
            res.add(_computeMeasurementUnitIntervals(i));
        return res;
    }
    
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        if (! input.getMeasurementUnit().equals(MeasurementUnit.PURE)) throwNewMeasurementUnitException("It was expected an argument without measurement unit");
        return MeasurementUnit.PURE;        
    }
    
}
