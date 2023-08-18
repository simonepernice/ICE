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

package engcalculator.function.embedded.diffuse;

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DifFeed extends FunctionPrefix {
    private final static String HELP = "... ({longExpression}, $f1, $f2, .., $fn) returns the list given by f1(longExpression), f2(longExpression), .., fn(longExpression).\nThis function is provided only for efficiency because if is it complex compute long expression (and it is not useful to store it in a variable) it can be feeded to other function without compute it again.";
    private final static String[] EXAMPLE = {"{0},$sin, $cos",";diffuseFeed({statisticRandomListAsDistribution($statisticDistributionUniform, 1_2, $x, 0_3)},$statisticAverage ,$statisticMeanAbsoluteDeviation) << (1.4_1.6, .24_.26)"};
    private final static String[] RESULT = {"0, 1","true, true"};
    private final static DomainList INTERVALDOMAIN = new DomainList (new DomainIntervalList(), new DomainFunctionPrefix(-1));

    public DifFeed() {
        super ("diffuse", "Feed", (byte) -2, INTERVALDOMAIN, false,true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals result = new ListIntervals();
        ListIntervals in = input.getFirst().getListIntervals();
        for (int i=1; i<input.size(); ++i) {
            result.addAll(FunctionPrefix.getFunction(input.get(i).getName()).compute(in));
        }
        return result;
    }

}
