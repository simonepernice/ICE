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

package engcalculator.function.embedded.define;

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
import engcalculator.domain.DomainListLiteral;
import engcalculator.function.Function;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefDelete extends FunctionPrefixSingleInputSingleOutput {
    private final static DomainListLiteral DOMAIN = new DomainListLiteral (new DomainIntervalLiteral());
    
    private final static String HELP = "... $a would delete the symbol a.\nThe symbol to delete may be a variable, a function (infix or prefix), a measurement unit. Ex.: delete ('x') deletes the variable or function x. The ICE functions are locked, so they cannot be deleted. To add or remove the lock of a function defineFunctionLock Set and Clear.";
    private final static String[] EXAMPLES = {"ada=5;defineDelete $ada;ada"};
    private final static String[] RESULTS = {"'ada'"};

    public DefDelete() {
        super ("define", "Delete", DOMAIN, true, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {

        String fname = input.getName();
        
        String result = Function.delete(fname);
        
        if (result == null) throwNewCalculusException ("It is not possible to find anything called "+fname+" or it is a constant.");
        
        return new IntervalLiteral(result);
    }
}
