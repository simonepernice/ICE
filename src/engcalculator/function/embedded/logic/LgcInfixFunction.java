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

package engcalculator.function.embedded.logic;


import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalLogic;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfixDualInputSingleOutput;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class LgcInfixFunction extends FunctionInfixDualInputSingleOutput {
    private final static String HELP = " In ICE logic 0 is false, 1 is true, 0_1 may be true or false. The constants true, false and truefalse are defined.";
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLogic());
//    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public LgcInfixFunction(String name, String help, String[] examplesleft, String[] examplesright, String[] results) {
        super("logic", name, DOMAIN, DOMAIN, true, true, help+HELP, examplesleft, examplesright, results);
    }

    @Override
    public final Interval _computeIntervals(Interval leftSide, Interval rightSide) throws Exception {
        return LgcBooleanInterval.ib2i(compare (LgcBooleanInterval.i2ib(leftSide), LgcBooleanInterval.i2ib(rightSide)));
    }

    public abstract LgcBooleanInterval compare (LgcBooleanInterval a, LgcBooleanInterval b) throws Exception;

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        return MeasurementUnit.PURE;
    }

    
}
