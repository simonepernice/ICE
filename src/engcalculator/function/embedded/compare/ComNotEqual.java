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

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.function.embedded.logic.LgcNot;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ComNotEqual extends ComInfixFunction {
    private final static String HELP = "a ... b checks if a is different from b. It checks if for every points of the first interval does not exist an equal point in the second and returns true, if juse some does not exists it return trueFalse, if none it returns false.";
    private final static String[] EXAMPLE_LEFT = {"6_7","6_7", "1_3", "3_4","1+I","'abl'"};
    private final static String[] EXAMPLE_RIGHT = {"4_5","7_9", "2_4", "3_4","I+1","'abl'"};
    private final static String[] RESULT = {"1","0_1","0_1","0","0","0"};

    private final static ComEqual EQUAL = new ComEqual ();
    private final static LgcNot NOT = new LgcNot();

    private final static DomainList DOMAIN = new DomainList (new DomainInterval());

    public ComNotEqual() {
        super("!=", DOMAIN, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public LgcBooleanInterval _compare(Interval a, Interval b) throws Exception {
        return NOT.compare(EQUAL._compare(a, b));
    }
}
