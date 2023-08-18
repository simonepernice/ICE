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
package engcalculator.function.embedded.define;

import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.domain.DomainLogicNot;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class DefLambdaGetValue extends FunctionPrefix {
 
    private final static String[] EXAMPLES = {";as=statisticRandomListAsDistribution($statisticDistributionUniform, 1_2, $x, 0_3);defineLambdaGetName (0) = as-as;  statisticAverage defineLambdaGetValue -1, statisticMeanAbsoluteDeviation  defineLambdaGetValue -1"};
    private final static String[] RESULTS = {"0, 0"};
    private final static String HELP = "... (-x) returns the value stored in the -x LambdaName.\nIt is the same of literalToExpression defineLambdaGetName -x.";
    private final static DomainList DOMAIN = new DomainList(new DomainLogicAnd(new DomainIntervalPoint(), new DomainInteger(), new DomainLogicNot(new DomainIntervalPositive())));
    
    public DefLambdaGetValue() {
        super("define", "LambdaGetValue", (byte) 1, DOMAIN, false, true, HELP, EXAMPLES, RESULTS);
    }    
    
    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        int i = (int) input.getFirst().getValue();
        FunctionVariable f = FunctionVariable.getFunction(DefLambdaGetName.getPreviousLambdaFunctionString(i));    
        if (f == null) throwNewCalculusException ("The lambda name "+i+" was not used to store a variable.");
        return f.getValue();
    }
    
}

