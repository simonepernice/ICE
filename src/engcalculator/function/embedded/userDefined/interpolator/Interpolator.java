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

package engcalculator.function.embedded.userDefined.interpolator;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public abstract class Interpolator {
    
    final Interval[] xp, fxp;
    final double xmin, xrange;
    final int size;  
        
    public Interpolator (ListIntervals x_fx) {        
        size = x_fx.size() / 2;
        xp = new Interval[size];
        fxp = new Interval[size];
        int j = 0;
        Iterator<Interval> xfxi = x_fx.iterator();
        while (xfxi.hasNext()) {
            xp[j] = xfxi.next();
            fxp[j] = xfxi.next();
            ++j;
        }
        
	xmin = xp[0].getLeft();
	xrange = xp[size-1].getRight()-xmin;
    }

    public double getXmin() {
        return xmin;
    }

    public double getXrange() {
        return xrange;
    }

    public Interval getFx(int i) {
        return fxp[i];
    }

    public int getSize() {
        return size;
    }        

    public abstract Interval getFx(double x) throws Exception;    

    
    Index findPoint (double xeval) {
        int i = (int) ((size*(xeval-xmin))/xrange); //first guess as good as the xp are uniformely distribuited
		
	i = setInRange (i);
		
        while (i < size && i > -1) {
            if (xeval >= xp[i].getLeft()) {
                if (xeval <= xp[i].getRight()) {
                    return new Index(i, false, false);
                } else ++i;
            } else if (i > 0 && xeval > xp[i-1].getRight()) {
                return new Index (i, false, true);
            } else --i;
        } 
		
	i = setInRange (i);
		
        return new Index (i, true, false);
    }
	
    private int setInRange (int i) {
        if (i < 0) return 0;
        if (i >= size) return size-1;
        return i;
    }   
    
}
