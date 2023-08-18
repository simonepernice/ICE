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

import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.interval.workFlow.WorkFlow;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class Interval {
    public final static IntervalPoint ZERO = new IntervalPoint (0);
    public final static IntervalPoint ONE = new IntervalPoint (1);
    public final static IntervalPoint ZEROONE = new IntervalReal (0,1);

    private final static Parameter<Integer> matrixCellWidth;
    
    static {
        matrixCellWidth = new Parameter<Integer> ("interval", "ALL", "matrixCellWidth", "Set the number of chars to use for each cell of a matrix (or vector).\nIf the value to print has less characters it is padded on the left with spaces, if it has more the last chars are truncated and it is added '...'", 20, new ConvertIntervalToInteger(10, 100));
        ParameterManager.addParameter(matrixCellWidth);
    }    
    
    public static Interval createIntervalWithMeasurementUnit (Interval i, MeasurementUnit m) {
        if (m == null) return null;
        Interval result;
        if (i instanceof IntervalFlowControl) result = new IntervalFlowControl(i);
        else if (i instanceof IntervalComplex) result = new IntervalComplex(i);
        else if (i instanceof IntervalReal) result = new IntervalReal (i);
        else if (i instanceof IntervalPoint) result = new IntervalPoint(i);
        else return null;

        result.setMeasurementUnit (m);
        return result;                    
    }

    public abstract double getRight();

    public abstract double getLeft();

    public abstract double getTolerance ();

    public abstract double getValue ();

    public abstract double getRange ();

    public abstract IntervalReal getRealPart ();

    public abstract IntervalReal getImaginaryPart ();
    
    public abstract String getName ();    
    
    public abstract ListIntervals getListIntervals ();    
    
    public abstract boolean isIntervalPoint ();

    public abstract boolean isIntervalReal ();

    public abstract boolean isIntervalComplex ();
    
    public abstract boolean isIntervalList ();    
    
    public abstract boolean isIntervalLiteral ();    

    public abstract Interval computeRoundings ();               

    public boolean hasMeasurementUnit() {
        return false;
    }

    public MeasurementUnit getMeasurementUnit() {
        return MeasurementUnit.PURE;        
    }     
    
    protected void setMeasurementUnit(MeasurementUnit m) {
        throw new RuntimeException ("It is not allowed to set the measuremnt unit for this kind of interval");
    }
    
    public WorkFlow getWorkFlow () {
        return null;
    }

    protected final String toStringSpaced () {
        final int mcw = matrixCellWidth.getVal();
        StringBuilder sb = new StringBuilder (mcw);
        final String s = toString();
        if (mcw > s.length()) {
            final int spaces = mcw - s.length();
            for (int i = 0; i < spaces; ++ i) sb.append(' ');
            sb.append (s);
        } else {
            sb.append(s.substring(0, mcw-3));
            sb.append("...");
        }
        return sb.toString();
    }

    public final boolean isInteger () {
        return (! isIntervalList()) && getRight() == Math.floor(getRight()) &&  getLeft() == Math.floor(getLeft());
    }

    public final boolean isEven () {
        return (! isIntervalList()) && isIntervalPoint() && isInteger() && (((int) getLeft())&1)==0;
    }

    public final boolean isPositive () {
        return (! isIntervalList()) && isIntervalReal() && getLeft() >= 0 && getRight() >= 0;
    }

    public final boolean isNegative () {
        return (! isIntervalList()) && isIntervalReal() && getLeft() < 0 && getRight() < 0;
    }

    public final boolean isProper () {
        if (isIntervalList() || isIntervalLiteral()) return false;
        if (getLeft() > getRight()) return false;
        Interval i = getImaginaryPart();
        return i == null || i.getLeft() <= i.getRight();
    }

    public final boolean doesContainZero () {
        if (isIntervalList()) return false;
        
        if ((getLeft() <= 0 && 0 <= getRight()) || (getRight() <= 0 && 0 <= getLeft())) return true;

        Interval i = getImaginaryPart();
        if (i == null) return false;
        return (i.getLeft() <= 0 && 0 <= i.getRight()) || (i.getRight() <= 0 && 0 <= i.getLeft());
    }

    public final boolean isComplexPointInterval () {
        if (isIntervalList()) return false;
        Interval im = getImaginaryPart();
        if (im != null && ! im.isIntervalPoint()) return false;
        Interval re = getRealPart();
        if (re != null) return re.isIntervalPoint();
        return true;
    }
    
    public final boolean isNAN() {
        if (isIntervalList ()) {
            for (Interval i : getListIntervals()) 
                if (i.isNAN()) return true;
            return false;
        }

        if (Double.isNaN(getLeft()) || Double.isNaN(getRight())) return true;
        
        if (! hasImaginaryPart()) return false;
        else return getImaginaryPart().isNAN();
    }
    
    public final boolean hasImaginaryPart () {
        if (isIntervalList()) return false;
        Interval im = getImaginaryPart();
        return im != null && !im.equals(ZERO);
    }
    
    public final double distance (Interval b) {
        if (isIntervalPoint()) return Math.abs(getValue()-b.getValue());
        if (isIntervalReal()) return Math.abs(getLeft()-b.getLeft()) + Math.abs(getRight()-b.getRight());
        if (isIntervalComplex()) {
            if (b.getImaginaryPart() == null) return getRealPart().distance(b.getRealPart())+Interval.ZERO.distance(getImaginaryPart());
            return Math.sqrt(square(getRealPart().distance(b.getRealPart()))+square(getImaginaryPart().distance(b.getImaginaryPart())));
        }
        throw new RuntimeException ("Distance called between interval types not expected");
    }
    
    private static double square (double v)  {//that is much faster than Math.pow(v, 2.)
        return v * v;
    }
    
    public final double compare (Interval b) {
        if (isIntervalPoint()) return getValue()-b.getValue();
        if (isIntervalReal()) return (getLeft()-b.getLeft()) + (getRight()-b.getRight());
        if (isIntervalComplex()) {
            if (b.getImaginaryPart() == null) return getRealPart().compare(b.getRealPart())+Interval.ZERO.compare(getImaginaryPart());
            throw new RuntimeException ("Distance called between complex numbers");
        }
        throw new RuntimeException ("Distance called between interval types not expected");
    }    

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if( ! (obj instanceof Interval)) throw new RuntimeException("Equals was called with an object which is not an interval");
        Interval b = (Interval) obj;
        if (hasMeasurementUnit()) {
            if (! getMeasurementUnit().equals(b.getMeasurementUnit())) return false;
        } else if (b.hasMeasurementUnit()) return false;
        if (isIntervalList()) {
            if (b.isIntervalList()) return getListIntervals().equals(b.getListIntervals());
            else return false;
        } else if (b.isIntervalList()) return false;
        if (getName() != null ) return getName().equals(b.getName());
        if (getLeft() != b.getLeft() || getRight() != b.getRight() ) return false;
        Interval bIm = b.getImaginaryPart(), aIm = getImaginaryPart();
        if (aIm == null ^ bIm == null)  return false;
        if (bIm != null) return (aIm.getLeft() == bIm.getLeft() && aIm.getRight() == bIm.getRight() );
        return true;
    }

    public final String getType () {
        if (getName() != null) return "Literal";
        if (isIntervalList()) return "List of Intervals";
        if (isIntervalPoint()) return "Point Interval";
        if (isIntervalReal()) return "Real Interval";
        if (isComplexPointInterval()) return "Complex Point Interval";
        if (isIntervalComplex()) return "Complex Interval";
        throw new RuntimeException ("Not understund the type of interval");
    }

    public final String getDetailedType () {
        StringBuilder result = new StringBuilder("(").append(getType());
        result.append(")");
        return result.toString();
    }

}
