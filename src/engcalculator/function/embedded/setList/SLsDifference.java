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

package engcalculator.function.embedded.setList;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SLsDifference extends FunctionInfix {
    private final static String HELP = "(listA) ... (listB) returns the difference of the given setlist.\nThe difference is given by the elements of listA not available in listB without duplicates. Note that difference is not commutative while union and intersections are commutative. If an element of the second set does not exist in the first it is not removed neither an error is rised: (1,2) ... (3,2,4) returns 1 although 3, 4 do not exists on the first list.";
    private final static String[] EXAMPLE_LEFT = {"1, 2, 3, 4, 4, 5, 6", "4, 5, 6, 7, 8"};
    private final static String[] EXAMPLE_RIGHT = {"4, 5, 6, 7, 8", "1, 2, 3, 4, 4, 5, 6"};
    private final static String[] RESULT = {"1,2,3", "7,8"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalComplex());
    private final static SLsExtract ESL = new SLsExtract() ;

    public SLsDifference() {
        super ("setList", "----", (byte) -1, (byte) -1, INTERVALDOMAIN, INTERVALDOMAIN, false, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals left, ListIntervals right) throws Exception {
        ListIntervals result = new ListIntervals ();        
        Interval l, r;
        boolean notFund;
        
        for (Iterator<Interval> iL = left.iterator();  iL.hasNext(); ) {
            l = iL.next();
            notFund = true;        
            for (Iterator<Interval> iR = right.iterator(); iR.hasNext(); ) {
                r = iR.next();
                if (l.equals(r)) {
                    notFund = false;
                    break;
                }
            }
            if (notFund) result.append(l);
        }

        result = ESL._compute(result);
        return result;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        MeasurementUnit mul = leftSide.getMeasurementUnit();
        if (! mul.equals(rightSide.getMeasurementUnit())) throwNewMeasurementUnitException("The measurement units of the agruments do not matchs: "+leftSide.toString()+" and "+rightSide.toString());
        return null;
    }
}
