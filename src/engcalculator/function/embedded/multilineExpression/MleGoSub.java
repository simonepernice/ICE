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

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.expression.Expression;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalFlowControl;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.workFlow.WorkFlow;
import engcalculator.interval.workFlow.WorkFlowExpression;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MleGoSub extends FunctionPrefixSingleInputSingleOutput {
    private static final DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... it is like go to a 'label'. However the next line continues after a return.\nIt is used to change the work flow calling sub  multi expression in multiline expression. The label is defined with the label name within apices followed by ; The maximum gosub depth is "+Expression.MAXGOSUBDEPTH+". "+FunctionVariableMultilineExpression.HELPTAIL;    
    private final static String[] EXAMPLES = {  "multilineExpressionStatement\n" +
                                                "a=1,i=0\n" +
                                                "'loop';\n" +
                                                "multilineExpressionGoSub 'calc'\n" +
                                                "i=i+1\n" +
                                                "conditionalIfTrue(i < 50, multilineExpressionGoToBeforePrevious, 1)\n" +
                                                "multilineExpressionGoToEnd a\n" +
                                                "'calc';\n" +
                                                "a = a * 2\n" +
                                                "multilineExpressionReturn \n" +
                                                "multilineExpressionStatement"};
    private final static String[] RESULTS = {"2 ^ 50"};

    public MleGoSub() {
        super("multilineExpression", "GoSub", DOMAIN, false, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        return new IntervalFlowControl(Interval.ZERO, new WorkFlow (WorkFlowExpression.GOSUB_LABEL_EX, input.getName()));
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval input) throws Exception {
        return input.getMeasurementUnit();
    }    

}
