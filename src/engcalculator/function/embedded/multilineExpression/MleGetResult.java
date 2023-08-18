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

import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.expression.Expression;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MleGetResult extends FunctionPrefix {
    private static final DomainList DOMAIN = new DomainList (new DomainLogicAnd  (new DomainIntervalPoint(), new DomainInteger(), new DomainIntervalPositive()));
    private final static String HELP = "... index retriever the previous computed lines results.\nindex = 0 is the last line result and so on. The maximum number of saved results is "+Expression.MAXPREVIOUSRESULTSTORE;    
    private final static String[] EXAMPLE = {   "multilineExpressionStatement\n" +
                                                "a=0\n" +
                                                "i=1\n" +
                                                "'loop';\n" +                                                
                                                "i = i * 2\n" +
                                                "a = a + 1\n" +
                                                "conditionalIfTrue(multilineExpressionGetResult 0 < 50, multilineExpressionGoTo 'loop', multilineExpressionGetResult 1)\n" +
                                                "multilineExpressionStatement\n"};
    private final static String[] RESULT = {"2 ^ 50"};
    
    public MleGetResult() {
        super("multilineExpression", "GetResult", (byte) 1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws Exception {
        return Expression.getResult((int) input.getFirst().getValue()).getMeasurementUnitList();
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return Expression.getResult((int) input.getFirst().getValue()); 
    }


}
