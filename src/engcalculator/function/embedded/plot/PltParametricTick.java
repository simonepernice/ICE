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
import engcalculator.interval.IntervalPoint;
import engcalculator.function.embedded.interval.ItvByAbsoluteToleranceBuilder;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PltParametricTick extends PltParametric {
    private final static ItvByAbsoluteToleranceBuilder INTERVAL_BY_TOLERANCE_BUILDER = new ItvByAbsoluteToleranceBuilder();

    private final static String HELP = "... ($func, nOfIntervals, xInterval) computes func (which should provide a list of 2 elements: x and y) for nOfIntervals in the given interval returning the plottable matrix.\nbuilds an array which can later be used to plot the function on the plot display window. It requires a function returning a list of a couple of elements to compute x and y, the number of intervals and, the interval range used to compute the parametric variable. Those intervals are computed to fill totally the required range without overlapping.";    
    private final static String[] EXAMPLE = {"($f,$x)='(cos x, sin x)', 2, 0_PI"};
    private final static String[] RESULT = {"((0_1,-1_0)#1, (-1_1,-1_1)#1)"};


    public PltParametricTick() {
        super("plot", "ParametricTick", HELP, EXAMPLE, RESULT);
    }

    @Override
    protected ListIntervals addTolerance(ListIntervals xrange) throws Exception {
        return INTERVAL_BY_TOLERANCE_BUILDER.compute(xrange, new ListIntervals (new IntervalPoint ((xrange.get(1).getValue()-xrange.get(0).getValue())/2)));
    }


}
