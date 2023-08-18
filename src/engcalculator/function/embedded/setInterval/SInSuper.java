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

package engcalculator.function.embedded.setInterval;

import static engcalculator.function.embedded.logic.LgcBooleanInterval.*;

import engcalculator.interval.Interval;
import engcalculator.function.embedded.compare.ComInfixFunction;
import engcalculator.function.embedded.logic.LgcBooleanInterval;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SInSuper extends ComInfixFunction {
    private final static String HELP = "intervalA ... intervalB checks if the intervalA is a super set of the intervalB returning 0 for false, 1 for true.\nSuper set means every element of the second interval is available in the first (the opposite is not required).";
    private final static String[] EXAMPLE_LEFT = {"6_7","6_9", "1_2", "3_4"};
    private final static String[] EXAMPLE_RIGHT = {"5_8","7_8", "1.5_3", "-3_-2"};
    private final static String[] RESULT = {"0_1","1","0_1","0"};

    public SInSuper() {
        super("setInterval", ">>", HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public LgcBooleanInterval _compare(Interval a, Interval b) throws Exception {
        if (a.getLeft() <= b.getLeft() && a.getRight() >= b.getRight()) return TRUE;
        if (a.getLeft() > b.getRight() || a.getRight() < b.getLeft()) return FALSE;
        return TRUEFALSE;
    }
}
