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

package engcalculator.function.embedded.engineer;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefixExpandLast;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngSPTau extends FunctionPrefixExpandLast {
    
    private final static String HELP = "... (vbegin_vend, v1_v2) compute the taus required by the single pole system running from vbegin to vend to go from v1 to v2.\nIf the result is negative it means the system before goes through v2 and then to v1.";
    private final static String[] RESULTS = {"2.197", "-2.197"};
    private final static String[] EXAMPLES = {"0_1, 0.1_0.9","0_1, 0.9_0.1"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal(), new DomainIntervalReal());

    public EngSPTau() {
        super ("engineer", "SinglePoleTauS", (byte) 2, DOMAIN, true, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        final double min = input.getFirst().isIntervalPoint() ? 0 : input.getFirst().getLeft();
        final double max = input.getFirst().getRight();
        final double begin = input.get(1).getLeft();
        final double end = input.get(1).getRight();
        return computeTaus (min, max, begin, end);
    }

    public ListIntervals computeTaus(double begin, double end, double y1, double y2) throws Exception {
        if (begin <= end) {
            if (! (within(y1, begin, end) &&  within(y2, begin, end)) ) throwNewCalculusException ("Some of the input values "+y1+" or "+y2+" are not inside the function range: "+begin+" , "+end);
        } else {
            if (! (within(y1, end, begin) &&  within(y2, end, begin)) ) throwNewCalculusException ("Some of the input values "+y1+" or "+y2+" are not inside the function range: "+begin+" , "+end);
        } 
        return new ListIntervals ( new IntervalPoint ( Math.log((y1-end)/(y2-end))));
    }

    private boolean within (double x, double min, double max) {
        return x >= min && x <= max;
    }
}

