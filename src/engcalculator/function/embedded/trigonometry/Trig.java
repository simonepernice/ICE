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


import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.function.embedded.setInterval.SInSuper;
import engcalculator.function.embedded.interval.ItvDual;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class Trig extends FunctionPrefixSingleInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());
    private final static SInSuper SUPERSET = new SInSuper();
    private final static ItvDual DUAL = new ItvDual();
    private double absoluteMin, absoluteMinPoint, absoluteMax, absoluteMaxPoint, range;

    public Trig(String name, String help, double absoluteMin, double absoluteMinPoint, double absoluteMax, double absoluteMaxPoint, double rangeMin, double rangeMax, String[] examples, String[] results) {
        super ("trigonometry", new StringBuffer (".").append(name).toString(), DOMAIN,true, true, help, examples, results);
        this.absoluteMin = absoluteMin;
        this.absoluteMinPoint = absoluteMinPoint;
        this.absoluteMax = absoluteMax;
        this.absoluteMaxPoint = absoluteMaxPoint;

        range = rangeMax-rangeMin;
    }

    @Override
    public final Interval _computeIntervals(Interval input) throws Exception {
        Interval result = null;

        if (input.isIntervalPoint()) result = new IntervalPoint(__compute(input.getValue()));
        else if (input.getRange() >= range) result = new IntervalReal(absoluteMin, absoluteMax);
        else {
            double displacedMinPoint = Math.floor(input.getLeft()/range)*range+absoluteMinPoint;
            double displacedMaxPoint = Math.floor(input.getRight()/range)*range+absoluteMaxPoint;

            double vmin, vmax, min, max;
            vmin = __compute(input.getLeft());
            vmax = __compute(input.getRight());

            max = Math.max(vmin, vmax);
            min = Math.min(vmin, vmax);

            if (SUPERSET.compareForTrue(input, new IntervalPoint(displacedMaxPoint))) max = absoluteMax;
            if (SUPERSET.compareForTrue(input, new IntervalPoint(displacedMinPoint))) min = absoluteMin;

            result = new IntervalReal (min, max).computeRoundings();
        }

        if (! input.isProper()) return DUAL._computeIntervals(result);
        else return result;
    }

    protected abstract double __compute (double input) ;

}
