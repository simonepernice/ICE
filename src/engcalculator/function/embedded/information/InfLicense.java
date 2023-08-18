/*
 *     ICE (Interval Calculator for Engineer) is a programmable calculator working on intervals.
 *     Copyright (C) 2009  Simone Pernice
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public InfLicense as published by
 *     the Free Software Foundation, either version 3 of the InfLicense, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public InfLicense for more details.
 *
 *     You should have received a copy of the GNU General Public InfLicense
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package engcalculator.function.embedded.information;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class InfLicense extends InfPrefixFunction {
    public static String LICENSE;
    public final static String HELP = "... () prints the GNU GPL 3.0 licence under which is released Engineer Calculator.";

    public InfLicense() {
        super("License", HELP);
        LICENSE = setOutputTextFromFile("EngCalcLicense.txt");
    }

}
