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

import engcalculator.expression.ParsingException;
import engcalculator.function.CalculusException;
import engcalculator.function.Function;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class Exponent {
    private final static int DECDIG = 3;
    final static short TENPDIGITS = (short) Math.pow(10, DECDIG);
    private final static int MIN_VAL = Short.MIN_VALUE/TENPDIGITS;
    private final static int MAX_VAL = Short.MAX_VALUE/TENPDIGITS;    
                
    public final static Exponent ZERO, ONE, MINUSONE;
    
    static {
        try {
            ZERO = new Exponent(0);
            ONE = new Exponent(1);
            MINUSONE = new Exponent(-1);
        } catch (CalculusException ex) {
            throw new RuntimeException ("Internal error while creating static basic exponent -1 0 1: "+ex.getMessage());
        }
    }
    
    public static Exponent parseExponent (String s) throws ParsingException, CalculusException {
        float f;
        try {
            f = Float.parseFloat(s);
        } catch (NumberFormatException nfe) {
            throw new ParsingException ("Not recognize exponent "+s);
        }
        return new Exponent(f);
    }
    
    //An exponent is stored in a short ad decimal fixed point (2 digitx, max 32
    short value;
    
    public Exponent () {
        value = 0;
    }    
    
    public Exponent (int i) throws CalculusException {
        if (i < MIN_VAL || i > MAX_VAL) Function.throwNewCalculusException ("Exponent for measurement unit out of range: "+i);
        value = (short) (i * TENPDIGITS);
    }
     
    public Exponent (float f) throws CalculusException {        
        if (f < MIN_VAL || f > MAX_VAL) Function.throwNewCalculusException ("Exponent for measurement unit out of range: "+f);
        value = (short) (f * TENPDIGITS);
    }
    
    public Exponent (Exponent s) {
        value = s.value;
    }
      
    @Override
    public boolean equals (Object o) {
        if (this == o) return true;        
        if (o==null) return false;
        Exponent s = (Exponent) o;
        return value == s.value;
    }        
    
    @Override
    public String toString () {
    if (value % TENPDIGITS  == 0) return String.format("%d",value/TENPDIGITS);
        return String.format("%s",value/(float)TENPDIGITS);
    }
}
