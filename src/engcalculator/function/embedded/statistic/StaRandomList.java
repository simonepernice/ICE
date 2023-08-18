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

import engcalculator.function.embedded.userDefined.*;
import engcalculator.domain.DomainListMatrix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class StaRandomList extends FunctionPrefix {
    private final static DomainListMatrix DOMAIN = new DomainListMatrix ();

    private final static String HELP = "... [probabilityDistribution] returns a list of random number following the given pdf.\nThe pdf does not require to be normalized (i.e. its integration should not be 1). The number of elements of the list is defined globally with randomgenerator function (by default it is "+StaRandomGenerator.getNUMELEMENTSFORSIMULATION()+"). The variables generated with this function can be used for Monte Carlo analysis.";
    private final static String[] EXAMPLES = {"pdf = [[0_1, 0.2],[1_2, 0.6], [2_3, 0.2]]; dist = statisticRandomList(pdf); diffuseCumulate('+', abs((statisticFrequencyBinNormalized(3, {dist})-pdf) :: (1_-1, 1))) < 0.1"};
    private final static String[] RESULTS = {"true"};       
    
    public StaRandomList () {
        super ("statistic", "RandomList", (byte) -2, DOMAIN, false, true, HELP, EXAMPLES, RESULTS);

    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {                            
        UDeFunctionPrefixByPoints.checkXFxProperty (input, true, true);

        UDeFunctionPrefixByPoints pdf = new UDeFunctionPrefixByPoints ("", "", input);
                
        final double ymax;
        double ymaxt = pdf.getFx(0).getValue();
        for (int i=1; i < pdf.getSize(); i ++) { 
            double y;
            if ((y = pdf.getFx(i).getValue()) > ymaxt) ymaxt = y;
        }
        ymax = ymaxt;
                
        ListIntervals result = new ListIntervals ();
        
        final int s = StaRandomGenerator.getNUMELEMENTSFORSIMULATION();
        for (int i=0; i<s; ++i) {
            double x, y;
            do {
                x = StaRandomGenerator.nextUniformRandom()*pdf.getXrange()+pdf.getXmin();
                y = pdf.getFx(x).getValue() / ymax;
            } while (StaRandomGenerator.nextUniformRandom() >= y);
            result.append (new IntervalPoint(x));
        }

        return result;
    }

}