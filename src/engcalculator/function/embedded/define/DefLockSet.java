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

import engcalculator.function.CalculusException;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefLockSet extends DefLockManagement {      
    private final static String HELP = "... ($functionName) set the lock in the given symbo.\nThe symbol may be a prefix, infix, variable function or a measurement unit.\nWhen a lock is set it is not possible to overwrite or delete the given function. By the defaultIf all the system functions are locked. It is possible to remove  a lock by defineFunctionLockClear. Since ICE is compiletd, although a function is deleted or renamed, it is still available in all the previous function used to refer to it. For examples look at LockClear. Calling the function on ALLFUNCTIONSDEFINED all the registered functions (prefix, infix and measurement unit) and variable lock is set.";
    
    public DefLockSet() {
        super("LockSet", HELP, null, null);
    }    

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        String res, fname = input.getName();
        if (fname.equals("ALLFUNCTIONSDEFINED")) res = setAllFunctionsLocks(true);
        else res = setLock(fname, true);
        if (res != null) return new IntervalLiteral(res);
        throwNewCalculusException ("Function "+input+" was not Found");
        return null;//this will never be reached unless error check are disabled
    }
}
