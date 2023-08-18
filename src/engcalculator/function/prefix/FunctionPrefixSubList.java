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

package engcalculator.function.prefix;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalList;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionPrefixSubList extends FunctionPrefix {
    private final DomainList domain;


    public FunctionPrefixSubList(String group, String name, byte args, DomainList domain, boolean expandToList, String help, String[] examples, String[] results) {
        super(group, name, args, new DomainList(), expandToList, true, help, examples, results);
        this.domain = domain;
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        if (input.isMadeByIntervalList()) {            
            ListIntervals result = new ListIntervals ();
            for (Interval i : input) {
                ListIntervals li = i.getListIntervals();
                if (domain != null && ! domain.isCompatible(li)) throwNewCalculusException ("Error: "+getSymbol()+" requires "+domain.toString()+" input domain while it was found "+li.getDetailedType());
                result.append(new IntervalList(__compute(li)));
            }        
            return result;
        }
        if (domain != null && ! domain.isCompatible(input)) throwNewCalculusException ("Error: "+getSymbol()+" requires "+domain.toString()+" input domain while it was found "+input.getDetailedType());
        return __compute(input);
    }
    
    public abstract  ListIntervals __compute(ListIntervals input) throws Exception;
    
}
