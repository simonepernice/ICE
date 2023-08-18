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

package engcalculator.function.embedded.integer;


import engcalculator.domain.DomainIntegerLong;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.infix.FunctionInfixDualInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class IntInfixFunction extends FunctionInfixDualInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntegerLong()));

    public IntInfixFunction(String name, String help, String[] examplesleft, String[] examplesright, String[] results) {
        super("integer", name, DOMAIN, DOMAIN, true, true, help, examplesleft, examplesright, results);
    }

    @Override
    public final Interval _computeIntervals(Interval leftSide, Interval rightSide) throws Exception {
        return new IntervalPoint (computeIntegerOperation ((long) leftSide.getValue(), (long) rightSide.getValue()));
    }

    public abstract long computeIntegerOperation (long a, long b) throws Exception;

}
