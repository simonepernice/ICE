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
 *     along with this program.  ProIf not, see <http://www.gnu.org/licenses/>.
 */

package engcalculator.function.embedded.conditional;

import engcalculator.domain.DomainList;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CndSwitch extends CndPrefixFunction {
    private final static String HELP = "... (value, cond1, val1, .., condn, valn, valDefault) it checks the given value, if it meets cond1(value) it returns val1 and so on, if a valDefalt is left alone (without condition) at the end it is always returned.\nIf condition is an interval it is verified value is a sub-set of condition. If vali is a prefix function then vali(argument) is returned. If no condition is triggered an exception is rised: to avoid that use a default value. If more then a condition is true, it is returned the value of the first. It is useful to define a function on several domains: ($f, $x) = 'conditionalSwitch (x, 0, 1, defineLambdaFunction(\'sin $x / $x\'))'";
    private final static String[] EXAMPLES = {"(1, 2_3, false, 0_2, true)"};
    private final static String[] RESULTS = {"true"};
    private final static DomainList DOMAIN = new DomainList ();

    public CndSwitch () {
        super("Switch", (byte) -2, DOMAIN, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public Interval _computeInterval() throws Exception {        
        final int s = getInputSize();

        int i;
        for (i = 1; i+1<s; i+=2) {            
            if (LgcBooleanInterval.i2ib(getCondition(i)) ==  LgcBooleanInterval.TRUE) return getInput(i+1);
        }
        if (i<s) return getInput(i);
        throwNewCalculusException ("No condition was triggered with input: "+getInput(0));        
        return null; //this will never be reached unless error check are disableded
    }

}
