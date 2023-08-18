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

package engcalculator.function.embedded.measurementUnit;

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicNot;
import engcalculator.function.infix.FunctionInfixDualInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MUnSet extends FunctionInfixDualInputSingleOutput {
    private final static DomainList DOMAINLEFT = new DomainList (new DomainLogicNot(new DomainIntervalLiteral()));
    private final static DomainList DOMAINRIGHT = new DomainList (new DomainIntervalLiteral());
    
    private final static String HELP = "(list) ... 'measurementUnit' : given a list of values it set up the measurement units for this list.\nThe measurement unit has to be set on the single interval. During some calculatio a ICE would stop if the measuremnt units do not match. The measurement unit can be find in the help menu and are literal like 'A' for ampere or 'V' for Volt. However every measurement unit with a valid name creates a variable containing its literal value. Therefore after loading the package the variable V would contain 'V' (if it was not previously used).";
    private final static String[] EXAMPLE_LEFT = {"; 5 @ V / 5 @ A"};
    private final static String[] EXAMPLE_RIGHT = {""};
    private final static String[] RESULT = {"1 @ 'Ohm'"};
    
    public MUnSet() {
        super ("measurementUnit", "@", DOMAINLEFT, DOMAINRIGHT, true, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }     

    @Override
    public Interval _computeIntervals(Interval left, Interval right) throws Exception {
        MeasurementUnit mu = MeasurementUnit.parseMeasurementUnit(right.getName());
        if (mu == null) throwNewMeasurementUnitException ("The measurement unit was not recognized");
        Interval result = Interval.createIntervalWithMeasurementUnit(left, mu);
        if (result != null) return result;
        return left;
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws Exception {
        return null;
    }
}
