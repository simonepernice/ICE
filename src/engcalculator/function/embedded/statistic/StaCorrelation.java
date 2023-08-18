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
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.list.LisGetElements;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaCorrelation extends FunctionPrefix {
    private final static StaCovariance COVARIANCE = new StaCovariance();
    private final static StaStandardDeviation DEVIATION = new StaStandardDeviation();
    private final static LisGetElements GET_VECTOR = new LisGetElements();
    private final static AriDiv DIV = new AriDiv();
    private final static AriMul MUL = new AriMul();
    private final static DomainList DOMAIN = new DomainListMatrix ();

    private final static String HELP = "... [matrix] returns the correlation of two column the matrix being every column an input data.";
    private final static String[] EXAMPLE = {"(1,2,4,8,5,10)#2"};
    private final static String[] RESULT = {"1"};

    public StaCorrelation() {
        super("statistic", "Correlation", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals x = GET_VECTOR._compute(input, new ListIntervals (new IntervalReal(0, -1)).append(new IntervalPoint(0)));
        ListIntervals y = GET_VECTOR._compute(input, new ListIntervals (new IntervalReal(0, -1)).append(new IntervalPoint(1)));
        return DIV._compute(COVARIANCE._compute(input), MUL._compute(DEVIATION._compute(x), DEVIATION._compute(y)));
    }
}
