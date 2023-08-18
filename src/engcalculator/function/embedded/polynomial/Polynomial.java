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
package engcalculator.function.embedded.polynomial;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class Polynomial {

    public static void setSameSize (ListIntervals p1, ListIntervals p2) {
        while (p1.size()<p2.size()) p1.addFirst(Interval.ZERO);
        while (p2.size()<p1.size()) p2.addFirst(Interval.ZERO);        
    }
    
    public static void delTrailingZeroCoefficients (ListIntervals p1) {        
        while (p1.size() > 1 && p1.getFirst().equals(Interval.ZERO)) p1.removeFirst();
    }
    
    public static void increaseGradeTo (ListIntervals tmp, int g) {        
        while (tmp.size() < g) tmp.addLast(Interval.ZERO);
    }
}
