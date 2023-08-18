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
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IntFactorial extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (a) returns 1*2*3* .. * a.";
    private final static String[] EXAMPLE = {"5"};
    private final static String[] RESULT = {"1*2*3*4*5"};
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntegerLong(), new DomainIntervalPositive()));

    public IntFactorial() {
        super("integer", "Factorial", DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public Interval _computeIntervals (Interval input) throws Exception {
        double res=1;
        final long top = (long) input.getValue();
        for (long i = 1;i<=top; ++i)
            res *= i;
        return new IntervalPoint(res);
    }
}
