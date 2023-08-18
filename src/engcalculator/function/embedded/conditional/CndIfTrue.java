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
 *     along with this program.  CndIf not, see <http://www.gnu.org/licenses/>.
 */

package engcalculator.function.embedded.conditional;

import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CndIfTrue extends FunctionPrefix {
    private final static String HELP = "... (booleanCondition, retIfTrue, retOtherwise) evaluate boolean condition and if it is true then return retIfTrue otherwise it returns retOtherwise.";
    private final static String[] EXAMPLES = {"1, 1_2, 2","0,1_2,2"};
    private final static String[] RESULTS = {"1_2","2"};
    private final static DomainList DOMAIN = new DomainList (new DomainInterval(), new DomainInterval(), new DomainInterval());

    public CndIfTrue () {
        super("conditional", "IfTrue", (byte) 3, DOMAIN, true, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        if (LgcBooleanInterval.i2ib(input.getFirst()) == LgcBooleanInterval.TRUE) return input.subList(1, 2);
        return input.subList(2, 3);
    }

}
