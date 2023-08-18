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

package engcalculator.function.embedded.statistic;

import engcalculator.function.embedded.compare.ComSmaller;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervalsMatrix;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaMinMax extends FunctionPrefix {
    private final static ComSmaller SMALLER = new ComSmaller();
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    private final static String HELP = "... (list) returns a list containing the minimum and maximum interval found in the given list of intervals.\nIf ... receives a function data (2 column matrix) return the following matrix [[xmin, min], [xmax, max]].";
    private final static String[] EXAMPLE = {"1_2, 4_5, 6_7", "a = plotStandard($sin, 5, 0_2*PI); statisticMinMax (a)"};
    private final static String[] RESULT = {"1_2, 6_7","[[3*PI/2, -1],[PI/2, 1]]"};


    public StaMinMax() {
        super ("statistic", "MinMax", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        Interval ymin, ymax;
        Interval xmin = null, xmax = null;
        
        final int col;
        if (input.isMatrix()) {
            if (input.columnSize() != 2) throwNewCalculusException ("MinMax can accept only 2 column matrixes while found "+input.columnSize());
            if (input.size() < 2) throwNewCalculusException ("MinMax applied to a matrix requires at least 2 elements while found "+input.size());
            col = 2;
        } else col = 1;
        
        Iterator<Interval> it = input.iterator();
        if (col == 2) {
            xmin = xmax = it.next();
        } 
        ymin = ymax = it.next();

        while (it.hasNext()) {
            Interval x = null, y;
            if (col == 2) x = it.next();
            y = it.next();
            LgcBooleanInterval c = SMALLER._compare(y, ymin);
            if (c == LgcBooleanInterval.TRUE) {
                ymin = y;
                xmin = x;
            }
            else if (c == LgcBooleanInterval.TRUEFALSE) throwNewCalculusException("It is not possible to find a minimum or maximum value because some intervals: "+y+" , "+ymin+" are overlapped");
            c = SMALLER._compare(ymax, y);
            if (c == LgcBooleanInterval.TRUE) {
                ymax = y;
                xmax = x;
            }
            else if (c == LgcBooleanInterval.TRUEFALSE) throwNewCalculusException("It is not possible to find a minimum or maximum value because some intervals: "+y+" , "+ymax+" are overlapped");            
            
        }

        if (col == 1) return new ListIntervals (ymin).append(ymax);
        return new ListIntervalsMatrix (new ListIntervals(xmin).append(ymin).append(xmax).append(ymax), 2);
    }
}
