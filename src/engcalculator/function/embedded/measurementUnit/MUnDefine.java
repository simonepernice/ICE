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

package engcalculator.function.embedded.measurementUnit;

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MUnDefine extends FunctionPrefix {    
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral(), new DomainIntervalLiteral(), new DomainIntervalLiteral());
    
    private final static String HELP = "... (symbolName, description, definition) create a new measurement unit with given symbolName for definition.\nThe symbolName can be the void literal '' if the new unit does not have a name like the speed m/s. The description is used for help and is optional. The definition requires any combination of previous defined units and exponent (positive or negative). It also possible to use the division symbol / : all the terms after division have the exponent negated. It is possible to use brackets. The space stands for times and it is required to separate the different units. A measurement unit can be defined several times as far as they have different symbols like rad, srad and pure have the same meaning. When ICE has to print an unit with multiple synonims it will use the last used as input or defined (if it is never used as input).";
    private final static String[] EXAMPLE = {"measurementUnitDefine ('speed', 'the velocity', 'm/s'); 6 @'m' / 3 @'s'"};
    private final static String[] RESULT = {"2 @ 'speed'"};

    public MUnDefine() {
        super ("measurementUnit", "Define", (byte) 3, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        Iterator<Interval> i = input.iterator();
        if (! MeasurementUnit.addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(i.next().getName(), i.next().getName(), i.next().getName(), false))) throwNewCalculusException ("It was not possible to add the new measurement unit because the symbol was already present.");
        return input.subList(0, 1);
    }
}
