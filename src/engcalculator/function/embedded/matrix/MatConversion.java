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

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatConversion {
    public static double[][] IntervalList2Array (ListIntervals inputMatrix) {
        final int r = inputMatrix.rowSize(), c = inputMatrix.columnSize();
        double[][] outputMatrix = new double [r][c];
        int ir = 0, ic = 0;
        for (Interval i : inputMatrix) {
            outputMatrix[ir][ic] = i.getValue();
            ++ ic;
            if (ic >= c) {
                ic = 0;
                ++ ir;
            }
        }
        return outputMatrix;
    }

    public static ListIntervals Array2IntervalList (double[][] inputMatrix) {
        final int r = inputMatrix.length, c = inputMatrix[0].length;
        ListIntervalsMatrix outputMatrix = new ListIntervalsMatrix(c);
        for (int ir=0; ir < r; ++ir)
            for (int ic=0; ic < c; ++ic)
                outputMatrix.add(new IntervalPoint(inputMatrix[ir][ic]));
        return outputMatrix;
    }
}
