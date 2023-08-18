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

package engcalculator.function.embedded.interval;

import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ItvModule extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (A) computes the module of the interval A.\nIt is a real value. The interval module is defined as the maximum of the absolute value of the extremes.";
    private final static String[] EXAMPLE = {"-10_10", "-10_-5", "10_15", "10_-10", "-5_-10", "15_10"};
    private final static String[] RESULT = {"10", "10", "15", "10", "10", "15"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    public ItvModule() {
        super("interval", "Module", DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public Interval _computeIntervals (Interval input) throws Exception {
        return new IntervalPoint (Math.max(Math.abs(input.getLeft()), Math.abs(input.getRight()))).computeRoundings();
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return input.getMeasurementUnit();
    }      
    
}
