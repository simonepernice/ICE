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

package engcalculator.function.embedded.trigonometry;

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
public abstract class TriArcTrig extends FunctionPrefixSingleInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    private double min, max;
    private boolean increasing;

    public TriArcTrig(String name, double min, double max,boolean increasing, String help, String[] examples, String[] results) {
        super ("trigonometry", new StringBuffer (".").append(name).toString(), DOMAIN, true, true, help, examples, results);
        this.min = min;
        this.max = max;
        this.increasing = increasing;
    }

    @Override
    public final Interval _computeIntervals (Interval a) throws Exception {
        if (a.getLeft() < min || a.getRight() > max) throwNewCalculusException("The interval is out of range for "+getSymbol()+": ("+min+","+max+")");

        if (a.isIntervalPoint()) return new IntervalPoint(__compute(a.getValue()));
        else if (increasing) return new IntervalReal (__compute(a.getLeft()), __compute(a.getRight())).computeRoundings();
        return new IntervalReal (__compute(a.getRight()), __compute(a.getLeft())).computeRoundings();
    }

    protected abstract double __compute (double input) ;

}
