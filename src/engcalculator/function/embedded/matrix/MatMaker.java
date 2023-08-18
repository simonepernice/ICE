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

package engcalculator.function.embedded.matrix;

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainInteger;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatMaker extends FunctionPrefix {
    private final static String HELP = "... (rows, columns, f(r,c)) builds a matrix with give number of r rows and c columns. The elements of the matrix are obtained evaluating f(row, colums) for each element.";
    private final static String[] EXAMPLE = {"3,3,defineLambdaFunction('conditionalIfTrue($r==$c,1,0)')"};
    private final static String[] RESULT = {"matrixDiagonal(1, 1, 1)"};
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalPoint(), new DomainIntervalPositive(), new DomainInteger() ),new DomainLogicAnd (new DomainIntervalPoint(), new DomainIntervalPositive(), new DomainInteger() ), new DomainFunctionPrefix(2));

    public MatMaker() {
        super("matrix", "Maker", (byte) 3, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        Iterator<Interval>  i = input.iterator();
        final int r = (int) i.next().getValue();
        final int c = (int) i.next().getValue();
        FunctionPrefix f = FunctionPrefix.getFunction(i.next().toString());
        ListIntervalsMatrix result = new ListIntervalsMatrix (c);
        for (int ir=0; ir < r; ++ir)
            for (int ic=0; ic < c; ++ic)
                result.addAll(f.compute(new ListIntervals(new IntervalPoint(ir)).append(new IntervalPoint(ic))));
        return result;

    }

}
