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


import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.calculus.CalJacobian;
import engcalculator.function.embedded.interval.ItvByRelativeToleranceBuilder;
import engcalculator.function.embedded.matrix.MatInvert;
import engcalculator.function.embedded.matrix.MatMul;
import engcalculator.function.parameter.ConvertIntervalToDouble;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.interval.IntervalPoint;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EquFindRootNewton extends FunctionPrefix {
    private final static Parameter<Double> TOLERANCE;
    private final static Parameter<Double> EPSILON;
    private final static Parameter<Integer> MAXITERATIONS;
    
    private final static String HELP = "... ({f1, .., fn},{x1, .., xn}) applies Newton methods to solve the equations with given starting point.";
    private final static String[] EXAMPLE = {"{defineLambdaFunction('100/$x+50/$y-20'),defineLambdaFunction('$x+$y-15')},{12,6}"};
    private final static String[] RESULT = {"10,5"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalList(new DomainFunctionPrefix(-1)), new DomainIntervalList(new DomainIntervalPoint())); 

    private final static MatMul MUL = new MatMul();
    private final static AriSub SUB = new AriSub();
    private final static ItvByRelativeToleranceBuilder TOL = new ItvByRelativeToleranceBuilder();
    private final static MatInvert  INVERT = new MatInvert();
    private final static CalJacobian JACOBIAN = new CalJacobian();    
    
    static {
        TOLERANCE = new Parameter<Double>("equation","FindRootNewton", "tolerance", "The relative tolerance to be used around the points to compute the Jacobian", 1.0, new ConvertIntervalToDouble (0,10));
        EPSILON = new Parameter<Double>("equation","FindRootNewton", "epsilon", "The maximum distance between two iterations, below this value it stops", 0.1, new ConvertIntervalToDouble (0,1));
        MAXITERATIONS = new Parameter<Integer>("equation","FindRootNewton", "maxIteration", "The maximum number of iterations, above which it stops", 50, new ConvertIntervalToInteger (1,1000));
        ParameterManager.addParameter(TOLERANCE);
        ParameterManager.addParameter(EPSILON);
        ParameterManager.addParameter(MAXITERATIONS);
    }

    public EquFindRootNewton() {
        super("equation", "FindRootNewton", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final ListIntervals tol = new ListIntervals(new IntervalPoint(TOLERANCE.getVal()));
        ListIntervals x0 = input.getLast().getListIntervals();
        List<FunctionPrefix> lfp = JACOBIAN.functions(input.getFirst().getListIntervals());
        int iteration = 0;
        ListIntervals x1 = x0;
        final double eps = EPSILON.getVal() ;
        final int maxit = MAXITERATIONS.getVal();
        do {
            x0 = x1;
            x1 = SUB.compute(x0, MUL.compute(INVERT.compute(JACOBIAN.jacobian(lfp, TOL.compute(x0, tol))), evaluate(lfp, x0)));
            ++iteration;
        } while (x0.distance(x1) > eps && iteration < maxit);

        return x1;

    }
    
    ListIntervalsMatrix evaluate (List<FunctionPrefix> lfp, ListIntervals x0) throws Exception {
        ListIntervalsMatrix result = new ListIntervalsMatrix (1);
        for (FunctionPrefix f : lfp)
            result.addAll(f.compute(x0));
        return result;
    }


}
