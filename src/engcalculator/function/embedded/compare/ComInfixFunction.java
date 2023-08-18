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

package engcalculator.function.embedded.compare;


import engcalculator.domain.DomainIntervalProper;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.function.infix.FunctionInfixDualInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class ComInfixFunction extends FunctionInfixDualInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd(new DomainIntervalReal(), new DomainIntervalProper()));

    public ComInfixFunction(String group, String name, String help, String[] examplesleft, String[] examplesright, String[] results) {
        super(group, name, DOMAIN, DOMAIN, true, true, help+" It returns 0 if the preposition is not verified for every point of the first interval, 1 for it is always verified, 0_1 if it is verified only for some points.", examplesleft, examplesright, results);
    }
    
    public ComInfixFunction(String group, String name, DomainList domain, String help, String[] examplesleft, String[] examplesright, String[] results) {
        super(group, name, domain, domain, true, true, help+" It returns 0 if the preposition is not verified for every point of the first interval, 1 for it is always verified, 0_1 if it is verified only for some points.", examplesleft, examplesright, results);
    }
    
    public ComInfixFunction(String name, String help, String[] examplesleft, String[] examplesright, String[] results) {
        super("compare", name, DOMAIN, DOMAIN, true, true, help+" It returns 0 if the preposition is not verified for every point of the first interval, 1 for it is always verified, 0_1 if it is verified only for some points.", examplesleft, examplesright, results);
    }

    public ComInfixFunction(String name, DomainList domain, String help, String[] examplesleft, String[] examplesright, String[] results) {
        super("compare", name, domain, domain, true, true, help, examplesleft, examplesright, results);
    }

    @Override
    public final Interval _computeIntervals (Interval leftSide, Interval rightSide) throws Exception {
        return LgcBooleanInterval.ib2i (_compare (leftSide, rightSide));
    }

    public abstract LgcBooleanInterval _compare (Interval a, Interval b) throws Exception;

    public boolean compareForTrue (Interval a, Interval b) throws Exception {
        return LgcBooleanInterval.TRUE == _compare (a,b);
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        if (! leftSide.getMeasurementUnit().equals(rightSide.getMeasurementUnit())) throwNewMeasurementUnitException("The measurement units of the agruments do not matchs: "+leftSide.toString()+" and "+rightSide.toString());
        return MeasurementUnit.PURE;
    }
       
}
