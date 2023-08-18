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

package engcalculator.function.embedded.flowControl;

import engcalculator.interval.workFlow.WorkFlow;
import engcalculator.interval.workFlow.WorkFlowSubExpression;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FlcRepeatPreviousWhile extends FlcPrefixFunction {

    private final static String HELP = "prevSubExpression; ... (condition); repeats the previous sub-expression while the condition is true.\nAfter executing the previous subExpression the current one is executed to chek if still true.";
    private final static String[] EXAMPLES = {"i=0;i=i+1;flowControlRepeatPreviousWhile (i<10);i"};
    private final static String[] RESULTS = {"10"};

    public FlcRepeatPreviousWhile() {
        super("RepeatPreviousWhile", HELP, EXAMPLES, RESULTS, new WorkFlow( WorkFlowSubExpression.PREVIOUS_SUBEX), new WorkFlow( WorkFlowSubExpression.NEXT_SUBEX));
    }

}
