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
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class LisList extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalPoint(), new DomainInteger(), new DomainIntervalPositive()), new DomainIntervalReal());

    public LisList(String name, String function, String[] examples, String[] results) {
        super ("list", name, (byte) 2, DOMAIN, true,true, "... (nOfIntervals, begin_eng) produces a list of nOfIntervals intervals "+function+" spaced from the minimum to the maximum of the given interval.\nSyntax: ... (number of points, interval). It is very useful to compute a function at several points.", examples, results);
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        double begin = __computeDirecte(input.get(1).getLeft());
        final double end = __computeDirecte(input.get(1).getRight());
        int noi = (int) input.getFirst().getLeft();

        -- noi;
        final double delta = (end-begin) / noi;

        ListIntervals result = new ListIntervals ();

        result.add(new IntervalPoint(__computeInverse(begin)));

        -- noi;
        for (int i=0; i<noi; ++i)
            result.add(new IntervalPoint(__computeInverse(begin+=delta)));

        result.add(new IntervalPoint(__computeInverse(end)));

        return result;
    }

    protected abstract double __computeInverse (double input);

    protected abstract double __computeDirecte (double input);
    
    @Override
    public final List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws MeasurementUnitException {      
        if (! input.getFirst().getMeasurementUnit().equals(MeasurementUnit.PURE)) throwNewMeasurementUnitException("The number of elements should be prure value instead it was foudn "+input.getFirst().getMeasurementUnit().toString());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(input.getLast().getMeasurementUnit());
        return res;        
    }      

}
