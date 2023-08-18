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
import engcalculator.domain.DomainIntervalProper;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.interval.ItvRange;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.function.embedded.setInterval.SInSub;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class StaUniformDistribution extends StaDistribution {
    private final static SInSub SUB_SET = new SInSub();
    private final static ItvRange RANGE = new ItvRange ();
    private final static AriDiv DIV = new AriDiv ();
    
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalProper(), new DomainIntervalProper());
    
    private final static String[] EXAMPLE = {"($g, statisticRandom(-5_0) _ statisticRandom(1_2), $x) #= $statisticDistributionUniform; abs(calculusIntegrate($g, -5_2)-1)<0.1"};
    private final static String[] RESULT = {"true"};
    private final static String HELP = "... (interval, x) computes the uniform distribution defined as 1/range(interval) if x belongs to interval, 0 otherwise.";
    
    public StaUniformDistribution() {
        super("Uniform", (byte) 2, DOMAIN, HELP, EXAMPLE, RESULT);
    }
    
    @Override
    protected Interval _computeDistribution(ListIntervals i) throws Exception {
        Interval interval = i.getFirst();
        Interval x = i.get(1);
        
        if (SUB_SET._compare(x, interval) == LgcBooleanInterval.TRUE) return DIV._computeIntervals(Interval.ONE, RANGE._computeIntervals(interval));
        return Interval.ZERO;
    }
    
    
}
