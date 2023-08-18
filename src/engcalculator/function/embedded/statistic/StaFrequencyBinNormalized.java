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

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaFrequencyBinNormalized extends StaFrequencyBin {
    private final static String HELP = "... (nOfBin, {list}) computes the smallest interval which contains all the list values, then it split that interval in the given nOfBins, then it counts the values of the list falling in each bin, eventually normalized the result in order to get total area equal to 1.\nThe output is a matrix form which can be used for plot window. ... requires: the number of bins and then the {sub-list} to analyze. Instead of the number of bins it is possible to provide the list of intervals that made up the bins, the only requirement it that they are monotonically increasing. In the last condition since there can be void between bins or the bins may fall outside the values the integrale may not get all the values.";
    private final static String[] EXAMPLE = {"2, {1,1,1,1,2.5,2.5,3}", "{1_2, 2_3}, {1,1,1,1,2.5,2.5,3}"};
    private final static String[] RESULT = {"[[1_2, 4/7],[2_3, 3/7]]", "[[1_2, 4/7],[2_3, 3/7]]"};

    public StaFrequencyBinNormalized() {
        super("FrequencyBinNormalized", HELP, EXAMPLE , RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return computeFrequencyBin(input, false);
    }
  
}
