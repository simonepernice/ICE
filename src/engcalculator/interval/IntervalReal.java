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

package engcalculator.interval;

import engcalculator.function.parameter.ConvertIntervalToBoolean;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class IntervalReal extends IntervalPoint {
    private final static boolean DIRECTED_INTERVAL = true;
    
    private final static Parameter<Boolean> AVOIDROUNDINGS;
    private final static Parameter<Boolean> PRINTVALTOL;

    static {
        AVOIDROUNDINGS = new Parameter<Boolean> ("interval", "ALL", "avoidRoundings", "Set the rounding of ICE depending on A.\nIf the argument is 1, every calculus involving an interval (NOT scalar) will be outer (inner) rounded  if proper (improper) in order to be sure to cover every result (not add false result). By default this option is disabled because it avoid some basic equalities to be true (like a - dual(a) == 0) although it grants that the solution will follow interval definition.", true, new ConvertIntervalToBoolean());
        PRINTVALTOL = new Parameter<Boolean> ("interval", "ALL", "valueAndTolerance", "Set the print mode to intervalValue%tolerance (1) or min_max (0).", true, new ConvertIntervalToBoolean());
        ParameterManager.addParameter(PRINTVALTOL);
        ParameterManager.addParameter(AVOIDROUNDINGS);
    }

    protected double right;

    public IntervalReal (Interval i)  {
        this (i.getLeft(), i.getRight());
        if (! i.isIntervalReal()) throw new RuntimeException ("The algorithm is trying to cast wrong kind of Interval to a real interval: "+i.toString());
        if (i.hasMeasurementUnit()) this.measurementUnit = i.getMeasurementUnit();
    }

    public IntervalReal (double left, double right) {
        super(left);
        if (DIRECTED_INTERVAL || left <= right) {
//            this.left = left; done in the super constructor
            this.right = right;
        } else {
            this.left = right;
            this.right = left;
        }
    }
    
    public IntervalReal (double left, double right, MeasurementUnit m) {
        this(left, right);
        if (m!=null) measurementUnit = m;
    }

    @Override
    public IntervalReal computeRoundings () {
        if (AVOIDROUNDINGS.getVal()) return this;

        if (isIntervalPoint()) return this; //that avoids to expand a point interval

        if (isProper()) {
            left = moveAnEpsilonToward(true, left);
            right = moveAnEpsilonToward(false, right);
        } else {
            left = moveAnEpsilonToward(false, left);
            right = moveAnEpsilonToward(true, right);
        }
        
        return this;
    }

    private static double moveAnEpsilonToward(boolean left, double val) {
        if (! (Double.isInfinite(val) || Double.isNaN(val)) ) {
            final int steps;
            if (left) steps = 1;
            else steps = -1;

            if (val == 0) val = left ? -Double.longBitsToDouble(1) : Double.longBitsToDouble(1);
            else if (val > 0) val = Double.longBitsToDouble(Double.doubleToLongBits(val)-steps);
            else val = Double.longBitsToDouble(Double.doubleToLongBits(val)+steps);
        }

        return val;
    }

    @Override
    public double getRight() {
        return right;
    }

    @Override
    public double getLeft() {
        return left;
    }

    @Override
    public double getTolerance () {
        return (right-left)*100/Math.abs(right+left);
    }

    @Override
    public double getValue () {
        if (isIntervalPoint()) return left; //to avoid error propagation
        return (right+left)/2;
    }

    @Override
    public double getRange () {
        return right-left;
    }

    @Override
    public IntervalReal getRealPart() {
        return this;
    }

    @Override
    public IntervalReal getImaginaryPart() {
        return null;
    }

    @Override
    public boolean isIntervalPoint () {
        return left == right;
    }

    @Override
    public boolean isIntervalReal () {
        return true;
    }

    @Override
    public boolean isIntervalComplex () {
        return true;
    }

    @Override
    public String getName () {
        return null;
    }
    
    public StringBuilder toStringBuilder () {
        StringBuilder result = new StringBuilder();

        if (PRINTVALTOL.getVal() && nicePrint.getVal()) {
            result.append(IntervalPoint.toEngString(getValue()));
            if (! isIntervalPoint()) {
                double tol = getTolerance();
                if (Math.abs(tol)>1e-6) result.append('%').append(IntervalPoint.toEngString(getTolerance()));
            }
        } else {
            result.append(toEngString(left));
            if (! isIntervalPoint()) result.append('_').append(toEngString(right));
        }

        return result;
    }

}
