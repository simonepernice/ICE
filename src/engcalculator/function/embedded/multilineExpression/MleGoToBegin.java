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

package engcalculator.function.embedded.multilineExpression;

import engcalculator.interval.workFlow.WorkFlowExpression;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MleGoToBegin extends FunctionVariableMultilineExpression {
    private final static String HELP = "... go to the begin: at the expression line after the initial multilineExpressionStatement.\n";    
    private final static String[] EXAMPLES = {  "multilineExpressionStatement\n" +
                                                "a=statisticRandom(1_100)\n" +
                                                "conditionalIfTrue(a < 50, multilineExpressionGoToBegin, a >= 50)\n" +
                                                "multilineExpressionStatement"};
    private final static String[] RESULTS = {"true"};
	
    public MleGoToBegin () {
        super ("GoToBegin", WorkFlowExpression.GOTO_BEGIN_EX, HELP, EXAMPLES, RESULTS);
    }

}
