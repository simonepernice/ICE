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

import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class ConvertConstant extends FunctionVariable {

    public ConvertConstant(double value, String from, String to) {
        super("convert", new StringBuilder().append(Character.toUpperCase(from.charAt(0))).append(from.substring(1)).append("To").append(Character.toUpperCase(to.charAt(0))).append(to.substring(1)).toString(), new IntervalPoint(value), true, new StringBuilder("This constant can be used to convert from a measure unit to another. Multiply this constant for ").append(from).append(" to get ").append(to).append(". This constants is based on the following equivalence 1 ").append(from).append(" = ").append(value).append(to).append(".").toString());
    }


}
