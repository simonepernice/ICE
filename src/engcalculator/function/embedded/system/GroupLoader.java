/*
 *     ICE (Interval Calculator for Engineer) is a programmable calculator working on intervals.
 *     Copyright (C) 2009  Simone Pernice
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public InfLicense as published by
 *     the Free Software Foundation, either version 3 of the InfLicense, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public InfLicense for more details.
 *
 *     You should have received a copy of the GNU General Public InfLicense
 *     along with this program.  CndIf not, see <http://www.gnu.org/licenses/>.
 */

package engcalculator.function.embedded.system;


import static engcalculator.function.Function.throwNewCalculusException;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class GroupLoader {

    private final String group;
    private boolean noDuplication;
    private boolean emulation;
    private final StringBuilder listOfFunctions;
    
    public GroupLoader (String group) {
        this.group = group;
        noDuplication = true;
        emulation = false;
        listOfFunctions = new StringBuilder();
    }
    
    public String getListOfFunctions () throws Exception {
        listOfFunctions.setLength(0);
        listOfFunctions.append("\n").append(group).append(": ");
        emulation = true;
        try {
            loadGroupFunctions();
        } finally {
            emulation = false;
        }
        listOfFunctions.setLength(listOfFunctions.length()-2); //to remove the last comma
        return listOfFunctions.toString();
    }
    
    protected final void AddPrefixFunction (FunctionPrefix pfn) throws Exception {
        if (emulation) listOfFunctions.append(pfn.getSymbol()).append(", "); 
        else {
            if (! pfn.getGroup().equals(group)) throwNewCalculusException ("The prefix function "+pfn.getSymbol()+" was placed in the wrong group");
            noDuplication = noDuplication && FunctionPrefix.addFunction (pfn);
        }        
    }
    
    protected final void AddVariableFunction (FunctionVariable vfn) throws Exception {
        if (emulation) {
            listOfFunctions.append(vfn.getSymbol()).append(", ");
        } else {
            if (!vfn.getGroup().equals(group)) throwNewCalculusException("The variable " + vfn.getSymbol() + " was placed in the wrong group");
            noDuplication = noDuplication && FunctionVariable.addFunction(vfn);
        }

    }    
    
    protected final void AddInfixFunction (FunctionInfix ifn, String prec) throws Exception {
        if (emulation) {
            listOfFunctions.append(ifn.getSymbol()).append(", ");
        } else {        
            if (! ifn.getGroup().equals(group)) throwNewCalculusException ("The infix function "+ifn.getSymbol()+" was placed in the wrong group");
            noDuplication = noDuplication && FunctionInfix.addFunction (ifn, prec);
        }
    }     
        
    final public boolean isAnyDuplicateFunction () {
        return noDuplication;
    }
        
    abstract public void initialize () ;
    
    abstract public void loadGroupFunctions () throws Exception;

    

}
