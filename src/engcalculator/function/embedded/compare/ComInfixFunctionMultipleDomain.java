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

package engcalculator.function.embedded.compare;


import engcalculator.domain.DomainList;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class ComInfixFunctionMultipleDomain extends  ComInfixFunction {
    public ComInfixFunctionMultipleDomain(String name, DomainList domain, String help, String[] examplesleft, String[] examplesright, String[] results) {
        super(name, domain, help, examplesleft, examplesright, results);
    }

    public final LgcBooleanInterval _compare (Interval leftSide, Interval rightSide) throws Exception {
        if (leftSide.isIntervalPoint() && rightSide.isIntervalPoint()) return _compareIntervalPoint (leftSide, rightSide);
        if (leftSide.isIntervalReal() && rightSide.isIntervalReal()) return _compareIntervalReal (leftSide, rightSide);
        if (leftSide.isIntervalLiteral() && rightSide.isIntervalLiteral()) return _compareIntervalLiteral (leftSide, rightSide);
        return _compareIntervalComplex (leftSide, rightSide);
    }

    public abstract LgcBooleanInterval _compareIntervalPoint(Interval leftSide, Interval rightSide) throws Exception;

    public abstract LgcBooleanInterval _compareIntervalReal(Interval leftSide, Interval rightSidet) throws Exception;

    public abstract LgcBooleanInterval _compareIntervalLiteral(Interval leftSide, Interval rightSide) throws Exception; 

    public abstract LgcBooleanInterval _compareIntervalComplex(Interval leftSide, Interval rightSide) throws Exception;
        

}
