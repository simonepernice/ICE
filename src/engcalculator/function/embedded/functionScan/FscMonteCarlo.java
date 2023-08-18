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
import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.interval.ItvProper;
import engcalculator.function.embedded.statistic.StaFrequencyBinNormalized;
import engcalculator.function.embedded.statistic.StaRandomGenerator;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalList;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FscMonteCarlo extends FunctionPrefix {
    private final static String HELP = "... ($func, numbSimulations, numbBaskets, inputValueInterval1, .., inputValuen) runs the MonteCarlo simulation executing func on random values taken from (inputValueInterval1, .., inputValueIntervaln) for numbSimulations times and collecting the results in the given numbBaskets.\nThis function is limititate, for a general purpose Monte Carlo analysis see statRandomList help. ... runs the montecarlo analysis on the input function. The output is a matrix form which can be used for plot windows. ... requires the function to check, the number of simulation cycles, the number of baskets, and the ranges of the input values. The ranges should be espressed as intervals. A proper interval means an uniform distribution, a not proper interval means a normal distribution with average on the interval mid point and three standard deviations on the interval extremes.";
    private final static String[] EXAMPLE = {"($mul,$a)='a*a';ms = functionScanMonteCarlo($mul,10000,100,1_10); mulstat := ms; abs(calculusIntegrate($mulstat,1_100)-1)<10m"};
    private final static String[] RESULT = {"true"};
    private final static ItvProper PROPER = new ItvProper();
    private final static StaFrequencyBinNormalized FREQUENCY_BIN_NORMALIZED = new StaFrequencyBinNormalized();
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(-1), new DomainLogicAnd(new DomainIntervalPoint(), new DomainInteger(), new DomainIntervalPositive()), new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntervalPositive()), new DomainIntervalReal());
    
    public FscMonteCarlo() {
        super("functionScan", "MonteCarlo", (byte) -4, DOMAIN, false, true, HELP, EXAMPLE , RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        final ListIntervals ranges = input.subList(3, input.size());
        final int steps = (int) input.get(1).getValue();

        ListIntervals yDomain = f._compute(PROPER.compute(ranges));

        if (yDomain.size() != 1) throwNewCalculusException ("The function should provide just one value as output while was found "+yDomain.size());
        if (! yDomain.getFirst().isProper()) throwNewCalculusException ("The function should provide a proper interval as output while found "+yDomain.getFirst());

        ListIntervals result = new ListIntervals ();
        result.append(input.get(2));  //to set the number of bins
        ListIntervals data = new ListIntervals ();
        for (int i=0; i<steps; ++i) { //data is loaded for frequencyBin
            data.append(f._compute(randomList(ranges)).getFirst());
        }
        result.append(new IntervalList(data));
           
        return FREQUENCY_BIN_NORMALIZED._compute(result);
    }

    private ListIntervals randomList (ListIntervals ranges) {
        ListIntervals result = new ListIntervals ();
        for (Interval i : ranges)
            result.add(randomInterval(i));
        return result;
    }

    protected Interval randomInterval (Interval range) {
        if (range.isProper()) return new IntervalPoint(range.getLeft()+StaRandomGenerator.nextUniformRandom()*range.getRange());
        else return new IntervalPoint(range.getValue()+(StaRandomGenerator.nextGaussianRandom()/3.*(range.getRange()/(-2.))));
        
    }
}
