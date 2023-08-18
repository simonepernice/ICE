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
public final class FlcRepeatNextWhile extends FlcPrefixFunction {

    private final static String HELP = "... (condition); nextSubExp; while the condition is true, executes the next sub-expression.\nEvery time the   current sub-expression is executed again to check if the condition is still true.";
    private final static String[] EXAMPLES = {"i=0,j=5;i=i+1,flowControlRepeatNextWhile (i<10);j=j+1;j"};
    private final static String[] RESULTS = {"14"};

    public FlcRepeatNextWhile() {
        super("RepeatNextWhile", HELP, EXAMPLES, RESULTS, new WorkFlow( WorkFlowSubExpression.NEXT_SUBEX_THEN_THIS_SUBEX), new WorkFlow( WorkFlowSubExpression.SKIP_NEXT_SUBEX));
    }

}
