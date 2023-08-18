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
import Jama.Matrix;
import engcalculator.domain.DomainListMatrix;
import engcalculator.domain.DomainIntervalPoint;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatDeterminant extends MatPrefixFunction {
    private final static String HELP = "... [matA] returns the determinant of the given square matrix matA. Uses JAMA library.";
    private final static String[] EXAMPLE = {"(2.5,3,4,6)#2"};
    private final static String[] RESULT = {"3"};
    private final static DomainList DOMAIN = new DomainListMatrix (0, new DomainIntervalPoint());

    public MatDeterminant() {
        super("Determinant", (byte) -1, DOMAIN, false, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals input) throws Exception {
        try {
            return new ListIntervals (new IntervalPoint(new Matrix(MatConversion.IntervalList2Array(input)).det()));
        } catch (Exception e) {
            throwNewCalculusException(e.toString());
            return null;//this will never be reached unless error check are disabled
        }
    }

}
