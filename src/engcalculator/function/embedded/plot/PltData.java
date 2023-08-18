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

import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.domain.*;
import engcalculator.function.embedded.list.LisSerieInteger;
import engcalculator.function.embedded.list.LisBuilder;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PltData extends FunctionPrefix {
    private final static LisSerieInteger INTEGERSERIE = new LisSerieInteger();
    private final static LisBuilder LIST_BUILDER = new LisBuilder();

    private final static String HELP = "... (list) return the plottable matrix composed by the given list of data in Y axes, for X axes it uses a 0, 1, 2, .. .\nIt is a function to use when it is required to display some data (Y) and the X is not defined, like the result of a fft or same statistical data. It build a printable matrix to be displayed in plot windows. As X it is use integer number from 0 to size of the input list - 1.";
    private final static String[] EXAMPLE = {"5,25,255,325"};
    private final static String[] RESULT = {"(0,1,2,3)#1 , (5,25,255,325)#1"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    public PltData () {
        super("plot", "Data", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return LIST_BUILDER._compute(new ListIntervalsMatrix (INTEGERSERIE._compute(new ListIntervals (new IntervalPoint(0)), new ListIntervals(new IntervalPoint(input.size()-1))), 1), new ListIntervalsMatrix (input, 1));
    }
}
