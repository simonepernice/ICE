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
import engcalculator.function.embedded.list.LisBuilder;
import engcalculator.function.embedded.list.LisGetElements;
import engcalculator.function.embedded.power.PowExp;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.*;
import java.util.Arrays;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FouDFT extends FunctionPrefix {

    private static final DomainList DOMAIN = new DomainList(new DomainIntervalComplex());
    private final static String HELP = "... (list) computes the discrete fourier transform of the list. It is also applicable to plot matrix.\nThe discrete Fourier Transform is able to transform a list of any size from time domain to frequency domain. ... can be also used with a matrix where the first column represents the samplig times and the second the values like the one provided by standardplot. The algorithm is got from http://nayuki.eigenstate.org/res/how-to-implement-the-discrete-fourier-transform/dft.py and adapted to EngCalculator data structures by myself. To display the ... result use dataplot function. The ... is slow compared to FFT but it can operate with every size of elements.";
    private final static String[] EXAMPLE = {"1,0,-1,0", "matrixTranspose([0,1,2,3],[1,0,-1,0])","testFourier = statisticRandom(listClone(64, 0_10));fourierDFT(testFourier)"};
    private final static String[] RESULT = {"0,2,0,2", "matrixTranspose([0,.25,.5,.75],[0,2,0,2])","fourierFFT(testFourier)"};
    private final static LisGetElements GETVECTOR = new LisGetElements();
    private final static AriSum SUM = new AriSum();
    private final static AriMul MUL = new AriMul();
    private final static PowExp EXP = new PowExp();
    private final static LisBuilder LISTBUILDER = new LisBuilder();
    private final static Interval[] ARRAYSAMPLE = new Interval[0];

    public FouDFT() {
        super("fourier", "DFT", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    // compute the FouFFT of x[], assuming its length is a power of 2
    @Override
    public ListIntervals _compute(ListIntervals x) throws Exception {
        ListIntervals time, frequency = null, tvalue, fvalue;
        double dt;

        if (x.rowSize() > 1) {
            if (x.columnSize() != 2) {
                throwNewCalculusException("The input matrix must have 2 columns the time and the value instead were found "+x.columnSize());
            }
            time = GETVECTOR._compute(x, new ListIntervals(new IntervalReal(0, -1)).append(new IntervalPoint(0)));
            tvalue = GETVECTOR._compute(x, new ListIntervals(new IntervalReal(0, -1)).append(new IntervalPoint(1)));

            dt = time.get(1).getValue() - time.get(0).getValue();
            for (int i = 2; i < time.size(); ++i) {
                if (Math.abs(time.get(i).getValue() - time.get(i - 1).getValue() - dt) > 1e-6) {
                    throwNewCalculusException("The sampling interval in not constant initially was found "+dt);
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

        fvalue.addAll(Arrays.asList(dft(tvalue.toArray(ARRAYSAMPLE))));

        if (frequency != null) {
            return LISTBUILDER._compute(frequency, fvalue);
        }
        return fvalue;
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

    public static Interval[] dft(Interval[] input) throws Exception {
        final int n = input.length;
        final Interval in = new IntervalPoint (n);
        final Interval m2jpion = new IntervalComplex(IntervalPoint.ZERO, new IntervalPoint(-2d*Math.PI/n));
        Interval[] output = new Interval[n];
        for (int k=0;k<n;++k) {// For each output element
            Interval s = IntervalPoint.ZERO;
            Interval ik = new IntervalPoint (k);
            for (int t=0;t<n;++t) {// For each input element
                Interval it = new IntervalPoint (t);
                s = SUM._computeIntervals(s, MUL._computeIntervals(input[t], EXP._computeIntervals(MUL._computeIntervals(m2jpion, MUL._computeIntervals(it, ik)))));
                //s += input[t] * cmath.exp(-2j * cmath.pi * t * k / n);            
            }            
            output[k] = s;
        }
        return output;
    }
}
