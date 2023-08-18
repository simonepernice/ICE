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

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class IntervalComplex extends IntervalReal {
    public final static String IMAGINARYCONSTANTSYMBOL = "I";

    private IntervalReal imaginary;

    public IntervalComplex(Interval i) {
        this (i.getRealPart(), i.getImaginaryPart());
    }

    public IntervalComplex(Interval real, Interval imaginary) {
        super (real);
        if (imaginary != null) {
            this.imaginary = new IntervalReal(imaginary);
            if (! real.getMeasurementUnit().equals(imaginary.getMeasurementUnit())) throw new RuntimeException ("Trying to create a complex number with real and imaginary part of different measurement unit");
        }
        else this.imaginary = null;                
    }

    @Override
    public IntervalComplex computeRoundings () {
        super.computeRoundings();
        imaginary.computeRoundings();
        return this;
    }

    @Override
    public IntervalReal getRealPart() {
        return new IntervalReal (left, right);
    }

    @Override
    public IntervalReal getImaginaryPart() {
        return imaginary;
    }

    @Override
    public boolean isIntervalPoint () {
        return super.isIntervalPoint() && (imaginary == null || imaginary.equals(ZERO));
    }

    @Override
    public boolean isIntervalReal () {
        return imaginary == null || imaginary.equals(ZERO);
    }

    @Override
    public boolean isIntervalComplex () {
        return true;
    }

    @Override
    public String getName () {
        return null;
    }

    @Override
    public StringBuilder toStringBuilder () {
        StringBuilder result = new StringBuilder();
        if (imaginary == null) {
            result.append (super.toString());
        } else {
            boolean np = ! IntervalPoint.nicePrint.getVal();
            if (np) result.append('(');
            if (left != 0. || right != 0.) result.append (super.toStringBuilder());
            if (imaginary.left != 0. || imaginary.right != 0.) {
                StringBuilder im = imaginary.toStringBuilder();
                if (im.charAt(0) != '-' && im.charAt(0) != '+') result.append('+');
                result.append(im).append('*').append(IMAGINARYCONSTANTSYMBOL);
            }
            if (result.length() == 0) result.append('0');
            if (np) result.append(')');
        }
        
        return result;
    }

}
