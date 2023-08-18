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
package engcalculator.function.embedded.equation;

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EquOrdinaryDifferentialEquationFirst extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix (-2), new DomainLogicAnd (new DomainIntervalPositive (), new DomainIntervalPoint()), new DomainIntervalReal());
    private final static String HELP = "... ($f, points, time interval, f0(0), .. fn(0)) solves a differential equation dy/dt = f(t, y), in the given time interval, beginning with initial condition f(0).\nIt uses explicit fourth order Runge-Kutta method. It needs the equation name f(t, y), the number of steps n, the time interval tb_te, the function value y(tb). ... solves system of equation differential as well, provided as follows: ...(name of y1' = f1(t,y1,..,yn),..,name of yn' = fn(t,y1,..,yn), points, tmin_tmax, y1(tmin), .., yn(tmin) ";
    private final static String[] EXAMPLE = {";equationOrdinaryDifferentialEquationFirst(($ydot,t,$y)='y', 100, 0_4, 1)::(100,1)", ";equationOrdinaryDifferentialEquationFirst(($ydot,t,$y)='y+t', 3000, 0_0.4, 1)::(3000,1)"};
    private final static String[] RESULT = {"E^4", "2*E^0.4-0.4-1"};

    public EquOrdinaryDifferentialEquationFirst() {
        super("equation", "OrdinaryDifferentialEquationFirst", (byte) -4, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        if ((input.size() - 2) % 2 != 0) {
            throwNewCalculusException("The number of paramenters provided is not correct: "+input.size());
        }

        final int n = (input.size() - 2) / 2;

        final FunctionPrefix[] f = new FunctionPrefix[n];
        for (int i = 0; i < n; ++i) {
            f[i] = FunctionPrefix.getFunction(input.get(i).getName());
            if (f[i] == null) {
                throwNewCalculusException("It was expected a function name as input at position "+i);
            }
            if (!(f[i].getNumbArgs() == n+1 || f[i].getNumbArgs() <= -n-1)) {
                throwNewCalculusException("The function " + f[i].getSymbol() + " should accept "+n+" arguments as input.");
            }
        }

        double[] y = new double[n];
        for (int i = 0; i < n; ++i) {
            y[i] = input.get(i + n + 2).getValue();
        }

        double t;        
        double[][] k = new double[4][n];

        final int STEPS = (int) input.get(n).getValue();
        final double t0 = input.get(n + 1).getLeft();
        final double h = (input.get(n + 1).getRight() - t0) / STEPS;
        t = t0;

        ListIntervalsMatrix result = new ListIntervalsMatrix(n + 1);

        appendResult(result, t, y, n);

        for (int j = 0; j < STEPS; ++j) {
            t += h;

            computeK(k[0], t, h, f, null, y, 0, n);
            computeK(k[1], t, h, f, k[0], y, 2, n);
            computeK(k[2], t, h, f, k[1], y, 2, n);
            computeK(k[3], t, h, f, k[2], y, 1, n);

            // Runge Kutta
            for (int i = 0; i < n; ++i) {
                y[i] += k[0][i] / 6 + k[1][i] / 3 + k[2][i] / 3 + k[3][i] / 6;
            }

            //euler
            //for (int i=0; i<n; ++i) y[i] += k[0][i];

            appendResult(result, t, y, n);
        }

        return result;
    }

    private void computeK(double[] l, double t, double h, FunctionPrefix[] f, double[] k, double[] y, int div, final int n) throws Exception {
        if (l == null) {
            l = new double[n];
        }
        for (int i = 0; i < n; ++i) {
            l[i] = h * f[i].compute(new ListIntervals(new IntervalPoint(t + (k == null ? 0 : h / div))).append(new IntervalPoint(y[i] + (k == null ? 0 : k[i] / div)))).getFirst().getValue();
        }
    }

    private void appendResult(ListIntervalsMatrix result, double t, double[] y, final int n) {
        result.append(new IntervalPoint(t));
        for (int i = 0; i < n; ++i) {
            result.append(new IntervalPoint(y[i]));
        }
    }
}
