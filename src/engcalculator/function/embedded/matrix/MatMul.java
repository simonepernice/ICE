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

import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainListMatrix;
import engcalculator.function.CalculusException;
import engcalculator.function.MeasurementUnitException;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatMul extends MatInfixFunction {
    private final static DomainListMatrix DOMAIN = new DomainListMatrix (new DomainIntervalComplex());
    private final static String HELP = "[matA ... [matB] multiply the matrix matA for the matrix matB row by columns as per Kaucher arithmetic.";    
    private final static String[] EXAMPLE_LEFT = {"(1,2,3,4)#2"};
    private final static String[] EXAMPLE_RIGHT = {"(5,6,7,8)#2"};
    private final static String[] RESULT = {"(19,22,43,50)#2"};
    private final static AriMul MUL = new AriMul();
    private final static AriSum SUM = new AriSum();

    public MatMul() {
        super ("**", (byte) -1, (byte)  -1, DOMAIN, DOMAIN, false, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {        
        if (leftSide.columnSize() != rightSide.rowSize()) throwNewCalculusException ("The matrix's column "+leftSide.columnSize()+" and rows "+rightSide.rowSize()+" do not match for multiplication.");
        final int leftRow = leftSide.rowSize(), rightCol = rightSide.columnSize(), size = leftSide.columnSize();
        ListIntervals result = new ListIntervalsMatrix ((byte) rightCol);
        Interval accumulator;
        int r, c, i;

        for (r = 0; r < leftRow; ++r) {            
            for (c = 0; c < rightCol; ++ c) {
                accumulator = new IntervalPoint(0);
                for (i = 0; i < size ; ++ i) {
                    accumulator = SUM._computeIntervals(accumulator, MUL._computeIntervals(leftSide.get(r, i), rightSide.get(i, c)));
                }
                result.add(accumulator);
            }            
        }

        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());
        mua.add(rightSide.getMeasurementUnit());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(mua.toMeasurementUnit());
        return res;           
    }    
}
