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

import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LitFromExpression extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalPoint(), new DomainInteger(), new DomainIntervalPositive()));

    private final static String HELP = "... (value) return the given value as a literal 'value'.\nNote value may be a complex expression. If the value is a list of intervals, it returns a single string containing all the list as string. If the single values should be returned in a list use literalFromInterval.";
    private final static String[] EXAMPLE = {"literalToExpression('(1+1)/2')"};
    private final static String[] RESULT = {"'1'"};

    public LitFromExpression() {        
        super("literal", "FromExpression", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals(new IntervalLiteral(input.toString())); 
    }

}
