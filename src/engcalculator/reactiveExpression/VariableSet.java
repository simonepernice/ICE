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

package engcalculator.reactiveExpression;


import engcalculator.function.variable.FunctionVariable;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class VariableSet extends TreeSet<String> {
    public VariableSet () {
        super ();
    }
    
    public VariableSet (List<String> a) {
        super (a);
    }
    
    public boolean containsAny (VariableSet b) {
        final String last = last();
        for (String s : b) {
            if (contains(s)) return true;
            if (s.compareTo(last) > 0) return false;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder ();
        for (String s : this) {
            result.append (FunctionVariable.getFunction(s).getValue().toString()).append("\n");                        
        }
        return result.toString();
    }            
}
