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

package engcalculator.function.embedded.power;

import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.CalculusException;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfixDualInputSingleOutput;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;
import java.util.Arrays;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class Pow extends FunctionInfixDualInputSingleOutput {

    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());	
    private final static String HELP = "a ... b rises a to the power of b.";	
    private final static String[] EXAMPLE_LEFT = {"-1_2","-1_2", "1_2", "1_2"};
    private final static String[] EXAMPLE_RIGHT = {"2","3", "2", "3"};
    private final static String[] RESULT = {"0_4","-1_8","1_4","1_8"};

    public Pow() {
        super ("power", "^", DOMAIN, DOMAIN, true, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public Interval _computeIntervals(Interval a, Interval b) throws Exception {
        if (a.isIntervalPoint() && b.isIntervalPoint()) {
            return new IntervalPoint(Math.pow(a.getValue(), b.getValue()));
        }
        if (b.isEven()) {
            double power = b.getValue();
            if(a.isPositive()) return new IntervalReal(Math.pow(a.getLeft(), power), Math.pow(a.getRight(), power)).computeRoundings();
            if(a.isNegative()) return new IntervalReal(Math.pow(a.getRight(), power), Math.pow(a.getLeft(), power)).computeRoundings();
            if(a.isProper()) return new IntervalReal(0, Math.max(Math.pow(a.getLeft(), power), Math.pow(a.getRight(), power))).computeRoundings();
            return new IntervalReal(Math.max(Math.pow(a.getLeft(), power), Math.pow(a.getRight(), power)), 0).computeRoundings();
        } else {
            double[] values = new double[4];
            values[0] = Math.pow(a.getLeft(), b.getLeft());
            values[1] = Math.pow(a.getLeft(), b.getRight());
            values[2] = Math.pow(a.getRight(), b.getLeft());
            values[3] = Math.pow(a.getRight(), b.getRight());
            Arrays.sort(values);
            if (a.isProper() && ! b.isProper()) return new IntervalReal(values[3], values[0]);
            if (! a.isProper() && ! b.isProper()) return new IntervalReal(values[3], values[0]);
            return new IntervalReal(values[0], values[3]);
        }
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException, CalculusException {
        if (! rightSide.getMeasurementUnit().equals(MeasurementUnit.PURE)) throwNewMeasurementUnitException("The measurement units on the right side should be a pure value while it was found: "+rightSide.toString());
        if (leftSide.getMeasurementUnit().equals(MeasurementUnit.PURE)) return MeasurementUnit.PURE; //That avoid problem when masurementn unit is active and a very high power is used in a base with pure measurement unit
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());
        mua.mul((float) rightSide.getValue());
        return mua.toMeasurementUnit();
    }
}
