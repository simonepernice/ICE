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

package engcalculator.function.embedded.power;

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutputMultipleDomain;
import engcalculator.interval.IntervalComplex;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PowSqrt extends FunctionPrefixSingleInputSingleOutputMultipleDomain {
	private final static String HELP = "... (a) computes the positive square root the interval a.\nIt is required interval a should not contain negative values. It functionality is extended to directed intervals. It works on complex point intervals.";
    private final static String[] RESULT = {"0_10,5_7"};
    private final static String[] EXAMPLE = {"0_100, 25_49"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public PowSqrt() {
        super ("power", ".sqrt", DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval input) throws CalculusException {
        return new MeasurementUnitAccumulator(input.getMeasurementUnit()).div(2).toMeasurementUnit();        
    }     

    @Override
    public Interval _computeIntervalPoint(Interval a) throws Exception {
        if (a.getValue() < 0) return _computeIntervalComplex(a);
        return new IntervalPoint (Math.sqrt(a.getValue()));
    }

    @Override
    public Interval _computeIntervalReal(Interval a) throws Exception {
        return new IntervalReal (Math.sqrt(a.getLeft()), Math.sqrt(a.getRight())).computeRoundings();
    }

    @Override
    public Interval _computeIntervalLiteral(Interval input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //It will never get here
    }

    @Override
    public Interval _computeIntervalComplex(Interval a) throws Exception {
        if (! a.isComplexPointInterval()) throwNewCalculusException("This function is not defined on complex intervals");
        Interval tmp;
        
        double aRe; 
        tmp = a.getRealPart() ;
        if (tmp != null) aRe = tmp.getValue();
        else aRe = 0;
                
        double aIm;
        //May be called onreal number because negative
        tmp = a.getImaginaryPart() ;
        if (tmp != null) aIm = tmp.getValue ();
        else aIm = 0; 
        
        double aAbs = Math.sqrt(aRe * aRe + aIm * aIm);
        
        return new IntervalComplex(new IntervalPoint (Math.sqrt((aAbs+aRe)/2)), new IntervalPoint (Math.sqrt((aAbs-aRe)/2)));
    }
}
