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

package engcalculator.function.embedded.list;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisLogarithmic extends LisList {
    private final static String[] EXAMPLE = {"5, 10_1G"};
    private final static String[] RESULT = {"10, 1k, 100k, 10M, 1G"};


    public LisLogarithmic() {
        super ("Logarithmic", "logarithmically", EXAMPLE, RESULT);
    }

    @Override
    protected double __computeInverse(double input) {
        return Math.pow(10, input);
    }

    @Override
    protected double __computeDirecte(double input) {
        return Math.log10(input);
    }

}
