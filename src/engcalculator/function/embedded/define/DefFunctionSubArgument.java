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

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.function.CalculusException;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.userDefined.UDeFunctionPrefixByFunction;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefFunctionSubArgument extends FunctionInfix {
    private final static DomainList LEFTDOMAIN = new DomainList (new DomainIntervalLiteral(), new DomainInterval ());
    private final static DomainList RIGHTDOMAIN = new DomainList (new DomainFunctionPrefix(-1));

    private final static String HELP = "($f, value0, $x, ..) ...  $g would define the function f (x) = g (value0, x).\nIt is useful to define a new function which executes the same of a given function but takes less parameter because some of them are fixed. The syntax is ($newFunctionName, interval if this param is fixed, any literal if this parameter is variable, ..) ... $functionName. The fixed parameters are kept in the same position as on ... definition. For instance defining ($f, $x, 10) #= $g, then f(1) will execute g(1, 10) while with ($f, 10, $x) #= $g then f 1 will execute g (10, 1). This function is useful because some ICE function works on function names with a given number of arguments. In those case ... can be used to fix the  parametric parameters. For example it can be used to plot several times a two argument function changing a parameter. It can also be used to define an alias for an already existint function to make it shorter.";
    private final static String[] EXAMPLESLEFT = {"($f, x, y)='x+2*y';($g,$x,5)#=$f;g(1,2,3)", "($g,5,$x)#=$f;g(1,2,3)", "($f, x, y, z)='x+2*y+3*z';($g,$x,5,$x)#=$f;g(1,2)", "($f, x, y) = 'y +sin x';($h, k) = 'calculusIntegrate(($g, $x, k) #= $f , 0_PI)';equationFindRoot($h, 0_2, 2+PI)",";($aliasSin, $x)#=$sin;aliasSin(1 .. 6) == sin (1 .. 6)"};
    private final static String[] EXAMPLESRIGHT = {"","", "", "", ""};
    private final static String[] RESULTS = {"11,12,13","7,9,11", "17","1","1,1,1,1,1,1"};
    public DefFunctionSubArgument() {
        super ("define", "#=", (byte) -1, (byte) 1, LEFTDOMAIN, RIGHTDOMAIN, false, true,  HELP, EXAMPLESLEFT, EXAMPLESRIGHT, RESULTS);
    }

    @Override
    public final ListIntervals _compute(ListIntervals left, ListIntervals right) throws Exception {
        FunctionPrefix f = FunctionPrefix.getFunction(right.getFirst().getName());
        if (f.getNumbArgs() > 0 && left.size()-1 != f.getNumbArgs()) throwNewCalculusException ("The number of parameters given (fixed and vairable) "+(left.size()-1)+ " defined does not match with function parameters "+f.getNumbArgs());

        ListIntervals parameters = new ListIntervals();
        int[] positions = new int[left.size() - 1]; //the size of array position is over extimated at the beginning
        int j = 0;
        for (int i = 1; i < left.size(); ++i) {
            if (! left.get(i).isIntervalLiteral()) {
                positions[j] = i-1;
                ++ j;
                parameters.add(left.get(i));
            }
        }
        int[] pp = new int [j];//the parameter position array is trimmed to the required space 
        for (int i = 0; i < j; ++ i)
            pp[i] = positions[i];
                
        FunctionPrefix g = new UDeFunctionPrefixByFunction(left.getFirst().getName(), f, parameters, pp);
        if (! FunctionPrefix.addFunction(g)) throwNewCalculusException ("There is a function already defined with the same name "+left.getFirst().getName()+" which is locked.");
        return new ListIntervals(new IntervalLiteral(g.getSymbol()));                
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(MeasurementUnit.PURE);
        return res;
    }     
}
