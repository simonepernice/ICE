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

package engcalculator.function.embedded.calculus;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.embedded.complex.CpxAbs;
import engcalculator.function.embedded.interval.ItvRange;
import engcalculator.function.parameter.ConvertIntervalToDouble;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CalIntegrateAdaptive extends FunctionPrefix {
    private static final AriMul MUL = new AriMul();
    private static final AriDiv DIV = new AriDiv();
    private static final AriSum SUM = new AriSum();
    private static final AriSub SUB = new AriSub();
    private static final CpxAbs ABS = new CpxAbs();
    private static final ItvRange RANGE = new ItvRange();

    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(-1), new DomainInterval());
    
    private final static String HELP = "... ('f', x0, x1, ..., xn) integrates the function f(x0, x1, ..., xn) on the input parameter provided as real intervals.\nIt is possible to integrate on several variables. The interval is split in sub intervals and integrated. The sub intervals are defined in order to get a good accuracy. If the function is stable few intervals are used. It is usually faster and with similar accuracy of a standard IntegrateUniform function with 100 intervals. It is possible to provide several interval input sets to compute the integral on several variables simultaneously. It possible to provide the some setting to the function with the function systemSetupParameters ($..., mindeep, maxdeep, tol): 2^mindeep is the minimum number of interval to set (default is 2^3=8 intervals) the smaller the faster works for simple function however with a too smal set of itnervals a functin may seem stable while it is not. 2^maxdeep is the maximum number of itnervals (default is 2^12=4048), the higher the higher is the accuracy however the computational and memory time will rise exponentially. accuracy is how the trapezoid found looks good to calculate its area (default is .2/100=0.2% to get 3 digits in sin integration). A smaller value will require more intervals as far as 2^maxdeep is reached.";
    private final static String[] EXAMPLE = {"$sin, 0_PI","defineLambdaFunction('$x+$y'),0_2,1_4"};
    private final static String[] RESULT = {"2","21"};    
       
    private final static Parameter<Integer> MINDEEP;
    private final static Parameter<Integer> MAXDEEP;
    private final static Parameter<Double> TOL;

    private int minDeep;
    private int maxDeep;
    private double tol;
    
    static {
        TOL = new Parameter<Double>("calculus","Integrate", "tolerance", "The maximum allowed tolerance in the trapezoid, unless maxDeep is reached. Default was found to have 3 decimal digit accuracy on sin x integrate between 0 and pi.", 0.2/100., new ConvertIntervalToDouble (0,1));//found to have 3 decimal digit accuracy on sin x integrate between 0 and pi
        MINDEEP = new Parameter<Integer>("calculus","Integrate", "minDeep", "The minimum deep (2^minDeep) to explore for functin characteristics.", 3, new ConvertIntervalToInteger (1,10));
        MAXDEEP = new Parameter<Integer>("calculus","Integrate", "maxDeep", "The maximum deep (2^maxDeep) to explore for functin characteristics.", 12, new ConvertIntervalToInteger (1,20));
        ParameterManager.addParameter(TOL);        
        ParameterManager.addParameter(MINDEEP);        
        ParameterManager.addParameter(MAXDEEP);        
    }
    
    private FunctionPrefix f;
    
    public CalIntegrateAdaptive() {
        super("calculus", "Integrate", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    } 
    
    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        minDeep = MINDEEP.getVal();
        maxDeep = MAXDEEP.getVal();
        tol = TOL.getVal();        
        f = FunctionPrefix.getFunction(input.getFirst().getName());
        final int a = f.getNumbArgs();                
        final int s = input.size(); //cannot be defined earlyer because the size may be decreased by 1
        if ((s-1)%a!=0) throwNewCalculusException ("The number of input points "+(s-1)+" does not match with function arguments "+a);
        ListIntervals result = new ListIntervals();
        for (int i=1;i<s;i+=a) {
            ListIntervals subInput = input.subList(i, i+a);
            result.addAll(highOrderIntegrate(subInput));
        }
        return result; 
    }
            
    private ListIntervals highOrderIntegrate (ListIntervals input) throws Exception {
        final int s = input.size();
        for (int i=0;i<s;++i) {
            if (input.get(i).isIntervalList()) {
                throwNewCalculusException ("Higher order of integration is not implemented yet.");
            }
        }
        return firstOrderIntegrate (input);

    }
    
    private ListIntervals firstOrderIntegrate (ListIntervals input) throws Exception {
        final int s = input.size();
        for (int i=0;i<s;++i) {
            Interval in = input.get(i);
            if (! in.isIntervalPoint()) {
                return integrate (in, input, i);
            }
        }
        
        return f.compute(input);
    }
    
    private ListIntervals integrate (Interval interv, ListIntervals input, int intervIndex) throws Exception {
        ListIntervals lInput = new ListIntervals (input);
        lInput.set(intervIndex, new IntervalPoint(interv.getLeft()));
        ListIntervals fLeft = firstOrderIntegrate(lInput); 
        
        ListIntervals rInput = new ListIntervals (input);
        rInput.set(intervIndex, new IntervalPoint(interv.getRight()));
        ListIntervals fRight = firstOrderIntegrate(rInput); 
                 
        return adaptiveIntegration(interv, input, intervIndex, fLeft, fRight, 0);
    }
    
    private ListIntervals adaptiveIntegration(Interval interv, ListIntervals input, int intervIndex, ListIntervals fLeft, ListIntervals fRight, int deep) throws Exception {
        final double value = interv.getValue();
        IntervalReal lInterv = new IntervalReal (interv.getLeft(), value);
        IntervalReal rInterv = new IntervalReal (value, interv.getRight());
        
        ListIntervals nInput = new ListIntervals (input);
        nInput.set(intervIndex, new IntervalPoint(value));
        ListIntervals fCenter = firstOrderIntegrate(nInput);        
        
        if (deep < minDeep) {//too few sectors, some function discontinuity may not be found
            return SUM.compute(adaptiveIntegration(lInterv, nInput, intervIndex, fLeft, fCenter, deep+1), adaptiveIntegration(rInterv, nInput, intervIndex, fCenter, fRight, deep+1));
        } else if (deep > maxDeep) {//too many sectors, it is taking too long
            return MUL.compute(RANGE.compute(new ListIntervals(interv)), fCenter);
        } else if (isGoodTrapezoid(fLeft, fCenter, fRight)) {
            return MUL.compute(RANGE.compute(new ListIntervals(interv)), fCenter);
        } else {
            return SUM.compute(adaptiveIntegration(lInterv, nInput, intervIndex, fLeft, fCenter, deep+1), adaptiveIntegration(rInterv, nInput, intervIndex, fCenter, fRight, deep+1));
        }
    }    

    private boolean isGoodTrapezoid(ListIntervals fLeft, ListIntervals fCenter, ListIntervals fRight) throws Exception {
        final int s=fCenter.size();
        for (int i=0;i<s;++i) {// to avoid division by 0
            if (fCenter.get(i).getValue() == 0.) return false;
        }
        ListIntervals dif = ABS.compute(DIV.compute(SUB.compute(SUB.compute(SUM.compute(fLeft,fRight),fCenter),fCenter),fCenter));        
        final double totTol = tol*s;
        for (int i=0;i<s;++i) {
            if (dif.get(i).getValue() > totTol) return false;
        }
        return true;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }
}
