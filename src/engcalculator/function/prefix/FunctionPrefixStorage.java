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

import engcalculator.expression.token.TknFunctionInfixPrefix;
import engcalculator.expression.token.TknFunctionPrefix;
import engcalculator.function.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FunctionPrefixStorage {
    private final static TknFunctionPrefix PREFIX_FUNCTION_TKN = new TknFunctionPrefix();
    private final static TknFunctionInfixPrefix INFIX_FUNCTION_TKN = new TknFunctionInfixPrefix();
    
    private HashMap<String, FunctionPrefix> functionPrefixHashMap;
    private final LinkedList<HashMap<String, FunctionPrefix>> functionPrefixStack;
    
    public FunctionPrefixStorage (int size) {
        functionPrefixStack = new LinkedList<HashMap<String, FunctionPrefix>> ();
        beginContext(size);        
    }
    
    
    public int beginContext (int size) {
        functionPrefixHashMap = new HashMap<String, FunctionPrefix> (size);
        functionPrefixStack.push(functionPrefixHashMap);        
        return functionPrefixStack.size();
    }
    
    public int endContext () throws Exception {
        if (functionPrefixStack.size() < 2) throw new CalculusException ("It is not exit the current context because it is the top level");
        functionPrefixStack.pop();        
        functionPrefixHashMap = functionPrefixStack.peek();        
        return functionPrefixStack.size();
    }        

    public boolean addFunction (FunctionPrefix func) throws Exception {
        //The name for a prefix function can also be the same of an infix function
        if(!(INFIX_FUNCTION_TKN.mayBeCompatible(func.getSymbol()) || PREFIX_FUNCTION_TKN.mayBeCompatible(func.getSymbol()))) throw new CalculusException("The prefix function name is not properly formed: "+func.getSymbol());

        FunctionPrefix f;

        if ((f = functionPrefixHashMap.get(func.getSymbol())) != null && f.isLocked()) return false;

        functionPrefixHashMap.put(func.getSymbol(), func);        

        return true;
    }

    public boolean delFunction(String symbol) {
        FunctionPrefix f;

        if ((f = functionPrefixHashMap.get(symbol)) != null) {
            if (f.isLocked()) {
                return false;
            } else {
                functionPrefixHashMap.remove(symbol);
                return true;
            }
        }
        return false;
    }
    
    private FunctionPrefix getFunctionOnStack (String symbol) {
        FunctionPrefix  result = functionPrefixHashMap.get(symbol);
        if (result != null) return result;
        else {
            final int s = functionPrefixStack.size();
            if (s > 1) {
                Iterator <HashMap<String, FunctionPrefix>> li = functionPrefixStack.iterator();
                li.next();
                while (li.hasNext()) {
                    result = li.next().get(symbol);
                    if (result != null) return result;
                }
            }
        }    
        return null;
    }    

    public FunctionPrefix getFunction (String symbol) {
        if (symbol == null) return null;
        
        final int s = symbol.length()-2;
        if (s > 0 && symbol.charAt(s) == '(' && symbol.charAt(s+1) == ')') symbol = symbol.substring(0, s);
        
        FunctionPrefix result = getFunctionOnStack(symbol);
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

    public Collection<FunctionPrefix> getPrefixFunctions () {
        return functionPrefixHashMap.values();
    }

    public void initialize () throws Exception {
       //This is called at the beginning in case something has to be set up
        while (functionPrefixStack.size() > 1) endContext();
        functionPrefixHashMap.clear();
    }

}
