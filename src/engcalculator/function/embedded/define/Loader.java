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

package engcalculator.function.embedded.define;


import engcalculator.function.embedded.system.GroupLoader;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class Loader extends GroupLoader {

    public Loader(String group) {
        super(group);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void loadGroupFunctions() throws Exception {
        AddInfixFunction (new DefFunctionVariable(), "set");
        AddInfixFunction (new DefFunctionCompiled(), "set");
        AddInfixFunction (new DefFunctionInterpolation(), "set");
        AddInfixFunction (new DefFunctionSubArgument(), "set");              
        AddPrefixFunction (new DefFunctionInverse());
        AddPrefixFunction (new DefShowAllFunctions());            
        AddPrefixFunction (new DefDelete());
        AddPrefixFunction (new DefDeleteAll());
        AddPrefixFunction (new DefLockSet());                        
        AddPrefixFunction (new DefLockClear());                        
        AddPrefixFunction (new DefGroupSearchPath());                        
        AddPrefixFunction (new DefGroupGet());            
        AddPrefixFunction (new DefLambdaGetName());                        
        AddPrefixFunction (new DefLambdaGetValue());                        
        AddPrefixFunction (new DefLambdaFunction());      
        AddPrefixFunction (new DefFunctionInfix());
        AddPrefixFunction (new DefContextBegin());
        AddPrefixFunction (new DefContextEnd());
        AddPrefixFunction (new DefSummaryUserDefinition());
    }        

}
