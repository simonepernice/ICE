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

package engcalculator.function.embedded.plot;

import engcalculator.function.embedded.list.LisLinear;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class PltParametric extends FunctionPrefix {
    private final static String HELP = "... ($func, nOfPoints, xInterval) computes func (which should provide a list of 2 elements: x and y) for nOfPoints in the given interval returning the plottable matrix.\nIt builds an array which can later be used to plot the function on the plot display window. It requires a function returning a list of a couple of elements to compute x and y, the number of points, the interval range, the absolute and the relative tolerance of those intervals used to compute the parametric variable.";
    private final static String[] EXAMPLE = {"($f,$x)='(cos x, sin x)', 5, 0_2*PI"};
    private final static String[] RESULT = {"((1,0,-1,0,1)#1 , (0,1,0,-1,0)#1)"};
    private final static LisLinear LIN_LIST = new LisLinear();

    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix (1), new DomainIntervalPoint(), new DomainIntervalReal());

    public PltParametric () {
        super("plot", "Parametric", (byte) 3, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    public PltParametric (String group, String name, String help, String[] example, String[] result) {
        super(group, name, (byte) 3, DOMAIN, false, true, help, example, result);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        FunctionPrefix fxy = FunctionPrefix.getFunction(input.getFirst().getName());

        ListIntervals t = addTolerance(LIN_LIST._compute(input.subList(1, 3)));

        return new ListIntervalsMatrix ( fxy.compute(t), 2);
    }

    protected ListIntervals addTolerance(ListIntervals xrange) throws Exception {
        return xrange;
    }
}
