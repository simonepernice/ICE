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

import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalFlowControl;
import engcalculator.interval.workFlow.WorkFlow;
import engcalculator.interval.workFlow.WorkFlowExpression;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionVariableMultilineExpression extends FunctionVariable {
    public final static String HELPTAIL = "Those instructions can be placed where ever in the current line. They are evaluated at the execution of the current line so a goto at the beginning would not skip the following calculation in the same line. It is possible just place one (1) multilineExpression or sub Expression flow control instruction per code line. ";
	
    public FunctionVariableMultilineExpression (String name, WorkFlowExpression wfe, String help, String[] examples, String[] results) {
        super ("multilineExpression", name, new IntervalFlowControl(Interval.ZERO, new WorkFlow (wfe, null)), true, help+HELPTAIL, examples, results);
    }

}
