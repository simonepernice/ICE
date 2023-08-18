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

package engcalculator.function.embedded.plot;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.function.CalculusException;
import engcalculator.function.parameter.ConvertIntervalToDouble;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervalsMatrix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PltStandardAdaptive extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(1), new DomainInterval());

    private final static String HELP = "... ($func, xInterval) computes func (which provides the y value) for the minimum numbero of point to get a good representatin in the given interval returning the plottable matrix.\n ... ('f', begin_end) calculate the matrix [x # 1, f(x) #1] to be plot later. The point at which f is calculate are self determinated looking into f variations. The interval is split in sub intervals. The sub intervals are defined in order to get the function values at the interval extremes equal with respect to the function value at the middle. In the ranges where the function is closer to a line less intervals are used. A minimum of 2^4+1 points are used to detect where the function requires more. It is usually faster and more accureta than plotStandard with 100 intervals. For example plotting sin between 0_2PI just takes 65 points. it possible to provide the some setting to the function with the function systemSetupParameters ($..., mindeep, maxdeep, tol): 2^mindeep is the minimum number of interval to set (default is 2^3=8 intervals) the smaller the faster works for simple function however with a too smal set of itnervals a functin may seem stable while it is not. 2^maxdeep is the maximum number of itnervals (default is 2^12=4048), the higher the higher is the accuracy however the computational and memory time will rise exponentially. accuracy is how the trapezoid found looks good to calculate its area. A smaller value will require more intervals as far as 2^maxdeep is reached.";
    private final static String[] EXAMPLE = {"defineLambdaFunction('2*$x+5'),0_10"};
    private final static String[] RESULT = {"listLinear(2^4+1, 0_10) # 1 , defineLambdaFunction('2*$x+5', listLinear(2^4+1, 0_10)) # 1"};    
       
    private FunctionPrefix f;
    
    private final static Parameter<Integer> MINDEEP;
    private final static Parameter<Integer> MAXDEEP;
    private final static Parameter<Double> TOL;
    
    private int minDeep;
    private int maxDeep;
    private double tol;
    
    
    static {
        TOL = new Parameter<Double>("plot","StandardAdaptive", "tolerance", "The maximum allowed tolerance in the trapezoid, unless maxDeep is reached.", 0.2/100., new ConvertIntervalToDouble (0,1));
        MINDEEP = new Parameter<Integer>("plot","StandardAdaptive", "minDeep", "The minimum deep (2^minDeep) to explore for functin characteristics.", 3, new ConvertIntervalToInteger (1,10));
        MAXDEEP = new Parameter<Integer>("plot","StandardAdaptive", "maxDeep", "The maximum deep (2^maxDeep) to explore for functin characteristics.", 12, new ConvertIntervalToInteger (1,20));
        ParameterManager.addParameter(TOL);        
        ParameterManager.addParameter(MINDEEP);        
        ParameterManager.addParameter(MAXDEEP);        
    }    
    
    public PltStandardAdaptive() {
        super("plot", "StandardAdaptive", (byte) 2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    } 

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        minDeep = MINDEEP.getVal();
        maxDeep = MAXDEEP.getVal();
        tol = TOL.getVal();
        f = FunctionPrefix.getFunction(input.getFirst().getName());       
        return plot(input.get(1)); 
    }
    
    private ListIntervals[] split (ListIntervals left, ListIntervals  right) {
        return split (left.getFirst().getLeft(), right.getFirst().getRight());
    }
    
    private ListIntervals[] split (Interval input) {
        return split (input.getLeft(), input.getRight());
    }
    
    private ListIntervals[] split (double left, double right) {
        ListIntervals[] result = new ListIntervals[3];
        result[0] = new ListIntervals (new IntervalPoint(left));
        result[1] = new ListIntervals (new IntervalPoint((left+right)/2d));
        result[2] = new ListIntervals (new IntervalPoint(right));
        return result;
    }

    private ListIntervals[] computeFunction (ListIntervals[] input) throws Exception{
        ListIntervals[] result = new ListIntervals[3];
        for (int i=0;i<3;++i)
            try {
                result[i] = f.compute(input[i]);
            } catch (CalculusException ce) {
                result[i] = null;
            }
        return result;
    }
    
    private ListIntervals[] computeFunction (ListIntervals fLeft, ListIntervals[] center, ListIntervals fRight) throws Exception{
        ListIntervals[] result = new ListIntervals[3];
        result[0] = fLeft;
        try {
            result[1] = f.compute(center[1]);
        } catch (CalculusException ce) {
            result[1] = null;
        }
        result[2] = fRight;
        return result;
    }
    
    private ListIntervals plot (Interval x) throws Exception {
        ListIntervals[] xp = split(x);
        ListIntervals[] yp = computeFunction(xp);
        
        ListIntervals result = plotAdaptive(xp, yp, 0);
        if (yp[2] != null) {
            result.add(xp[2].getFirst());
            result.add(yp[2].getFirst());
        }
        return new ListIntervalsMatrix(result,2);
    }   
    
    private ListIntervals append (ListIntervals left, ListIntervals right) {
        left.addAll(right);
        return left;
    }
    
    private ListIntervals joinAll (ListIntervals[] x, ListIntervals[] y) {
        ListIntervals result = new ListIntervals ();
        for (int i=0; i<2; ++i) {
            if (y[i] != null) {
                result.add(x[i].getFirst());
                result.add(y[i].getFirst());
            }
        }
        return result;
    }    
    
    private ListIntervals lookDeeper (ListIntervals[] x, ListIntervals[] y, int deep) throws Exception {
        ListIntervals[] nxl = split(x[0], x[1]);
        ListIntervals[] nyl = computeFunction(y[0], nxl, y[1]);
        ListIntervals[] nxr = split(x[1], x[2]);
        ListIntervals[] nyr = computeFunction(y[1], nxr, y[2]);
        return append(plotAdaptive(nxl, nyl, deep+1), plotAdaptive(nxr, nyr, deep+1));        
    }
    
    private ListIntervals plotAdaptive(ListIntervals[] x, ListIntervals[] y, int deep) throws Exception {               
        if (deep < minDeep) {//too few sectors, some function discontinuity may not be found
            return lookDeeper(x, y, deep);
        } else if (deep > maxDeep) {//too many sectors, it is taking too long
            return joinAll(x, y);
        } else if (isGoodTrapezoid(y)) {//good aproximation found, can go back  
            return joinAll(x, y);
        } else {//not good aproximation, try deeper
            return lookDeeper(x, y, deep);
        }
    }    

    private boolean isGoodTrapezoid(ListIntervals[] y) throws Exception {
        
        if (y[0] == null || y[1] == null || y[2] == null) return false; //there is some discontinuity

        double num = ((y[0].getFirst().getValue()+y[2].getFirst().getValue())-2d*y[1].getFirst().getValue());
        
        if (y[1].getFirst().getValue() == 0.) return Math.abs(num) < tol; // to avoid division by 0
        
        return Math.abs(num/y[1].getFirst().getValue()) <= tol;
    }

}
