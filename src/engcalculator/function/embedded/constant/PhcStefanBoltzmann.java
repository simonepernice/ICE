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
public final class PhcStefanBoltzmann extends PhysicalConstant {
    public PhcStefanBoltzmann () {
        super ("stefanBoltzmann", 5.670400e-8, 0.000040e-8, "W/m 2 K 4", "[W/m^2/K^4] The Stefan–Boltzmann constant (also Stefan's constant), a physical constant denoted by the Greek letter σ, is the constant of proportionality in the Stefan–Boltzmann law: the total energy radiated per unit surface area of a black body in unit time is proportional to the fourth power of the thermodynamic temperature.");
    }

}
