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

import engcalculator.domain.DomainFunctionVariable;
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainIntervalReactiveExpression;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.userDefined.UDeFunctionPrefixByReactiveExpression;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class RexDefineFunctionReactiveExpression extends FunctionPrefix  {
    
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral(), new DomainIntervalList(new DomainFunctionVariable ()), new DomainIntervalReactiveExpression()); 

    private final static String HELP = "... ($functionName, {$inputVar1, .., $inputVarn}, $outputReEx) defines a new prefix function which takes n input variables and store them on the system vars: inputVar1, .., inputVarn; then it execute the reactive expressions and return the value of outputReEx.\nThe purpose of this function it to transform a reactive expression in a standard prefix function which can be used in further calculus like findRoot or optimizer..";
    private final static String[] EXAMPLESLEFT = {"$a=5, $y1==='a+5', reactiveExpressionDefineFunction($testfre, {$a}, $y1); a, y1, equationFindRoot($testfre, 0_10, 11), a, y1"};    
    private final static String[] RESULTS = {"5, 10, 6, 6, 11"};

    public RexDefineFunctionReactiveExpression() {
        super ("reactiveExpression", "DefineFunction", (byte) 3, DOMAIN, false, true, HELP, EXAMPLESLEFT, RESULTS);
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        String fname = input.getFirst().getName();
        String out = input.getLast().getName();
        ListIntervals inputs = input.get(1).getListIntervals();
        String[] in = new String[inputs.size()];
        for (int i=0; i<in.length; ++i)
            in[i] = inputs.get(i).getName();
     
        UDeFunctionPrefixByReactiveExpression f = new UDeFunctionPrefixByReactiveExpression (fname, " ",in, out);
        if (! FunctionPrefix.addFunction(f)) throwNewCalculusException ("There is a function already defined with the same name "+fname+" which is constant.");

        return new ListIntervals (new IntervalLiteral (f.getSymbol()));
    }
}
