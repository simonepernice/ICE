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


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvIsProper extends ItvIs {
    private static final String[] EXAMPLE = {"1_10","'1_10'","10_10","10_1","1_10+I* 1_10","1_10+I* 10_1"};
    private static final String[] RESULT = {"true","false","true","false","true","false"};           

    public ItvIsProper() {
        super("Proper", EXAMPLE, RESULT);
    }
        
    public boolean _computeIntervalsIs (Interval a) {
        return a.isProper();
    }
}
