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
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CndFor extends CndPrefixFunction {
    private final static String HELP = "... (start1, $condition, $increment, start2, $calculations)  executes ('argument1=start, condition(argument1), argument1 = increment(argument1), argument2, argument2 = calculations(argument1, argument2)).\nThe expression 'start' is evaluated then while the 'condition(argument)' is true, the argument2=calculation(argument, argument2) is computed, then the argumetn=increment(argument) and again the condition. The result of last argument2 is returned.";
    private final static String[] EXAMPLES = {"$a=(1,2,3,4),$b=(5,6,7,8),$c=(0,0,0,0);conditionalFor(0,defineLambdaFunction '$i<4', defineLambdaFunction '$i+1', {c}, defineLambdaFunction '$j ::=($i, (a :: $i) * (b :: $i))')"};
    private final static String[] RESULTS = {"{a*b}"};
    private final static DomainList DOMAIN = new DomainList (new DomainInterval(), new DomainIntervalLiteral(), new DomainInterval(), new DomainInterval(), new DomainInterval());

    public CndFor () {
        super("For", (byte) 5, DOMAIN, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public Interval _computeInterval() throws Exception {       
        int ite = 0;
        final int maxIteration = MAXITERATIONS.getVal();        
        Interval res = null;
        Interval status = getInput(3);
        for (; LgcBooleanInterval.i2ib(getCondition(1)) == LgcBooleanInterval.TRUE; setArgument(getInput(2))) {
            status = getInput(4, status);
            if (ite++ > maxIteration) throwNewCalculusException ("Exceeded the maximum number of allowed iterations: "+maxIteration);
        }
        return status;
    }

}
