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
package engcalculator.function.embedded.equation;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IperPlaneScanner {

    final int dimensions;
    final private int[] position;
    final private int[] max;
    final private ArrayList<ArrayList<Interval>> planeToScan;

    public IperPlaneScanner(ListIntervals iperPlane) {
        dimensions = iperPlane.size();

        position = new int[dimensions];
        max = new int[dimensions];

        planeToScan = new ArrayList<ArrayList<Interval>>(dimensions);

        {
            Iterator<Interval> it = iperPlane.iterator();
            for (int j = 0; j < dimensions; ++j) {
                Interval i = it.next();
                ArrayList<Interval> scanLine = new ArrayList<Interval>(i.isIntervalList() ? i.getListIntervals() : new ListIntervals(i));
                planeToScan.add(scanLine);
                position[j] = 0;
                max[j] = scanLine.size();
            }
        }
    }

    public void scanPlane(FunctionScanner f) throws Exception {

        ListIntervals x = new ListIntervals();

        boolean updatedX = true;

        f.scanBegin();

        while (updatedX) {
            x.clear();
            int j = 0;
            for (ArrayList<Interval> it : planeToScan) {
                x.append(it.get(position[j++]));
            }

            f.scanAt(x);

            updatedX = false;
            for (j = 0; j < dimensions; ++j) {
                ++position[j];
                if (position[j] >= max[j]) {
                    position[j] = 0;
                } else {
                    updatedX = true;
                    break;
                }
            }

        }

        f.scanEnd();
    }

}
