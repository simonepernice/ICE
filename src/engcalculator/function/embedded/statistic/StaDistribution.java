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

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class StaDistribution extends FunctionPrefix {
    private final static String DISTRIBUTIONHELP = "\nThe distribution can be used together with statistiRandomList to generate random variable to be used in MonteCarlo simulation. For example to generate a Normal statistic variable a with average 1 and standard deviation 2 it is possible to write: a = statisticRandomList(plotStandard(($tmp,1,2,$x)#=$statisticDistributionNormal, 100, -9_11)). The variable a contains 8000 random valued with given probability distribution. The variable a can be used in further calculations like it was a single value. To analyze the statistical result of the calculation it is possible to use the statistic functions like statisticAverage or statisticStandardDeviation. The resul statistic distribution can be captured by statisticFrequencyBinNormalized to be used as a new distribution probability to generate new statistic variables.";

    public StaDistribution(String name, byte argNumb, DomainList domain, String help, String[] EXAMPLE, String[] RESULT) {
        super("statistic", "Distribution"+name, argNumb, domain, false, true, help+DISTRIBUTIONHELP, EXAMPLE , RESULT);
    }
    
    protected abstract Interval _computeDistribution (ListIntervals x) throws Exception;

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals ( _computeDistribution (input));
    }

}
