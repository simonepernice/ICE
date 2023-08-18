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


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DifCumulate extends FunctionPrefix {
    private final static String HELP = "... ('infixFunction', x1, .., xn) applies the given infixfunction at the first and second element of the list, the result is computed with the third element of the list and so on.\nFor example ... ('+', 1, 2, 3) = ((1+2)+3). If the infix function returns more than an element only the first is used.";
    private final static String[] EXAMPLE = {"'+', 1, 2, 3","'-', 1, 2, 3", "'&&', 1, 0_1, 0_1","'&&', 1, 0, 0_1", "'*', 3, 2, 3", "'||', 0, 0, 0","'||', 1, 0, 0_1", "'+/', 1, 2, 3", "'+^', 1, 2, 3"};
    private final static String[] RESULT = {"6", "-4", "0_1", "0", "18", "0", "1", "0.545", "3.742"};
    private final static DomainList INTERVALDOMAIN = new DomainList (new DomainFunctionInfix(1, 1), new DomainIntervalComplex());

    public DifCumulate() {
        super ("diffuse", "Cumulate", (byte) -2, INTERVALDOMAIN, false,true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        input = new ListIntervals(input); //to avoid to change the input which may affect other function
        FunctionInfix f = FunctionInfix.getFunction(input.getFirst().getName());
        input.removeFirst();
        while (input.size() > 1) {
            input.set(1, f._compute(input.subList(0, 1), input.subList (1, 2)).getFirst());
            input.removeFirst();
        }
        return input;
    }

}
