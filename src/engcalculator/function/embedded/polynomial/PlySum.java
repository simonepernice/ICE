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
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PlySum extends FunctionInfix {

    private final static String HELP = "(poly1) ... (poly2) returns the polynomial given by the sum of the two polynomials.\npoly1 and poly2 should contain the coefficients of the two polynomial ordered from the higher to lower grades.";
    private final static String[] EXAMPLELEFT = {"1,2","1","3,4,1"};
    private final static String[] EXAMPLERIGHT = {"1,4","3,4,1","1"};
    private final static String[] RESULT = {"2, 6","3,4,2","3,4,2"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalComplex());

    private final static AriSum SUM = new AriSum(); 
    
    public PlySum() {
        super("polynomial", "+++++", (byte) -1, (byte) -1, INTERVALDOMAIN, INTERVALDOMAIN, false, true, HELP, EXAMPLELEFT, EXAMPLERIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        ListIntervals ls = new ListIntervals(), rs = new ListIntervals ();
        ls.addAll(leftSide);
        rs.addAll(rightSide);
        Polynomial.setSameSize(ls, rs);
        ListIntervals result =  SUM.compute(ls, rs);  
        Polynomial.delTrailingZeroCoefficients(result);
        return result;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        MeasurementUnit mu = leftSide.getMeasurementUnit();
        if (! rightSide.getMeasurementUnit().equals(mu)) throwNewMeasurementUnitException("The measurement units of the agruments do not matchs: "+leftSide.toString()+" and "+rightSide.toString());
        LinkedList<MeasurementUnit> result = new LinkedList<MeasurementUnit> ();
        result.add(mu);
        return result;
    }
}
