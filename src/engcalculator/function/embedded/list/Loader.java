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

package engcalculator.function.embedded.list;


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
        AddPrefixFunction (new LisIncremental());
        AddPrefixFunction (new LisInteger());
        AddPrefixFunction (new LisLinear());
        AddPrefixFunction (new LisLogarithmic());
        AddPrefixFunction (new LisClone()); 
        AddPrefixFunction (new LisSize());            
        AddPrefixFunction (new LisShuffle());            
        AddPrefixFunction (new LisGrow());            
        AddPrefixFunction (new LisDistance());            
        AddPrefixFunction (new LisCombination());            
        AddPrefixFunction (new LisFlatten());            
        AddPrefixFunction (new LisIndexOf());            
        AddPrefixFunction (new LisPick());            
        AddInfixFunction (new LisGetElements(), "get");
        AddInfixFunction (new LisSerieInteger(), "get");
        AddInfixFunction (new LisSetElements(), "set");
        AddInfixFunction (new LisBuilder(), "list");    
        AddInfixFunction (new LisKeepIfTrue(), "set2");                           
    }        

}
