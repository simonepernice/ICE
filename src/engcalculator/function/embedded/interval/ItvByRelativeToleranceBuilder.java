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

package engcalculator.function.embedded.interval;

import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfixDualInputSingleOutput;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvByRelativeToleranceBuilder extends FunctionInfixDualInputSingleOutput {
    private final static DomainList DOMAINLEFT = new DomainList (new DomainIntervalComplex());
    private final static DomainList DOMAINRIGHT = new DomainList (new DomainIntervalPoint());

    private final static String HELP = "a ... b generates theinterval with the value of a-a/b*100 as left exreme and a+a/b*100 as right extreme.\nCan be used to add a tolerance to a list of intervals. Although this function has a very high priority, keep in mind prefix functions has higher priority than infix, therefor func a ... b returns actually (func a) ... b.";
    private final static String[] EXAMPLE_LEFT = {"2", "3,4,5"};
    private final static String[] EXAMPLE_RIGHT = {"4","5"};
    private final static String[] RESULT = {"2%4", "3%5,4%5,5%5"};

    public ItvByRelativeToleranceBuilder() {
        super("interval", "%", DOMAINLEFT, DOMAINRIGHT, true, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public Interval _computeIntervals (Interval leftSide, Interval rightSide) throws Exception {
        double first = leftSide.getLeft();
        double second = leftSide.getRight();
        double val = leftSide.getValue();
        double tol = rightSide.getLeft()*val/100;

        if (tol == 0) return leftSide;

        if (val >= 0) {
            first -= tol;
            second += tol;
        } else {
            first += tol;
            second -= tol;
        }
        return  new IntervalReal(first, second);
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        if (! rightSide.getMeasurementUnit().equals(MeasurementUnit.PURE)) throwNewMeasurementUnitException("The measurement units on the right side should be a pure value while it was found: "+rightSide.toString());
        return leftSide.getMeasurementUnit();
    }            
}
