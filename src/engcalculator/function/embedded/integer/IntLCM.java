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

package engcalculator.function.embedded.integer;

import engcalculator.domain.DomainIntegerLong;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IntLCM extends FunctionPrefix {
    private final static String HELP = "... (a, b) returns the least common multiple between a and b.\nSee also gcd.";
    private final static String[] EXAMPLE = {"2, 3","10, 15", "10, 20"};
    private final static String[] RESULT = {"6", "30", "20"};
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntegerLong()), new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntegerLong()));

    public IntLCM() {
        super("integer", "LCM", (byte) 2, DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public ListIntervals _compute (ListIntervals input) throws Exception {
        long a = (long) input.getFirst().getValue(), b = (long) input.get(1).getValue();
        return new ListIntervals(new IntervalPoint(a*b/IntGCD.gcd(a, b)));
    }

}
