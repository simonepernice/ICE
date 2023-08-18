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
import engcalculator.function.embedded.complex.CpxConjugate;
import engcalculator.function.embedded.list.LisGetElements;
import engcalculator.function.embedded.list.LisBuilder;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.*;
import java.util.Arrays;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FouFFTInverse extends FunctionPrefix {

    private static final DomainList DOMAIN = new DomainList(new DomainIntervalComplex());
    private final static String HELP = "... (list) computes the inverse fast forier transform of the given list.\nInverse Fast Fourier Transform is able to transform a list of size equals to a power of 2 from frequency domain to time domain. ... can be also used with a matrix where the first column represents the samplig times and the second the values like the one provided by standardplot. The algorithm is got from http://www.cs.princeton.edu/introcs/97data/FFT.java.html and adapted to EngCalculator data structures by myself. To display the fft result use dataplot function.";
    private final static String[] RESULT = {"1,0,-1,0", "matrixTranspose([0,1,2,3],[1,0,-1,0])"};
    private final static String[] EXAMPLE = {"0,2,0,2", "matrixTranspose([0,.25,.5,.75],[0,2,0,2])"};
    private final static AriMul MUL = new AriMul();
    private final static CpxConjugate CONJUGATE = new CpxConjugate();
    private final static Interval[] ARRAYSAMPLE = new Interval[0];
    private final static LisBuilder LISTBUILDER = new LisBuilder();
    private final static LisGetElements GETVECTOR = new LisGetElements();

    public FouFFTInverse() {
        super("fourier", "FFTInverse", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals x) throws Exception {
        ListIntervals frequency, time = null, fvalue, tvalue;
        double df;

        if (x.rowSize() > 1) {
            if (x.columnSize() != 2) {
                throwNewCalculusException("The input matrix must have 2 columns the frequency and the value, instead was found "+x.columnSize());
            }
            frequency = GETVECTOR._compute(x, new ListIntervals(new IntervalReal(0, -1)).append(new IntervalPoint(0)));
            fvalue = GETVECTOR._compute(x, new ListIntervals(new IntervalReal(0, -1)).append(new IntervalPoint(1)));

            df = frequency.get(1).getValue() - frequency.get(0).getValue();
            for (int i = 2; i < frequency.size(); ++i) {
                if (Math.abs(frequency.get(i).getValue() - frequency.get(i - 1).getValue() - df) > 1e-6) {
                    throwNewCalculusException("The sampling interval in not constant, initially it was found "+df);
                }
            }

            time = new ListIntervalsMatrix(1);

            final double dt = 1 / (frequency.size() * df);
            double t = 0;
            for (int i = 0; i < frequency.size(); ++i) {
                time.add(new IntervalPoint(t));
                t += dt;
            }

            tvalue = new ListIntervalsMatrix(1);
        } else {
            fvalue = x;
            tvalue = new ListIntervals();
        }

        tvalue.addAll(Arrays.asList(ifft(fvalue.toArray(ARRAYSAMPLE))));

        if (time != null) {
            return LISTBUILDER._compute(time, tvalue);
        }
        return tvalue;
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
    // compute the inverse FouFFT of x[], assuming its length is a power of 2
    public static Interval[] ifft(Interval[] x) throws Exception {
        final int N = x.length;


        if (N % 2 != 0) {
            throwNewCalculusException("The length of the list is not a power of 2, it was found "+N);
        }

        Interval[] y = new Interval[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = CONJUGATE._computeIntervals(x[i]);
        }

        // compute forward FouFFT
        y = FouFFT.fft(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = CONJUGATE._computeIntervals(y[i]);
        }

        // divide by N
        final Interval nth = new IntervalPoint(1.0 / N);
        for (int i = 0; i < N; i++) {
            y[i] = MUL._computeIntervals(y[i], nth);
        }

        return y;

    }
}
