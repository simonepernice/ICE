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

package engcalculator.function.embedded.hyperbolic;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class HypArcSin extends HypArc {
    private final static String HELP = "... (interval) computes the hyperbolic inverse sin of the interval.";
    private final static String[] EXAMPLE = {"0", ";hyperbolicArcSin (123)"};
    private final static String[] RESULT = {"0", ";-hyperbolicArcSin (-123)"};

    public HypArcSin() {
        super("ArcSin", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, HELP, EXAMPLE, RESULT);
    }
    
    @Override
    protected double __compute(double input) {
        return Math.log(input + Math.sqrt(input*input + 1.0));
    }
}
