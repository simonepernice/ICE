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
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.function.embedded.arithmetic.AriNegate;
import engcalculator.function.embedded.power.PowSqr;
import engcalculator.function.embedded.power.PowSqrt;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CpxAbs extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... a return the module of complex interval a.\nIt compute the absolute value of an interval. It is extended to directed interval and need to be verified for not proper interval.";
    private final static String[] EXAMPLE = {"-10_10","-10_-5","10_15","10_-10","-5_-10","15_10","4+3*I","3*I"};
    private final static String[] RESULT = {"0_10","5_10","10_15","10_0","10_5","15_10","5","3"};
    private final static AriNegate NEGATE = new AriNegate();
    private final static PowSqr SQUARE = new PowSqr();
    private final static PowSqrt SQUAREROOT = new PowSqrt();
    private final static AriSum SUM = new AriSum();
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public CpxAbs() {
        super("complex", ".abs", DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public Interval _computeIntervals (Interval input) throws Exception {
        Interval im = input.getImaginaryPart(), re = input.getRealPart();

        if (im != null) {
            if (re.equals(Interval.ZERO)) re = im;
            else if (! im.equals(Interval.ZERO)) return SQUAREROOT._computeIntervals(SUM._computeIntervals(SQUARE._computeIntervals(re), SQUARE._computeIntervals(im)));
        }

        if (re.isPositive()) re = new IntervalReal ( (IntervalReal)re);
        else if (re.isNegative()) re = NEGATE._computeIntervals(re);
        else if (re.isProper()) re = new IntervalReal (0, Math.max(re.getLeft(), re.getRight()));
        else re = new IntervalReal (Math.max(re.getLeft(), re.getRight()), 0);
        return re;
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return input.getMeasurementUnit();        
    }
    
}
