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
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisSize extends FunctionPrefix {
    private final static String HELP = "... (a1, a2, .., an) returns n.\nIt returns the number of intervals content in a list.";
    private final static String[] EXAMPLE = {"-10_-2, 5_7, -7_-5"};
    private final static String[] RESULT = {"3"};
    private final static DomainList DOMAIN = new DomainList (new DomainInterval());

    public LisSize() {
        super("list", "Size", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals ( new IntervalPoint (input.size()));
    }
    
    public int computeSize (ListIntervals input) throws Exception {
        return input.size() ;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws MeasurementUnitException {      
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(MeasurementUnit.PURE);
        return res;        
    }      
}
