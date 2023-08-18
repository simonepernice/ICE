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
import engcalculator.interval.IntervalLiteral;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MleStatement extends FunctionVariable {
    private final static String MULTILINEEXP = "multilineExpression";
    private final static String STATEMENT = "Statement";
    public final static String MULTILINEEXPSTATEMENT  =  MULTILINEEXP + STATEMENT;
    private final static String HELP = "... is used to define the begin and the end of a multi line expression.\nEach expression can be terminated by a new line. All the expressions instructions within ... and ... are treated as one. The result of the multiline expression is the value of the last one."+FunctionVariableMultilineExpression.HELPTAIL;
	
    public MleStatement () {
        super (MULTILINEEXP, STATEMENT, new IntervalLiteral("multilineStatement"), true, HELP);
    }

}
