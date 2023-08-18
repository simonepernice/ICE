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

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LitFromInterval extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd (new DomainIntervalComplex()));

    private final static String HELP = "... (interval1, .., intervaln) return the given values as a literal: 'interval1', .. ,'intervaln'.\nCompared to literal from expression it would return a list of literal given a list of intervals.";
    private final static String[] EXAMPLE = {"(1,2,3,PI)"};
    private final static String[] RESULT = {"'1','2','3','3.142'"};

    public LitFromInterval() {        
        super("literal", "FromInterval", (byte) 1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals(new IntervalLiteral(input.getFirst().toString())); 
    }

}
