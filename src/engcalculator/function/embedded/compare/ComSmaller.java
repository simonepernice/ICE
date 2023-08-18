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

package engcalculator.function.embedded.compare;

import static engcalculator.function.embedded.logic.LgcBooleanInterval.*;

import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ComSmaller extends ComInfixFunction {
    private final static String HELP = "a ... b check is a is less than be.\nIt checks if for every points of the first interval exists a smaller point in the second and returns true, if just some is smaller it returns trueFlase, if none it returns false.";
    private final static String[] EXAMPLE_LEFT = {"6_7","6_7", "1", "3_4"};
    private final static String[] EXAMPLE_RIGHT = {"4_5","7_8", "2_4", "-3_-2"};
    private final static String[] RESULT = {"0","0_1","1","0"};

    public ComSmaller() {
        super("<", HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public LgcBooleanInterval _compare(Interval a, Interval b) throws Exception {
        if (a.getName() != null && b.getName() != null) {
            if (a.getName().compareTo(b.getName()) < 0) return TRUE;
            else return FALSE;
        }
        if (a.getRight() < b.getLeft()) return TRUE;
        if (a.getLeft() >= b.getRight()) return FALSE;
        return TRUEFALSE;
    }
}
