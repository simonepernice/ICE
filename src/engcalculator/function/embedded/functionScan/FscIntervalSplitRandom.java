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

import engcalculator.domain.DomainIntervalProper;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.list.LisLinear;
import engcalculator.function.embedded.statistic.StaRandomGenerator;
import engcalculator.function.parameter.ConvertIntervalToIntervalPoint;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FscIntervalSplitRandom extends FunctionPrefix {
    private final static Parameter<IntervalPoint> SAMPLES;
    private final static LisLinear LIS_LINEAR = new LisLinear();
        
    private final static String HELP = "... (interval) split the intervals in 1000 (by default) equal points uniformely distribuited in the given interval and then arrage the sample randomly.\nIt is useful to evaluate expression where the same intervals is present more than one. Eventually with Join will be possible to check the result. With systemSetup ($..., numberOfSamples) it is possible to set the default number of samples.";
    private final static String[] EXAMPLE = {";functionScanIntervalJoin functionScanIntervalSplitRandom (1_19)"};
    private final static String[] RESULT = {"1_19"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalProper());

    static {
        SAMPLES = new Parameter<IntervalPoint>("functionScan","IntervalSplitRandom", "samples", "The number of samples in which the interval is split", new IntervalPoint(1000), new ConvertIntervalToIntervalPoint (1,10000));        
        ParameterManager.addParameter(SAMPLES);
    }
    
    public FscIntervalSplitRandom() {
        super("functionScan", "IntervalSplitRandom", (byte) 1, INTERVALDOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals li = new ListIntervals(SAMPLES.getVal());
        li.addAll(input);        
        li = LIS_LINEAR._compute(li);                
        ListIntervals result = new ListIntervals ();
        int s;
        while ((s=li.size())>0) {
            result.append(li.remove(StaRandomGenerator.nextUniformRandomInteger(0, s)));
        }
        return result;
    }

}
