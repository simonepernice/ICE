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

import engcalculator.interval.ListIntervals;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.variable.FunctionVariable;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DifAccumulatorOnLeft extends DifAccumulator  {
    private final static String HELP = "... ($accumulator, 'infixFunction', b1, .., bn) computes: accumulator = accumulator 'infixFunction' (b1, .., bn).\n It can be used to simplify expression like a = a + 4.";
    private final static String[] EXAMPLE = {"; $a=0", "$a, '-', 5", "$a, ',', 6,7,8"};
    private final static String[] RESULT = {"0", "-5", "-5,6,7,8"};
   
    public DifAccumulatorOnLeft() {
        super ("Left", HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _computeListIntervals(FunctionVariable acc, FunctionInfix f, ListIntervals rest) throws Exception {
        FunctionVariable newAcc = new FunctionVariable (acc.getGroup(), acc.getSymbol(), f.compute(acc.getValue(), rest), false, "User defined variable."); 
        FunctionVariable.delete(acc.getSymbol());
        FunctionVariable.addFunction(newAcc);
        return newAcc.getValue();
    }

}
