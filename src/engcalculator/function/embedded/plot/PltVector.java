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
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PltVector extends FunctionPrefix {
    private final static String HELP = "... ($func, nXint, X_range, nYint, Y_range) returns a 4 column plottable matrix where func (of two arguments x and y and returning a list of 2 values xVector, yVector) is computed at every x, y point made by splitting X and Y ranges in nXint and nYint intervals.\nIt builds an array which can later be used to plot the vector field given by the input fuction. It requires the function name to compute its vector field (return fields components x,y), the number of points on x, the range of x, the number of points on y, the range of y.";
    private final static String[] EXAMPLE = {"defineLambdaFunction('-$y,$x'), 2, 2_4, 2, 2_4"};
    private final static String[] RESULT = {"(2,2,-2,2,2,4,-4,2,4,2,-2,4,4,4,-4,4)#4"};
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix (2), new DomainIntervalPoint(), new DomainIntervalReal (), new DomainIntervalPoint(), new DomainIntervalReal());

    public PltVector () {
        super("plot", "Vector", (byte) 5, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {

        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());

        final double xmin = input.get(2).getLeft();
        final double xmax = input.get(2).getRight();
        final double xdelta = (xmax-xmin)/(input.get(1).getValue()-1);

        final double ymin = input.get(4).getLeft();
        final double ymax = input.get(4).getRight();
        final double ydelta = (ymax-ymin)/(input.get(3).getValue()-1);

        ListIntervals ytmpil = new ListIntervals ();
        for (double yval=ymin; yval <= ymax; yval += ydelta ) {
            ytmpil.append(new IntervalPoint (yval));
        }

        ListIntervalsMatrix x = new ListIntervalsMatrix (1);
        ListIntervalsMatrix y = new ListIntervalsMatrix (1);
        Interval xtmp;

        for (double xval=xmin; xval <= xmax; xval += xdelta) {
            xtmp = new IntervalPoint (xval);
            for (Interval ytmp: ytmpil) {
                y.append(ytmp);
                x.append(xtmp);
            }
        }

        //it is not used the property of function to work on list in order to skip the 'bad' values
        ListIntervalsMatrix result = new ListIntervalsMatrix (4);
        ListIntervals data = new ListIntervals ();
        final int s = x.size();
        for (int i=0; i<s; ++i) {
            ListIntervals z=null;
            data.clear();
            data.add(x.get(i));
            data.add(y.get(i));
            try {               
                z = f._compute(data);                
                result.addAll(data);
                result.addAll(z);
            } catch (Exception ce) {
            }
            if (z == null || z.size() != 2) throwNewCalculusException ("It was expected a function with two return values, while was found "+f.getSymbol()+" returning "+z);
        }

        return result;
    }

}
