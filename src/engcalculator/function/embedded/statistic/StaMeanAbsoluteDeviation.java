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

package engcalculator.function.embedded.statistic;


import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.complex.CpxAbs;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaMeanAbsoluteDeviation extends FunctionPrefix {
    private final static StaAverage AVE = new StaAverage();
    private final static AriSub SUB = new AriSub();
    private final static CpxAbs ABS = new CpxAbs();
	
    private final static String HELP = "... (list) returns the mean of the absolute variation between samples of the list and the mean of them.\nIt is an index of how much the samples are far from the mean. Usually it is ERRONEOUSLY used the standard deviation to indicate the MAD.";
    private final static String[] EXAMPLE = {"1, 2, 3, 4"};
    private final static String[] RESULT = {"statisticAverage(abs((1 .. 4)-statisticAverage(1 .. 4)))"};
    
	private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public StaMeanAbsoluteDeviation() {
        super("statistic", "MeanAbsoluteDeviation", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return AVE.compute(ABS.compute(SUB.compute(input,AVE.compute(input))));
    }

}
