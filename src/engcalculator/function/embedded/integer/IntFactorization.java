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
public final class IntFactorization extends FunctionPrefix {
    private final static String HELP = "... (a) returns the list of prima number in which can be factorized the given input a.";
    private final static String[] EXAMPLE = {"123"};
    private final static String[] RESULT = {"3,41"};
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntegerLong()));

    public IntFactorization() {
        super("integer", "Factorization", (byte) 1, DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public ListIntervals _compute (ListIntervals input) throws Exception {
        long n = (long) input.getFirst().getValue();
        if (n<=1) throwNewCalculusException("The number to be factorizated "+n+" must be higher than 1.");
        ListIntervals result = new ListIntervals ();
        for (long i = 2; i <= n / i; ++i) {
            while (n % i == 0) {
                result.append(new IntervalPoint(i));
                n /= i;
            }
        }
        if (n>1) result.append(new IntervalPoint(n));

        return result;
    }
}
