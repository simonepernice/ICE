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
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalList;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PlyDiv extends FunctionInfix {

    private final static String HELP = "(poly1) ... (poly2) returns the polynomials the coefficients of the quotient of the division between poly1 and poly2 and its reminder.\nThe result is provided in two sub-lists ({q},{r}), where q contains quotient and r the remider. The coefficients of the two polynomials are ordered from the higher to lower grades.";
    private final static String[] EXAMPLELEFT = {"(1,2) ***** (1,4)"};
    private final static String[] EXAMPLERIGHT = {"1,4"};
    private final static String[] RESULT = {"{1, 2},{0}"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalComplex());

    private final static AriMul MUL = new AriMul(); 
    private final static AriDiv DIV = new AriDiv(); 
    private final static AriSub SUB = new AriSub(); 
    
    public PlyDiv() {
        super("polynomial", "/////", (byte) -1, (byte) -1, INTERVALDOMAIN, INTERVALDOMAIN, false, true, HELP, EXAMPLELEFT, EXAMPLERIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        ListIntervals div = new ListIntervals(), spr = new ListIntervals (), tmp;
        spr.addAll(leftSide);
        div.add(Interval.ZERO);
        while (spr.size()>=rightSide.size()) {
            tmp = new ListIntervals(DIV._computeIntervals(spr.getFirst(), rightSide.getFirst()));
            div.add(tmp.getFirst());
            tmp = MUL.compute(tmp, rightSide);
            Polynomial.increaseGradeTo(tmp, spr.size());
            //To save the computation of difference on first elements it is skipped 
            spr.removeFirst();
            tmp.removeFirst();
            if (spr.size() == 0) {
                spr.add(Interval.ZERO);
                break;
            }
            else spr = SUB.compute(spr, tmp);
        }
        Polynomial.delTrailingZeroCoefficients(div);
        return new ListIntervals ().append(new IntervalList (div)).append(new IntervalList (spr));               
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());
        mua.sub(rightSide.getMeasurementUnit());
        LinkedList<MeasurementUnit> result = new LinkedList<MeasurementUnit> ();
        result.add(mua.toMeasurementUnit());
        return result;
    }
}
