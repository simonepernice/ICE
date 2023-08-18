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

package engcalculator.function.embedded.userDefined;

import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class UDeCurrentGroup {
    private final static Parameter<String> currentGroup;
    private static boolean multiGroup = false;

    static {
        currentGroup = new Parameter<String>("define", "ALL", "currentGroup", "sets the group name for the new functions and variables created.\nAll the functions (and variables) created will have that group. If it is called with '' as argument it returns the current group without any set, if it is called with 'CLEARGROUPNAME' argument it sets the current group to ''. By default the current group is ''. It is possible to work with several groups at the same time calling with 'MULTIGROUP'. Then when some new element is defined it will look for the first upper case character to understand where is the group and where the name. If the input is 'CLEARGROUPNAME' the search path is cleared.", "", new ConvertIntervalToStringCurrentGroup());
        ParameterManager.addParameter(currentGroup);
    }
    
    public static String getGroup(String fullName) {
        if (multiGroup) {
            int i;
            for (i=0; i< fullName.length(); ++i) {
                if (Character.isUpperCase(fullName.charAt(i))) break;
            }
            if (i == fullName.length()) return "";
            return fullName.substring(0,i);
        }
        return currentGroup.getVal();
    }

    public static String getName(String fullName) {
        if (multiGroup) {
            int i;
            for (i=0; i< fullName.length(); ++i) {
                if (Character.isUpperCase(fullName.charAt(i))) break;
            }
            if (i == fullName.length()) return fullName;
            return fullName.substring(i);
        }        
        return fullName;
    }    
    
    public static void setCurrentGroup(String currentGroup) {
        multiGroup = currentGroup.equals("MULTIGROUP");
    }
    
    public static String getCurrentGroup () {
        return currentGroup.getVal();
    }

        
}
