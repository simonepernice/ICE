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

public final class InterpolatorLinear extends Interpolator {
    
    private final double[] dfdx;
    

    public InterpolatorLinear (ListIntervals x_fx) {        
        super (x_fx);
        final int s = size-1;
        dfdx = new double[s];
        for (int i = 0; i < s; ++i) {
            int ip1 = i + 1;
            dfdx[i] = (fxp[ip1].getLeft()-fxp[i].getRight())/(xp[ip1].getLeft()-xp[i].getRight());
        }
    }
    
    public Interval getFx(double x) throws Exception{
        Index i = findPoint(x);
        if (i.isBetweenPoints()) {
            final int im1 = i.getIndex()-1;
            return new IntervalPoint((x - xp[im1].getRight())*dfdx[im1]+fxp[im1].getRight());
        }
        return getFx(i.getIndex());        
    }    

    
}
