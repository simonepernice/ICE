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

package engcalculator.function.parameter;

import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

public class ConvertIntervalToString extends ConvertIntervalTo<String> {
    private final int length;
    
    public ConvertIntervalToString() {
        length = -1;
    }

    public ConvertIntervalToString(int length) {
        this.length = length;
    }
    @Override
    protected String convertInterval(Interval i) throws CalculusException {
        String res = i.getName();
        if (res == null) Function.throwNewCalculusException ("It was expected a literal while it is received "+i.toString());
        if (length != -1 && res.length()!= length) Function.throwNewCalculusException ("It was expected a literal of length "+length+" while it is received "+i.toString());
        return res;
    }
    
    @Override
    public String toString () {
        if (length != -1) return "Requires literal value of length "+length;
        return "Requires literal value";
    }
}
