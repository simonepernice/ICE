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
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicOr;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LitFromIntervalDetailed extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicOr (new DomainIntervalComplex(), new DomainIntervalList()));

    private final static String HELP = "... (interval1, .., intervaln) return the given values as a literal: 'interval1', .. ,'intervaln'.\nCompared to literal from interval it prints the output with all the internal accuracy and in the same way in which they can be entered again as new input: the same would be showed with nicePrint off. It is useful to see an interval with full details without changing the print settings.";
    private final static String[] EXAMPLE = {"PI"};
    private final static String[] RESULT = {"'3.141592653589793'"};

    public LitFromIntervalDetailed() {        
        super("literal", "FromIntervalDetailed", (byte) 1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        @SuppressWarnings("unchecked")
        Parameter<Boolean> nicePrint = (Parameter<Boolean> ) ParameterManager.getParameter("intervalALL", "nicePrint");
        boolean tmp = nicePrint.getVal();
        nicePrint.setVal(false);
        ListIntervals result = new ListIntervals(new IntervalLiteral(input.getFirst().toString())); 
        nicePrint.setVal(tmp);
        return result;
    }

}
