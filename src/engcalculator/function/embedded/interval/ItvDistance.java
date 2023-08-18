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

package engcalculator.function.embedded.interval;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvDistance extends FunctionPrefix {
    private final static String HELP = "... (A, B) computes the distance between intervals A and B.\nIt is the maximum between the absolute difference for their extreme points.";
    private final static String[] EXAMPLE = {"-10_10, 3_4", "10_-5, 3_4", "10_10, 3_4"};
    private final static String[] RESULT = {"13", "9", "7"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal(), new DomainIntervalReal());

    public ItvDistance() {
        super("interval", "Distance", (byte) 2, DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {        
        return new ListIntervals(_computeIntervals(input.getFirst(), input.get(1)));
    }
    
    public Interval _computeIntervals (Interval i1, Interval i2) throws Exception {
        return new IntervalPoint(Math.max(Math.abs(i1.getLeft()-i2.getLeft()), Math.abs(i1.getRight()-i2.getRight())));
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        List<MeasurementUnit> mul = new LinkedList<MeasurementUnit> ();
        mul.add(input.getMeasurementUnit()); //correctly throw exception if not the same mu
        return mul;
    }
    
    
}
