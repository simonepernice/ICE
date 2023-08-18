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
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfixDualInputSingleOutput;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvByExtremesBuilder extends FunctionInfixDualInputSingleOutput {
    private final static DomainList DOMAIN= new DomainList (new DomainIntervalPoint());

    private final static String HELP = "a ... b generates the interval having a as left extreme, and b as right extremes.\nAlthough this function has a very high priority, keep in mind prefix functions has higher priority than infix, therefor func a ... b returns actually (func a) ... b.";    
    private final static String[] EXAMPLE_LEFT = {"2","3"};
    private final static String[] EXAMPLE_RIGHT = {"4","5"};
    private final static String[] RESULT = {"2_4","3_5"};

    public ItvByExtremesBuilder() {
        super("interval", "_", DOMAIN, DOMAIN, true, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public Interval _computeIntervals (Interval leftSide, Interval rightSide) throws Exception {
        final double a = leftSide.getLeft();
        final double b = rightSide.getRight();
        if (a == b) return leftSide;
        return new IntervalReal (a, b);
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        MeasurementUnit mul = leftSide.getMeasurementUnit();
        if (! mul.equals(rightSide.getMeasurementUnit())) throwNewMeasurementUnitException("The measurement units of the agruments do not matchs: "+leftSide.toString()+" and "+rightSide.toString());
        return mul;
    }            
}
