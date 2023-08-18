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

package engcalculator.function.embedded.program;

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.interval.Interval;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.IntervalLiteral;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ProType extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (interval) returns a literal showing the type of the given interval.\nIt can be useful to check the input domain in user function.";
    private final static String[] EXAMPLE = {"10_10","10_11","5+|4","1_2+|5","5+|3_4","'12'","5%2+|3-(-5%2+|3)","5+|3-(-5+|3)","4_5-intervalDual(4_5)"};
    private final static String[] RESULT = {"'Point Interval'","'Real Interval'","'Complex Point Interval'","'Complex Interval'","'Complex Interval'","'Literal'","'Real Interval'","'Point Interval'","'Point Interval'"};
    private final static DomainList DOMAIN = new DomainList(new DomainInterval());
    
    public ProType() {
        super("program", "Type", DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }
    
    @Override
    public Interval _computeIntervals (Interval i) throws Exception {
        return new IntervalLiteral (i.getType());
    }
}
