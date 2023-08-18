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

import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainListMatrix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatTranspose extends MatPrefixFunction {
    private final static String HELP = "... [mat] transposes the matrix mat.\nIt means every element a(i,j) is exchanged with a(j,i).";
    private final static String[] EXAMPLE = {"(1, 2, 3, 4)#2"};
    private final static String[] RESULT = {"(1, 3, 2, 4)#2"};
    private final static DomainListMatrix DOMAIN = new DomainListMatrix (new DomainIntervalComplex());

    public MatTranspose() {
        super("Transpose", (byte) -4, DOMAIN, false, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals __compute(ListIntervals input) throws Exception {
        ListIntervalsMatrix inputMatrix = (ListIntervalsMatrix) input;
        final int rs = input.rowSize(), cs = input.columnSize();
        ListIntervalsMatrix result = new ListIntervalsMatrix (rs);
        for (int c=0; c<cs; ++c)
            for (int r=0; r<rs; ++r)
                result.add(inputMatrix.get(r,c));
        return result;

    }

}
