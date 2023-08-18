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

package engcalculator.function.embedded.integer;

import engcalculator.function.MeasurementUnitException;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IntOr extends IntInfixFunction {
    private final static String HELP = "a ... b compute the OR operation bit by bit between two integer point intervals a and b.";
    private final static String[] EXAMPLE_LEFT = {  "integerFromBaseToBaseTen(2, '0011')"};
    private final static String[] EXAMPLE_RIGHT = { "integerFromBaseToBaseTen(2, '0101')"};
    private final static String[] RESULT = {"integerFromBaseToBaseTen(2, '0111')"};

    public IntOr() {
        super("|||", HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public long computeIntegerOperation(long a, long b) throws Exception {
        return a | b;
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        return MeasurementUnit.PURE;
    }

}
