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
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 * 
 * Based on Spline interpolation From Wikipedia, the free encyclopedia
 * http://en.wikipedia.org/wiki/Spline_interpolation#Algorithm_to_find_the_interpolating_cubic_spline
 * 
 * 
 */

public abstract class InterpolatorCubicSpline extends Interpolator {
    
    double[] ks;    

    public InterpolatorCubicSpline (ListIntervals x_fx) {        
        super (x_fx);
        ks = new double[size];
    }
    
    public final Interval getFx(double x) throws Exception{
        Index in = findPoint(x);
        if (in.isBetweenPoints()) {
            final int i = in.getIndex();
            final int im1 = i-1;
            
            final double t = (x - xp[im1].getRight()) / (xp[i].getLeft() - xp[im1].getRight());

            final double a =  ks[im1]*(xp[i].getLeft()-xp[im1].getRight()) - (fxp[i].getLeft()-fxp[im1].getRight());
            final double b = -ks[i  ]*(xp[i].getLeft()-xp[im1].getRight()) + (fxp[i].getLeft()-fxp[im1].getRight());

            return new IntervalPoint((1-t)*fxp[im1].getRight() + t*fxp[i].getLeft() + t*(1-t)*(a*(1-t)+b*t));
	}            
        return fxp[in.getIndex()];        
    }    
    
}
