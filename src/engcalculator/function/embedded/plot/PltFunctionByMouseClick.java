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

package engcalculator.function.embedded.plot;

import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.function.variable.FunctionVariable;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PltFunctionByMouseClick extends FunctionVariable {
    private final static String HELP = "... is automatically filled with points of the mouse pointer clicking with left button on a graph while shift key is pressed.\nThe right button would delete last added point and the central button the first point.";

    public PltFunctionByMouseClick() {
        super("plot", "FunctionPointsByMouseClick", new ListIntervalsMatrix(2), false, HELP);
    }

}
