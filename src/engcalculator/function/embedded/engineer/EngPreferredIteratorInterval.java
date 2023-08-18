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
package engcalculator.function.embedded.engineer;

import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class EngPreferredIteratorInterval extends EngPreferred implements Iterator<Interval>{

    final int bIndex, eIndex, bExp, eExp, tolIndex;
    final boolean forward, pointInterval;
    int  cIndex, cExp;
    
    public EngPreferredIteratorInterval (double begin, double end, double tol) {
        this (begin, end, tol, false);
    }
    
    protected  EngPreferredIteratorInterval (double begin, double end, double tol, boolean pointInterval) {
        this.pointInterval = pointInterval;
        tolIndex = getRoundToleranceIndex(tol);
        
        bExp = getExponentIndex(begin);
        eExp = getExponentIndex(end);
        
        forward = end >= begin;        
        
        if (forward) {
            bIndex = getFloorPreferredIndex(tolIndex, begin/getExponent(bExp));        
            eIndex = getCeilingPreferredIndex(tolIndex, end/getExponent(eExp));
        } else {
            bIndex = getCeilingPreferredIndex(tolIndex, begin/getExponent(bExp));        
            eIndex = getFloorPreferredIndex(tolIndex, end/getExponent(eExp));
            
        }
        
        reset ();
    }
    
    public final void reset () {
        cIndex = bIndex;
        cExp = bExp;         
        if (forward) {
            -- cIndex;            
        } else {
            ++ cIndex;
        }
    }
    
    @Override
    public boolean hasNext () {
        if (forward) {
            if (cExp < eExp) return true;
            return cIndex < eIndex;
        } else {
            if (cExp > eExp) return true;
            return cIndex > eIndex;            
        }
    }
    
    public Interval current () {
        if (pointInterval) return new IntervalPoint(getPreferredValue(tolIndex, cIndex, cExp));
        return getIntervalByTolerance(getPreferredValue(tolIndex, cIndex, cExp), tolIndex);
    }

    @Override
    public Interval next() {
        if (forward) {
            if (cExp > eExp) return null;
            else if (cExp == eExp) {
                if (cIndex < eIndex) {
                    ++cIndex;
                    return current();
                }
                return null;
            } else {
                ++cIndex;
                if (cIndex >= getMaxIndex(tolIndex)) {
                    cIndex = 0;
                    ++cExp;
                }
                return current();
            }
        } else {
            if (cExp < eExp) return null;
            else if (cExp == eExp) {
                if (cIndex > eIndex) {
                    --cIndex;
                    return current();
                }
                return null;
            } else {
                --cIndex;
                if (cIndex < 0) {
                    cIndex = getMaxIndex(tolIndex)-1;
                    --cExp;
                }
                return current();
            }            
        }
    }

    
}
