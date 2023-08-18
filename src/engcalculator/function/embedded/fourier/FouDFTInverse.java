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
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.embedded.list.LisGetElements;
import engcalculator.function.embedded.list.LisBuilder;
import engcalculator.function.embedded.power.PowExp;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.*;
import java.util.Arrays;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FouDFTInverse extends FunctionPrefix {

    private static final DomainList DOMAIN = new DomainList(new DomainIntervalComplex());
    private final static String HELP = "... (list) computes the inverse discrete forier transform of the given list.\nInverse Discrete Fourier Transform is able to transform a list of any size from frequency domain to time domain. ... can be also used with a matrix where the first column represents the samplig times and the second the values like the one provided by standardplot. The algorithm is got from http://nayuki.eigenstate.org/res/how-to-implement-the-discrete-fourier-transform/dft.py and adapted to IDFT and EngCalculator data structures by myself. To display the ... result use dataplot function. ... is much slower than FFTInverse however it can operate on any data size.";    
    private final static String[] RESULT = {"1,0,-1,0", "matrixTranspose([0,1,2,3],[1,0,-1,0])", "fourierFFTInverse(testFourier)"};
    private final static String[] EXAMPLE = {"0,2,0,2", "matrixTranspose([0,.25,.5,.75],[0,2,0,2])","testFourier = statisticRandom(listClone(64, 0_10));fourierDFTInverse(testFourier)"};
    private final static AriMul MUL = new AriMul();
    private final static Interval[] ARRAYSAMPLE = new Interval[0];
    private final static LisBuilder LISTBUILDER = new LisBuilder();
    private final static LisGetElements GETVECTOR = new LisGetElements();
    private final static AriSum SUM = new AriSum();
    private final static PowExp EXP = new PowExp();    

    public FouDFTInverse() {
        super("fourier", "DFTInverse", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals x) throws Exception {
        ListIntervals frequency, time = null, fvalue, tvalue;
        double df;

        if (x.rowSize() > 1) {
            if (x.columnSize() != 2) {
                throwNewCalculusException("The input matrix must have 2 columns the frequency and the value instead was found "+x.columnSize());
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

        tvalue.addAll(Arrays.asList(idft(fvalue.toArray(ARRAYSAMPLE))));

        if (time != null) {
            return LISTBUILDER._compute(time, tvalue);
        }
        return tvalue;
    }

//
//# 
//# Discrete Fourier transform
//# By Nayuki Minase, 2014. Public domain.
//# http://nayuki.eigenstate.org/page/how-to-implement-the-discrete-fourier-transform
//# 
//
//# 
//# This file contains multiple implementations.
//# Before running the code, choose one and delete the rest.
//# 
//
//# --------------------------------------------------------------------------------
//
//# 
//# Computes the discrete Fourier transform (DFT) of the given input vector.
//# 'input' is a sequence of numbers (integer, float, or complex).
//# Returns a list of complex numbers as output, having the same length.
//# 
//import cmath

    public static Interval[] idft(Interval[] input) throws Exception {
        final int n = input.length;
        final Interval in = new IntervalPoint (n);
        final Interval ininv = new IntervalPoint (1d/n);
        final Interval m2jpion = new IntervalComplex(IntervalPoint.ZERO, new IntervalPoint(2d*Math.PI/n));
        Interval[] output = new Interval[n];
        for (int k=0;k<n;++k) {// For each output element
            Interval s = IntervalPoint.ZERO;
            Interval ik = new IntervalPoint (k);
            for (int t=0;t<n;++t) {// For each input element
                Interval it = new IntervalPoint (t);
                s = SUM._computeIntervals(s, MUL._computeIntervals(input[t], EXP._computeIntervals(MUL._computeIntervals(m2jpion, MUL._computeIntervals(it, ik)))));
                //s += input[t] * cmath.exp(-2j * cmath.pi * t * k / n);            
            }            
            output[k] = MUL._computeIntervals(s, ininv);
        }
        return output;
    }
}
