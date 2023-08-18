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

package engcalculator.function.embedded.information;


import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.system.LoadGroup;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class InfFunctionListGroup extends FunctionPrefixSingleInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... ('group') showes all the functions available in the given group without loading it.\nThe functions are separated by commas, if the request is on a super group each group is split by semicolon.";
             
    public InfFunctionListGroup() {
        super("information", "FunctionListGroup", DOMAIN, true, true, HELP, null, null);
    }
    
    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        String group = input.getName();

        return new IntervalLiteral ( LoadGroup.getGroupListOfFunctions(group));
    }    
}
