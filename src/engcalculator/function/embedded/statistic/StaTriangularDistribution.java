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

package engcalculator.function.embedded.statistic;

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalProper;
import engcalculator.function.CalculusException;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class StaTriangularDistribution extends StaDistribution {
    
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalProper(), new DomainIntervalPoint(), new DomainIntervalPoint());
    
    private final static String[] EXAMPLE = {"($g, statisticRandom(-5_-1) _ statisticRandom(1_5), statisticRandom(-1_1), $x) #= $statisticDistributionTriangular; calculusIntegrate($g, -10_10)"};
    private final static String[] RESULT = {"1"};
    private final static String HELP = "... (interval, max, x) computes the triangular distribution defined as 1/range(i) if x is within the interval i, 0 otherwise.";
    
    public StaTriangularDistribution() {
        super("Triangular", (byte) 3, DOMAIN, HELP, EXAMPLE, RESULT);
    }
    
    @Override
    protected Interval _computeDistribution(ListIntervals i) throws Exception {
        double imin = i.getFirst().getLeft();
        double imax = i.getFirst().getRight();
        double max = i.get(1).getValue();
        double x = i.get(2).getValue();
        
        if (max < imin || max > imax) throwNewCalculusException("The given max "+max+" does not belongs to the input interval: "+imin+" , "+imax);
        
        if (x >= imin && x <= imax) {
            if (x <= max ) return new IntervalPoint(2/(imax-imin)*(x - imin)/(max - imin));
            return new IntervalPoint(2/(imax-imin)*(x - imax)/(max - imax));
        }
        return Interval.ZERO;
    }
    
    
}
