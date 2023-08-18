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
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

public class ConvertIntervalToBoolean extends ConvertIntervalTo<Boolean> {
    
    public ConvertIntervalToBoolean() {
    }
    
    @Override
    protected Boolean convertInterval(Interval i) throws CalculusException {
        LgcBooleanInterval lbi = LgcBooleanInterval.i2ib(i);
        if (lbi.equals(LgcBooleanInterval.TRUE)) return true;
        if (lbi.equals(LgcBooleanInterval.FALSE)) return false;
        Function.throwNewCalculusException ("It is expected a boolean value while it is received "+i.toString());
        return false;
    }
    
    @Override
    public String toString () {
        return "Requires boolean value";
    }
}
