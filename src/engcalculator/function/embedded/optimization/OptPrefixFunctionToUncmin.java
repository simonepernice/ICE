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
package engcalculator.function.embedded.optimization;

import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class OptPrefixFunctionToUncmin implements OptUncmin_methods {

    private final FunctionPrefix userFunction;

    public OptPrefixFunctionToUncmin(FunctionPrefix userFunction) {
        this.userFunction = userFunction;
    }

    public double f_to_minimize(double[] x) {
        ListIntervals input = new ListIntervals();
        for (int i = 1; i < x.length; ++i) {
            input.append(new IntervalPoint(x[i]));
        }
        double result;
        try {
            result = userFunction.compute(input).getFirst().getValue();
        } catch (Exception e) {
            return Double.POSITIVE_INFINITY;
        }
        return result;
    }

    public void gradient(double[] x, double[] g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void hessian(double[] x, double[][] h) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
