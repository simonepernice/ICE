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

package engcalculator.function.embedded.define;

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.function.Function;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefContextEnd extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... () will end the current context and go to the one before returning its value.\nIt will lose all the variables created here. The first context is at level 1 and it is not possible to end it. Every time a variable is made it is created in the current context level. Every time the system looks for a variable is search in the current level. If it is not found it looks in the level before and so on unti the first level is reached.";
    private final static String[] EXAMPLE = {";sin PI","defineContextBegin, ($sin, $x) = ' 2*x'; sin PI", "defineContextEnd; sin PI"};
    private final static String[] RESULT = {"0","2*PI","0"};


    public DefContextEnd() {
        super("define", "ContextEnd", (byte) 0, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }    

    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals (new IntervalPoint (Function.endContext()));
    }
}
