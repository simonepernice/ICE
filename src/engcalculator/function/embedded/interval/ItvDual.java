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

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.IntervalComplex;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvDual extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (A_B) conjugates of the directed interval A_B which is B_A.\nIt basically means to exchange the extreme points.";    
    private final static String[] EXAMPLE = {"-10_10", "-10_-5", "10_15"};
    private final static String[] RESULT = {"10_-10", "-5_ -10", "15_10"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public ItvDual() {
        super("interval", "Dual", DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public Interval _computeIntervals(Interval i) throws Exception {
        if (i.isIntervalPoint()) return i;
        IntervalReal r = new IntervalReal (i.getRight(), i.getLeft()); //that need to be fixed for directed intervals
        if (i.isIntervalReal()) return r;
        IntervalReal c, ic = i.getImaginaryPart();
        if (ic != null) c = new IntervalReal (ic.getRight(), ic.getLeft());
        else return r;
        return new IntervalComplex(r, c);
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return input.getMeasurementUnit();
    }     
}
