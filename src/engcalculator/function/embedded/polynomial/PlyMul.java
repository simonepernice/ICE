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
package engcalculator.function.embedded.polynomial;

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PlyMul extends FunctionInfix {

    private final static String HELP = "(poly1) ... (poly2) returns the polynomial given by the multiplication of poly1 and 2.\npoly1 and poly2 should contain the coefficients of the two polynomial ordered from the higher to lower grades. The result is the coefficient of the polynomial ordered from highest to lower grade.";
    private final static String[] EXAMPLELEFT = {"1,2"};
    private final static String[] EXAMPLERIGHT = {"1,4"};
    private final static String[] RESULT = {"1, 6, 8"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalComplex());

    private final static AriMul MUL = new AriMul(); 
    private final static PlySum SUM = new PlySum(); 
    
    public PlyMul() {
        super("polynomial", "*****", (byte) -1, (byte) -1, INTERVALDOMAIN, INTERVALDOMAIN, false, true, HELP, EXAMPLELEFT, EXAMPLERIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        ListIntervals acc = new ListIntervals(), mul = new ListIntervals (), tmp;
        mul.addAll(leftSide);
        acc.add(Interval.ZERO);
        int i = 0;
        while (mul.size()>0) {
            tmp = MUL.compute(rightSide, new ListIntervals(mul.pollLast()));
            for (int j=0; j<i; ++j) tmp.add(Interval.ZERO);
            acc = SUM.compute(acc, tmp);
            ++i;
        }
        return acc;               
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());
        mua.add(rightSide.getMeasurementUnit());
        LinkedList<MeasurementUnit> result = new LinkedList<MeasurementUnit> ();
        result.add(mua.toMeasurementUnit());
        return result;
    }
}
