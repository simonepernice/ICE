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
import engcalculator.function.infix.FunctionInfix;
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
public final class UDeFunctionInfixByExpression extends FunctionInfix {
    private final Expression expression;
    private final List<String> varNamesLeft;
    private final List<String> varNamesRight;
    private final int sizeLeft;
    private final int sizeRight;
    private final static DomainList DOMAIN = new DomainList (new DomainInterval());

    public UDeFunctionInfixByExpression(String name, byte numbArgsLeft, byte numbArgsRight, boolean acceptLists, String help, Expression expression, List<String> varNamesLeft, List<String> varNamesRight) {
        super (UDeCurrentGroup.getGroup(name), UDeCurrentGroup.getName(name), numbArgsLeft, numbArgsRight, DOMAIN, DOMAIN, acceptLists, false, help, null, null, null);
        this.expression = expression;
        this.varNamesLeft = varNamesLeft;     
        this.sizeLeft = varNamesLeft.size();
        this.varNamesRight = varNamesRight;     
        this.sizeRight = varNamesRight.size();
    }   

    @Override
    public final ListIntervals _compute(ListIntervals left, ListIntervals right) throws Exception {
        HashMap<String, FunctionPrefix> temporaryVars = new HashMap <String, FunctionPrefix> ();

        Iterator<Interval> ileft = left.iterator();
        Iterator<String> iVarNamesleft = varNamesLeft.iterator();
        
        while (iVarNamesleft.hasNext()) {
            String vleft = iVarNamesleft.next();
            temporaryVars.put(vleft, new FunctionVariable ("", vleft, ileft.next(), false, "Temporary variable"));
        }

        Iterator<Interval> iright = right.iterator();
        Iterator<String> iVarNamesright = varNamesRight.iterator();
        
        while (iVarNamesright.hasNext()) {
            String vright = iVarNamesright.next();
            temporaryVars.put(vright, new FunctionVariable ("", vright, iright.next(), false, "Temporary variable"));
        }

        return expression.evaluate(temporaryVars);
    }

    @Override
    public String getHelp() {
        return new StringBuilder(super.getHelp()).append("\nUser defined infix function by expression ").append(expression.toString()).toString();
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        return null;
    }

    
}
