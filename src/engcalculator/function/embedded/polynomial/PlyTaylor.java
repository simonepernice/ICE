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

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriInvert;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriNegate;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PlyTaylor extends FunctionPrefix {

    private final static String HELP = "... (x0, f(x0), f'(x0), f''(x0), ..) returns the Tylor polynomial computed at x0.";
    private final static String[] EXAMPLE = {";polynomialEvaluate({polynomialTaylor(1, listClone(12, E))}, listLinear(5, 0_2))"};
    private final static String[] RESULT = {"exp listLinear(5, 0_2)"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalComplex());

    private final static PlyMul PMUL = new PlyMul();      
    private final static PlySum PSUM = new PlySum();      
    private final static AriDiv ADIV = new AriDiv();      
    private final static AriMul AMUL = new AriMul();      
    private final static AriNegate ANEG = new AriNegate();      
    
    public PlyTaylor() {
        super("polynomial", "Taylor", (byte) -2, INTERVALDOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        Iterator<Interval> i = input.iterator();
                                     
        ListIntervals core = new ListIntervals();
        core.add(Interval.ONE);
        core.add(ANEG._computeIntervals(i.next()));
        ListIntervals coreAccumulator = new ListIntervals();
        coreAccumulator.addAll(core);

        ListIntervals result = new ListIntervals();   
        result.add(i.next());
        
        int denominator = 1;
        int step = 1;        
        while (i.hasNext()) {

            ++step;
System.out.println("denominator "+denominator);
            result = PSUM._compute(result, AMUL.compute(new ListIntervals( ADIV._computeIntervals(i.next(), new IntervalPoint(denominator))),coreAccumulator));
            System.out.println("result "+result);
                        coreAccumulator = PMUL._compute(coreAccumulator, core);
            System.out.println("coreAccumulator "+coreAccumulator);
            denominator *= step;
        }

       return result;
    }
}
