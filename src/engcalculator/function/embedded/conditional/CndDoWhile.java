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
public final class CndDoWhile extends CndPrefixFunction {
    private final static String HELP = "... (initialValue, $calculations, $condition) executes ... (argument=initialValue, argument=calculations(argument), condition(argument)) while condition(argument) is ture.\nIt set internal variable argument to initialValue, then executes argument = calculation(argument) while condition(argument) returns true. Note if 'condition' is false the calculation is executed one time. calculation must be a prefix function while condition can be a prefix function or an interval.";
    private final static String[] EXAMPLES = {"0,defineLambdaFunction('$x+1'), defineLambdaFunction('$x<5')","5,defineLambdaFunction('$x+1'), defineLambdaFunction('$x<5')"};
    private final static String[] RESULTS = {"5","6"};
    private final static DomainList DOMAIN = new DomainList (new DomainInterval(), new DomainFunctionPrefix(1), new DomainLogicOr(new DomainIntervalReal(), new DomainFunctionPrefix(1)));

    public CndDoWhile () {
        super("DoWhile", (byte) 3, DOMAIN, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public Interval _computeInterval() throws Exception {       
        int ite = 0;
        final int maxIteration = MAXITERATIONS.getVal();
        do {
            setArgument(getInput(1));
            if (ite++ > maxIteration) throwNewCalculusException ("Exceeded the maximum number of allowed iterations: "+maxIteration);
        } while (LgcBooleanInterval.i2ib(getCondition(2)) == LgcBooleanInterval.TRUE);
        return getArgumen();
    }

}
