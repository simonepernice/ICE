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

package engcalculator.function.prefix;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.list.LisBuilder;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionPrefixExpandLast extends FunctionPrefix {
    private final static LisBuilder BUILDER = new LisBuilder();
    private final static String HELP = "It is possible to apply this function to multiple arguments just adding several intervals as last parameter. ";

    public FunctionPrefixExpandLast(java.lang.String group, String name, byte numbArgs, DomainList domain, boolean expandsToLists, boolean locked, String help, String[] examples, String[] results) {
        super(group, name, numbArgs, domain, expandsToLists, locked, help+HELP, examples, results);
    }

    @Override
    public ListIntervals compute(ListIntervals... inputArray) throws Exception {
        if (inputArray.length != getComputeNumbArg()) throw new Exception ("It was expected just one argument for a prefix function");
        ListIntervals input = inputArray[0];

        ListIntervals result = null;

        int i = getNumbArgs()-1;
        ListIntervals argument, fixArgument = input.subList(0, i);
        while (i < input.size()) {
            argument = new ListIntervals(fixArgument);
            argument.add(input.get(i));
            if (i < getNumbArgs()) result = super.compute(argument);
            else result = BUILDER._compute(result, super.compute(argument));
            ++i;
        }
        return result;
    }
}
