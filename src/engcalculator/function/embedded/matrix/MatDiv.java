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
public final class MatDiv extends MatInfixFunction {
    private final static String HELP = "[matA] ... [matB] returns the division between square matrices matA and matB.\nInternalli it actually perform the inversion of matB and their multiplication. Uses JAMA library.";
    private final static String[] EXAMPLELEFT = {"[3,4],[6,9]"};
    private final static String[] EXAMPLERIGHT = {"[3,4],[6,9]"};
    private final static String[] RESULT = {"[1,0],[0,1]"};
    private final static DomainList DOMAIN = new DomainListMatrix (0, new DomainIntervalPoint());

    public MatDiv() {
        super("//", (byte) -1, (byte) -1, DOMAIN, DOMAIN, false, HELP, EXAMPLELEFT, EXAMPLERIGHT, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals a, ListIntervals b) throws Exception {
        try {
            Matrix am = new Matrix(MatConversion.IntervalList2Array(a));
            Matrix bm = new Matrix(MatConversion.IntervalList2Array(b));
            return MatConversion.Array2IntervalList(am.times(bm.inverse()).getArray());
        } catch (Exception e){
            throwNewCalculusException (e.toString());
            return null;//this will never be reached unless error check are disabled
        }
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());        
        mua.sub(rightSide.getMeasurementUnit());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(mua.toMeasurementUnit());
        return res;           
    }
}
