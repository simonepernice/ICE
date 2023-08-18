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
public final class DefLockClear extends DefLockManagement {
    private final static String HELP = "... ($functionName) clear the lock in the given function name (It can be prefix, infix or variable function).\nWhen a lock is cleared it is not possible to overwrite or delete the given function. By default all the ICE system function are locked. It is possible to remove  a lock by defineFunctionLockClear. Since ICE is compiletd wwith early binding, although a function is deleted or renamed, it is still available (its pointer actually) in all the previous function used to refer to it. Calling the function on ALLFUNCTIONSDEFINED all the registered functions (prefix, infix) and variable lock is cleared.";
    private final static String[] EXAMPLE = {"defineLockClear ($tan);($oldtan, $x) #= $tan; ($tan,x)='2+x';$result = (tan (0, PI/4), oldtan (0, PI/4));($tan, $x) #= $oldtan; defineLockSet($tan); result"};
    private final static String[] RESULT = {"2, 2+PI/4, 0, 1"};
    
    public DefLockClear() {
        super("LockClear", HELP, EXAMPLE, RESULT);
    }    

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        String res, fname = input.getName();
        if (fname.equals("ALLFUNCTIONSDEFINED")) res = setAllFunctionsLocks(false);
        else res = setLock(fname, false);
        if (res != null) return new IntervalLiteral(res);
        throwNewCalculusException ("Function "+input+" was not Found");
        return null;//this will never be reached unless error check are disabled
    }
}
