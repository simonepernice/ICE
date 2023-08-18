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

import engcalculator.interval.Interval;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.arithmetic.AriInvert;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngPartition extends FunctionPrefix {
    private final static AriDiv DIV = new AriDiv();
    private final static AriSum SUM = new AriSum();
    private final static AriInvert INVERT = new AriInvert();

    private final static String HELP = "... (Rd, Ru) computes the voltage across Rd (down) in the partitor Ru (up) and Rd.\nIt computes the partition ratio among the first given value and the sum of the two values: partition (a, b) = 1/(1+b/a).";    
    private final static String[] EXAMPLE = {"1, 2+ 3+ 4"};
    private final static String[] RESULT = {"0.1"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex(), new DomainIntervalComplex());

    public EngPartition() {
        super ("engineer", "Partition", (byte) 2, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals (INVERT._computeIntervals(SUM._computeIntervals(Interval.ONE, DIV._computeIntervals(input.get(1), input.get(0)))));
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        input.getMeasurementUnit(); //It is required to rise an exception if input is made by different measurement units
        LinkedList<MeasurementUnit>  result = new LinkedList<MeasurementUnit> ();
        result.add(MeasurementUnit.PURE);
        return result;
    }

    
}
