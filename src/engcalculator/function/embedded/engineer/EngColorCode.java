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

package engcalculator.function.embedded.engineer;

import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

public abstract class EngColorCode extends FunctionPrefix {
    protected final static String[] COLORS = {"black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "gray", "white", "gold", "silver", "none"};
    protected final static float[] TOLERANCE = {-1, 1, 2, -1, -1, 0.5f, 0.25f, 0.1f, 0.05f, -1, 5, 10, 20};

    protected EngColorCode(String group, String name, byte numbArgs, DomainList domain, boolean expandsToLists, boolean locked, String help, String[] examples, String[] results) {
        super(group, name, numbArgs, domain, expandsToLists, locked, help, examples, results);
    }
    
    protected static int findCloserIndexTolerance (float value, boolean ascendingSorted) {
        int closerIndex = 0;
        float minDistance = Math.abs(value - TOLERANCE[0]), distance;
        for (int index = 1; index < TOLERANCE.length; ++index) {
            distance = Math.abs(value - TOLERANCE[index]);
            if (distance < minDistance) {
                closerIndex = index;
                minDistance = distance;
            } else  if (ascendingSorted) break; //it would be faster to use binary search for ascending ordered array
        }
        return closerIndex;
    }
    
    protected static int findCloserIndexColors (String value, boolean ascendingSorted) {
        int closerIndex = 0;
        int minDistance = Math.abs(value.compareTo(COLORS[0])), distance;
        for (int index = 1; index < COLORS.length; ++index) {
            distance = Math.abs(value.compareTo(COLORS[index]));
            if (distance < minDistance) {
                closerIndex = index;
                minDistance = distance;
            } else if (ascendingSorted) break; //it would be faster to use binary search for ascending ordered array
        }
        return closerIndex;
    }

}
