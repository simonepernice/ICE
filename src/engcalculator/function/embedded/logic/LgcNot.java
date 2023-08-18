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

import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalLogic;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LgcNot extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (a) returns the logic operator NOT operator applied on the interval a.\nIt works on a three state logic: true (1), false (0) and turefalse (0_1). ... return true for false and vice versa, it returns true_false for true_false.";
    private final static String[] EXAMPLES = {"1","0","1,0_1,1,0,0"};
    private final static String[] RESULTS = {"0","1","0,0_1,0,1,1"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLogic());

    public LgcNot() {
        super("logic", ".!!", DOMAIN, true, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        return LgcBooleanInterval.ib2i(compare (LgcBooleanInterval.i2ib(input)));
    }

    public LgcBooleanInterval compare (LgcBooleanInterval a) throws Exception {
        switch (a) {
            case TRUE:
                return FALSE;
            case FALSE:
                return TRUE;
            case TRUEFALSE:
                return TRUEFALSE;
            default:
                throw new RuntimeException ("Unexpected type in NOT function.");
        }
    }

}
