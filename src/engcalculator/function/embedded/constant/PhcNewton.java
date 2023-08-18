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
public final class PhcNewton extends PhysicalConstant {
    public PhcNewton () {
        super ("newton", 6.67428e-11 , 0.00067e-11, "m 3/kg s 2", "[m^3/kg/s^2] According to the law of universal gravitation, the attractive force (F) between two bodies is proportional to the product of their masses (m1 and m2), and inversely proportional to the square of the distance (r) between them: F = G / (m_1 m_2 r^2). The constant of proportionality, G, is the gravitational constant.");
    }

}
