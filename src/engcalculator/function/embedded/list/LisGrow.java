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
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisGrow extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalPoint(), new DomainInteger(), new DomainIntervalPositive()),new DomainLogicAnd (new DomainIntervalPoint(), new DomainInteger(), new DomainIntervalPositive()), new DomainIntervalList());

    private final static String HELP = "... (size, clone, {list}) return a new list obtained from elements of list at group of size cloned clone times.\nIt gets the group size, the number of copies of each group and a {sub-list}. Return a new list where the number of groups were copied for the given times. For example suppose you have the following foundamental frequencies found = (100, 500, 600,900) and you want to make a new list with the foundamental plus the first 4 harmonics you can achieve with this: ... (1,4,found)* ...(4,4, 1 .. 4).";
    private final static String[] EXAMPLE = {"3, 2, {1 .. 6}"};
    private final static String[] RESULT = {"1 .. 3, 1 .. 3, 4 .. 6, 4 .. 6"};

    public LisGrow() {        
        super("list", "Grow", (byte) 3, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int gs = (int) input.getFirst().getValue();
        final ListIntervals ili = input.getLast().getListIntervals();
        final int ls = ili.size();
        
        if (gs > ls || gs < 0) throwNewCalculusException ("The group size "+gs+" should be within the list size "+ls);
        if (ls%gs != 0) throwNewCalculusException ("The group size "+gs+" does not fit exactly with the list size "+ls);
        
        final int rep = (int) input.get(1).getValue();
        if (rep < 1) throwNewCalculusException ("It was required to repeat "+rep+" while each group should be repeated at least once.");
        
        ListIntervals result = new ListIntervals ();        
        final int sinput = input.size();
        
        
        for (int j=0; j<ls; j += gs) 
            for (int k=0; k<rep; ++k)
                for (int i = 0; i<gs; ++i) 
                    result.append(ili.get(i+j));
        
        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }    

}
