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
public final class PowSqr extends FunctionPrefixSingleInputSingleOutputMultipleDomain {
	private final static String HELP = "... (a) computes the square the interval a.\nIt is extended to directed intervals but needs to be verified in that case. It works on complex point intervals.";
    private final static String[] EXAMPLE = {"-10_2, -10_-2, 5_7, -7_-5"};
    private final static String[] RESULT = {"0_100, 4_100, 25_49, 25_49"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public PowSqr() {
        super ("power", ".sqr", DOMAIN,true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval input) throws CalculusException {
        return new MeasurementUnitAccumulator(input.getMeasurementUnit()).mul(2).toMeasurementUnit();        
    }  

    @Override
    public Interval _computeIntervalPoint(Interval i) throws Exception {
        return new IntervalPoint(i.getValue()*i.getValue());
    }

    @Override
    public Interval _computeIntervalReal(Interval i) throws Exception {
        if (i.isPositive()) return  new IntervalReal (Math.pow(i.getLeft(),2), Math.pow(i.getRight(),2)).computeRoundings();
        if (i.isNegative()) return  new IntervalReal (Math.pow(i.getRight(),2), Math.pow(i.getLeft(),2)).computeRoundings();
        if (i.isProper()) return  new IntervalReal (0, Math.max(Math.pow(i.getLeft(), 2), Math.pow(i.getRight(), 2))).computeRoundings();
        return  new IntervalReal (Math.max(Math.pow(i.getLeft(), 2), Math.pow(i.getRight(), 2)), 0).computeRoundings();
    }

    @Override
    public Interval _computeIntervalLiteral(Interval input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interval _computeIntervalComplex(Interval i) throws Exception {
        if (! i.isComplexPointInterval()) throwNewCalculusException("This function is not defined on complex intervals");
        Interval tmp;
        
        double aRe; 
        tmp = i.getRealPart();
        if (tmp != null) aRe = tmp.getValue();
        else aRe = 0;
                
        double aIm;
        //May be called onreal number because negative
        tmp = i.getImaginaryPart();
        if (tmp != null) aIm = tmp.getValue ();
        else aIm = 0; 
        
        return new IntervalComplex(new IntervalPoint (aRe*aRe-aIm*aIm), new IntervalPoint (aRe*aIm));
    }
}
