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
public final class TriArcSin extends TriArcTrig {
    private final static String HELP = "... (interval) computes the inverse sin of the interval included in -1, 1.";
    private final static String[] EXAMPLE = {"1", "0"};
    private final static String[] RESULT = {"PI/2", "0"};

    public TriArcSin() {
        super("arcSin", -1, 1, true, HELP, EXAMPLE, RESULT);
    }
    
    @Override
    protected double __compute(double input) {
        return Math.asin(input);
    }
}
