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
import engcalculator.function.embedded.arithmetic.AriMul;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CalSeriesProduct extends CalSeries {
    private final static AriMul MUL = new AriMul();

    private final static String HELP = "... ('f', begin_end) compute f(begin) * ... * f(end).\nIt can be used to evaluate productories.";
    private final static String[] EXAMPLE = {"defineLambdaFunction('$x'), 1_(1 .. 4)"};
    private final static String[] RESULT = {"1, 2, 6, 24"};

    public CalSeriesProduct() {
        super("Product", HELP, EXAMPLE, RESULT);
    }    
    
    protected Interval operation (Interval a, Interval b) throws Exception {
        return MUL._computeIntervals(a, b);
    }

}
