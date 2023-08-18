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
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.ListIntervals;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PlyDerivate extends FunctionPrefix {

    private final static String HELP = "... (an, an-1, .., a1, a0) returns the polynomial given by the first derivate of the given one.";
    private final static String[] EXAMPLE = {"polynomialIntegrate(1,5,6)"};
    private final static String[] RESULT = {"1,5,6"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalComplex());

    private final static AriMul MUL = new AriMul();      
    
    public PlyDerivate() {
        super("polynomial", "Derivate", (byte) -1, INTERVALDOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        int grade = input.size()-1;
        ListIntervals derived = new ListIntervals ();
        Iterator<Interval> ii = input.iterator();
        while (grade > 0) {
            derived.add(MUL._computeIntervals(new IntervalPoint(grade), ii.next()));
            --grade;
        }
       return derived;
    }
}
