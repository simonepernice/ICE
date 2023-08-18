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

package engcalculator.function.embedded.system;

import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrixNamed;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SysQuit extends FunctionPrefix {
    private final static String HELP = "... () exits from ICE.\nIt drastically close the JVM regardless of the status of the history, etc.. So no question about not-saved files will be asked.";

    public SysQuit() {
        super ("system", "Quit", (byte) 0, null, true,true, HELP, null, null);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        System.exit(0);
        return new ListIntervalsMatrixNamed ("bye");
    }

}
