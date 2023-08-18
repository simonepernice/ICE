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

package engcalculator.function.embedded.integer;

import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IntRound extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (a) returns the closer integer to the argument a.\nSee also ceiling and floor.";
    private final static String[] EXAMPLE = {"-10.7_10.7","10.1_-5.8", "10.99"};
    private final static String[] RESULT = {"-11_11", "10_-6", "11"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    public IntRound() {
        super("integer", "Round", DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public Interval _computeIntervals (Interval input) throws Exception {
        if (input.isIntervalPoint()) {//faster shortcut for point intervals
            double a1 = input.getValue();
            return new IntervalPoint(Math.round(a1));            
        }
        double a1 = input.getLeft(), a2 = input.getRight();
        return new IntervalReal(Math.round(a1), Math.round(a2));
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return input.getMeasurementUnit(); 
    }     
}
