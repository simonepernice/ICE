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

import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class OptUnconditionalMinimum extends FunctionPrefix {
    private final static String HELP = "... ($func, x0, x1, .., xn) finds the list of input values (x'0, x'1, .., x'n) required to minimize the given error fuction func beginning the search at (x0, x1, .., xn).\nIt requires the function to be minimized and the initial point values. It is very usefull in design where the function measure how far is from the best result. The starting point must be a list of point intervals and the average value of the function output is used if it is a tick function. The non linear optimizator is downloaded from http://www1.fpl.fs.fed.us/optimization.src.tar.html and it is based on the public domain UNCMIN routines traslated in Java by Steve Verrill.";
    private final static String[] EXAMPLE = {"$sin, 3", "(($f,x,y,z)='(x-2)^2+(y-7)^2+(z-10)^2',0,0,0)"};
    private final static String[] RESULT = {"3*PI/2", "2,7,10"};
    private final static DomainList DOMAIN = new DomainList(new DomainFunctionPrefix(-1), new DomainIntervalPoint());

    public OptUnconditionalMinimum() {
        super ("optimization", "UnconditionalMinimum", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        FunctionPrefix userPrefixFunction = FunctionPrefix.getFunction(input.getFirst().getName());

        final int np1 = userPrefixFunction.getNumbArgs() + 1;

        if (input.size() != np1) {
            throwNewCalculusException("The starting point dimension "+(input.size()-1)+" and the function input dimension "+(np1-1)+" do not match:");
        }

        double[] x0 = new double[np1];
        for (int i = 1; i < np1; ++i) {
            x0[i] = input.get(i).getValue();
        }

        OptPrefixFunctionToUncmin uncmintest = new OptPrefixFunctionToUncmin(userPrefixFunction);

        double[] x = new double[np1];       //solution point
        double[] f = new double[2];         //value of uncmintest at solution point

        double[] g = new double[np1];       //value of gradient at solution point
        int[] info = new int[2];            //optimization result quality
        double a[][] = new double[np1][np1];//workspace for hessian
        double udiag[] = new double[np1];   //workspace for diagonaal of hessian

        OptUncmin_f77.optif0_f77(np1 - 1, x0, uncmintest, x, f, g, info, a, udiag);

        if (info[1] >= 4) {
            throwNewCalculusException("It was not possible to find the minimum of the given function");
        }

        ListIntervals result = new ListIntervals();
        for (int i = 1; i < np1; ++i) {
            result.append(new IntervalPoint(x[i]));
        }

        return result;

    }
}
