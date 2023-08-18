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

package engcalculator.function.embedded.complex;

import engcalculator.domain.DomainIntervalComplex;
import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.function.embedded.trigonometry.TriArcTan;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.measurementUnit.MeasurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CpxArg extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... a returns the phase of complex interval a.\nIt compute the phase of a complex interval. It is extended to directed interval and need to be verified for not proper interval.";
    private final static String[] EXAMPLE = {"1+I","1-I","-1-I","-1+I"};
    private final static String[] RESULT = {"PI/4","-PI/4","-3*PI/4","3*PI/4"};

    private final static TriArcTan ARCTAN = new TriArcTan();
    private final static AriDiv DIV = new AriDiv();
    private final static AriSum SUM = new AriSum();

    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());

    public CpxArg() {
        super("complex", "Arg", DOMAIN, true, true, HELP, EXAMPLE , RESULT);
    }
    
    @Override
    public Interval _computeIntervals (Interval input) throws Exception {
        Interval im = input.getImaginaryPart(), re = input.getRealPart();

        if (im == null) im = Interval.ZERO;

        Interval angle = ARCTAN._computeIntervals( DIV._computeIntervals(im, re));

        if (re.isNegative()) {
            if (im.isPositive()) angle = SUM._computeIntervals(angle, new IntervalPoint(Math.PI));
            else angle = SUM._computeIntervals(angle, new IntervalPoint(-Math.PI));
        }

        return angle;

    }
    
    @Override
    public MeasurementUnit _computeMeasurementUnitIntervals (Interval input) throws Exception {
        return MeasurementUnit.PURE;        
    }    
}
