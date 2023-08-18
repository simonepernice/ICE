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
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatInternalBuilder extends FunctionPrefix {
    private final static String[] EXAMPLE = {"1,2"};
    private final static String[] RESULT = {"(1,2)#2"};
    private final static DomainList DOMAIN = new DomainList ();

    public MatInternalBuilder() {
        super("matrix", "matrix builder from []", (byte) -1, DOMAIN, false, true, "... should not be instantiated, it is recalled by [].", EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        if (input.rowSize() > 1) return input; //that is to use main [ [a, b], [c, d] ] in the matrix definition
        return new ListIntervalsMatrix (input, input.size());

    }

}
