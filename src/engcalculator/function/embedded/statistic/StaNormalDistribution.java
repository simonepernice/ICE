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
import engcalculator.domain.DomainInterval;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriInvert;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriNegate;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.power.PowExp;
import engcalculator.function.embedded.power.PowSqr;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class StaNormalDistribution extends StaDistribution {
    private final static PowSqr SQUARE = new PowSqr();
    private final static AriDiv DIV = new AriDiv();
    private final static AriInvert INVERT = new AriInvert();
    private final static AriMul MUL = new AriMul();
    private final static AriSub SUB = new AriSub();
    private final static AriNegate NEGATE = new AriNegate();
    private final static PowExp EXP = new PowExp();
    private final static Interval TWO = new IntervalPoint (2);
    private final static Interval SQRT2PI = new IntervalPoint (Math.sqrt(2*Math.PI));
    
    private final static DomainList DOMAIN = new DomainList (new DomainInterval(), new DomainInterval(), new DomainInterval());
    
    private final static String[] EXAMPLE = {"($g, statisticRandom(-5_5), statisticRandom(1_2), $x) #= $statisticDistributionNormal; calculusIntegrate($g, -40_40)"};
    private final static String[] RESULT = {"1"};
    private final static String HELP = "... (average, sdeviation, x) computes the Gauss or Normal distribution defined as 1/(sdeviation*sqrt(2*PI))*exp(-sqr((x-average)/sdeviation/2)) where m is the average, d the standard deviation and x the evaluation point";
    
    public StaNormalDistribution() {
        super("Normal", (byte) 3, DOMAIN, HELP, EXAMPLE, RESULT);
    }
    
    @Override
    protected Interval _computeDistribution(ListIntervals i) throws Exception {
        Interval average = i.getFirst();
        Interval sdeviation = i.get(1);
        Interval x = i.get(2);
        
        return MUL._computeIntervals(INVERT._computeIntervals(MUL._computeIntervals(sdeviation, SQRT2PI)), EXP._computeIntervals(NEGATE._computeIntervals(DIV._computeIntervals(SQUARE._computeIntervals(DIV._computeIntervals(SUB._computeIntervals(x, average), sdeviation)), TWO))));
    }
    
}
