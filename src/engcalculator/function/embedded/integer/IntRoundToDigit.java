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

package engcalculator.function.embedded.integer;

import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefixExpandLast;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IntRoundToDigit extends FunctionPrefixExpandLast {
    private final static String HELP = "... (digits, a) returns the closer decimal with given number of digits to the argument a.\nSee also rounding, ceiling and floor.";
    private final static String[] EXAMPLE = {"1, -10.73_10.75","1, 10.11_-5.81", "1, 10.99"};
    private final static String[] RESULT = {"-10.7_10.8", "10.1_-5.8", "11.0"};
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalPoint(), new DomainIntervalPositive()), new DomainIntervalReal());

    public IntRoundToDigit() {
        super("integer", "RoundToDigit", (byte) 2, DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals in) throws Exception {
        Interval input = in.getLast();
        double pow = Math.pow(10.0, in.getFirst().getValue());
        if (input.isIntervalPoint()) {//faster shortcut for point intervals
            double a1 = input.getValue()*pow;
            return new ListIntervals(new IntervalPoint(Math.round(a1)/pow));            
        }
        double a1 = input.getLeft()*pow, a2 = input.getRight()*pow;
        return new ListIntervals(new IntervalReal(Math.round(a1)/pow, Math.round(a2)/pow));
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        MeasurementUnit mu = input.getLast().getMeasurementUnit(); 
        LinkedList<MeasurementUnit>  result = new LinkedList<MeasurementUnit> ();
        result.add(mu);
        return result;
    }    
}
