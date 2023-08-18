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

import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainList;
import engcalculator.interval.Interval;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.measurementUnit.MeasurementUnit;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class ItvIs extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (A) returns true if the interval is ";
    private final static DomainList DOMAIN = new DomainList (new DomainInterval());

    public ItvIs(String name, String[] Example, String[] Result) {
        super("interval", "Is"+name, DOMAIN, true, true, HELP+(name.toLowerCase()), Example , Result);
    }
    
    @Override
    public final Interval _computeIntervals (Interval a) throws Exception {
        if (_computeIntervalsIs (a)) return Interval.ONE;
        return Interval.ZERO;
    }
    
    public abstract boolean _computeIntervalsIs (Interval a);
    
    @Override
    public final MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return MeasurementUnit.PURE;
    }      

}
