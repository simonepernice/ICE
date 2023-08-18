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
package engcalculator.function.embedded.fourier;

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.embedded.list.LisBuilder;
import engcalculator.function.embedded.list.LisGetElements;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.*;
import java.util.Arrays;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FouFFT extends FunctionPrefix {

    private static final DomainList DOMAIN = new DomainList(new DomainIntervalComplex());
    private final static String HELP = "... (list) computes the fast fourier transform of the list. It is also applicable to plot matrix.\nFast Fourier Transform is able to transform a list of size equals to a power of 2 from time domain to frequency domain. ... can be also used with a matrix where the first column represents the samplig times and the second the values like the one provided by standardplot. The algorithm is got from http://www.cs.princeton.edu/introcs/97data/FFT.java.html and adapted to EngCalculator data structures by myself. To display the fft result use dataplot function.";
    private final static String[] EXAMPLE = {"1,0,-1,0", "matrixTranspose([0,1,2,3],[1,0,-1,0])"};
    private final static String[] RESULT = {"0,2,0,2", "matrixTranspose([0,.25,.5,.75],[0,2,0,2])"};
    private final static LisGetElements GETVECTOR = new LisGetElements();
    private final static AriSum SUM = new AriSum();
    private final static AriSub SUB = new AriSub();
    private final static AriMul MUL = new AriMul();
    private final static LisBuilder LISTBUILDER = new LisBuilder();
    private final static Interval[] ARRAYSAMPLE = new Interval[0];

    public FouFFT() {
        super("fourier", "FFT", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    // compute the FouFFT of x[], assuming its length is a power of 2
    @Override
    public ListIntervals _compute(ListIntervals x) throws Exception {
        ListIntervals time, frequency = null, tvalue, fvalue;
        double dt;

        if (x.rowSize() > 1) {
            if (x.columnSize() != 2) {
                throwNewCalculusException("The input matrix must have 2 columns the time and the value");
            }
            time = GETVECTOR._compute(x, new ListIntervals(new IntervalReal(0, -1)).append(new IntervalPoint(0)));
            tvalue = GETVECTOR._compute(x, new ListIntervals(new IntervalReal(0, -1)).append(new IntervalPoint(1)));

            dt = time.get(1).getValue() - time.get(0).getValue();
            for (int i = 2; i < time.size(); ++i) {
                if (Math.abs(time.get(i).getValue() - time.get(i - 1).getValue() - dt) > 1e-6) {
                    throwNewCalculusException("The sampling interval in not constant.");
                }
            }

            frequency = new ListIntervalsMatrix(1);

            final double df = 1 / (time.size() * dt);
            double f = 0;
            for (int i = 0; i < time.size(); ++i) {
                frequency.add(new IntervalPoint(f));
                f += df;
            }

            fvalue = new ListIntervalsMatrix(1);
        } else {
            tvalue = x;
            fvalue = new ListIntervals();
        }

        fvalue.addAll(Arrays.asList(fft(tvalue.toArray(ARRAYSAMPLE))));

        if (frequency != null) {
            return LISTBUILDER._compute(frequency, fvalue);
        }
        return fvalue;
    }

    /*************************************************************************
     *  Compilation:  javac FouFFT.java
     *  Execution:    java FouFFT N
     *  Dependencies: Complex.java
     *
     *  Compute the FouFFT and inverse FouFFT of a length N complex sequence.
     *  Bare bones implementation that runs in O(N log N) time. Our goal
     *  is to optimize the clarity of the code, rather than performance.
     *
     *  Limitations
     *  -----------
     *   -  assumes N is a power of 2
     *
     *   -  not the most memory efficient algorithm (because it uses
     *      an object type for representing complex numbers and because
     *      it re-allocates memory for the subarray, instead of doing
     *      in-place or reusing a single temporary array)
     *
     *************************************************************************/
    // compute the FouFFT of x[], assuming its length is a power of 2
    public static Interval[] fft(Interval[] x) throws Exception {
        final int N = x.length;

        // base case
        if (N == 1) {
            return new Interval[]{x[0]};
        }

        // radix 2 Cooley-Tukey FouFFT
        if (N % 2 != 0) {
            throwNewCalculusException("The length of the list is not a power of 2");
        }

        // fft of even terms
        Interval[] even = new Interval[N / 2];
        for (int k = 0; k < N / 2; k++) {
            even[k] = x[2 * k];
        }
        Interval[] q = fft(even);

        // fft of odd terms
        Interval[] odd = even;  // reuse the array
        for (int k = 0; k < N / 2; k++) {
            odd[k] = x[2 * k + 1];
        }
        Interval[] r = fft(odd);

        // combine
        Interval[] y = new Interval[N];
        for (int k = 0; k < N / 2; k++) {
            double kth = -2 * k * Math.PI / N;
            Interval wk = new IntervalComplex(new IntervalPoint(Math.cos(kth)), new IntervalPoint(Math.sin(kth)));
            y[k] = SUM._computeIntervals(q[k], MUL._computeIntervals(wk, r[k]));
            y[k + N / 2] = SUB._computeIntervals(q[k], MUL._computeIntervals(wk, r[k]));
        }
        return y;
    }
}
