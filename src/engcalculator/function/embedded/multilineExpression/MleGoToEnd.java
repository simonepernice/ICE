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
import engcalculator.domain.DomainLogicNot;
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
public final class MleGoToEnd extends FunctionPrefixSingleInputSingleOutput {
    private static final DomainList DOMAIN = new DomainList (new DomainLogicNot (new DomainIntervalLiteral()));
    private final static String HELP = "... value go to the end of the current multiple line expression and returns value.\nIt is used to complete the execution if after there are lines that do not have to be executed because called by goto or gosub.";    
    private final static String[] EXAMPLE = {   "multilineExpressionStatement\n" +
                                                "a=0\n" +
                                                "i=1\n" +
                                                "'loop';\n" +
                                                "a = a + 1\n" +
                                                "i = i * 2\n" +
                                                "conditionalIfTrue(a < 50, 0, multilineExpressionGoToEnd i)\n" +
                                                "multilineExpressionGoTo 'loop'\n"+
                                                "multilineExpressionStatement\n"};
    private final static String[] RESULT = {"2 ^ 50"};
    
    public MleGoToEnd() {
        super("multilineExpression", "GoToEnd", DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        return new IntervalFlowControl(input, new WorkFlow (WorkFlowExpression.GOTO_END_EX, null));
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval input) throws Exception {
        return input.getMeasurementUnit();
    }    

}
