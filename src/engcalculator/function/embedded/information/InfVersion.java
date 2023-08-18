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

package engcalculator.function.embedded.information;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class InfVersion extends InfPrefixFunction {
    public final static String VERSION = "First release on 18th July 2009. Last update on 6th September 2016. Version 1.8.0, build 71bb3a202ef3cfbc4023358958cca218039ef3ca";
    public final static String HELP = "... () prints the Engineer Calculator version.";
    public InfVersion() {
        super("Version", HELP, VERSION);
    }

}
