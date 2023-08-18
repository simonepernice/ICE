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
import engcalculator.interval.IntervalPoint;
import engcalculator.function.embedded.interval.ItvByAbsoluteToleranceBuilder;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PltStandardTick extends PltStandard {
    private final static ItvByAbsoluteToleranceBuilder INTERVAL_BY_TOLERANCE_BUILDER = new ItvByAbsoluteToleranceBuilder();

    private final static String HELP = "... ($func, nOfIntervals, xInterval) computes func (which should provide y values) for nOfIntervals in the given interval returning the plottable matrix.\nIt build an array which can later be used to plot the function graph. It requires the function name to compute the y, the number of intervals where it will be evaluated and an interval with the range of the intervals to be evaluated. Those intervals are automatically computed to fill in all the required interval without overlapping.";
    private final static String[] EXAMPLE = {"$cos, 2, 0_PI"};
    private final static String[] RESULT = {"(((-PI/2)_(PI/2),(PI/2)_(3*PI/2))#1 , (0_1,-1_0)#1)"};

    public PltStandardTick() {
        super("plot", "StandardTick", HELP, EXAMPLE, RESULT);
    }

    @Override
    protected ListIntervalsMatrix addTolerance(ListIntervalsMatrix xrange) throws Exception {
        return new ListIntervalsMatrix (INTERVAL_BY_TOLERANCE_BUILDER.compute(xrange, new ListIntervals (new IntervalPoint ((xrange.get(1).getValue()-xrange.get(0).getValue())/2))), 1);
    }

}
