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
import engcalculator.function.embedded.power.PowSqr;
import engcalculator.function.embedded.power.PowSqrt;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaStandardDeviation extends FunctionPrefix {
    private final static StaAverage AVERAGE = new StaAverage ();
    private final static AriSub SUB = new AriSub();
    private final static PowSqr SQR = new PowSqr();
    private final static PowSqrt SQRT = new PowSqrt();
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    private final static String HELP = "... (list) returns the square root of the average of the difference among the list of value and its average value squared.\nIt computes the average from a sample (dividing by n-1).";
    private final static String[] EXAMPLE = {"1,1,1","1,2,1,2"};
    private final static String[] RESULT = {"0","sqrt(1/3)"};

    public StaStandardDeviation() {
        super("statistic", "StandardDeviation", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return SQRT._compute(AVERAGE.computeSubAverage(1, SQR.compute(SUB.compute(AVERAGE.compute(input), input))));
    }
}
