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

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class Domain {
    
    protected static String makeName (Domain[] domains, String separator) {
        StringBuilder result = new StringBuilder ();
        for (int i=0; i<domains.length-1; ++i) {
            result.append(domains[i].name);
            result.append(' ');
            result.append(separator);
            result.append(' ');
        }
        result.append(domains[domains.length-1]);
        return result.toString();
    }
    
    private final String name;

    public Domain(String name) {
        this.name = name;
    }

    public abstract boolean isCompatible (Interval i);

    @Override
    public final String toString() {
        return name;
    }

}
