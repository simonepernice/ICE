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
import engcalculator.domain.DomainListMatrix;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.list.LisGetElements;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaCovariance extends FunctionPrefix {
    private final static StaAverage AVERAGE = new StaAverage ();
    private final static LisGetElements GET_VECTOR = new LisGetElements();
    private final static AriSub SUB = new AriSub();
    private final static AriMul MUL = new AriMul();
    private final static DomainList DOMAIN = new DomainListMatrix ();

    private final static String HELP = "... [matrix] returns the covariance of two column of the matrix being every column an input data.";
    private final static String[] EXAMPLE = {"(1,2,3,4,5,6)#2"};
    private final static String[] RESULT = {"4"};

    public StaCovariance() {
        super("statistic", "Covariance", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals x = GET_VECTOR._compute(input, new ListIntervals (new IntervalReal(0, -1)).append(new IntervalPoint(0)));
        ListIntervals y = GET_VECTOR._compute(input, new ListIntervals (new IntervalReal(0, -1)).append(new IntervalPoint(1)));
        return AVERAGE.computeSubAverage(1, MUL.compute(SUB.compute(x, AVERAGE._compute(x)), SUB.compute(y, AVERAGE._compute(y))));
    }
}
