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
public final class CalFuncFindAbsolute extends CalFuncFind {

    private final static String HELP = "... ($f, 'compareFunc', npoints, int0, int1, .., intn ) split each input interval (int1 .. intn) in the given number of points (npoints) linearly spaced then compute f in each point and returns the xi value where f(xi) 'compareFunction' xj (i != j) is always true.\nThis test is not able to find the exact x-point, but can be used as a starting point for optimization function.";
    private final static String[] EXAMPLE = {"$sin, '>', 101, 0_PI", "$cos, '>', 100, 0_PI","defineLambdaFunction('(10-($x-1)^2)+(10-($y-2)^2)'),'>',21,-10_10,-10_10", "$sin, '<', 101, 0_2*PI", "$cos, '<', 100, 0_PI","defineLambdaFunction('(($x-1)^2)+(($y-2)^2)'), '<',21,-10_10,-10_10"};
    private final static String[] RESULT = {"PI/2","0","1,2", "PI+PI/2","PI","1,2"};
    
    public CalFuncFindAbsolute() {
        super("Absolute", HELP, EXAMPLE, RESULT);
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        scanInput(input);
        
        ListIntervals xMax, yMax;        
        xMax = getX();
        yMax = getY();
        
        while (nextX()) {
            if (compare(yMax)) {
                yMax = getY();
                xMax = getX();
            }
        }
        
        return xMax;
    }

}
