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

package engcalculator.function.embedded.power;

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.trigonometry.TriCos;
import engcalculator.function.embedded.trigonometry.TriSin;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutputMultipleDomain;
import engcalculator.interval.IntervalComplex;
import engcalculator.interval.IntervalPoint;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PowExp extends FunctionPrefixSingleInputSingleOutputMultipleDomain {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());
    private final static String HELP = "... (a) computes E ^ a (where E is Neper number) in a more computational efficient way.\nMoreover ... supports complex number.";
    private final static String[] EXAMPLE = {"-1_2","-1_2", "1_2", "1_2", "21_2", "21_2", "1 +| (PI/2)"};
    private final static String[] RESULT = {"E^(-1_2)","E^(-1_2)", "E^(1_2)", "E^(1_2)", "E^(21_2)", "E^(21_2)", "I * E"};
    private final static TriSin SIN = new TriSin();
    private final static TriCos COS = new TriCos();
    private final static AriMul MUL = new AriMul ();
    
    public PowExp() {
        super ("power", ".exp", DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public Interval _computeIntervalPoint(Interval a) throws Exception {
        return new IntervalPoint( Math.exp(a.getValue()));
    }

    @Override
    public Interval _computeIntervalReal(Interval a) throws Exception {
        return new IntervalReal( Math.exp(a.getLeft()), Math.exp(a.getRight()));
    }

    @Override
    public Interval _computeIntervalLiteral(Interval input) throws Exception {
        throw new RuntimeException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interval _computeIntervalComplex(Interval a) throws Exception {
        Interval k = _computeIntervals(a.getRealPart());
        Interval re = COS._computeIntervals(a.getImaginaryPart());
        Interval im = SIN._computeIntervals(a.getImaginaryPart());
        return new IntervalComplex (MUL._computeIntervals(k, re), MUL._computeIntervals(k, im));
    }
}
