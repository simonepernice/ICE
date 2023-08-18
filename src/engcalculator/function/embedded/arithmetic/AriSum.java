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

import engcalculator.domain.DomainInterval;
import engcalculator.function.infix.*;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.function.MeasurementUnitException;
import engcalculator.interval.IntervalComplex;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class AriSum extends FunctionInfixDualInputSingleOutputMultipleDomain {
    private final static DomainList DOMAIN = new DomainList (new DomainInterval());
    private final static String HELP = "a ... b  return a summed to b.\nIt adds the a and b intervals. It works on complex intervals. If the input are literals they are joined. However if more than two literals need to be join it is more efficen use literalJoin function.";
    private final static String[] EXAMPLE_LEFT = {"6_9","'hel'"};
    private final static String[] EXAMPLE_RIGHT = {"1_3","'lo'"};
    private final static String[] RESULT = {"7_12","'hello'"};

    public AriSum() {
        super ("arithmetic", "+", DOMAIN, DOMAIN, true, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public Interval _computeIntervalPoint(Interval a, Interval b) throws Exception {
        return new IntervalPoint (a.getValue() + b.getValue());
    }

    @Override
    public Interval _computeIntervalReal(Interval a, Interval b) throws Exception {
        return new IntervalReal (a.getLeft()+b.getLeft(), a.getRight()+b.getRight());
    }

    @Override
    public Interval _computeIntervalLiteral(Interval leftSide, Interval rightSide) throws Exception {
        return new IntervalLiteral (new StringBuilder(leftSide.getName()).append(rightSide.getName()).toString());        
    }

    @Override
    public Interval _computeIntervalComplex(Interval a, Interval b) throws Exception {
        Interval imp;
        if (a.getImaginaryPart() == null) imp = b.getImaginaryPart();
        else if (b.getImaginaryPart() == null) imp = a.getImaginaryPart();
        else imp = _computeIntervals(a.getImaginaryPart(), b.getImaginaryPart());
        
        return new IntervalComplex(_computeIntervals(a.getRealPart(), b.getRealPart()), imp);
    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval leftSide, Interval rightSide) throws MeasurementUnitException {
        MeasurementUnit mul = leftSide.getMeasurementUnit();
        if (! mul.equals(rightSide.getMeasurementUnit())) throwNewMeasurementUnitException("The measurement units of the agruments do not matchs: "+leftSide.toString()+" and "+rightSide.toString());
        return mul;
    }        
}
