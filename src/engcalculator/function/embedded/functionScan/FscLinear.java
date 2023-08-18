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

package engcalculator.function.embedded.functionScan;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.embedded.setInterval.SInIntersection;
import engcalculator.function.embedded.setInterval.SInUnion;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.interval.ItvProper;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.interval.IntervalPoint;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FscLinear extends FunctionPrefix {
    private final static String HELP = "... ($func, interval1, .., intervaln) applies the Kaucher definition for the given func at the given intervals.\nFor each input interval it computes the function at a number of points to run a total of 1/10 of the maximum cycles set in ICE (with stepaccuracy command) equally spaced inside each input interval. When the input interval is proper it joins the result intervals, otherwise it intersects them. That a is very slow procedure. However it may give a smaller result than standard interval arithmetic althought the resault is NOT sure because just few points of the functions are checked (it is correct for sure only if the input function is linear respect to all the input variables). For example it is not affected by the variable repetition issue: a function computing a - a will returns exactly 0. For a faster command look at randomfunctionscan, which computes the function at random points instead of using a deterministic algorithm.";
    private final static String[] EXAMPLE = {"($sum,$a,$b)='a+b';(functionScanLinear($sum,1_3,4_7),functionScanLinear($sum,1_3,7_4),functionScanLinear($sum,3_1,4_7),functionScanLinear($sum,3_1,7_4))"};
    private final static String[] RESULT = {"(5_10,8_7,7_8,10_5)"};
    private final static SInUnion UNION = new SInUnion();
    private final static SInIntersection INTERSECTION = new SInIntersection();
    private final static engcalculator.function.embedded.list.LisLinear LINLIST = new engcalculator.function.embedded.list.LisLinear();
    private final static ItvProper PROPER = new ItvProper ();
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(-1), new DomainIntervalReal());
    private final static boolean FSDEBUG = DEBUG;
    private final static Parameter<Integer> SAMPLES;
    
    static {
        SAMPLES = new Parameter<Integer>("functionScan","Linear", "samples", "The number of samples in which the interval is split", 10000, new ConvertIntervalToInteger  (1,1000000));        
        ParameterManager.addParameter(SAMPLES);
    }    

    public FscLinear() {
        super("functionScan", "Linear", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE , RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        if (FSDEBUG)  System.out.println("New run of functionscan on "+input);
        
        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        final int variables = input.size()-1;
        final int steps = (int) Math.max(3, Math.pow(SAMPLES.getVal(),1./variables));
        if (FSDEBUG)  System.out.println("Steps: "+steps);
        
        LinkedList<ListIntervals> domain = new LinkedList<ListIntervals> ();
        for (int i = 1;i < input.size(); ++i) {
            ListIntervals step = new ListIntervals (new IntervalPoint(steps));
            domain.add(LINLIST._compute(step.append(PROPER._computeIntervals(input.get(i)))));
        }

        ListIntervals result = null, currentVarResult = null;
        int[] index = new int[variables];
        index[0] = -1;//to begin from index 0,0,0,0... at the first step

        while (true) {
            int position;

            position = 0;
            while (++index[position] >= steps || 
                    (index[position]>0 && input.get(position+1).isIntervalPoint())) {//that is to scan quickly on point intervals, it does just a run through a point interval
                index[position++] = 0;

                if (result == null) {
                    result = currentVarResult;
                    currentVarResult = null;
                } else if (currentVarResult != null) {
                    if (input.get(position+1).isProper()) result = UNION._compute(result, currentVarResult);
                    else result = INTERSECTION._compute(result, currentVarResult);
                    currentVarResult = null;
                }

                if (FSDEBUG)  System.out.println("Result "+result);
                if (position >= variables) {
                    return result;
                }
            }

            ListIntervals testPoint = new ListIntervals();
            for (int i=0; i < variables; ++i) testPoint.add(domain.get(i).get(index[i]));

            if (FSDEBUG)  System.out.println("Index "+Arrays.toString(index));
            if (FSDEBUG)  System.out.println("Test point "+testPoint);
            
            if (currentVarResult == null) currentVarResult = f._compute(testPoint);
            else {
                if (input.get(1).isProper()) currentVarResult = UNION._compute(currentVarResult, f._compute(testPoint));
                else currentVarResult = INTERSECTION._compute(currentVarResult, f._compute(testPoint));
            }

            if (FSDEBUG)  System.out.println("Current Var Result "+currentVarResult);

        }
    }

}
