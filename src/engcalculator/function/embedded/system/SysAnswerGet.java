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

package engcalculator.function.embedded.system;

import engcalculator.Calculator;
import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntegerLong;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.CalculusException;
import engcalculator.interval.ListIntervals;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SysAnswerGet extends FunctionPrefix {
    private final static String HELP = "... (answerInd1, answerInd2, answerInt1Begin_answerInt1End, ..) return previous ICE answers.\nIt gets the list of answer to return: 1, 2, 3 would return the list made by answer1, answer2, answer3. If an interval is provided 1_4 all the answer between 1 and 4 will be returned. Negative number goes from the back of the list: -1 is lastAnswer, -2 the answer befor and so on.";
    private final static DomainList DOMAIN = new DomainList (new DomainInteger ());

    public SysAnswerGet() {
        super ("system", ".answerGet", (byte) -1, DOMAIN, false,true, HELP, null, null);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int ns = Calculator.getNextStep();
        ListIntervals result = new ListIntervals ();
        for (Interval i : input) {
            int low = (int) i.getLeft();
            int high = (int) i.getRight();            
            if (low < 0) low += ns ;
            if (high < 0) high += ns ;
            if (low <= 0 || low > ns)  throw new CalculusException ("The input range is out of value "+low);
            if (high <= 0 || high > ns)  throw new CalculusException ("The input range is out of value "+high);
            int step;
            if (low < high) step = 1;
            else step = -1;
            high += step;
            for (int j = low; j != high; j += step)
                result.addAll(FunctionVariable.getFunction("answer"+j).getValue());
        }
        return result;        
    }

}
