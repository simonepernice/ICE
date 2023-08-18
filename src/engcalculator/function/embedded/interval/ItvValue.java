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

import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvValue extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (a%b) return a.\nIt computes the value on every interval of a list of intervals.";
    private final static String[] EXAMPLE = {"1%10, 5.10%2"};
    private final static String[] RESULT = {"1, 5.10"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    public ItvValue() {
        super ("interval", "Value", DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        return new IntervalPoint(input.getValue());
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return input.getMeasurementUnit();
    }    

}
