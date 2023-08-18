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
package engcalculatorSelfHelp;

import engcalculator.Calculator;
import engcalculator.function.Function;
import engcalculator.function.SymbolWithHelp;
import engcalculator.function.embedded.system.LoadGroup;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MakeFunctionListTxtFile {

    private final static String[] STRINGARRAY = {"a","b"};

    
    public MakeFunctionListTxtFile() {
        
    }
    
    public static void main( String[] args ){
        System.out.println ("This program creates the help of all functions available in ICE. It is the one showed by function: functionList. This tool should be run every time a new function is added or the help of some function is changed.\n");
        
        Calculator c = new Calculator(true, null);
        try {
            LoadGroup.loadGroup("ALL");
        } catch (Exception ex) {
            System.out.println("It was not possible to initialize all the functions: "+ex.getMessage());
        }
                
        Collection<SymbolWithHelp> functions = Function.getAllSymbolsWithHelp();
        ArrayList<String> functionArray = new ArrayList<String> (functions.size());
        for (SymbolWithHelp f : functions) {
            String fhelp;
            fhelp = f.getHelp();
            if (fhelp != null && !fhelp.equals("")) {
                functionArray.add(fhelp);
            }
        }        
        
        String[] helpString = functionArray.toArray(STRINGARRAY);
        Arrays.sort(helpString);

        OutputStreamWriter os;
        try {
            os = new OutputStreamWriter(new FileOutputStream ("src/engcalculator/function/embedded/information/EngCalcFunctionList.txt"), "UTF-8");
        } catch (Exception uee) {
            System.out.println ("Unable to make the functions file");
            return ;
        }
        
        
        try {
            for (String help : helpString)
                os.append(help);
        } catch (IOException ioe) {
            System.out.println ("Unable to make the functions file");
            return;
        }
        
        try {
            os.close();
        } catch (Exception e) {
            System.out.println ("Unable to make the functions file");
            return;
        }
        
        System.out.println ("Functions file help created correctly.");
        return;
    }
}
