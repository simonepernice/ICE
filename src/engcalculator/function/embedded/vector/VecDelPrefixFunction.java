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

package engcalculator.function.embedded.vector;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.CalculusException;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalList;
import engcalculator.interval.IntervalPoint;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class VecDelPrefixFunction extends FunctionPrefix {
    private final DomainList domain;

	private final static String HELP = " This function can be also applied to a set of sublist of ({matrix1}, {matrix2}, ..)";

    public VecDelPrefixFunction(String name, byte args, DomainList domain, boolean expandToList, String help, String[] examples, String[] results) {
        super("vector", name, args, new DomainList(), expandToList, true, help+HELP, examples, results);
        this.domain = domain;
    }

    @Override
    public final ListIntervals _compute(ListIntervals maininput) throws Exception {        
        ListIntervals input = maininput.subList(1, maininput.size());
        if (input.isMadeByIntervalList()) {            
            ListIntervals result = new ListIntervals ();
            for (Interval i : input) {                
                ListIntervals li = maininput.subList(0, 1);
                li.addAll(i.getListIntervals());
                if (domain != null && ! domain.isCompatible(li)) throwNewCalculusException ("Error: "+getSymbol()+" requires "+domain.toString()+" input domain while it was found "+li.getDetailedType());
                result.append(new IntervalList(__compute(li)));
            }        
            return result;
        }
        if (domain != null && ! domain.isCompatible(maininput)) throwNewCalculusException ("Error: "+getSymbol()+" requires "+domain.toString()+" input domain while it was found "+maininput.getDetailedType());
        return __compute(maininput);
    }
    
    public abstract  ListIntervals __compute(ListIntervals input) throws Exception;
    
    protected ListIntervals deriveOnVariable (int j, ListIntervals input) throws Exception {
        final int na = input.size()-1;
        ListIntervals p = input.subList(1, 1+na);
        for (int k=0;k<na;++k) {
            if (k!=j) p.set(k, new IntervalPoint(p.get(k).getValue()));
            else if (p.get(k).isIntervalPoint()) throwNewCalculusException ("The given input has some point intervals "+p.get(k));
        }    
        return p;
    }
}
