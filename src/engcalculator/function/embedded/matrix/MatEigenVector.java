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
import engcalculator.domain.DomainList;
import Jama.Matrix;
import engcalculator.domain.DomainListMatrix;
import engcalculator.domain.DomainIntervalPoint;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatEigenVector extends MatPrefixFunction {
    private final static String HELP = "... [mat] returns the eigen vector matrix of the given square matrix mat.\nUses JAMA library.";
    private final static String[] EXAMPLE = {"vec = matrixEigenVector((13,5,2,4)#2);val=matrixEigenValue((13,5,2,4)#2);vec ** val ** matrixInvert(vec)"};
    private final static String[] RESULT = {"(13,5,2,4)#2"};
    private final static DomainList DOMAIN = new DomainListMatrix (new DomainIntervalPoint());

    public MatEigenVector() {
        super("EigenVector", (byte) -1, DOMAIN, false, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals input) throws Exception {
        try {
            return MatConversion.Array2IntervalList(new Matrix(MatConversion.IntervalList2Array(input)).eig().getV().getArray());
        } catch (Exception e) {
            throwNewCalculusException(e.toString());
            return null;//this will never be reached unless error check are disabled
        }
    }

}
