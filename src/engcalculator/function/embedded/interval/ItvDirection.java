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
public final class ItvDirection extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (A) returns -1, if A is an improper directed interval; 1, if A is proper or degenerate and 0, if A involves NaN.";    
    private final static String[] EXAMPLE = {"-10_10","10_-5", "10_10"};
    private final static String[] RESULT = {"1", "-1", "1"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    public ItvDirection() {
        super("interval", "Direction", DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public Interval _computeIntervals (Interval input) throws Exception {
        double a1 = input.getLeft(), a2 = input.getRight();
        if (Double.isNaN(a1) || Double.isNaN(a2)) return new IntervalPoint(0);
        if (a1 <= a2) return new IntervalPoint(1);
        return new IntervalPoint(-1);
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return MeasurementUnit.PURE;
    }           
}
