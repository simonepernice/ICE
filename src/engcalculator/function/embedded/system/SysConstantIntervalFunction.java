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
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 *
 */
//It is used to contain an interval in a compiled program since a compiled program is a list of functions
public final class SysConstantIntervalFunction extends FunctionPrefix {
    ListIntervals value;

    public SysConstantIntervalFunction(ListIntervals value) {
        super("", value.toString(), (byte) 0, null, false, true, "", null, null);
        this.value = value;
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return value;
    }

    public ListIntervals getValue () {
        return value;
    }


    @Override
    public int getComputeNumbArg() {
        return 0;
    }
}
