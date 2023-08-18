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

package engcalculator.function.embedded.constant;

import engcalculator.function.variable.PhysicalConstant;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PhcSpeedOfLight extends PhysicalConstant {
    public PhcSpeedOfLight () {
        super ("speedOfLight", 299792458, 0, "m/s", "[m/s] In physics, the speed of light (usually denoted c) refers to a fundamental physical constant, the speed at which light and all electromagnetic radiation travel in a perfect vacuum, which is 299,792,458 meters per second (about 300,000 kilometers per second or 186,000 miles per second).");
    }

}
