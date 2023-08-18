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
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatBuilder extends FunctionInfix {
    private final static DomainList DOMAINLEFT = new DomainList ();
    private final static DomainList DOMAINRIGHT = new DomainList (new DomainIntervalReal());
    
    private final static String HELP = "(list) ... columns : given the list and the number of columns of the matrix '...' builds up a matrix displacing the elements of the list in the rows of the matrix.\nIf the value is zero (and the list is a matrix) the matrix is transfomed in a list.";
    private final static String[] EXAMPLE_LEFT = {"5,6,7,8","5,6,7,8","5,6,7,8","[1,2],[3,4]","'a','b','c'"};
    private final static String[] EXAMPLE_RIGHT = {"4","4_0","0_4","0","1"};
    private final static String[] RESULT = {"[5,6,7,8]","[5,6,7,8]","[5],[6],[7],[8]","1,2,3,4","['a'],['b'],['c']"};

    public MatBuilder() {
        super ("matrix", "#", (byte) -1, (byte) 1, DOMAINLEFT, DOMAINRIGHT, false, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals left, ListIntervals right) throws Exception {
        if (right.getFirst().equals(Interval.ZERO)) {
            return new ListIntervals(left);
        } else if (right.getFirst().isIntervalPoint() || right.getFirst().getRight() == 0) {
            if (left.size() % right.getFirst().getLeft() != 0) throwNewCalculusException ("The number of columns of the matrix "+right.getFirst().getLeft()+" must be an integer multiple of the number of elements of the list "+left.size());
            return new ListIntervalsMatrix(left, (int) right.getFirst().getLeft());
        } else if (right.getFirst().getLeft() == 0) {
            if (left.size() % right.getFirst().getRight() != 0) throwNewCalculusException ("The number of columns of the matrix "+right.getFirst().getRight()+"must be an integer multiple of the number of elements of the list "+left.size());
            return new ListIntervalsMatrix(left, (int) (left.size() / right.getFirst().getRight() ));
        } else throwNewCalculusException ("The matrix builder argument is not proper.");
        return null;//this will never be reached unless error check are disabled
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        if (! MeasurementUnit.PURE.equals(rightSide.getMeasurementUnit())) throwNewMeasurementUnitException("The measurement units of the agruments at right side should be pure while it was found"+rightSide.toString());
        return leftSide.getMeasurementUnitList();
    }    
}
