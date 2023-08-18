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
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvRight extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (a_b) returns b.\nIt returns the right extreme of an interval.";
    private final static String[] EXAMPLE = {"0_10, 5_12"};
    private final static String[] RESULT = {"10, 12"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    public ItvRight() {
        super ("interval", "Right", DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        return new IntervalPoint(input.getRight());
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return input.getMeasurementUnit();
    }      

}
