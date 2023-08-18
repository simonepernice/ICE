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
public final class PhcElectronVolt extends PhysicalConstant {
    public PhcElectronVolt () {
        super ("electronVolt", 1.602176487e-19, 0.000000040e-19, "J",  "[J] In physics, the electron volt (symbol eV; also written electron-volt according to the NIST, IUPAC,[1] and BIPM[2]) is a unit of energy. By definition, it is equal to the amount of kinetic energy gained by a single unbound electron when it accelerates through an electrostatic potential difference of one volt.");
    }

}
