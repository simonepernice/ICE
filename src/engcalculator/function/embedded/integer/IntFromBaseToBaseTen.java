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
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.CalculusException;
import static engcalculator.function.embedded.integer.IntBaseTenToBase.DIGITS;
import engcalculator.function.prefix.FunctionPrefixExpandLast;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IntFromBaseToBaseTen extends FunctionPrefixExpandLast {
    private final static String HELP = "... (base, number) returns a literal representing the number (in base 10) converted to the required base.\nSee tobase for the opposite operation. The algorithm is taken from http://www.cs.princeton.edu/introcs/51data/Converter.java.html . Copyright © 2000–2010, Robert Sedgewick and Kevin Wayne. ";
    private final static String[] RESULT = {"3", "15", "29", "24_29"};
    private final static String[] EXAMPLE = {"2, '11'", "10, '15'", "16, '1D'","16, '18_1D'"};
    private final static DomainList DOMAIN = new DomainList(new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntegerLong()), new DomainIntervalLiteral());

    public IntFromBaseToBaseTen() {
        super("integer", "FromBaseToBaseTen", (byte) 2, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        int base = (int) input.getFirst().getValue();
        if (base < 0 || base > DIGITS.length()) throwNewCalculusException("The converting base "+base+" must be between 0 and "+DIGITS.length());
        String s = input.get(1).getName();
        final int us = s.indexOf('_');
        if (us == -1) return new ListIntervals(new IntervalPoint(fromString(s, base)));
        return new ListIntervals(new IntervalReal(fromString(s.substring(0, us), base), fromString(s.substring(us+1), base)));
    }

    // convert a String representing a base b integer into an int
    public static long fromString(String s, int b) throws Exception {
       long result = 0;
       int digit = 0;
       for (int i = 0; i < s.length(); i++) {
          char c = s.charAt(i);
          if (c >= '0' && c <= '9')
             digit = c - '0';
          else if (c >= 'A' && c <= 'Z')
             digit = 10 + c - 'A';
          else throwNewCalculusException ("Not allowed char "+c+" while parsing a string representing a number in base "+b);

          if (digit < b) result = b * result + digit;
          else throwNewCalculusException ("Not allowed char "+c+" while parsing a string representing a number in base "+b);
       }
       return result;
    }
}
