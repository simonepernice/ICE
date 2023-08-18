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

package engcalculator.function.embedded.list;


import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainIntervalLogic;
import engcalculator.domain.DomainList;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisKeepIfTrue extends FunctionInfix {
    private final static DomainList DOMAINLEFT = new DomainList (new DomainInterval());
    private final static DomainList DOMAINRIGHT = new DomainList (new DomainIntervalLogic());

    private final static String HELP = "(list) ... (boolean list) return the (list) elements whose correspondig (boolean list) elements are != 0.\n ... with trueIf the right side of ... is true (1) it returns its left side, otherwise it does not return anything. It is useful to extract elements from a list.";
    private final static String[] EXAMPLE_LEFT = {"(testkit = 1 .. 10); testkit ? (testkit > 5)"};
    private final static String[] EXAMPLE_RIGHT = {""};
    private final static String[] RESULT = {"6 .. 10"};

    public LisKeepIfTrue() {
        super("list", "?", (byte) -1, (byte) -1, DOMAINLEFT, DOMAINRIGHT, false, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public final ListIntervals _compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        final int s = leftSide.size(); 
        if (s != rightSide.size()) throwNewCalculusException ("Right side has size "+rightSide.size()+", left side has "+s+" instead they should be of the same size.");
        ListIntervals result = new ListIntervals ();
        for (int i=0; i < s; ++i) {
            if (LgcBooleanInterval.i2ib(rightSide.get(i)) == LgcBooleanInterval.TRUE) result.append(leftSide.get(i));
        }
        
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
