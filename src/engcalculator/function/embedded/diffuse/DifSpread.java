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

package engcalculator.function.embedded.diffuse;

import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainFunctionInfix;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.Interval;
import java.util.Iterator;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DifSpread extends FunctionPrefix {
    private final static String HELP = "... ('infixFunction', x1, .., xn) applies the given infix function sequentially to all the couples of the given list.\nFor example ... ('+', 1, 2, 3) = ((1+2), (2+3)).";
    private final static String[] EXAMPLE = {"'+', 1, 2, 3","'-', 1, 2, 3", "'&&', 1, 0_1, 0_1","'&&', 1, 0, 0_1", "'*', 3, 2, 3", "'||', 0, 0, 0","'||', 1, 0, 0_1", "'+/', 1, 2, 3", "'+^', 1, 2, 3", "'_',listLinear(5,0_4)"};
    private final static String[] RESULT = {"3, 5", "-1, -1", "0_1, 0_1", "0, 0", "6, 6", "0, 0", "1, 0_1", "2/3, 6/5", "sqrt(5), sqrt(13)","0_1,1_2,2_3,3_4"};
    private final static DomainList INTERVALDOMAIN = new DomainList (new DomainFunctionInfix(1, 1), new DomainIntervalComplex());

    public DifSpread() {
        super ("diffuse", "Spread", (byte) -3, INTERVALDOMAIN, false,true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals result, left, right;
        
        result = new ListIntervals();
        left = new ListIntervals();
        right = new ListIntervals();
        
        Iterator<Interval> ii = input.iterator();
        
        FunctionInfix f = FunctionInfix.getFunction(ii.next().getName());
        Interval prev = ii.next();
        while (ii.hasNext()) {
            left.clear();
            right.clear();
            left.add(prev);            
            right.add(prev = ii.next());
            result.addAll(f._compute(left, right));
        }
        return result;
    }

}
