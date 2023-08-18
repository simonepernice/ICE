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

package engcalculator.domain;

import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DomainMeasurementUnit extends Domain {

    public DomainMeasurementUnit() {
        super("Measurement Unit");
    }

    @Override
    public boolean isCompatible(Interval i) {
        MeasurementUnit mu;
        try {
            mu = MeasurementUnit.parseMeasurementUnit(i.getName());
        } catch (Exception ex) {
            return false;
        } 
        return mu != null ;
    }

}
