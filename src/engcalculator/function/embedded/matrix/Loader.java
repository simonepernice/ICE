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

package engcalculator.function.embedded.matrix;


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
        AddPrefixFunction (new MatTranspose ());
        AddPrefixFunction (new MatLINE());
        AddPrefixFunction (new MatIdentity ());
        AddPrefixFunction (new MatDiagonal ());
        AddPrefixFunction (new MatDeterminant ());
        AddPrefixFunction (new MatInvert ());
        AddPrefixFunction (new MatEigenValue());
        AddPrefixFunction (new MatEigenVector());
        AddPrefixFunction (new MatSort());
        AddInfixFunction (new MatMul(), "times");
        AddInfixFunction (new MatDiv(), "times");
        AddInfixFunction (new MatByColumnBuilder(), "list");
        AddInfixFunction (new MatBuilder(), "create");
        AddInfixFunction (new MatPower(), "power");
        AddPrefixFunction (new MatSizeColumn());
        AddPrefixFunction (new MatSizeRow());
        AddPrefixFunction (new MatSpreadOnRows());               
        AddPrefixFunction (new MatSpreadOnColumns());                                       
        AddPrefixFunction (new MatMaker());                                       
    }        

}
