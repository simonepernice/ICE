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

package engcalculator.function.variable;

import engcalculator.function.Function;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrixNamed;
import java.util.Collection;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

/*
 * This interface is used by the Expression
 * class so that it can solve for variables.
 */

public class FunctionVariable extends FunctionPrefix {
    private final static FunctionVariableStorage FUNCTION_VARIABLE_STORAGE = new FunctionVariableStorage(200);

    public static int beginContext () {
        return FUNCTION_VARIABLE_STORAGE.beginContext(15);
    }
    
    public static int endContext () throws Exception {
        return FUNCTION_VARIABLE_STORAGE.endContext();
    }
    
    public static boolean addFunction (FunctionVariable var) throws Exception {
        return FUNCTION_VARIABLE_STORAGE.addFunction(var);
    }

    public static boolean delFunction (String symbol) {
        return FUNCTION_VARIABLE_STORAGE.delFunction(symbol);
    }

    public static FunctionVariable getFunction (String symbol) {
        return FUNCTION_VARIABLE_STORAGE.getFunction(symbol);
    }

    public static Collection<FunctionVariable> getVariables () {
        return FUNCTION_VARIABLE_STORAGE.getVariables();
    }

    public static void initialize () throws Exception {        
        //This is called at the beginning in case something has to be set up
        FUNCTION_VARIABLE_STORAGE.initialize();
        Function.getGroupSearchPath().getGroupPath().clear();
    }

    final private ListIntervalsMatrixNamed value;

    public FunctionVariable(String name, ListIntervals value, boolean constant, String help) {
        super ("", name, (byte) 0, null, false, constant, help, null, null);
        this.value = new ListIntervalsMatrixNamed (value, name);
    }

    public FunctionVariable(java.lang.String group, String name, ListIntervals value, boolean constant, String help) {
        super (group, name, (byte) 0, null, false, constant, help, null, null);
        this.value = new ListIntervalsMatrixNamed (value, buildName(group,name));
    }
    
    public FunctionVariable(java.lang.String group, String name, Interval value, boolean constant, String help) {
        super (group, name, (byte) 0, null, false, constant, help, null, null);
        this.value = new ListIntervalsMatrixNamed (value, buildName(group,name));
    }    
    
    public FunctionVariable(java.lang.String group, String name, Interval value, boolean constant, String help, String[] examples, String[] results) {
        super (group, name, (byte) 0, null, false, constant, help, examples, results);
        this.value = new ListIntervalsMatrixNamed (value, buildName(group,name));
    }        

    public ListIntervalsMatrixNamed getValue () {
        ListIntervalsMatrixNamed newValue = new ListIntervalsMatrixNamed (value);
        return newValue;
    }
    
    public ListIntervalsMatrixNamed getValueToModifiy () {
        if (isLocked()) throw new RuntimeException ("It is not possible to access a value in modify mode if protected.");
        return value;
    }
    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervalsMatrixNamed (value);
    }

    @Override
    public String getHelp() {
        if (getBasicHelpMessage().equals("")) return "";

        StringBuilder result = new StringBuilder();
        
        result.append("\nGroup: ");
        String g = getGroup();
        if (g == null || g.length() == 0) result.append("NOT DEFINED");
        else result.append (g);
        
        result.append("\nName: ").append(getSymbol());
        
        result.append("\nType: variable");
        
        if (isLocked()) result.append("\nThis variable is locked: cannot be changed by user");
        else result.append("\nThis variable can be changed by user");
        
        result.append("\nHelp: ").append(getBasicHelpMessage()).append('\n');
        return result.toString();
    }

    @Override
    public final int getComputeNumbArg() {
        return 0;
    }
}