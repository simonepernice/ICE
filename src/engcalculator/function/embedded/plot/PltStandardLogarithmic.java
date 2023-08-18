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

import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.domain.*;
import engcalculator.function.embedded.list.LisBuilder;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.list.LisLogarithmic;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PltStandardLogarithmic extends FunctionPrefix {
    private final static LisLogarithmic LOG_LIST = new LisLogarithmic();
    private final static LisBuilder LIST_BUILDER = new LisBuilder();

    private final static String HELP = "... ($func, nOfPoints, xInterval) computes func (which provides the y value) for nOfPoints logarithmically spaced in the given interval returning the plottable matrix.\nIt builds an array which can later be used to plot the function graph. It requires the function name to compute the y, the number of points where it will be evaluated and an interval with the range of the point to be evaluated. The x will be logarithmically spaced. This function is useful to have the function points equally spaced for logarithmic plot.";    
    private final static String[] EXAMPLE = {"defineLambdaFunction('$x'), 5, 1_1k"};
    private final static String[] RESULT = {"(listLogarithmic (5, 1_1k) # 1, defineLambdaFunction('$x',listLogarithmic (5, 1_1k)) # 1)"};
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix (1), new DomainIntervalPoint(), new DomainIntervalReal());

    public PltStandardLogarithmic () {
        super("plot", "StandardLogarithmic", (byte) 3, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    public PltStandardLogarithmic(String group, String name, String help, String[] examples, String[] results) {
        super(group, name, (byte) 3, DOMAIN, false, true, help, examples, results);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());

        ListIntervalsMatrix xrange = new ListIntervalsMatrix (LOG_LIST._compute(input.subList(1, 3)), 1);

        xrange = addTolerance (xrange);

        ListIntervalsMatrix yrange = new ListIntervalsMatrix (f.compute(xrange), 1);

        return LIST_BUILDER._compute(xrange, yrange);
    }

    protected ListIntervalsMatrix addTolerance(ListIntervalsMatrix xrange) throws Exception {
        return xrange;
    }

}
