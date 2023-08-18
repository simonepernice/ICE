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

package engcalculator.function.embedded.logarithm;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LogLn extends LogLogarithm {
    private final static String HELP = "... (a) computes the natural logarithm of the interval a.\nAccept complex arguments.";
    private final static String[] EXAMPLE = {"E", ";exp ln -5",";exp ln (10+5*I)"};
    private final static String[] RESULT = {"1", "-5", "(10+5*I)"};

    public LogLn() {
        super (".ln", HELP, EXAMPLE, RESULT);
    }

    @Override
    public double  __compute(double input) throws Exception {
        return Math.log(input);
    }

    @Override
    public double getBase() throws Exception {
        return Math.E;
    }

}
