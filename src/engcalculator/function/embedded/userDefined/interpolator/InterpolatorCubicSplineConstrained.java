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

import engcalculator.interval.ListIntervals;
//import java.util.Arrays;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 * 
 * Based on Constrained Cubic Spline Interpolation for Chemical Engineering Applications by CJC Kruger 
 * Link: http://www.korf.co.uk/spline.pdf
 * With exeption of condition 7b and 7c which are not suitable for ICE spline implementation
 */


public final class InterpolatorCubicSplineConstrained extends InterpolatorCubicSpline {

    public InterpolatorCubicSplineConstrained (ListIntervals x_fx) {        
        super (x_fx);
        
        final int s = size-1;
        
        double[] dfdx = new double[s];
        for (int i=0; i<s; ++i){
            final int ip1 = i + 1;
            dfdx[i] = (fxp[ip1].getLeft()-fxp[i].getRight())/(xp[ip1].getLeft()-xp[i].getRight());
        }
        
        final int sm1 = s-1;
        for (int i=1; i<sm1; ++i){
            final int ip1 = i + 1;
            final int im1 = i - 1;
            if (! xp[i].isIntervalPoint()) ks[i]=0.;
            else {
                if (Math.signum(dfdx[im1])!=Math.signum(dfdx[i])) ks[i]=0.;
                else ks[i]=(dfdx[im1]+dfdx[i])/2.;
            }
        }
        ks[0]= 0.;//(3. * dfdx[0]-ks[1])/2.;
        ks[sm1]= 0.;//(3. * dfdx[sm1]-ks[s])/2.;          
        
//        System.out.println("xs"+Arrays.toString(xp));
//        System.out.println("fxs"+Arrays.toString(fxp));
//        System.out.println("dfdx"+Arrays.toString(dfdx));
//        System.out.println("ks"+Arrays.toString(ks));
    }
    
}
