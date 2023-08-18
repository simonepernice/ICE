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

package engcalculator.function.embedded.arithmetic;

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfixDualInputSingleOutputMultipleDomain;
import engcalculator.interval.IntervalComplex;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class AriMul extends FunctionInfixDualInputSingleOutputMultipleDomain {
    private final static String HELP = "a ... b return a multiplied by b.\nIt works on Kaucher and complex arithmetic.";
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());
    private final static String[] EXAMPLE_LEFT = {"6_9"};
    private final static String[] EXAMPLE_RIGHT = {"1_3"};
    private final static String[] RESULT = {"6_27"};
    private final static AriSum SUM = new AriSum ();
    private final static AriSub SUB = new AriSub ();

    public AriMul() {
        super ("arithmetic", "*", DOMAIN, DOMAIN, true, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public Interval _computeIntervalPoint(Interval x, Interval y) throws Exception {
        return new IntervalPoint (x.getValue()*y.getValue());        
    }

    @Override
    public Interval _computeIntervalReal(Interval x, Interval y) throws Exception {
        double x1 = x.getLeft(), x2 = x.getRight(), y1 = y.getLeft(), y2 = y.getRight();

        if (x.isPositive()) {
            if (y.isPositive())      return new IntervalReal (x1*y1, x2*y2).computeRoundings();
            else if (y.isNegative()) return new IntervalReal (x2*y1, x1*y2).computeRoundings();
            else if (y.isProper())   return new IntervalReal (x2*y1, x2*y2).computeRoundings();
            else                     return new IntervalReal (x1*y1, x1*y2).computeRoundings();
        } else if (x.isNegative()) {
            if (y.isPositive())      return new IntervalReal (x1*y2, x2*y1).computeRoundings();
            else if (y.isNegative()) return new IntervalReal (x2*y2, x1*y1).computeRoundings();
            else if (y.isProper())   return new IntervalReal (x1*y2, x1*y1).computeRoundings();
            else                     return new IntervalReal (x2*y2, x2*y1).computeRoundings();
        } else if (x.isProper()) {
            if (y.isPositive())      return new IntervalReal (x1*y2, x2*y2).computeRoundings();
            else if (y.isNegative()) return new IntervalReal (x2*y1, x1*y1).computeRoundings();
            else if (y.isProper())   return new IntervalReal (Math.min(x1*y2, x2*y1), Math.max(x1*y1, x2*y2)).computeRoundings();
            else                     return new IntervalPoint (0);
        } else {
            if (y.isPositive())      return new IntervalReal (x1*y1, x2*y1).computeRoundings();
            else if (y.isNegative()) return new IntervalReal (x2*y2, x1*y2).computeRoundings();
            else if (y.isProper())   return new IntervalPoint (0);
            else                     return new IntervalReal (Math.max(x1*y1, x2*y2), Math.min(x1*y2, x2*y1)).computeRoundings();
        }
    }

    @Override
    public Interval _computeIntervalLiteral(Interval x, Interval y) throws Exception {
        throw new RuntimeException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interval _computeIntervalComplex(Interval x, Interval y) throws Exception {
        Interval rePart, imPart;
        
        rePart = _computeIntervals(x.getRealPart(), y.getRealPart());
        
        Interval xim = x.getImaginaryPart(), yim = y.getImaginaryPart();
        //the following settings are required because if imaginary part is null the next calls may fail because null pointer is not checked on _computeIntervals. Setting to zero does not affect the check on imaginary part
        if (xim == null) xim = Interval.ZERO;
        if (yim == null) yim = Interval.ZERO;
        if (! x.hasImaginaryPart()) {
            imPart = _computeIntervals(x.getRealPart(), yim);
        } else if (! y.hasImaginaryPart()) {
            imPart = _computeIntervals(xim, y.getRealPart());
        } else {
            rePart = SUB._computeIntervals(rePart, _computeIntervals(xim, yim));
            imPart = SUM._computeIntervals(_computeIntervals(xim, y.getRealPart()), _computeIntervals(x.getRealPart(), yim));
        }
        
        return new IntervalComplex(rePart, imPart);

    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());
        mua.add(rightSide.getMeasurementUnit());
        return mua.toMeasurementUnit();
    }    
}
