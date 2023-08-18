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

package engcalculatorSelfCheck;

import engcalculator.Calculator;
import engcalculator.function.Function;
import engcalculator.function.embedded.system.LoadGroup;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngCalculatorSelfCheck {

    /*
    * Test our Expression class by evaluating the command-line
    * argument and then returning.
    */

    public static void main( String[] args ){
        Calculator c = new Calculator(true, null);
        
        System.out.println("This is a simple tool for the self check of the functions. It basically runs all the function examples and check for the corectness of the results. Please wait for the check.");
        try {
            LoadGroup.loadGroup("ALL");
        } catch (Exception ex) {
            System.out.println("It was not possible to initialize all the functions: "+ex.getMessage());
        }
        System.out.println(Function.checkFunctions());
        System.out.println("End of check");
    }

}
