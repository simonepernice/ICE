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

public class ConvertIntervalToLong extends ConvertIntervalTo<Long> {
    final long min, max;
    
    public ConvertIntervalToLong(long min, long max) {
        this.min = min;
        this.max = max;           
    }
    
    @Override
    protected Long convertInterval(Interval i) throws CalculusException {
        long res = (long) i.getValue();
        if (res < min || res > max) Function.throwNewCalculusException ("The given value "+res+" is out of range: "+min+"_"+max);
        return res;
    }
    
    @Override
    public String toString () {
        return "Requires long integer value between "+min+" and "+max;
    }       
}
