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

package engcalculator.function.prefix;


import engcalculator.domain.DomainList;
import engcalculator.expression.ExpressionCompiled;
import engcalculator.expression.Expression;
import engcalculator.function.embedded.system.LoadGroup;
import engcalculator.interval.ListIntervals;
import engcalculator.function.variable.FunctionVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionPrefixByExpression extends FunctionPrefix {
    private final static String HELP = "\nThis is a system defined function as per the folowing ICE expression: ";
    
    private final String sExpression;
    private final LinkedList<String> varnames;
    private final boolean isCompiled;
    private final int size;
    private final String[] requiredGroups;
    private Expression expression;
            
    public FunctionPrefixByExpression(String group, String name, byte numbArgs, DomainList domain, boolean acceptLists, String help, String[] examples, String[] results, String expression, boolean isCompiled, String[] varnames, String[] requiredGroups) {
        super (group, name, numbArgs, domain, acceptLists, true, help+HELP+expression, examples, results);
        
        this.varnames = new LinkedList<String>();
        this.varnames.addAll(Arrays.asList(varnames));
        this.varnames.add(buildName(group, name));
        
        this.size = varnames.length;   
        if (size != numbArgs) throw new RuntimeException ("The internal function "+getSymbol()+" has number of argument not matching the number of variables.");
        this.sExpression = expression;
        this.requiredGroups = requiredGroups;
        this.isCompiled = isCompiled;
    }    

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        if (expression == null) {
            String missingGroups;
            if (requiredGroups != null && (missingGroups = LoadGroup.areGroupsLoaded(requiredGroups)) != null) throwNewCalculusException("The called functions depends on the following groups which are not loaded: "+missingGroups);
            if (isCompiled) expression = new ExpressionCompiled(sExpression, varnames);
            else expression = new Expression(sExpression, varnames);
        }
        
        HashMap<String, FunctionPrefix> temporaryVars = new HashMap <String, FunctionPrefix> ();

        
        for (int i=0;i<size;++i) {//skip last being the function itself
            temporaryVars.put(varnames.get(i), new FunctionVariable ("", varnames.get(i), input.get(i), false, "Temporary variable"));
        }

        temporaryVars.put(getSymbol(), this);

        return expression.evaluate(temporaryVars);
    }

}
