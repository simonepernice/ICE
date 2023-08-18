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
public final class ItvSign extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (A) returns the A sign.\nIt means to return -1, if A contains only negative values and 0; 1, if A contains only positive values and 0; 0 if A contains both positive and negative values or A is 0 or A involves NaN.";
    private final static String[] EXAMPLE = {"-10_10, 5_7, -7_-5"};
    private final static String[] RESULT = {"0, 1, -1"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    public ItvSign() {
        super("interval", ".sign", DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }
    
    @Override
    public Interval _computeIntervals (Interval i) throws Exception {
        double a1 = i.getLeft(), a2 = i.getRight();
        if (a1 == 0 || a2 == 0 || Double.isNaN(a1) || Double.isNaN(a2)) return new IntervalPoint (0);
        if (i.isPositive()) return new IntervalPoint (1);
        if (i.isNegative()) return new IntervalPoint (-1);
        return new IntervalPoint (0);
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return MeasurementUnit.PURE;
    }      
}
