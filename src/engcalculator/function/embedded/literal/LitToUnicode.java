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

package engcalculator.function.embedded.literal;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LitToUnicode extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());

    private final static String HELP = "... ('literal') returns the list of unicode values to made its chars.";
    private final static String[] EXAMPLE = {"literalFromUnicode(105 .. 110)"};
    private final static String[] RESULT = {"105 .. 110"};

    public LitToUnicode() {        
        super("literal", "ToUnicode", (byte) 1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals result = new ListIntervals();
        String data = input.getFirst().getName();
        final int s = data.length();
        for (int i=0;i<s;++i)
            result.append(new IntervalPoint(data.codePointAt(i)));
        return result;        
    }

}
