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

package engcalculator.function.embedded.logarithm;


import engcalculator.domain.DomainIntervalExact;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicNot;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutputMultipleDomain;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalComplex;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class LogLogarithm extends FunctionPrefixSingleInputSingleOutputMultipleDomain {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicNot(new DomainIntervalExact(0d)));

    public LogLogarithm(String name, String help, String[] examples, String[] results) {
        super ("logarithm", name, DOMAIN,true, true, help, examples, results);
    }
    
    @Override
    public Interval _computeIntervalPoint(Interval a) throws Exception {
        if (a.getValue() < 0) return _computeIntervalComplex(a);
        return new IntervalPoint (__compute(a.getValue()));
    }

    public Interval _computeIntervalReal(Interval a) throws Exception {
        if (a.getLeft() < 0 || a.getRight() < 0) return _computeIntervalComplex(a);
        return new IntervalReal ( __compute(a.getLeft()),  __compute(a.getRight()));
    }

    public Interval _computeIntervalLiteral(Interval input) throws Exception {
        return null; //not supported
    } 

    public Interval _computeIntervalComplex(Interval a) throws Exception {
        final double i = a.hasImaginaryPart() ? a.getImaginaryPart().getValue() : 0.;
        final double r = a.getRealPart().getValue();
        return new IntervalComplex(new IntervalPoint(__compute(Math.sqrt(r*r+i*i))),new IntervalPoint(Math.atan2(i, r)/Math.log(getBase())));
    }
        
    public abstract double __compute (double val) throws Exception;
    
    public abstract double getBase () throws Exception;

}
