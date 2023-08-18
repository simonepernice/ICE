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
public final class PhcConductanceQuantum extends PhysicalConstant {
    public PhcConductanceQuantum () {
        super ("conductanceQuantum", 7.7480917004e-5 , 0.0000000053e-5, "S", "[S] The conductance quantum is the quantized unit of conductance. It is defined by \frac{2e^2}{h} and equals 77.5 microsiemens, which corresponds to 12.9kΩ. The conductance quantum is often denoted as G0.");
    }

}
