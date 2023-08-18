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
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainFunctionVariable;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.interval.ListIntervals;
import engcalculator.function.parameter.ConvertIntervalToDouble;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.reactiveExpression.ReactiveExpression;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class RexRecursive extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList(new DomainFunctionVariable(), new DomainFunctionPrefix(-1), new DomainIntervalLiteral());

    private final static String HELP = "... ($y, $f, $x1, .., $xn) add a recursive equation to the reactive expression set.\nICE computes y=f(x1, .., xn) untile two iteration are within espilon unless max iterations are reached. It is useful if y === 'f(x1, .., xn)' cannot be added because recursive.";
    private final static String[] EXAMPLE = {"vth = 3, vin = 5, rs = 100, id = 0.1m,  k=0.2, vs === 'vin-vth-sqrt (id/k)';reactiveExpressionRecursive($id ,defineLambdaFunction(' $x/rs'), $vs)"};
    private final static String[] RESULT = {"vt =vin-vth;equationFindRoot(defineLambdaFunction('$x^2-(1/rs/k+2*vt)*$x+vt^2'),1.6_1.8,0)/rs"};

    private final static Parameter<Integer> MAXITERATIONS;
    private final static Parameter<Double> EPSILON;
    
    static {
        MAXITERATIONS = new Parameter<Integer>("reactiveExpression","Recursive", "maxIteration", "The maximum number of iterations, above which it stops", 100, new ConvertIntervalToInteger (1,1000));
        EPSILON = new Parameter<Double>("reactiveExpression","Recursive", "epsilon", "The maximum distance between two iterations, below this value it stops", 0.0000001, new ConvertIntervalToDouble (0,1));
        ParameterManager.addParameter(MAXITERATIONS);        
        ParameterManager.addParameter(EPSILON);        
    }        
    
    public RexRecursive() {
        super("reactiveExpression", "Recursive", (byte) -3, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        FunctionPrefix f = FunctionPrefix.getFunction(input.get(1).getName());
        if (input.size() - 2 != f.getNumbArgs()) {
            throwNewCalculusException("The number of paramenters provided "+(input.size() - 2)+" does not match the function requirements: "+f.getNumbArgs());
        }


        ListIntervals y = FunctionVariable.getFunction(input.getFirst().getName()).getValue(), yNew;
        ListIntervals x = new ListIntervals();
        
        String[] xArg = new String[input.size()-2];
        for (int i=0; i<xArg.length; ++i) xArg[i] = input.get(i+2).getName();
        
        String yArg = input.getFirst().getName();
        
        int iter = 0;
        final int maxIter = MAXITERATIONS.getVal();
        final double epsilon = EPSILON.getVal();
        FunctionVariable result= null;
        while (iter < maxIter) {
            ++iter;
            
            x.clear();
            for (String i : xArg)
                x.addAll(FunctionVariable.getFunction(i).getValue());
            
            yNew = f.compute(x);                                
            
            result = new FunctionVariable(yArg, yNew, false, "");
                    
            FunctionVariable.addFunction(result);
            
            ReactiveExpression.computeReactiveExpressions(false);
            
            if (iter > 1 && y.distance(yNew) < epsilon) break;
            
            y = yNew;
            
        }
        
        
        
        if (iter >= maxIter) throwNewCalculusException("It was reached the maximum numer of iteration without converging.");
        
        return result.getValue();
    }

}
