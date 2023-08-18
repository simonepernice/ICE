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

package engcalculator.interval.measurementUnit;

import engcalculator.function.CalculusException;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class ExponentAccumulator {
    private final Exponent accumulator;
    
    public ExponentAccumulator () {
        accumulator = new Exponent();
    }
   
    public ExponentAccumulator (int i) throws CalculusException {
        accumulator = new Exponent (i);
    }
     
    public ExponentAccumulator (float f) throws CalculusException {
        accumulator = new Exponent (f);
    }
    
    public ExponentAccumulator (Exponent s) {
        accumulator = new Exponent (s);
    }
    
    public ExponentAccumulator (ExponentAccumulator s) {
        accumulator = new Exponent (s.accumulator);
    }    
    
    public ExponentAccumulator add (Exponent s) {
        accumulator.value += s.value;
        return this;
    }

    public ExponentAccumulator sub (Exponent s) {
        accumulator.value -= s.value;
        return this;
    }
    
    public ExponentAccumulator mul (Exponent s) {
        int t = accumulator.value * s.value;
        accumulator.value = (short) (t/Exponent.TENPDIGITS);
        return this;
    }

    public ExponentAccumulator div (Exponent s) {
        int t = (accumulator.value*Exponent.TENPDIGITS) / s.value;
        accumulator.value = (short) t;
        return this;
    }   
    
    public ExponentAccumulator add (ExponentAccumulator s) {
        return add(s.accumulator);
    }

    public ExponentAccumulator sub (ExponentAccumulator s) {
        return sub(s.accumulator);
    }
    
    public ExponentAccumulator mul (ExponentAccumulator s) {
        return mul(s.accumulator);
    }

    public ExponentAccumulator div (ExponentAccumulator s) {
        return div(s.accumulator);
    }  
    
    public Exponent toExponent () {
        return new Exponent (accumulator);
    }

    @Override
    public String toString() {
        return accumulator.toString();
    }        
    
    @Override
    public boolean equals (Object o) {
        if (this == o) return true;        
        if (o==null) return false;
        return accumulator.equals(((ExponentAccumulator) o).accumulator);
    }    
    
    public boolean equals (Exponent o) {   
        if (o==null) return false;
        return accumulator.equals(o);        
    }     

    boolean isInteger() {
        return (accumulator.value % Exponent.TENPDIGITS) == 0;
    }
    
    public boolean isPositive () {
        return accumulator.value > 0;
    }     

    public int absVal  () {
        return Math.abs(accumulator.value);
    }    
    
    public int distance (Exponent ea) {
        return Math.abs(accumulator.value-ea.value);
    }
}
