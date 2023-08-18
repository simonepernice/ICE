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

package engcalculator.function.embedded.complex;

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CpxImaginaryPart extends FunctionPrefixSingleInputSingleOutput {
    private static final DomainList DOMAIN = new DomainList (new DomainIntervalComplex());
    private final static String HELP = "... (a+I*b) returns b.\nIt gets the imaginary part of a complex interval.";
    private final static String[] EXAMPLE = {"1+|2"};
    private final static String[] RESULT = {"2"};

    public CpxImaginaryPart() {
        super("complex", "ImaginaryPart", DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        Interval im = input.getImaginaryPart();
        if (im != null) return new IntervalReal(im);
        return new IntervalReal(Interval.ZERO);
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval input) throws Exception {
        return input.getMeasurementUnit();
    }

    
}
