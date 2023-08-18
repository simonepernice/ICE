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


import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.function.embedded.setInterval.SInSuper;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class Hyperbolic extends FunctionPrefixSingleInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());
    private final static SInSuper SUPERSET = new SInSuper();
    private final double absoluteMin;
    private final IntervalPoint absoluteMinPoint;
    private boolean hasMin = false;
    
    public Hyperbolic(String name, String help, double absoluteMin, double absoluteMinPoint, boolean hasMin, String[] examples, String[] results) {
        super ("hyperbolic", name, DOMAIN,true, true, help, examples, results);
        this.absoluteMin = absoluteMin;
        this.absoluteMinPoint = new IntervalPoint(absoluteMinPoint);
        this.hasMin = hasMin;               
    }

    @Override
    public final Interval _computeIntervals(Interval input) throws Exception {
        if (input.isIntervalPoint()) return new IntervalPoint(__compute(input.getValue()));
        if (hasMin && SUPERSET.compareForTrue(input, absoluteMinPoint)) return new IntervalReal(absoluteMin, Math.max(__compute(input.getLeft()), __compute(input.getRight())));
        return new IntervalReal (__compute(input.getLeft()), __compute(input.getRight()));         
    }

    protected abstract double __compute (double input) ;

}
