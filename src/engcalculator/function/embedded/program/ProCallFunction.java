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
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ProCallFunction extends FunctionPrefix {
    private final static String HELP = "... ($func, arg1, arg2, .., argn) returns func(arg1, arg2, .., argn).\nIt requires a literal which is the function name to be called followed by its arguments. It is useful to define new functions that accepts function as arguments.";
    private final static String[] EXAMPLE = {"$cos, 0"};
    private final static String[] RESULT = {"1"};
    private final static DomainList DOMAIN = new DomainList(new DomainFunctionPrefix(-1), new DomainInterval());

    public ProCallFunction() {
        super("program", "CallFunction", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        return FunctionPrefix.getFunction(input.getFirst().getName()).compute(input.subList(1, input.size()));
    }
}
