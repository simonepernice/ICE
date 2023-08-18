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

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalProper;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.embedded.setInterval.SInUnion;
import engcalculator.function.embedded.statistic.StaRandomGenerator;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FscRandom extends FunctionPrefix {
    private final static String HELP = "... ($func, interval1, .., intervaln)  computes several times the input function on random Point Interval uniformely distribuited in the input intervals and as result provide the union of all function output.\nThe syntax is ...(function name, list of intervals). That will give a smaller result than standard interval arithmetic althought it is not deterministic (the real interval result may be bigger and each execution will give slightly different result). However that function helps because interval by nature tends to provide bigger result interval than necessry. For example it is not affected by the variable repetition issue of the intervals: a function computing a - a will returns exactly 0, which is not the case of real interval. The iterations are a tenth of the maximum iterations. Work only on proper interval. For a deterministic scan algorithm see linearfunctionscan.";
    private final static String[] EXAMPLE = {"($subtract,$a)='a-a';functionScanRandom ($subtract,1_100)"};
    private final static String[] RESULT = {"0"};
    private final static SInUnion UNION = new SInUnion();
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(-1), new DomainLogicAnd(new DomainIntervalReal(), new DomainIntervalProper()));
    private final static Parameter<Integer> SAMPLES;
    
    static {
        SAMPLES = new Parameter<Integer>("functionScan","Random", "samples", "The number of random samples used within the interval.", 10000, new ConvertIntervalToInteger  (1,1000000));        
        ParameterManager.addParameter(SAMPLES);
    } 
    
    public FscRandom() {
        super("functionScan", "Random", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE , RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        ListIntervals ranges = input.subList(1, input.size());
        ListIntervals result = f._compute(randomList(ranges));
        final int samples = SAMPLES.getVal();
        for (int i=0; i<samples; ++i)
            result = UNION._compute(result, f._compute(randomList(ranges)));
        return result;
    }

    private ListIntervals randomList (ListIntervals ranges) {
        ListIntervals result = new ListIntervals ();
        for (Interval i : ranges)
            result.add(randomInterval(i));
        return result;
    }

    protected Interval randomInterval (Interval range) {
        return new IntervalPoint(range.getLeft()+StaRandomGenerator.nextUniformRandom()*range.getRange());
    }
}
