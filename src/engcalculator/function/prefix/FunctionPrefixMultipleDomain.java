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
import java.util.Arrays;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionPrefixMultipleDomain extends FunctionPrefix {
    private final DomainList[] domains;
    
    public FunctionPrefixMultipleDomain(String group, String name, byte arguments, DomainList[] domains, boolean expandsToLists, boolean locked, String help, String[] examples, String[] results) {
        super(group, name, arguments, null, expandsToLists, locked, help, examples, results);
        this.domains = domains;
    }  

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        int domainMatchingFunction;
        
        for (domainMatchingFunction = 0; domainMatchingFunction < domains.length; ++domainMatchingFunction)
            if (domains[domainMatchingFunction] == null || domains[domainMatchingFunction].isCompatible(input)) break;
        
        if (domainMatchingFunction >= domains.length) 
            throwNewCalculusException("The function requires any of the following " + Arrays.toString(domains) + " input domain while it was found " + input.toString());
        
        return _computeDomain(domainMatchingFunction, input);        
    }

    public abstract ListIntervals _computeDomain(int domainMatchingFunction, ListIntervals input);

}
