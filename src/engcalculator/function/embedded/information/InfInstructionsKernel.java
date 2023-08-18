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

package engcalculator.function.embedded.information;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class InfInstructionsKernel extends InfPrefixFunction {
    public static String INSTRUCTIONSKERNEL;
    public final static String HELP = "... provides a beginning detailed set of instruction to use Enginner Calculator kernel. It should be fully read to understand the how it works. After instruction it is suggested to read the 'feature' to know all the features available and then the help on each function.";
 
    public InfInstructionsKernel() {
        super("InstructionsKernel", HELP);
        INSTRUCTIONSKERNEL = setOutputTextFromFile("EngCalcInstructionsKernel.txt");
     }

}
