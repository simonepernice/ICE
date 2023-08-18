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


import engcalculator.function.embedded.list.LisSize;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.diffuse.DifSumUp;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaAverage extends FunctionPrefix {
    private final static DifSumUp SUM_UP = new DifSumUp ();
    private final static AriDiv DIV = new AriDiv();
    private final static LisSize SIZE = new LisSize();
	
    private final static String HELP = "... (list) returns average interval among the intervals content in the list.";
    private final static String[] EXAMPLE = {"1, 2, 3, 4"};
    private final static String[] RESULT = {"2.5"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public StaAverage() {
        super("statistic", "Average", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return DIV._compute(SUM_UP._compute(input), SIZE._compute(input));
    }
    
    public ListIntervals computeSubAverage (int n, ListIntervals input) throws Exception {
        return DIV._compute(SUM_UP._compute(input), new ListIntervals( new IntervalPoint (input.size() - n)));
    }
}
