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

import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CalFuncFindRelative extends CalFuncFind {

    private final static String HELP = "... ($f, 'compareFunc', npoints, int0, int1, .., intn ) split each input interval (int1 .. intn) in the given number of points (npoints) linearly spaced then compute f in each point and returns the xi values where f(xi) 'compareFunction' xj for all the xj points around xi is true.\nThat comparison is not made at the extremes of the interval. This test is not able to find the exact x-point, but can be used as a starting point for optimization function.";
    private final static String[] EXAMPLE = {"defineLambdaFunction('($x-1)*($x-2)*($x-3)'), '>', 10001, 0_4", "defineLambdaFunction('($x-1)*($x-2)*($x-3)'), '<', 10001, 0_4"};
    private final static String[] RESULT = {"($f1, $x)='(x-2)*(x-3)+(x-1)*(x-3)+(x-1)*(x-2)';equationFindRoot($f1, 1_2, 0)", "($f1, $x)='(x-2)*(x-3)+(x-1)*(x-3)+(x-1)*(x-2)';equationFindRoot($f1, 2_3, 0)"};
    
    public CalFuncFindRelative() {
        super("Relative", HELP, EXAMPLE, RESULT);
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        scanInput(input);
        
        ListIntervals result = new ListIntervals();
        
        do {
            if (compare(getY(getCloserXs()))) result.addAll(getX());
        } while (nextX());
            
        return result;
    }

}
