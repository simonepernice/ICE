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
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
//import engcalculator.function.IterationLimits;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.compare.ComGreater;
import engcalculator.function.embedded.compare.ComSmaller;
import engcalculator.function.parameter.ConvertIntervalToDouble;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefixExpandLast;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

public abstract class EquBisectionMethod extends FunctionPrefixExpandLast {
    private static final ComSmaller SMALLER = new ComSmaller();
    private static final ComGreater GREATER = new ComGreater();
    private static final AriSub SUB = new AriSub();
    
    private final static Parameter<Double> EPSILON;
    private final static Parameter<Integer> MAXITERATIONS;
    
    static {
        EPSILON = new Parameter<Double>("equation","Find", "epsilon", "The maximum distance between two iterations, below this value it stops", 1e-8, new ConvertIntervalToDouble (0,1));
        MAXITERATIONS = new Parameter<Integer>("equation","Find", "maxIteration", "The maximum number of iterations, above which it stops", 300, new ConvertIntervalToInteger (1,1000));
        ParameterManager.addParameter(EPSILON);
        ParameterManager.addParameter(MAXITERATIONS);        
    }      

    public EquBisectionMethod(String group, String name, byte numbArgs, DomainList domain, boolean expandsToLists, boolean constant, String help, String[] examples, String[] results) {
        super (group, name, numbArgs, domain, expandsToLists, constant, help, examples, results);
    }

    abstract protected Interval f (double x) throws Exception;    //first function
    abstract protected Interval g (double x) throws Exception;    //second function or constant

    public ListIntervals findIntersection (Interval range) throws Exception {
        Interval fmin, fmax, fmid, newrange;
        double mid, min, max;
        int findMidMinMaxMixRatio, findMidStep;
        
        fmin = SUB._computeIntervals(f(range.getLeft()), g(range.getLeft()));
        fmax = SUB._computeIntervals(f(range.getRight()), g(range.getRight()));

        if (! ((SMALLER.compareForTrue(fmin, Interval.ZERO) && GREATER.compareForTrue(fmax, Interval.ZERO)) || (GREATER.compareForTrue(fmin, Interval.ZERO) && SMALLER.compareForTrue(fmax, Interval.ZERO)))) throwNewCalculusException ("The initial interval "+range+" does not seem to contain an intersection: "+fmin+" , "+fmax);

        final int maxIteration = MAXITERATIONS.getVal();
        final double epsilon = EPSILON.getVal();
        int step = 0;
        while (++step < maxIteration) {
            mid = range.getValue();
            min = range.getLeft();
            max = range.getRight();
            findMidMinMaxMixRatio = 4;
            findMidStep = 0;
            do {
                fmid = SUB._computeIntervals(f(mid), g(mid));
                if (SMALLER.compareForTrue(fmid, Interval.ZERO)) {
                    if (SMALLER.compareForTrue(fmin, Interval.ZERO)) {
                        fmin = fmid;
                        min = mid;
                    } else {
                        max = mid;
                        fmax = fmid;
                    }
                    break;
                }
                if (GREATER.compareForTrue(fmid, Interval.ZERO)) {
                    if (GREATER.compareForTrue(fmin, Interval.ZERO)) {
                        fmin = fmid;
                        min = mid;
                    } else {
                        max = mid;
                        fmax = fmid;
                    }
                    break;
                }
                if (fmid.equals(Interval.ZERO)) {//that works only if the function is thin
                    return new ListIntervals (new IntervalPoint (mid) );
                }
                switch (findMidStep) {
                    case 2: //restart from left
                        findMidStep = 0;
                        findMidMinMaxMixRatio *= 2;
                    case 0: //left
                        ++findMidStep;
                        mid = ((findMidMinMaxMixRatio-1)*min+max)/findMidMinMaxMixRatio;
                        break;
                    case 1: //right
                         ++findMidStep;
                        mid = ((findMidMinMaxMixRatio-1)*max+min)/findMidMinMaxMixRatio;
                        break;
                }

            } while (++step < maxIteration);

            

            newrange = new IntervalReal (min, max);

            if (newrange.distance(range)<epsilon) break;


            range = newrange;

        }

        if (step >= maxIteration) throwNewCalculusException("Reached maximum number of iterations: "+maxIteration);
        return new ListIntervals(range);

    }

}
