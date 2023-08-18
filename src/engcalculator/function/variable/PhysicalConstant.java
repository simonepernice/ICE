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

package engcalculator.function.variable;

import engcalculator.function.Function;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class PhysicalConstant extends FunctionVariable {

    public PhysicalConstant(String symbol, double value, double tolerance, String measurementUnit, String help) {
        super ("constant", new StringBuffer ().append(Character.toUpperCase(symbol.charAt(0))).append(symbol.substring(1)).toString(), new IntervalReal(value-tolerance, value+tolerance, /*Function.USEMEASUREMENTUNIT && */measurementUnit != null ? MeasurementUnit.parseMeasurementUnitNoInternalException(measurementUnit) : null), true, help);

    }


}
