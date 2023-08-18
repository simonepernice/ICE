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

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class VecGradient extends VecDelPrefixFunction {
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix (-1), new DomainIntervalReal());
    private final static String HELP = "... ($f, x0, .., xn) returns the gradient of the scalar function f applied to the given interval vector space x0, .., xn.\nThe input intervals are used to compute the incremental ratio.";
    private final static String[] EXAMPLE = {"defineLambdaFunction('2*$x+3*$y'), 1_2,3_4","defineLambdaFunction('2*$x*$y'), 1_2,3_4"};        
    private final static String[] RESULT = {"2,3","2*3.5,2*1.5"};
    
    private final static CalDerivate DER = new CalDerivate();

    public VecGradient() {
        super ("Gradient", (byte) -1, DOMAIN, false, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals input) throws Exception {                
        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        final int s = input.size();
        final int na = f.getNumbArgs();
        if (s-1 != f.getNumbArgs()) throwNewCalculusException("Function argument size and input size do not match: "+f.getNumbArgs()+" vs "+(s-1));
        
        ListIntervals result = new ListIntervals();                
        
        double a = 0;
        for (int j=0;j<na;++j) {
            ListIntervals re = DER.partialDerivate(f, deriveOnVariable(j, input));
            if (re.size() != 1) throwNewCalculusException ("The given input function is not scalar, computing returned "+re);
            result.append(re.getFirst());
        }
        
        return result;
    }
}
