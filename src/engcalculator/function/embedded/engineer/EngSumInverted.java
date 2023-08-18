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

package engcalculator.function.embedded.engineer;

import engcalculator.function.infix.*;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.embedded.arithmetic.AriInvert;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngSumInverted extends FunctionInfixDualInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());
    private static final AriSum ADD = new AriSum();
    private static final AriInvert INVERT = new AriInvert();
    private final static String[] EXAMPLE_LEFT = {"2_4"};
    private final static String[] EXAMPLE_RIGHT = {"4_6"};
    private final static String[] RESULT = {"1.333333333_2.4"};

    public EngSumInverted() {
        super ("engineer", "+/", DOMAIN, DOMAIN, true, true, "a ... b adds the inverse of the given vector of intervals and then inverts again the result: 1/(1/a+1/b).", EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public Interval _computeIntervals(Interval left, Interval right) throws Exception {
        return INVERT._computeIntervals(ADD._computeIntervals(INVERT._computeIntervals(left), INVERT._computeIntervals(right)));
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        MeasurementUnit mul = leftSide.getMeasurementUnit();
        if (! mul.equals(rightSide.getMeasurementUnit())) throwNewMeasurementUnitException("The measurement units of the agruments do not matchs: "+leftSide.toString()+" and "+rightSide.toString());
        return mul;        
    }            
    
}
