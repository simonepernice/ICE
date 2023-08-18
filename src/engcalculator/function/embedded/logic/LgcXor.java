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

package engcalculator.function.embedded.logic;

import static engcalculator.function.embedded.logic.LgcBooleanInterval.*;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LgcXor extends LgcInfixFunction {
    private final static String HELP = "a ... b returns the logic XOR between the intervervals (or lists) a and b.\n... returns true if the inputs (trueFalse excluse) are different, if any of the input is trueFalse it returns trueFalse.";
    private final static String[] EXAMPLE_LEFT = {  "true", "true",      "true",  "trueFalse", "trueFalse", "trueFalse", "false", "false",     "false"};
    private final static String[] EXAMPLE_RIGHT = { "true", "trueFalse", "false", "true",      "trueFalse", "false",     "true",  "trueFalse", "false"};
    private final static String[] RESULT = {        "false","trueFalse", "true",  "trueFalse", "trueFalse", "trueFalse", "true",  "trueFalse", "false"};

    public LgcXor() {
        super("!!", HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public LgcBooleanInterval compare(LgcBooleanInterval a, LgcBooleanInterval b) throws Exception {
        if (a == FALSE) return b;
        if (b == FALSE) return a;
        if (a == TRUE && b == TRUE ) return FALSE;
        return TRUEFALSE;
    }

}
