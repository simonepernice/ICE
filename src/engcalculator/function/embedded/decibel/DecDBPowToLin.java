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

package engcalculator.function.embedded.decibel;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DecDBPowToLin extends DecDBToLin {
    private final static String[] EXAMPLE = {"20"};
    private final static String[] RESULT = {"100"};

    public DecDBPowToLin() {
        super("DbPowToLin", 10, EXAMPLE, RESULT);
    }
}