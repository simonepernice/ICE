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

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.embedded.list.LisLinear;
import engcalculator.function.embedded.list.LisBuilder;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefFunctionInverse extends FunctionPrefix  {
    private final static DomainList DOMAIN = new DomainList ( new DomainFunctionPrefix(1), new DomainIntervalLiteral(), new DomainIntervalPoint(), new DomainIntervalReal());
    
    private final static String HELP = "$invFunc ... [[xMatrixColumn], [yMatrixColumn]] would define an inversion function of the given matrix (y becomes x and x becomes y).\nIt can be used to create an interpolation of the inverse function given: from y = f (x) it will create x = inversef (y). Provide the function name to invert, the name of the inverse function, the number of points to use, the interval in which it has to be inverted..";
    private final static String[] EXAMPLES = {"defineFunctionInverse($sin,$inversesin, 21,0_PI/2); inversesin(sin(0, PI/20, PI*5/20, PI*10/20))"};
    private final static String[] RESULTS = {"0, PI/20, PI*5/20, PI*10/20"};
    
    private final static LisLinear LIN_LIST = new LisLinear();
    private final static LisBuilder LIST_BUILDER = new LisBuilder();
    private final static DefFunctionInterpolation FUNCTION_INTERPOLATION_DEFINITION = new DefFunctionInterpolation();

    public DefFunctionInverse() {
        super ("define", "FunctionInverse", (byte) 4, DOMAIN, false, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals x, y;
        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        
        x = LIN_LIST._compute(input.subList(2, 4));
        y = f.compute(x);
        
        return FUNCTION_INTERPOLATION_DEFINITION.compute(input.subList(1, 2), LIST_BUILDER._compute(new ListIntervalsMatrix(y, 1), new ListIntervalsMatrix (x, 1)));
    }

}
