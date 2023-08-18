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
package engcalculator.function.embedded.equation;

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.function.parameter.ConvertIntervalToDouble;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EquActractiveFixedPoint extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList(new DomainFunctionPrefix(-1), new DomainInterval());

    private final static String HELP = "... ($f1, .., $fn, x1, .., xn) solves a set of equations with iterative steps.\nThe equations should be in the form x1 = f1 (x1, .., xn), x2 = f2 (x1, .., xn), .. , xn = fn (x1, .., xn). It iterates from an initial point x1', .., xn' applying f1, .., fn to get a more accurate estimation x1'', .., xn'', until the difference between the current and previous solution is less than n * internal epson. If x1', .., xn' is an attractive fixed point of the function f the sequence will converge to the solution x. It needs the equation names and the starting point: ... ($f1, .., $fn, x1', .., xn').";
    private final static String[] EXAMPLE = {"'(x^2)-3*x+2 has solution 1 and 2';($sq1, $x)='sqrt(3*x-2)';($sq2, $x)='(x^2+2)/3';equationActractiveFixedPoint($sq1, 10), equationActractiveFixedPoint($sq2, -1)"};
    private final static String[] RESULT = {"2, 1"};

    private final static Parameter<Integer> MAXITERATIONS;
    private final static Parameter<Double> EPSILON;
    
    static {
        MAXITERATIONS = new Parameter<Integer>("equation","ActractiveFixedPoint", "maxIteration", "The maximum number of iterations, above which it stops", 100, new ConvertIntervalToInteger (1,1000));
        EPSILON = new Parameter<Double>("equation","ActractiveFixedPoint", "epsilon", "The maximum distance between two iterations, below this value it stops", 0.00001, new ConvertIntervalToDouble (0,1));
        ParameterManager.addParameter(MAXITERATIONS);        
        ParameterManager.addParameter(EPSILON);        
    }        
    
    public EquActractiveFixedPoint() {
        super("equation", "ActractiveFixedPoint", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        if (input.size() % 2 != 0) {
            throwNewCalculusException("The number of paramenters provided is not correct: "+input.size());
            return null;//this will never be reached unless error check are disabled
        }

        final int n = input.size()  / 2;

        final FunctionPrefix[] f = new FunctionPrefix[n];
        for (int i = 0; i < n; ++i) {
            f[i] = FunctionPrefix.getFunction(input.get(i).getName());
            if (f[i] == null) {
                throwNewCalculusException("It was expected a function name as input at place: "+i);
            }
            if (!(f[i].getNumbArgs() == n || f[i].getNumbArgs() <= -n)) {
                throwNewCalculusException("The function " + f[i].getSymbol() + " should accept "+n+" arguments as input.");
            }
        }

        ListIntervals oldY = input.subList(n, input.size());
        ListIntervals y;
        
        int j = 0;
        final int maxIteration = MAXITERATIONS.getVal();
        double distance = 0;
        final double epsilon = EPSILON.getVal();
        while (j < maxIteration) {
            y = new ListIntervals ();
            for (int i = 0; i < n; ++i) {
                y.append(f[i]._compute(oldY).getFirst());
            }
            
            double d=0;
            for (int i = 0; i < n; ++i) {
                d += y.get(i).distance(oldY.get(i)); 
            }
            
            if (j > 0) {
                if (d < epsilon*n) return y;
                if (d > distance) throwNewCalculusException ("The solution is diverging");
            }
            
            distance = d;
            oldY = y;
            ++j;                         
        }
        throwNewCalculusException ("Maximum number of iterations reached "+maxIteration);           
        return null;//this will never be reached unless error check are disabled
    }

}
