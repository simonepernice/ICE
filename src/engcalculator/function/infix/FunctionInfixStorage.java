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

package engcalculator.function.infix;


import engcalculator.expression.token.TknFunctionInfixPrefix;
import engcalculator.function.CalculusException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class FunctionInfixStorage {
    private final static TknFunctionInfixPrefix INFIX_FUNCTION_TKN = new TknFunctionInfixPrefix();
    private final HashMap<String, FunctionInfix> FunctionInfixHashMap;
    private final LinkedList<Priority> AssociativityLinkedList;
    

    public FunctionInfixStorage(int size) {
        FunctionInfixHashMap = new HashMap <String, FunctionInfix> (size);
        AssociativityLinkedList = new LinkedList<Priority> ();        
    }
    
    public String getPriorityList() {
        StringBuilder res = new StringBuilder ();
        for (Priority p : AssociativityLinkedList) {
            res.append('\'');
            res.append(p.getName());
            res.append ("': ");
            if (p.isLeftAssociative()) res.append (" left associative; ");
            else res.append (" right associative; ");
        }
        return res.toString();
    }

    public boolean addFunction (FunctionInfix func, String priorityName) throws Exception {
        if(! INFIX_FUNCTION_TKN.mayBeCompatible(func.getSymbol())) throw new CalculusException("The infix function name is not properly formed: "+func.getSymbol());

        FunctionInfix f;
        if ((f = FunctionInfixHashMap.get(func.getSymbol())) != null && f.isLocked()) return false;

        func.priority = getPriorityByName(priorityName);

        FunctionInfixHashMap.put(func.getSymbol(), func);

        return true;
    }

    public boolean delFunction(String symbol) {
        FunctionInfix f;

        if ((f = FunctionInfixHashMap.get(symbol)) != null) {
            if (f.isLocked()) {
                return false;
            } else {
                FunctionInfixHashMap.remove(symbol);
                return true;
            }
        }
        
        return false;
    }

    public FunctionInfix getFunction (String symbol) {
        return FunctionInfixHashMap.get(symbol);
    }

    public Collection<FunctionInfix> getInfixFunctions () {
        return FunctionInfixHashMap.values();
    }

    public byte getMaxPriority () {
        return (byte) (AssociativityLinkedList.size()-1);
    }
    
    private byte getPriorityByName(String pn) {
        final int s = AssociativityLinkedList.size();
        for (int i = 0; i < s; ++i)
            if (AssociativityLinkedList.get(i).getName().equals(pn)) return (byte) i;
        throw new RuntimeException ("The required priority was not registered");
    }

    public void addPriority (Priority p) {
        for (Priority pr : AssociativityLinkedList) {
            if (pr.getName().equals(p.getName())) throw new RuntimeException ("Two priority where defined with the same name");
        }
        AssociativityLinkedList.add(p);
    }

    public boolean isLeftAssociative (int priority) {
        return AssociativityLinkedList.get(priority).isLeftAssociative();
    }

    public void initialize () throws Exception {
        FunctionInfixHashMap.clear();
        AssociativityLinkedList.clear();
       
        addPriority(new Priority ("list", true));
        addPriority(new Priority ("set", false));
        addPriority(new Priority ("set2", false));
        addPriority(new Priority ("or", true));
        addPriority(new Priority ("and", true));
        addPriority(new Priority ("union", true));
        addPriority(new Priority ("intersection", true));
        addPriority(new Priority ("equal", true));
        addPriority(new Priority ("compare", true));
        addPriority(new Priority ("sum", true));
        addPriority(new Priority ("times", true));
        addPriority(new Priority ("power", false));
        addPriority(new Priority ("get", true));
        addPriority(new Priority ("create", true));        
    }
}
