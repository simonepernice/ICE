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

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ProOnErrorReturn extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList(new DomainFunctionPrefix(1), new DomainInterval(), new DomainInterval());
    private final static String HELP = "... ($func, arg, onErrArg) try to compute and return func(arg), if that rises any error it returns onErrArg.\nThis function can be used to show any error (risen by sub function) to the user.";
    private final static String[] EXAMPLES = {"defineLambdaFunction 'sin $x / $x',0,1","defineLambdaFunction 'sin $x / $x',5,1"};
    private final static String[] RESULTS = {"1","sin 5 / 5"};
    
    public ProOnErrorReturn() {
        super("program", "OnErrorReturn", (byte) 3, DOMAIN, false, true, HELP, EXAMPLES, RESULTS);
    }
    
    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        try {
            FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
            return f.compute(input.subList(1, 2));
        } catch (CalculusException ce) {
            return input.subList(2, 3);
        }
    }
}
