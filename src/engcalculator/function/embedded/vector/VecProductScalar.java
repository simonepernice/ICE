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

package engcalculator.function.embedded.vector;

import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.embedded.diffuse.DifSumUp;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class VecProductScalar extends VecInfixFunction {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());
	
    private final static String HELP = "(vectorA) ... (vectorB) multiply the vectorA represented by its list for the vectorB represented by its list.\nIt sum up the product of the respective elements as per Kaucher arithmetic. ";
    private final static String[] EXAMPLE_LEFT = {"(1,2,3,4)"};
    private final static String[] EXAMPLE_RIGHT = {"(2,3,4,5)"};
    private final static String[] RESULT = {"2+6+12+20"};
    private final static AriMul MUL = new AriMul();
    private final static DifSumUp SUMUP = new DifSumUp();

    public VecProductScalar() {
        super ("*.", (byte) -1, (byte)  -1, DOMAIN, DOMAIN, false, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {                
        return SUMUP._compute(MUL.compute(leftSide, rightSide));
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());
        mua.add(rightSide.getMeasurementUnit());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(mua.toMeasurementUnit());
        return res;          
    }
}
