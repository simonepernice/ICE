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
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ComEqual extends ComInfixFunction {
    private final static String HELP = "a ... b check if a equals to b.\nIt return true if a and b are equals, trueFalse if they have some point in common, false if they have none.";
    private final static String[] EXAMPLE_LEFT = {"6_7","6_7", "1_3", "3_4","1+I","'abl'"};
    private final static String[] EXAMPLE_RIGHT = {"4_5","7_9", "2_4", "3_4","I+1","'abl'"};
    private final static String[] RESULT = {"0","0_1","0_1","1","1","1"};

    private final static DomainList DOMAIN = new DomainList (new DomainInterval());

    public ComEqual() {
        super("==", DOMAIN, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public LgcBooleanInterval _compare(Interval a, Interval b) throws Exception {
        if (a.equals(b)) return TRUE;

        if ( //this check is to avoid the TRUEFALSE check for literal, complex and not proper intervals which would make not sense or throw exception
            (a.getName() != null &&  b.getName() != null) ||
            (! a.isIntervalReal() || ! b.isIntervalReal()) ||
            (! a.isProper() || ! b.isProper())
           ) return FALSE;

        if ((a.getLeft() <= b.getRight() && a.getLeft() >= b.getLeft()) || (a.getRight() <= b.getRight() && a.getRight() >= b.getLeft())) return TRUEFALSE;
        return FALSE;
    }
}
