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

package engcalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngCalculator {
    private static final AsynchronousOutputPrinter output = new AsynchronousOutputPrinter();

    /*
    * Test our Expression class by evaluating the command-line
    * argument and then returning.
    */

    public static void main( String[] args ){

        String expression = "0"; // Line read from standard in

        System.out.println("Enter a line of text (type 'systemQuit' to exit): ");
        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);

        Calculator calc = new Calculator(true, output);
        System.out.println (calc.compute("informationAbout"));
        while (true) {
            System.out.print ("Expression"+calc.getStep()+" > ");
            try {
                expression = in.readLine();
            } catch (IOException ex) {
                System.out.println ("Internal Error Reading Input");
                break;
            }

            calc.compute(expression);

        }
    }

}
