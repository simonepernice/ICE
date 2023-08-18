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

package engcalculator.function.embedded.hyperbolic;

import engcalculator.function.CalculusException;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class HypArc extends FunctionPrefixSingleInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    private final double min, max;

    public HypArc(String name, double min, double max, String help, String[] examples, String[] results) {
        super ("hyperbolic", name, DOMAIN, true, true, help, examples, results);
        this.min = min;
        this.max = max;
    }

    @Override
    public final Interval _computeIntervals (Interval a) throws Exception {
        if (a.getLeft() < min || a.getRight() > max) throwNewCalculusException("The interval is out of range for "+getSymbol()+": ("+min+","+max+")");

        
        
        if (a.isIntervalPoint()) return new IntervalPoint(__compute(a.getValue()));
        return new IntervalReal (__compute(a.getLeft()), __compute(a.getRight())).computeRoundings();
        
    }

    protected abstract double __compute (double input) ;

}
