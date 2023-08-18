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

package engcalculator.function.embedded.userDefined;


import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.interval.ListIntervals;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.reactiveExpression.ReactiveExpression;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class UDeFunctionPrefixByReactiveExpression extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainInterval());
    
    private final String[] in;
    private final String out;    

    public UDeFunctionPrefixByReactiveExpression(String name, String help, String[] in, String out) {
        super (UDeCurrentGroup.getGroup(name), UDeCurrentGroup.getName(name), (byte) in.length, DOMAIN, false, false, help, null, null);
        this.in = in;
        this.out = out;     
    }   

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        //It is not possible to use getVariableToModify because:
        //the modify would not be recorder by the reactive expressions, therefore all of them should have been triggered
        //Triggering all the variable may be overwritten by some other reactive expression 
        //Also getVariableToModify at every call of this function may be not efficient because of the requirement to trigger all the functions and moreover the variable itself my be changed by higher priority reactive expression
        for (int i = 0; i<getNumbArgs(); ++i)
            FunctionVariable.addFunction (new FunctionVariable(in[i], input.subList(i, i+1), false, "reactive expression find root set variable"));
        ReactiveExpression.computeReactiveExpressions(false);
        return FunctionVariable.getFunction(out).getValue();
    }

    @Override
    public String getHelp() {
        return new StringBuilder(super.getHelp()).append("User defined prefix function by reactive expresison using: ").append(Arrays.toString(in)).append(" as input variables and: ").append(out).append(" as output.").toString();
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }    
}
