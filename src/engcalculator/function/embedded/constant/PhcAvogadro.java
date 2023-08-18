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
public final class PhcAvogadro extends PhysicalConstant {
    public PhcAvogadro () {
        super ("avogadro", 6.02214179e23, 0.00000030e23, "mol -1", "[1/mol] The Avogadro constant (symbols: L, NA) is the number of \"elementary entities\" (usually atoms or molecules) in one mole, that is (from the definition of the mole), the number of atoms in exactly 12 grams of carbon-12.");
    }

}
