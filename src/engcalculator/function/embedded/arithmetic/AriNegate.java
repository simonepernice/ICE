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

import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutputMultipleDomain;
import engcalculator.interval.IntervalComplex;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class AriNegate extends FunctionPrefixSingleInputSingleOutputMultipleDomain {
    private final static String HELP = "... a returns the opposite value to a: -a.\nIt negates the a interval or list of intervals. Please note the precedence for prefix function (like ...) is higher than infix function therefore -2^2 is computed like (-2)^2, which gives 4.";
    private final static String[] EXAMPLE = {"(1%4)",";-2^2",";0-2^2"};
    private final static String[] RESULT = {"-1%4","4","-4"};

    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public AriNegate() {
        super ("arithmetic", ".-", DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals(Interval input) throws Exception {
        return input.getMeasurementUnit();
    }
       
    @Override
    public Interval _computeIntervalPoint(Interval input) throws Exception {
        return new IntervalPoint (- input.getValue());
    }

    @Override
    public Interval _computeIntervalReal(Interval input) throws Exception {
        return new IntervalReal(-input.getRight(), -input.getLeft()).computeRoundings();
    }

    @Override
    public Interval _computeIntervalLiteral(Interval input) throws Exception {
        throw new RuntimeException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interval _computeIntervalComplex(Interval input) throws Exception {
        return new IntervalComplex (_computeIntervals(input.getRealPart()),  _computeIntervals(input.getImaginaryPart()));
    }

}
