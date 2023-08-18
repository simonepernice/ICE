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

import engcalculator.domain.DomainIntervalExact;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicNot;
import engcalculator.domain.DomainLogicOr;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutputMultipleDomain;
import engcalculator.interval.IntervalComplex;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class AriInvert extends FunctionPrefixSingleInputSingleOutputMultipleDomain {
    private final static String HELP = "... a return the a inverted interval (1/a).\nIt is faster than executing (1/a) and works on complex intervals. a should not contain 0.";
    private final static String[] EXAMPLE = {"2, 4"};
    private final static String[] RESULT = {"0.5, 0.25"};
    private final static DomainList DOMAIN = new DomainList (new DomainLogicNot(new DomainLogicOr (new DomainIntervalLiteral(), new DomainIntervalExact(0d))));

    private final static AriNegate NEGATE = new AriNegate ();
    private final static AriSum SUM = new AriSum ();
    private final static AriDiv DIV = new AriDiv ();
    private final static AriMul MUL = new AriMul ();
    
    public AriInvert() {
        super ("arithmetic", ".invert", DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public Interval _computeIntervalPoint(Interval input) throws Exception {
        return new IntervalPoint (1/input.getValue());
    }

    @Override
    public Interval _computeIntervalReal(Interval input) throws Exception {
        return  new IntervalReal(1/input.getRight(), 1/input.getLeft()).computeRoundings();
    }

    @Override
    public Interval _computeIntervalLiteral(Interval input) throws Exception {
        throw new RuntimeException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interval _computeIntervalComplex(Interval input) throws Exception {
        Interval imPart = input.getImaginaryPart();
        Interval rePart = input.getRealPart();
        Interval denominator = SUM._computeIntervals(MUL._computeIntervals(rePart, rePart), MUL._computeIntervals(imPart, imPart));
        return new IntervalComplex (DIV._computeIntervals(rePart, denominator),NEGATE._computeIntervals(DIV._computeIntervals(imPart, denominator)));
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval input) throws Exception {
        return new MeasurementUnitAccumulator(MeasurementUnit.PURE).sub(input.getMeasurementUnit()).toMeasurementUnit();
    }


}
