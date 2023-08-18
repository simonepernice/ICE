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

package engcalculator.function.embedded.setInterval;


import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfixDualInputSingleOutput;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SInUnion extends FunctionInfixDualInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());
    private final static String HELP = "intervalA ... intervalB returns the interval which is union of the intervals A and B.\nSince the system works only on single interval the joining may contain also points not available in the two intervals if they are disjoint.";
    private final static String[] EXAMPLE_LEFT = {"6_7","5_7", "1_2", "3_4"};
    private final static String[] EXAMPLE_RIGHT = {"4_5","4_6", "2_4", "-3_-2"};
    private final static String[] RESULT = {"4_7","4_7","1_4","-3_4"};

    public SInUnion() {
        super("setInterval", "|", DOMAIN, DOMAIN, true, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public Interval  _computeIntervals (Interval ls, Interval rs) throws Exception {
        return new IntervalReal (Math.min(ls.getLeft(), rs.getLeft()), Math.max(ls.getRight(), rs.getRight()));
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        MeasurementUnit mul = leftSide.getMeasurementUnit();
        if (! mul.equals(rightSide.getMeasurementUnit())) throwNewMeasurementUnitException("The measurement units of the agruments do not matchs: "+leftSide.toString()+" and "+rightSide.toString());
        return mul;
    }

}
