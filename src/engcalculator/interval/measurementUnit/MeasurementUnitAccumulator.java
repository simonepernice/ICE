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
import static engcalculator.interval.measurementUnit.MeasurementUnit.BASESIZE;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

public final class MeasurementUnitAccumulator {   
    private MeasurementUnit accumulator;
      
    public MeasurementUnitAccumulator (MeasurementUnit mu) {
        accumulator = new MeasurementUnit(mu);        
    }

    public MeasurementUnitAccumulator set (MeasurementUnit mu) {
        accumulator = new MeasurementUnit(mu); 

        return this;
    }

    public MeasurementUnitAccumulator add (MeasurementUnit m) {
        for (int i=0;i<MeasurementUnit.BASESIZE;++i) 
            accumulator.base[i].add(m.base[i]);
        return this;        
    }        
    
    public MeasurementUnitAccumulator sub (MeasurementUnit m) {
        for (int i=0;i<MeasurementUnit.BASESIZE;++i) 
            accumulator.base[i].sub(m.base[i]);
        return this;        
    }            
    
    public MeasurementUnitAccumulator mul (MeasurementUnit m) {
        for (int i=0;i<MeasurementUnit.BASESIZE;++i) 
            accumulator.base[i].mul(m.base[i]);
        return this;        
    }    

    public MeasurementUnitAccumulator div (MeasurementUnit m) {
        for (int i=0;i<MeasurementUnit.BASESIZE;++i) 
            accumulator.base[i].div(m.base[i]);
        return this;        
    }     
    
    public MeasurementUnitAccumulator add (MeasurementUnitAccumulator m) {
        return add(m.accumulator);        
    }        
    
    public MeasurementUnitAccumulator sub (MeasurementUnitAccumulator m) {
        return sub(m.accumulator);        
    }            
    
    public MeasurementUnitAccumulator mul (MeasurementUnitAccumulator m) {
        return mul(m.accumulator);        
    }    

    public MeasurementUnitAccumulator div (MeasurementUnitAccumulator m) {
        return div(m.accumulator);        
    }           
    
    public MeasurementUnitAccumulator mul (float n) throws CalculusException {
        Exponent p = new Exponent(n);
        for (int i=0;i<MeasurementUnit.BASESIZE;++i) 
            accumulator.base[i].mul(p);
        return this;        
    }            
    
    public MeasurementUnitAccumulator div (float n) throws CalculusException {
        Exponent p = new Exponent(n);
        for (int i=0;i<MeasurementUnit.BASESIZE;++i) 
            accumulator.base[i].div(p);
        return this;
    }    
    
    public MeasurementUnitAccumulator div (int n) throws CalculusException {
        Exponent p = new Exponent(n);
        for (int i=0;i<MeasurementUnit.BASESIZE;++i) 
            accumulator.base[i].div(p);
        return this;
    }       
    
    public ExponentAccumulator hasSamePwrRatio (MeasurementUnit m) {
        ExponentAccumulator pwrRatio = null;
        for (int i=0;i<BASESIZE;++i) {
            if (m.base[i].equals(Exponent.ZERO)) {
                if (accumulator.base[i].equals(Exponent.ZERO)) continue;
                else return null;            
            } 
            
            if (accumulator.base[i].equals(Exponent.ZERO)) return null;
            
            if (pwrRatio == null) pwrRatio = new ExponentAccumulator(accumulator.base[i]).div(m.base[i]);
            else if (! pwrRatio.equals(new ExponentAccumulator(accumulator.base[i]).div(m.base[i]))) {
                return null;
            }
        }
        return pwrRatio;
    }    
    
    public ExponentAccumulator getCurrentBase (int b) {
        return new ExponentAccumulator(accumulator.base[b]);
    }
    
    @Override
    public String toString () {
        return accumulator.toString();
    }
    
    public MeasurementUnit toMeasurementUnit () {
        return new MeasurementUnit(accumulator);
    }

    @Override
    public boolean equals (Object o) {
        if (o == null) return false;
        if (this == o) return true;
        MeasurementUnitAccumulator m = (MeasurementUnitAccumulator) o;
        for (int i=0;i<BASESIZE;++i) 
            if (! accumulator.base[i].equals(m.accumulator.base[i])) return false;
        return true;
    }     
    
    public boolean equals (MeasurementUnit o) {
        if (o == null) return false;
        return accumulator.equals(o);
    }      
}
