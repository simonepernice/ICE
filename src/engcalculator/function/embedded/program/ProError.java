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

package engcalculator.function.embedded.program;

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.CalculusException;
import engcalculator.interval.Interval;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ProError extends FunctionPrefixSingleInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList(new DomainIntervalLiteral());
    private final static String HELP = "... ('error message') stops the current execution flow generating an error which reports the given 'error message' to the user.\nIt can be useful for user defined functions to rise exception.";
    private final static String[] EXAMPLES = {";programOnErrorReturn(defineLambdaFunction 'programError ($x)','error should have been risen','error catched')"};
    private final static String[] RESULTS = {"'error catched'"};
        
    public ProError() {
        super("program", "Error", DOMAIN, false, true, HELP, EXAMPLES, RESULTS);
    }
    
    @Override
    public Interval _computeIntervals (Interval i) throws Exception {
        throwNewCalculusException ("User made errore: "+i.getName());
        return null;//this will never be reached unless error check are disabled
    }
}
