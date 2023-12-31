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

package engcalculator.function.embedded.trigonometry;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class TriSin extends Trig {
    private final static String HELP = "... (interval) computes the sin of the given interval.";
    private final static String[] RESULT = {"1", "0"};
    private final static String[] EXAMPLE = {"PI/2", "0"};

    public TriSin() {
        super ("sin", HELP, -1, 3*Math.PI/2, 1, Math.PI/2, 0, 2 * Math.PI, EXAMPLE, RESULT);
    }

    @Override
    protected double __compute(double input) {
        return Math.sin(input);
    }

}
