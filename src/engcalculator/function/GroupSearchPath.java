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

package engcalculator.function;

import engcalculator.expression.token.TknFunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import java.util.LinkedList;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class GroupSearchPath  {
    private final LinkedList<String> groupPath;
    private final static TknFunctionPrefix TFP = new TknFunctionPrefix ();

    public GroupSearchPath() {
        groupPath = new LinkedList<String> ();
    }
        
    public LinkedList<String> getGroupPath() {
        return groupPath;
    }
    
    public ListIntervals getGroupPathAsListIntervals() {
        ListIntervals result = new ListIntervals ();
        for (String s : groupPath)
            result.add(new IntervalLiteral(s));
        return result;
    }    
    
    public boolean isGroupPathDefined () {
        return groupPath.size() != 0;
    }

    public void setGroupPath(ListIntervals gp) throws Exception {
        groupPath.clear();
        for (Interval i : gp) {
            String s = i.getName();
            if (s == null || s.length() == 0) throw new CalculusException ("It was not provided a literal as input: "+i);
            if (! TFP.mayBeCompatible(s)) throw new CalculusException ("The given literl is not suitable as group name: "+s);
            groupPath.add(s);
        }
    }
}
