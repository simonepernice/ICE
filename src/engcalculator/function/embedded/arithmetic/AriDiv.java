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

package engcalculator.function.embedded.arithmetic;

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainIntervalExact;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.infix.*;
import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicNot;
import engcalculator.domain.DomainLogicOr;
import engcalculator.function.MeasurementUnitException;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class AriDiv extends FunctionInfixDualInputSingleOutput {
    private final static DomainList DOMAINL = new DomainList (new DomainIntervalComplex());
    private final static DomainList DOMAINR = new DomainList (new DomainLogicNot(new DomainLogicOr (new DomainIntervalLiteral(), new DomainIntervalExact(0d))));

    private final static AriInvert INVERT = new AriInvert ();
    private final static AriMul MUL = new AriMul();

    private final static String HELP = "a ... b divides a by b.\nThe b interval should not contain the 0.";
    private final static String[] EXAMPLE_LEFT = {"6_9"};
    private final static String[] EXAMPLE_RIGHT = {"1_3"};
    private final static String[] RESULT = {"2_9"};

    public AriDiv() {
        super ("arithmetic", "/", DOMAINL, DOMAINR, true, true, HELP, EXAMPLE_LEFT,  EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public Interval _computeIntervals(Interval left, Interval right) throws Exception {
        return MUL._computeIntervals(left, INVERT._computeIntervals(right));

    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());
        mua.sub(rightSide.getMeasurementUnit());
        return mua.toMeasurementUnit();
    }
}
