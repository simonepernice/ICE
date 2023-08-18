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

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisFlatten extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList ();

    private final static String HELP = "... ({a, b}, {c}, d, {e, f}) returns a flat list (a,b,c,d,e,f).\nIt takes a list of element (usually sub-list nested) it create a single list containing all the elements.";
    private final static String[] EXAMPLE = {"0,{1,{2,3},{4,5,{6,7,{8}},9},10},11,12"};
    private final static String[] RESULT = {"0 .. 12"};

    public LisFlatten() {        
        super("list", "Flatten", (byte) -1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {        
        return flat(input);
    }
    
    private ListIntervals flat (ListIntervals input) {
        if (input.size() > 1) {
            ListIntervals res = flat(input.subList(0, 1));
            res.addAll(flat(input.subList(1, input.size())));
            return res;            
        } else if (input.getFirst().isIntervalList()) {
            return flat(input.getFirst().getListIntervals());
        } else {
            return input;
        }        
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }

    
}
