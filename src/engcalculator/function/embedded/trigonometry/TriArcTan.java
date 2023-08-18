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
public final class TriArcTan extends TriArcTrig {
    private final static String HELP = "... (interval) computes the inverse tan of the given interval.";
    private final static String[] EXAMPLE = {"0","1"};
    private final static String[] RESULT = {"0", "PI/4"};

    public TriArcTan() {
        super("arcTan", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true, HELP, EXAMPLE, RESULT);
    }
    
    @Override
    protected double __compute(double input) {
        return Math.atan(input);
    }
}
