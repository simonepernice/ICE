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

package engcalculator.function.embedded.vector;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.calculus.CalDerivate;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class VecCurl extends VecDelPrefixFunction {
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix (3), new DomainIntervalReal());
    private final static String HELP = "... ($f, x0, .., xn) returns the vector product (also known as curl) of the vector function f applied to the given intervals.\nThe input interval are used to compute the incremental ratio.";	
    private final static String[] EXAMPLE = {"defineLambdaFunction('$y,-$x,0*$z'), 1_2,1_2,1_2","defineLambdaFunction('0*$y,-($x^2),0*$z'), 1_2,1_2,1_2"};
    private final static String[] RESULT = {"0,0,-2","0,0,-2*1.5"};

    private final static CalDerivate DER = new CalDerivate();

    public VecCurl() {
        super ("Curl", (byte) -1, DOMAIN, false, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals input) throws Exception {                
        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        final int s = input.size();
        final int na = 3;
        if (na != f.getNumbArgs()) throwNewCalculusException("Curl is defined only on 3 dimensions space while found function returning a vector of "+f.getNumbArgs());
        if (s-1 != f.getNumbArgs()) throwNewCalculusException("Function argument size "+f.getNumbArgs()+" and input size "+(s-1)+ " do not match.");
        
        ListIntervals result = new ListIntervals();                
        
        double[][] a = new double[na][na]; //first index is derivative (dx, dy, dz), second index is the variable (fx, fy, fz)
        for (int j=0;j<na;++j) {
            ListIntervals i = DER.partialDerivate(f, deriveOnVariable(j, input));
            for (int k=0;k<na;++k) {
                a[j][k] = i.get(k).getValue();
            }
        }
        
        result.append(new IntervalPoint(a[1][2]-a[2][1])).append(new IntervalPoint(a[2][0]-a[0][2])).append(new IntervalPoint(a[0][1]-a[1][0]));
        
        return result;
    }
}
