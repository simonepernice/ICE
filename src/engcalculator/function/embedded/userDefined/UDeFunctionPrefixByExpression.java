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
import engcalculator.expression.Expression;
import engcalculator.interval.ListIntervals;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class UDeFunctionPrefixByExpression extends FunctionPrefix {
    private final Expression expression;
    private final List<String> varnames;
    private final static DomainList DOMAIN = new DomainList (new DomainInterval());

    public UDeFunctionPrefixByExpression(String name, byte numbArgs, boolean acceptLists, String help, Expression expression, List<String> varnames) {
        super (UDeCurrentGroup.getGroup(name), UDeCurrentGroup.getName(name), numbArgs, DOMAIN, acceptLists, false, help, null, null);
        this.expression = expression;
        this.varnames = varnames;     
    }   

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        HashMap<String, FunctionPrefix> temporaryVars = new HashMap <String, FunctionPrefix> ();

        Iterator<Interval> iinput = input.iterator();
        Iterator<String> iVarnames  = varnames.iterator();
        
        if (iVarnames.hasNext()) 
            while (true) {
                String varnamesi = iVarnames.next();
                if (! iVarnames.hasNext()) break; //skip last being the function itself
                temporaryVars.put(varnamesi, new FunctionVariable ("", varnamesi, iinput.next(), false, "Temporary variable"));
            }

        temporaryVars.put(getSymbol(), this);

        return expression.evaluate(temporaryVars);
    }

    @Override
    public String getHelp() {
        return new StringBuilder(super.getHelp()).append("\nUser defined prefix function by expression ").append(expression.toString()).toString();
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }
}
