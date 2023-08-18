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

package engcalculator.function.embedded.logarithm;


import engcalculator.domain.DomainIntervalExact;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.domain.DomainLogicNot;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class LogBase extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalPositive(), new DomainIntervalPoint(), new DomainLogicNot(new DomainIntervalExact(0d))), new DomainLogicAnd (new DomainIntervalPositive(), new DomainLogicNot(new DomainIntervalExact(0d))));

    private final static String HELP = "... (base, a) computes the logarithm of the interval a with base.";
    private final static String[] EXAMPLE = {"10, 10, 3, 3, 5,25"};
    private final static String[] RESULT = {"1, 1, 2"};
    
    public LogBase() {
        super ("logarithm", ".logBase",  (byte) 2, DOMAIN,true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public final ListIntervals _compute (ListIntervals input) throws Exception {
        double base = input.getFirst().getValue();
        Interval a = input.getLast();
        if (a.isIntervalPoint()) return new ListIntervals(new IntervalPoint (lobBase(base, a.getValue())));
        else {
            if (base >= 1) {
                return new ListIntervals(new IntervalReal ( lobBase(base, a.getLeft()),  lobBase(base, a.getRight())));
            } else {
                return new ListIntervals(new IntervalReal (   lobBase(base, a.getRight()), lobBase(base, a.getLeft())));
            }
        }
    }

    public double lobBase (double base, double val) {
        return Math.log(val)/Math.log(base);
    }

}
