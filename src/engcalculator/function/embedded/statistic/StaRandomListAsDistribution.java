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
import engcalculator.function.embedded.define.DefFunctionSubArgument;
import engcalculator.function.embedded.define.DefLambdaGetName;
import engcalculator.function.embedded.plot.PltStandardAdaptive;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class StaRandomListAsDistribution extends FunctionPrefix {
    private final static StaRandomList RANDOM_LIST = new StaRandomList();
    private final static PltStandardAdaptive STANDARD_PLOT = new PltStandardAdaptive();
    private final static DefFunctionSubArgument FSAD = new DefFunctionSubArgument();    
    
    private final static DomainList DOMAIN = new DomainList ();
    private final static String HELP = "... ($funcName, param1, param2, $x, .., interval) is just a short cut for the following espression statisticRandomList(plotStandardAdaptive((defineLambdaGetName, param1, param2, $x, ..)#=$funcName, interval)).\nIt can be used to create a statistical variable with distribution like $funcName. The variable can be used for MonteCarlo analysis. Please note the function $temporaryFunction remains defined after the function ran, and if it was already defined, its definition is replaced.";
    private final static String[] EXAMPLES = {"a = statisticRandomListAsDistribution($statisticDistributionUniform, 0_1, $x, 0_1); statisticStandardDeviation(statisticFrequencyBinNormalized(100, {a}) :: (0_-1, 1)) < 0.2, abs(statisticAverage(a)-intervalValue(0_1))<10m, abs(statisticStandardDeviation(a)-intervalRange(0_1)/sqrt(12))<10m"};
    private final static String[] RESULTS = {"true, true, true"};       
    
    public StaRandomListAsDistribution () {
        super ("statistic", "RandomListAsDistribution", (byte) -3, DOMAIN, false, true, HELP, EXAMPLES, RESULTS);
    }    
    
    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception { 
        //statisticRandomList(plotStandard(($tmp, param1, param2, .., $x)#=$funcName, numbPoints, interval))
        ListIntervals tmpFunc = DefLambdaGetName.getNextLambdaFunctionName();
        ListIntervals fsadArg = new ListIntervals ();
        fsadArg.addAll(tmpFunc);
        fsadArg.addAll(input.subList(1, input.size()-1));
        FSAD._compute(fsadArg, input.subList(0, 1));
        ListIntervals splotArg = new ListIntervals (); 
        splotArg.addAll(tmpFunc);
        splotArg.addAll(input.subList(input.size()-1, input.size()));
        return RANDOM_LIST._compute(STANDARD_PLOT._compute(splotArg));
    }

}