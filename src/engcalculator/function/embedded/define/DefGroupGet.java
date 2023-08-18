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
import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import engcalculator.function.variable.FunctionVariable;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefGroupGet extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... ($function) returns the group of the given function (infix or prefix) or variable.";
    private final static String[] EXAMPLE = {"'defineGroupGet','*','help'"};
    private final static String[] RESULT = {"'define','arithmetic','information'"};
    
    public DefGroupGet() {
        super("define", "GroupGet", (byte) 1, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals result = new ListIntervals();
        String sym = input.getFirst().getName();
        Function f;
        f = FunctionPrefix.getFunction(sym);
        if (f != null) result.add(new IntervalLiteral(f.getGroup()));
        f = FunctionInfix.getFunction(sym);
        if (f != null) result.add(new IntervalLiteral(f.getGroup()));
        f = FunctionVariable.getFunction(sym);
        if (f != null) result.add(new IntervalLiteral(f.getGroup()));
        if (result.size() == 0) throwNewCalculusException ("The given funxtion was not found: "+sym);
        return result;
    }
}
