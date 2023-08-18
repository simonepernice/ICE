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

import engcalculator.expression.token.TknFunctionPrefix;
import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.reactiveExpression.ReactiveExpression;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

/*
 * This interface is used by the Expression
 * class so that it can solve for variables.
 */

public final class FunctionVariableStorage {   
    private final static TknFunctionPrefix PREFIX_FUNCTION_TKN = new TknFunctionPrefix();
    
    private HashMap<String, FunctionVariable> functionVariableHashMap;
    private final LinkedList<HashMap<String, FunctionVariable>> functionVariableStack;

    public FunctionVariableStorage(int size) {
        functionVariableStack = new LinkedList<HashMap<String, FunctionVariable>> ();
        beginContext(size);
    }
    
    public int beginContext (int size) {
        functionVariableHashMap = new HashMap<String, FunctionVariable> (size);
        functionVariableStack.push(functionVariableHashMap);        
        return functionVariableStack.size();
    }
    
    public int endContext () throws Exception {
        if (functionVariableStack.size() < 2) throw new CalculusException ("It is not exit the current context because it is the top level");
        functionVariableStack.pop();        
        functionVariableHashMap = functionVariableStack.peek();        
        return functionVariableStack.size();
    }    
    
    public boolean addFunction (FunctionVariable var) throws Exception {
        final String symbol = var.getSymbol();
        if(! PREFIX_FUNCTION_TKN.mayBeCompatible(symbol)) throw new CalculusException("The variable name is not properly formed: "+var.getSymbol());
        
        if (var.getNumbArgs() != 0) throw new RuntimeException("It was defined a variable with arguments!?");

        FunctionVariable v;
        if ((v = functionVariableHashMap.get(symbol)) != null && v.isLocked()) return false;

        functionVariableHashMap.put(symbol, var);
        
        ReactiveExpression.addChangedVariable(symbol);

        return true;
    }

    public boolean delFunction (String symbol) {
        if (ReactiveExpression.isUsedAsInput(symbol)) return false;
        
        FunctionVariable v;
        if ((v = functionVariableHashMap.get(symbol)) != null) {
            if (v.isLocked()) {
                return false;
            } else {
                functionVariableHashMap.remove(symbol);
                return true;
            }
        }
        return false;
    }
    
    private FunctionVariable getFunctionOnStack (String symbol) {
        FunctionVariable result = functionVariableHashMap.get(symbol);
        if (result != null) return result;
        else {
            final int s = functionVariableStack.size();
            if (s > 1) {
                Iterator <HashMap<String, FunctionVariable>> li = functionVariableStack.iterator();
                li.next();
                while (li.hasNext()) {
                    result = li.next().get(symbol);
                    if (result != null) return result;
                }
            }
        }    
        return null;
    }

    public FunctionVariable getFunction (String symbol) {
        FunctionVariable result = getFunctionOnStack(symbol);
        if (result != null) return result;
        
        if (Function.getGroupSearchPath().isGroupPathDefined()) {
            StringBuilder groupName = new StringBuilder ();
            for (String gp : Function.getGroupSearchPath().getGroupPath()) {
                 groupName.setLength(0);
                 groupName.append(gp);
                 groupName.append(symbol);
                 result = getFunctionOnStack(groupName.toString());
                 if (result != null) return result;
            }
        }
        return null;        
    }

    public Collection<FunctionVariable> getVariables () {
        return functionVariableHashMap.values();
    }

    public void initialize () throws Exception {
        //This is called at the beginning in case something has to be set up
        while (functionVariableStack.size() > 1) endContext();        
        functionVariableHashMap.clear();
    }
}