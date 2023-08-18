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

package engcalculator.function.embedded.calculus;

import engcalculator.interval.Interval;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CalSeriesSum extends CalSeries {
    private final static AriSum SUM = new AriSum();

    private final static String HELP = "... ('f', begin_end) compute f(begin)+ ... + f(end).\nIt can be used to compute summatories.";
    private final static String[] EXAMPLE = {"defineLambdaFunction('2*$x+1'), 0_(1 .. 5)"};
    private final static String[] RESULT = {"(2 .. 6)^2"};

    public CalSeriesSum() {
        super("Sum", HELP, EXAMPLE, RESULT);
    }    
    
    protected Interval operation (Interval a, Interval b) throws Exception {
        return SUM._computeIntervals(a, b);
    }

    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws Exception {
        LinkedList<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(input.getLast().getMeasurementUnit());
        return  res;
    }    
}
