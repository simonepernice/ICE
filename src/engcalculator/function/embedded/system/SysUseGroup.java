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

package engcalculator.function.embedded.system;


import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SysUseGroup extends FunctionPrefixSingleInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... ('group') is able to load the functions contained in the given group.\nIt returns the group name loaded if any. A group cannot be loaded twice, if you try it will not be loaded the second time. It is possible select any of the following group: "+LoadGroup.getGroups()+" or a super group. A super group is all upper case and is a short cut to load several groups. For details on the functions available for each group see features command. It is possible to load a group of groups calling ... with the following names: BASE (the basic groups like arithmetic, power, trigonometry, etc.) and it is loaded automatically at startup. ENGINEER to load all the groups useful for engineer stuff, PROGRAM to load all the groups used for advanced programming, ALL to load all the groups available. The following list show the exact groups loaded for a given group: "+LoadGroup.getSuperGroups()+ "It is also posible just use the beginning chars of the group name as far as it is unique. It is also possible loading groups from third parth (not compiled together with ICE), in that case the group name should present the full path like for example uni.edu.ice.fuzzy in this case the last name of the path is the same of the groups, therefore in the previous example all the functions are expected to begin by fuzzy.";
             
    public SysUseGroup() {
        super("system", "UseGroup", DOMAIN, true, true, HELP, null, null);
    }
    
    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        String group = input.getName();

        return new IntervalLiteral ( LoadGroup.loadGroup(group));
    }    
}
