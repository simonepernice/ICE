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
import engcalculator.domain.DomainIntervalList;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisShuffle extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalList());

    private final static String[] EXAMPLE = {"{1 .. 3}, {4 .. 6}"};
    private final static String[] RESULT = {"1,4,2,5,3,6"};

    public LisShuffle() {        
        super("list", "Shuffle", (byte) -1, DOMAIN, true, true, "... ({a1, a2, .., an}, {b1, b2, .. bn}, ..) returns the list made by (a1, b1, c1, .., a2, b2, c2, .. )\n.The first element of each gets several {sub-lists} (of the same size). It return a list obtained taking the first element of each list, then the second, and so on.", EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int s = (int) input.size();
        for (int i=1; i<s; ++i) if (input.get(i).getListIntervals().size() != input.get(i-1).getListIntervals().size()) throwNewCalculusException ("The lists are not of the same size: "+input.get(i).getListIntervals().size()+" , "+input.get(i-1).getListIntervals().size());

        ListIntervals result = new ListIntervals ();  
        
        final int listLength = input.getFirst().getListIntervals().size();
        
        for (int j=0; j<listLength; ++j) 
            for (int i = 0; i<s; ++i) 
                result.append(input.get(i).getListIntervals().get(j));
        
        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws MeasurementUnitException {              
        return null;        
    }      

}
