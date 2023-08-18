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

package engcalculator.function.infix;

import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionInfixDualInputSingleOutputMultipleDomain extends FunctionInfixDualInputSingleOutput {

    public FunctionInfixDualInputSingleOutputMultipleDomain(java.lang.String group, String name, DomainList domainLeft, DomainList domainRight, boolean expandsScalarInput, boolean locked, String help, String[] examplesLeft, String[] examplesRight, String[] results) {
        super(group, name, domainLeft, domainRight, expandsScalarInput, locked, help, examplesLeft, examplesRight, results);
    }

    public final Interval _computeIntervals (Interval leftSide, Interval rightSide) throws Exception {
        if (leftSide.isIntervalPoint() && rightSide.isIntervalPoint()) return _computeIntervalPoint (leftSide, rightSide);
        if (leftSide.isIntervalReal() && rightSide.isIntervalReal()) return _computeIntervalReal (leftSide, rightSide);
        if (leftSide.isIntervalComplex() && rightSide.isIntervalComplex()) return _computeIntervalComplex(leftSide, rightSide);
        if (leftSide.isIntervalLiteral() && rightSide.isIntervalLiteral()) return _computeIntervalLiteral (leftSide, rightSide);
        throwNewCalculusException ("It is not possible to apply any operation to the input due to its different domains: "+leftSide+" , "+rightSide);
        return null;//this will never be reached unless error check are disabled
    }    

    public abstract Interval _computeIntervalPoint(Interval leftSide, Interval rightSide) throws Exception;

    public abstract Interval _computeIntervalReal(Interval leftSide, Interval rightSidet) throws Exception;

    public abstract Interval _computeIntervalLiteral(Interval leftSide, Interval rightSide) throws Exception; 

    public abstract Interval _computeIntervalComplex(Interval leftSide, Interval rightSide) throws Exception;
        
}
