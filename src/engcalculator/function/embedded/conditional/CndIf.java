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

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicOr;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CndIf extends CndPrefixFunction {
    private final static String HELP = "... (argument, condition, retIfTrue, retIfTrueFalse, retIfFalse) evaluate condition(argument) then depending on the logic result returns one of the intervals or prefix function: retIfTrue(argument), retIfTrueFalse(argument), retIfFalse(argument).\nIf condition is an interval it verifies if interval argument is included in condition. If the return functions are intervals they are returned. retIfTrueFalse, retIfFalse or just retIfFalseIt may miss, however an error will be risen if a condition not available is reached. This function can be used in recursion (only with prefix function as argument) because it would evaluate all its arguments (also the true and false values), therefore the loop would never end.";
    private final static String[] EXAMPLES = {"1, 1_2, 2, 3, 4","0,1_2,2,3,4", "0_1,0.5_2,2,3,4"};
    private final static String[] RESULTS = {"2","4","3"};
    private final static DomainList DOMAIN = new DomainList (new DomainInterval(), new DomainLogicOr(new DomainIntervalReal(), new DomainFunctionPrefix(1)));

    public CndIf () {
        super("If", (byte) -3, DOMAIN, HELP, EXAMPLES, RESULTS);
    }

    @Override
    protected Interval _computeInterval() throws Exception {
        if (getInputSize() > 5) throwNewCalculusException ("Maximum 5 input variables are expected while were found "+getInputSize());
        switch (LgcBooleanInterval.i2ib(getCondition(1))) {
            case TRUE:
                return getInput(2);
            case TRUEFALSE:
                return getInput(3);
            case FALSE:
                return getInput(4);
            default:
                throw new RuntimeException ("Unexpected condition in if function.");
        }
    }

}
