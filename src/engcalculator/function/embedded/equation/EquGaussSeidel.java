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

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.domain.DomainListMatrix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.interval.ItvDual;
import engcalculator.function.parameter.ConvertIntervalToDouble;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EquGaussSeidel extends FunctionPrefix {
    private final static String HELP = "... ([A matrix], [B matrix], [x0 matrix]) solves the system of simultaneous equations A x = B beginning at X0 point.\nIt uses  Gauss Seidel algorithm. The matrix must be diagonal dominant to ensure the algorithm convergence.";
    private final static String[] EXAMPLE = {"(10, 2, 3, 40)#2,(5,6)#1,(1,1)#1"};
    private final static String[] RESULT = {"(0.47716, 0.11421)#1"};
    private final static DomainListMatrix DOMAIN = new DomainListMatrix(2);

    private final static AriMul MUL = new AriMul();
    private final static AriDiv DIV = new AriDiv ();
    private final static AriSub SUB = new AriSub();
    private final static ItvDual DUAL = new ItvDual();
    
    private final static Parameter<Double> EPSILON;
    private final static Parameter<Integer> MAXITERATIONS;
    
    static {
        EPSILON = new Parameter<Double>("equation","GaussSeidel", "epsilon", "The maximum distance between two iterations, below this value it stops", 0.1, new ConvertIntervalToDouble (0,1));
        MAXITERATIONS = new Parameter<Integer>("equation","GaussSeidel", "maxIteration", "The maximum number of iterations, above which it stops", 50, new ConvertIntervalToInteger (1,1000));
        ParameterManager.addParameter(EPSILON);
        ParameterManager.addParameter(MAXITERATIONS);        
    }    
    

    public EquGaussSeidel() {
        super("equation", "GaussSeidel", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int rows = input.rowSize();

        final double epsilon = EPSILON.getVal();
        final int maxIteration = MAXITERATIONS.getVal();

        Interval acc;
        ListIntervals oldinput;
        int iteration = 0;
        
        do {
            ++iteration;
            oldinput = new ListIntervals (input);

            for(int r = 0; r < rows; r++) {
                acc = input.get(r, rows);
                for(int c = 0; c < rows; c++) {
                    if( r != c ) {
                        acc = SUB._computeIntervals(acc, DUAL._computeIntervals(MUL._computeIntervals(input.get(r, c), input.get(c, rows+1))));
                    }
                }
                input.set(r, rows+1, DIV._computeIntervals(acc, DUAL._computeIntervals(input.get(r, r))));
            }

        } while (iteration < maxIteration && input.distance(oldinput) > epsilon);

        ListIntervalsMatrix result = new ListIntervalsMatrix(1);
        for (int r=0; r<rows; ++r)
            result.append(input.get(r, rows+1));
        
        return result;
    }

}
