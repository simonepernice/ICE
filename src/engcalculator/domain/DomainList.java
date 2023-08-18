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

import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class DomainList {
    private final Domain[] listOfDomains;

    public DomainList (Domain... listOfDomains) {
        this.listOfDomains = listOfDomains;
    }

    public boolean isCompatible (ListIntervals il) {
        if (listOfDomains == null || il == null || listOfDomains.length == 0 ) return true;

        for (int i=0; i<il.size(); ++i) {
            if (i < listOfDomains.length) {
                if (! listOfDomains[i].isCompatible(il.get(i))) return false;
            } else  if (! listOfDomains[listOfDomains.length-1].isCompatible(il.get(i))) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder ();
        result.append('(');
        if (listOfDomains.length == 0) result.append("everything,");
        else for (Domain d : listOfDomains)
            result.append (d.toString()).append(',');
        result.setCharAt(result.length()-1, ')');

        return result.toString();
    }

    public int getSize() {
        return listOfDomains.length;
    }

}
