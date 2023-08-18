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

import Jama.EigenvalueDecomposition;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import Jama.Matrix;
import engcalculator.domain.DomainListMatrix;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.function.CalculusException;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatPower extends FunctionInfix {
    private final static String HELP = "mat ... p returns the given square matrix mat rised to the power p.\nUse eigen value/vector decomposition: A^n = VEC * VAL^n * VEC^-1. Uses JAMA library.";
    private final static String[] EXAMPLELEFT = {"[3,4],[6,9]"};
    private final static String[] EXAMPLERIGHT = {"3"};
    private final static String[] RESULT = {"([3,4],[6,9])**([3,4],[6,9])**([3,4],[6,9])"};
    private final static DomainList LEFTDOMAIN = new DomainListMatrix (0, new DomainIntervalPoint());
    private final static DomainList RIGHTDOMAIN = new DomainList (new DomainIntervalPoint());

    public MatPower() {
        super("matrix", "^^", (byte) -1, (byte) 1, LEFTDOMAIN, RIGHTDOMAIN, false, true, HELP, EXAMPLELEFT, EXAMPLERIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input, ListIntervals power) throws Exception {
        try {
            EigenvalueDecomposition ed = new Matrix(MatConversion.IntervalList2Array(input)).eig();
            Matrix vect = ed.getV();
            Matrix valu = ed.getD();
            final double pwr = power.getFirst().getValue();
            for (int i=0; i<input.columnSize(); ++i)
                valu.set(i, i, Math.pow(valu.get(i, i),pwr));
            return MatConversion.Array2IntervalList(vect.times(valu).times(vect.inverse()).getArray());
        } catch (Exception e){
            throwNewCalculusException (e.toString());
            return null;//this will never be reached unless error check are disabled
        }
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException, CalculusException {
        if (! rightSide.getMeasurementUnit().equals(MeasurementUnit.PURE)) throwNewMeasurementUnitException("The measurement units on the right side should be a pure value while it was found: "+rightSide.toString());
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());
        mua.mul((float) rightSide.getFirst().getValue());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(mua.toMeasurementUnit());
        return res;  
    }    

}
