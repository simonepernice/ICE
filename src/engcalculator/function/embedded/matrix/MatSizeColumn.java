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
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatSizeColumn extends MatPrefixFunction {
    private final static String HELP = "... [mat] returns the number of columns of the given matrix mat.";
    private final static String[] EXAMPLE = {"(-10_-2, 5_7, -7_-5)#3"};
    private final static String[] RESULT = {"3"};
    private final static DomainList DOMAIN = new DomainList();

    public MatSizeColumn() {        
        super("SizeColumn", (byte) -1, DOMAIN, false, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals input) throws Exception {
        return new ListIntervals ( new IntervalPoint (input.columnSize()));
    }



}
