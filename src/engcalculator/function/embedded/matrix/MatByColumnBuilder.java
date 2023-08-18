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
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatByColumnBuilder extends FunctionInfix {

    private final static DomainList DOMAIN = new DomainList();
    
    private final static String HELP = "[matA] ...  [matB] joins two matrices by column.\nThis command can usually be replaced by ',' which makes the more significant action. However if the matrices have same rows and column ',' would always join by row, which may no be the right thing to do." ;    
    private final static String[] EXAMPLE_LEFT = {"6_7", "1_2,3_4", "(1,2)#1"};
    private final static String[] EXAMPLE_RIGHT = {"4_5", "5_6,7_8", "(3,4)#1"};
    private final static String[] RESULT = {"(6_7,4_5)#1", "(1_2, 3_4, 5_6, 7_8)#2", "(1,2,3,4)#1"};

    public MatByColumnBuilder() {
        super("matrix", ",|", (byte) -1, (byte) -1, DOMAIN, DOMAIN, false, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals left, ListIntervals right) throws Exception {
        ListIntervals result;

        if (left.columnSize() != right.columnSize()) {
            throwNewCalculusException("The number of columns "+left.columnSize()+" , "+right.columnSize()+" does not match between the matrices");
        }

        result = new ListIntervalsMatrix(left.columnSize());
        result.addAll(left);
        result.addAll(right);

        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        if (! rightSide.getMeasurementUnit().equals(MeasurementUnit.PURE)) throwNewMeasurementUnitException("The measurement units on the right side should be a pure value while it was found: "+rightSide.toString());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(leftSide.getMeasurementUnit());
        return res;   
    }       
}
