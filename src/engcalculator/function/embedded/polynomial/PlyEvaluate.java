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
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PlyEvaluate extends FunctionPrefix {

    private final static String HELP = "... ({an, an-1, .., a1, a0}, eval1, eval2, .., evaln) evaluate the polynomial in variable x with given coefficients at the given points eva1, .., evaln.\nThe polynomial coefficents are from the maximum grade x (x^n) to the minimum (x^0). ";
    private final static String[] EXAMPLE = {"{1,2,3},1,2"};
    private final static String[] RESULT = {" defineLambdaFunction ('$x^2+2*$x+3',1,2)"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalList(), new DomainIntervalComplex());
    private final static AriMul MUL = new AriMul();
    private final static AriSum SUM = new AriSum(); 
    
    public PlyEvaluate() {
        super("polynomial", "Evaluate", (byte) -2, INTERVALDOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        Iterator<Interval> ii = input.iterator();
        ListIntervals c =  ii.next().getListIntervals();
        ListIntervals result = new ListIntervals ();
        while (ii.hasNext())
            result.add(_computeIntervals(c, ii.next()));
        return result;
    }
        
    public Interval _computeIntervals (ListIntervals coefficients, Interval x) throws Exception {        
        Iterator<Interval> ii = coefficients.iterator();
        Interval acc = ii.next();
        while (ii.hasNext()) {
            acc = MUL._computeIntervals(x, acc);
            acc = SUM._computeIntervals(acc, ii.next());
        }
        return acc;
    }    
}
