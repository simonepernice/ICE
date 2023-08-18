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

package engcalculator.function.infix;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionInfixDualInputSingleOutput extends FunctionInfix {

    public FunctionInfixDualInputSingleOutput(java.lang.String group, String name, DomainList domainLeft, DomainList domainRight, boolean expandsScalarInput, boolean locked, String help, String[] examplesLeft, String[] examplesRight, String[] results) {
        super(group, name, (byte) 1, (byte) 1, domainLeft, domainRight, expandsScalarInput, locked, help, examplesLeft, examplesRight, results);
    }

    @Override
    public final ListIntervals _compute (ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        return new ListIntervals (_computeIntervals(leftSide.getFirst(), rightSide.getFirst()));
    }
    
    public abstract Interval _computeIntervals (Interval leftSide, Interval rightSide) throws Exception;    

    @Override
    public final List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        Iterator<Interval> il = leftSide.iterator();
        Iterator<Interval> ir = rightSide.iterator();
        LinkedList<MeasurementUnit> result = new LinkedList<MeasurementUnit> ();
        while (il.hasNext()) {
            result.add(_computeMeasurementUnitIntervals(il.next(), ir.next()));
        }
        return result;
    }
    
    public abstract  MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws Exception;    
            
}
