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

package engcalculator.function.embedded.calculus;

import engcalculator.function.CalculusException;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CalFuncFindMatch extends CalFuncFind {

    private final static String HELP = "... ($f, 'compareFunc', npoints, int0, int1, .., intn ) split each input interval (int1 .. intn) in the given number of points (npoints) linearly spaced then compute f in each point and check if the test function applied at that value returns true (storing that x value).\nThis test is not able to find the exact x-point, but can be used as a starting point for optimization function.";
    private final static String[] EXAMPLE = {"; calculusFindMatch($sin, defineLambdaFunction('$x << -0.004_0.004'), 1001, -1_7) << (0, PI, 2*PI)+-0.05"};
    private final static String[] RESULT = {"true, true, true"};
    
    public CalFuncFindMatch() {
        super("Match", HELP, EXAMPLE, RESULT);
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        scanInput(input);
        
        if (! isPrefixFuncToTestDefined()) throwNewCalculusException ("The matching function should be prefix not infix");
        
        ListIntervals result = new ListIntervals();
        final ListIntervals zeroElements = new ListIntervals();
        
        do {
            if (compare(zeroElements)) result.addAll(getX());
        } while (nextX());
            
        return result;
    }

}
