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

package engcalculator.function.embedded.matrix;

import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.IntervalReal;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatLINE extends FunctionVariable {
    private final static String HELP = "... returns the interval 0_-1 which can be used to select an entire row or column for extraction.";
	
    public MatLINE () {
        super ("matrix", "LINE", new IntervalReal(0. , -1.), true, HELP);
    }

}
