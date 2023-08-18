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


import engcalculator.domain.DomainIntervalComplex;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.domain.DomainListMatrix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.compare.ComGreater;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.interval.ItvDual;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EquGaussElimination extends FunctionPrefix {
    private final static String HELP = "... ([A matrix],[B matrix]) solves the simultaneous equation defined by A x = B.\nIt uses Gauss elimination algorithm. The source code was adapted from http://snippets.dzone.com/posts/show/4874. It is still not properly working on intervals, I have to understand if it is possible to solve simultaneous equations with real number, complex number and also intervals. Unfortunately using interval as input, due to interval missing of distributive property, the result is only approximate. Try with gaussseidel function instead for intervals.";
    private final static String[] EXAMPLE = {"(1, 2, 3, 4)#2,(5,6)#1","a=statisticRandom(listClone(9, -100_100))#3, b=statisticRandom(listClone(3, -100_100))#1;a ** equationGaussElimination(a,b) ","a=statisticRandom(listClone(9, -100_100))#3+I*statisticRandom(listClone(9, -100_100))#3, b=statisticRandom(listClone(3, -100_100))#1+I*statisticRandom(listClone(3, -100_100))#1;a ** equationGaussElimination(a,b) "};
    private final static String[] RESULT = {"(-4,4.5)#1","b","b"};
    private final static DomainListMatrix DOMAIN = new DomainListMatrix(1, new DomainIntervalComplex());

    private final static ComGreater GREATER = new ComGreater();
    private final static AriMul MUL = new AriMul();
    private final static AriDiv DIV = new AriDiv ();
    private final static AriSub SUB = new AriSub();
    private final static ItvDual DUAL = new ItvDual();

    public EquGaussElimination() {
        super("equation", "GaussElimination", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervalsMatrix result = new ListIntervalsMatrix (input);
        forwardSubstitution(result);
        reverseElimination(result);
        return getSolutionColumn(result);

    }

    void forwardSubstitution(ListIntervalsMatrix result) throws Exception {
        int i, j, k, max;
        final int n=result.rowSize();
        Interval t;
        for (i = 0; i < n; ++i) {
            max = i;
            for (j = i + 1; j < n; ++j) {
                if (GREATER.compareForTrue(result.get(j, i), result.get(max, i)))
                    max = j;
            }

            for (j = 0; j < n + 1; ++j) {
                t = result.get(max,j);
                result.set(max,j, result.get(i,j));
                result.set(i, j, t);
            }

            for (j = n; j >= i; --j)
                for (k = i + 1; k < n; ++k) {
                    result.set(k, j, SUB._computeIntervals(result.get(k, j), DUAL._computeIntervals(DIV._computeIntervals(MUL._computeIntervals(result.get(k, i), result.get(i, j)), DUAL._computeIntervals(result.get(i ,i))))));
                }

        }
    }

    void reverseElimination(ListIntervalsMatrix result) throws Exception {
        int i, j;
        final int n=result.rowSize();
        for (i = n - 1; i >= 0; --i) {
            result.set(i, n, DIV._computeIntervals(result.get(i, n), DUAL._computeIntervals(result.get(i, i))));
            for (j = i - 1; j >= 0; --j) {
                result.set(j, n, SUB._computeIntervals(result.get(j, n), DUAL._computeIntervals(MUL._computeIntervals(result.get(j,i), result.get(i, n)))));
            }
        }
    }

    private ListIntervalsMatrix getSolutionColumn(ListIntervalsMatrix result) throws Exception {
        ListIntervalsMatrix output = new ListIntervalsMatrix(1);
        final int col = result.columnSize()-1, row = result.rowSize();
        for(int r=0;r<row;++r) {
            output.add(result.get(r,col));

        }
        return output;
    }

}
