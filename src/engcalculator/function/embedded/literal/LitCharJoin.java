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
import engcalculator.interval.IntervalLiteral;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LitCharJoin extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());

    private final static String HELP = "... (lit1, lit2, .. litn) returns a literal made by the concatenation of all the given ones.";
    private final static String[] EXAMPLE = {"literalCharSplit('home')"};
    private final static String[] RESULT = {"'home'"};

    public LitCharJoin() {        
        super("literal", "CharJoin", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        StringBuilder result = new StringBuilder (input.getFirst().getName());
        final int s = input.size();
        for (int i=1;i<s;++i)
            result.append(input.get(i).getName());
        return new ListIntervals(new IntervalLiteral(result.toString())); 
    }

}
