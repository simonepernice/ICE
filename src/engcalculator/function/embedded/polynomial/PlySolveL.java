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
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PlySolveL extends FunctionPrefixPlySolveARootATime {

    private final static String HELP = "... ({an, an-1, .., a1, a0}, {rn-1, rn-2, .., r1, r0}) given the coefficients of polynomial and a first guess of the roots it finds iterativaly all the available roots.\nThe algorithm is based on Laguerre's method: https://en.wikipedia.org/wiki/Laguerre%27s_method .To find complex roots for each starting point the algorithm of Laguerre is applied.";
    private final static String[] EXAMPLE = {"{1,5,6},{1,2}", "{(1, -6) ***** (1, -3)}, {1,10}"};
    private final static String[] RESULT = {"-2,-3", "3, 6"};

    public PlySolveL() {
        super("SolveL", HELP, EXAMPLE, RESULT);
    }

    @Override
    public Interval _findNextRoot (ListIntervals f, Interval guess) throws Exception {
        ListIntervals fp, fpp;        

        final int maxIteration = MAXITERATIONS.getVal();
        final double epsilon = EPSILON.getVal();                

        fp = DER._compute(f);
        fpp = DER._compute(fp);
        Interval x0, y0 ; 
        Interval x1 ;
        Interval G, H, a, d, d1, d2;
        final Interval n = new IntervalPoint (f.size()-1);
        final Interval nm1 = new IntervalPoint (f.size()-2);
        x1 = guess;
        int iterations = 0;
        do {
            x0 = x1;
            y0 = EVAL._computeIntervals(f, x0);
            
            G = DIV._computeIntervals(EVAL._computeIntervals(fp, x0), y0);            
            H = SUB._computeIntervals(SQR._computeIntervals(G), DIV._computeIntervals(EVAL._computeIntervals(fpp, x0), y0));
            d = SQRT._computeIntervals(MUL._computeIntervals(nm1, SUB._computeIntervals(MUL._computeIntervalComplex(n, H), SQR._computeIntervals(G))));
            d1 = SUM._computeIntervals(G, d);
            d2 = SUB._computeIntervals(G, d);
            if (ABS._computeIntervals(d1).getValue() >= ABS._computeIntervals(d2).getValue()) x1 = SUB._computeIntervals(x0, DIV._computeIntervals(n, d1));
            else x1 = SUB._computeIntervals(x0, DIV._computeIntervals(n, d2));          
            ++ iterations;
        } while (y0.distance(Interval.ZERO) > epsilon && iterations < maxIteration);

        if (iterations >= maxIteration) return null;

        return x1;
    }

}
