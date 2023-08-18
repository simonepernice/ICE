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

import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisSerieInteger extends FunctionInfix {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalPoint(), new DomainInteger()));

    private final static String HELP = "a ... b returns the integer list from a to b.\n... is thought to build a list of integer numbers to be used to get intervals from list. If ... is applied two a couple of integers it returns a list going from the first to the last element. If ... is applied to a list and an integer it gets the value from the list reading with the given step.";    
    private final static String[] EXAMPLELEFT = {"2","6","1;1 .. 10 .. 2"};
    private final static String[] EXAMPLERIGHT = {"6","4",""};
    private final static String[] RESULT = {"2, 3, 4, 5, 6","6,5,4","1,3,5,7,9"};

    public LisSerieInteger() {
        super("list", "..", (byte) -1, (byte) 1, DOMAIN, DOMAIN, false, true, HELP, EXAMPLELEFT, EXAMPLERIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute (ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        ListIntervals result = new ListIntervals();

        if (leftSide.size() == 1) {
            int begin = (int) leftSide.getFirst().getLeft();
            final int end = (int) rightSide.getFirst().getRight();

            if (begin <= end)
                for (; begin <= end; ++begin)
                    result.add(new IntervalPoint (begin));
            else
                for (; end <= begin; --begin)
                    result.add(new IntervalPoint (begin));
            
        } else {
            final int step = (int) rightSide.getFirst().getRight();
            final int size = leftSide.size();
            for (int i=0; i<size; i+= step)
                result.append(leftSide.get(i));
        }
        
        return result;

    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        MeasurementUnit mul = leftSide.getMeasurementUnit();
        if (! mul.equals(rightSide.getMeasurementUnit())) throwNewMeasurementUnitException("The measurement units of the agruments do not matchs: "+leftSide.toString()+" and "+rightSide.toString());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(mul);
        return res;   
    }    
}
