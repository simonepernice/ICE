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
package engcalculator.function.embedded.polynomial;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PlySolveNR extends FunctionPrefixPlySolveARootATime {

    private final static String HELP = "... ({an, an-1, .., a1, a0}, {rn-1, rn-2, .., r1, r0}) given the coefficients of polynomial and a first guess of the roots it finds iterativaly all the available roots.\nThe algorithm is based on Newton-Raphson algorithm: https://en.wikipedia.org/wiki/Newton%27s_method .To find complex roots for each starting point the algorithm of Newton Rapshon is applied.";
    private final static String[] EXAMPLE = {"{1,5,6},{-1,-4}", "{(1, -6) ***** (1, -3)}, {1,10}"};
    private final static String[] RESULT = {"-2,-3", "3, 6"};

    public PlySolveNR() {
        super("SolveNR", HELP, EXAMPLE, RESULT);
    }

    @Override
    public Interval _findNextRoot (ListIntervals f, Interval guess) throws Exception {
        ListIntervals fp;        

        final int maxIteration = MAXITERATIONS.getVal();
        final double epsilon = EPSILON.getVal();                

        fp = DER._compute(f);
        Interval x0 , y0; 
        Interval x1 ;            
        x1 = guess;
        int iterations = 0;
        do {
            x0 = x1;
            y0 = EVAL._computeIntervals(f, x0);
            x1 = SUB._computeIntervals(x0, DIV._computeIntervals(y0, EVAL._computeIntervals(fp, x0)));
            ++ iterations;
        } while (y0.distance(Interval.ZERO) > epsilon && iterations < maxIteration);

        if (iterations >= maxIteration) return null;

        return x1;
    }

}
