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

import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.interval.*;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisDistance extends FunctionPrefix {
    private final static String HELP = "... (intervalA, intervalB) returns the distance between the given itnervals.\nIt computes the distance between two {sub-lists}, which is the sum of the distances between each couple of intervals, which is the maximum between the absolute difference for their extreme points.";
    private final static String[] EXAMPLE = {"{-10_10, 10_-5, 10_10},{3_4, 3_4, 3_4}"};
    private final static String[] RESULT = {"13+9+7"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalList(), new DomainIntervalList());
    private final static ItvDistance DISTANCE = new ItvDistance();

    public LisDistance() {
        super("list", "Distance", (byte) 2, DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {   
        
        final ListIntervals a = input.getFirst().getListIntervals(), b = input.getLast().getListIntervals();

        if (a.size() !=  b.size()) throwNewCalculusException ("The given interval list do not have the same length: "+a.size()+" , "+b.size());
        
        double distance = 0;
        Iterator<Interval> ia, ib;
        
        ia = a.iterator();
        ib = b.iterator();
        
        while (ia.hasNext()) {    
            distance += DISTANCE._computeIntervals(ia.next(), ib.next()).getValue();
        }
        return new ListIntervals(new IntervalPoint(distance));
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        List<MeasurementUnit> mul = new LinkedList<MeasurementUnit> ();
        mul.add(input.getMeasurementUnit());
        return mul;
    }      
    
}
