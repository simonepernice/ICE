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

package engcalculator.domain;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import java.util.Arrays;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DomainIntervalList extends Domain {
    private final Domain[] subDomains;

    public DomainIntervalList(Domain... subDomains) {
        super("{Interval List of "+Arrays.toString(subDomains)+"}");
        this.subDomains = subDomains;
    }

    @Override
    public boolean isCompatible(Interval i) {
        if (! i.isIntervalList()) return false;
        
        if (subDomains == null || subDomains.length == 0) return true;
        
        ListIntervals li = i.getListIntervals();
        if (li.size() < subDomains.length) return false;
        
        int k;
        for (int j=0; j<li.size(); ++j) {
            if (j >= subDomains.length) k = subDomains.length-1;
            else k = j;
            if (! subDomains[k].isCompatible(li.get(j))) return false;
        }
        
        return true;                                             
    }
}
