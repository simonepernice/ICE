
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

package engcalculator.function.embedded.reactiveExpression;

import engcalculator.domain.DomainList;
import engcalculator.reactiveExpression.ReactiveExpression;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainListLiteral;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.ListIntervals;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class RexDefine extends FunctionInfix {       
    private final static DomainList DOMAINRIGHT = new DomainList (new DomainIntervalLiteral());
    private final static DomainListLiteral DOMAINLEFT = new DomainListLiteral (new DomainIntervalLiteral());
    private final static String HELP = "$variable ... ('expression of other already defined variables', [extra dependent variables])  defines a reactive expression.\nIt is an expression which is automatically called every time any of its argument changes. It is very usefull on the design phase. Usually the best result is not get on the first list of expression. Therefore it is required to compute again the same list of interdependent expressions changing a little some input argument until the optimal result is got. However with reactive expression it is enough to chane any input variable to have them react to recompute their value. The reactive expression can depend on eache other output value. Only the once whose where at least an input is change is recalculated automatically. They are recalculated at the end of the current list of expression unless a manual defineRecurrentExpressionTrigger() is given. It is not allowed any recusive definition (cycle) within a recursive expression (a === 'a + 1') or through them (a === 'b + 1', b === 'a'). The command is used OutputVariableName ... ' function of input variables '. If the output variable is deleted the relative reactive expression is deleted as well. In the output variable help is stored the reactive expression syntax. The input argument are automatically extracted by ICE looking at the variables on the right side expression. However ICE may miss some argument if it is written within a literal (defining an expression). For instance in the reactive expression: c === 'a+literalToExpression \\'b\\'', the bariable b is not recognized because it is written within apices. It is possible to add extra dependences from b with the syntax: $c === ('a+literalToExpression \\'b\\'', $b) ";
    private final static String[] EXAMPLESLEFT =  {"$a=1,$b=2;$c==='a+b';$b=1;c==3","reactiveExpressionTrigger(false);c==2","a = 5, b = 7, c === 'a+literalToExpression \\'b\\''; c == 5+7","a=4,reactiveExpressionTrigger(false);c==4+7","b=4,reactiveExpressionTrigger(false);c==4+7","a = 5, b = 7, c === ('a+literalToExpression \\'b\\'', $b); c == 5+7","a=4,reactiveExpressionTrigger(false);c==4+7","b=4,reactiveExpressionTrigger(false);c==4+4"};
    private final static String[] EXAMPLESRIGHT = {"", "", "", "", "", "", "", ""};
    private final static String[] RESULTS = {"true","true","true","true","true","true","true","true"};
    
    public RexDefine() {
        super ("reactiveExpression", "===", (byte) 1, (byte) -1, DOMAINLEFT,  DOMAINRIGHT, false, true, HELP, EXAMPLESLEFT, EXAMPLESRIGHT, RESULTS);        
    }

    @Override
    public final ListIntervals _compute(ListIntervals left, ListIntervals right) throws Exception {
        final String output;
    
        {
            String temp = left.getName();
            if (temp == null) output = left.getFirst().getName();
            else output = temp;
        }
        
        if (FunctionPrefix.getFunction(output) != null) throwNewCalculusException ("There is a function already defined with the same name of this variable: "+output);                          

        final String rEBody = right.getFirst().getName();
        
        LinkedList<String> extraVar = null;
        if (right.size() > 1) {
            extraVar = new LinkedList<String>();
            Iterator<Interval> ii = right.iterator();
            ii.next(); //skip equation
            while (ii.hasNext()) {
                String var = ii.next().getName();
                if (FunctionVariable.getFunction(var) == null) throwNewCalculusException ("There is an extra variable which is not yet defined: "+var);                          
                extraVar.add(var);
            }
        }
        
        ReactiveExpression re = new ReactiveExpression(output, rEBody, extraVar);
        
        ListIntervals result = ReactiveExpression.addReactiveExpression(re); 
        
        if (result == null) throwNewCalculusException ("The recursive reactive expression "+re+" cannot be added.");

        return result;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide)  {
        return null;
    }
}
