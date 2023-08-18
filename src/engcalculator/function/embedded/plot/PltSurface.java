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

package engcalculator.function.embedded.plot;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PltSurface extends FunctionPrefix {
    private final static String HELP = "... ($func, nXint, X_range, nYint, Y_range) returns a 3 column plottable matrix where func (of two arguments x and y) is computed at every x, y square made by splitting X and Y ranges in nXint and nYint intervals.\nIt builds an array which can later be used to plot the height of a function with a color graph. It requires the function name to compute its height (z), the number of squares on x, the range of x, the number of square on y, the range of y.";
    private final static String[] EXAMPLE = {"sp=plotSurface(($f,x,y)='x+y', 2, 2_4, 2, 2_4);sp"};
    private final static String[] RESULT = {"((1_3,1_3,3_5,3_5)#1 , (1_3,3_5,1_3,3_5)#1 , (2_6,4_8,4_8,6_10)#1)"};
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix (2), new DomainIntervalPoint(), new DomainIntervalReal (), new DomainIntervalPoint(), new DomainIntervalReal());

    public PltSurface () {
        super("plot", "Surface", (byte) 5, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {

        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());

        final double xmin = input.get(2).getLeft();
        final double xmax = input.get(2).getRight();
        final double xdelta = (xmax-xmin)/2/(input.get(1).getValue()-1);

        final double ymin = input.get(4).getLeft();
        final double ymax = input.get(4).getRight();
        final double ydelta = (ymax-ymin)/2/(input.get(3).getValue()-1);

        ListIntervals ytmpil = new ListIntervals ();
        for (double yval=ymin; yval <= ymax; yval += ydelta + ydelta) {
            ytmpil.append(new IntervalReal (yval-ydelta,yval+ydelta));
        }

        ListIntervalsMatrix x = new ListIntervalsMatrix (1);
        ListIntervalsMatrix y = new ListIntervalsMatrix (1);
        Interval xtmp;

        for (double xval=xmin; xval <= xmax; xval += xdelta + xdelta) {
            xtmp = new IntervalReal (xval-xdelta,xval+xdelta);
            for (Interval ytmp: ytmpil) {
                y.append(ytmp);
                x.append(xtmp);
            }
        }

        //it is not used the property of function to work on list in order to skip the 'bad' values
        ListIntervalsMatrix result = new ListIntervalsMatrix (3);
        ListIntervals data = new ListIntervals ();
        final int s = x.size();
        for (int i=0; i<s; ++i) {
            Interval z;
            data.clear();
            data.add(x.get(i));
            data.add(y.get(i));
            try {               
                z = f._compute(data).getFirst();
                result.addAll(data);
                result.add(z);
            } catch (Exception ce) {
            }
        }

        return result;
    }

}
