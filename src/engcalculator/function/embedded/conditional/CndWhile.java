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
public final class CndWhile extends CndPrefixFunction {
    private final static String HELP = "... (initialValue, $condition, $calculations) exexutes ... (argument=initialValue, condition(argument), argument=calculations(argument)) while condition(argument) is true.\nIt set internal variable arg to initialValue, then executes condition(arg) and whils it is true executes arg = calculation(arg). Note if 'condition' is false the calculation is never executed. calculation must be a prefix function while condition can be a prefix function or an interval.";
    private final static String[] EXAMPLES = {"0, defineLambdaFunction'$i<5',defineLambdaFunction '$i+1'","5, defineLambdaFunction'$i<5',defineLambdaFunction '$i+1'"};
    private final static String[] RESULTS = {"5","5"};
    private final static DomainList DOMAIN = new DomainList (new DomainInterval(), new DomainLogicOr(new DomainIntervalReal(), new DomainFunctionPrefix(1)), new DomainFunctionPrefix(1));

    public CndWhile () {
        super("While", (byte) 3, DOMAIN, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public Interval _computeInterval() throws Exception {               
        int ite = 0;
        final int maxIteration = MAXITERATIONS.getVal();
        while (LgcBooleanInterval.i2ib(getCondition(1)) == LgcBooleanInterval.TRUE) {
            setArgument(getInput(2)); //as side effect should update the iteration variable..
            if (ite++ > maxIteration) throwNewCalculusException ("Exceeded the maximum number of allowed iterations: "+maxIteration);
        }
        return getArgumen();
    }

}
