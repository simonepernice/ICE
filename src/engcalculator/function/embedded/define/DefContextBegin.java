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
public final class DefContextBegin extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... () will create a new context for variables or prefix function definition and returns the current context level.\nThe first context is at level 1. Every time a variable is made it is created in the current context level. Every time the system looks for a variable is search in the current level. If it is not found it looks in the level before and so on unti the first level is reached.";
    private final static String[] EXAMPLE = {"a=1, b=2, c=3;a,b,c,d","defineContextBegin, b =4, d=5;a,b,c,d","defineContextEnd;a,b,c,d"};
    private final static String[] RESULT = {"1,2,3,'d'","1,4,3,5","1,2,3,'d'"};

    public DefContextBegin() {
        super("define", "ContextBegin", (byte) 0, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }    

    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals (new IntervalPoint (Function.beginContext()));
    }
}
