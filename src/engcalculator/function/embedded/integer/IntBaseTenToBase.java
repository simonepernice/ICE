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
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefixExpandLast;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IntBaseTenToBase extends FunctionPrefixExpandLast {
    public final static String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final static String HELP = "... (base, number) returns a literal representing the number (in base 10) converted to the required base.\nSee frombase for the opposite operation. The algorithm is taken from http://www.cs.princeton.edu/introcs/51data/Converter.java.html . Copyright © 2000–2010, Robert Sedgewick and Kevin Wayne. ";    
    private final static String[] EXAMPLE = {"2, 3", "10, 15", "16, 29", "16, 24_29"};
    private final static String[] RESULT = {"'11'", "'15'", "'1D'","'18_1D'"};
    private final static DomainList DOMAIN = new DomainList(new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntegerLong()), new DomainIntegerLong());

    public IntBaseTenToBase() {
        super("integer", "BaseTenToBase", (byte) 2, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        int base = (int) input.getFirst().getValue();
        if (base < 0 || base > DIGITS.length()) throwNewCalculusException("The converting base must be between 0 and "+DIGITS.length());
        long l = (long) input.get(1).getLeft();
        long r = (long) input.get(1).getRight();
        if (l==r) return new ListIntervals(new IntervalLiteral(toString(l, base)));
        return new ListIntervals(new IntervalLiteral(new StringBuilder(toString(l, base)).append('_').append(toString(r,base)).toString()));
    }

    private static String toString(long n, int base) {
        // special case
        if (n == 0) {
            return "0";
        }

        StringBuilder s = new StringBuilder();
        while (n > 0) {
            int d = (int) (n % base);
            s.insert(0, DIGITS.charAt(d));
            n /= base;
        }

        return s.toString();
    }
}
