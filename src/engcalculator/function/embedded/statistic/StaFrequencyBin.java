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

import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.domain.DomainLogicOr;
import engcalculator.function.embedded.diffuse.DifSpread;
import engcalculator.function.embedded.list.LisLinear;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class StaFrequencyBin extends FunctionPrefix {
    private final static LisLinear LINLIST = new LisLinear ();
    private final static DifSpread SPREAD = new DifSpread ();
    private final static StaMinMax MINMAX = new StaMinMax ();
    private final static DomainList DOMAIN = new DomainList (new DomainLogicOr(new DomainIntervalList(new DomainIntervalReal()), new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntervalPositive())), new DomainIntervalList(new DomainIntervalReal()));

    public StaFrequencyBin(String name, String help, String[] EXAMPLE, String[] RESULT) {
        super("statistic", name, (byte) 2, DOMAIN, false, true, help, EXAMPLE , RESULT);
    }
    
    public static ListIntervals computeFrequencyBin (ListIntervals input, boolean returnFrequency) throws Exception {
        final ListIntervals data = input.getLast().getListIntervals();                
        ListIntervals minmax = MINMAX._compute(data);
        
        final int nOfBins;
        
        Interval[] bins;
        if (input.getFirst().isIntervalList()) {
            ListIntervals binIntervals = input.getFirst().getListIntervals();
            nOfBins = binIntervals.size();
            bins = new Interval[nOfBins];
            Interval prev = null;
            int j=0;
            for (Interval i : binIntervals) {
                bins[j++] = i;
                if (prev != null && prev.getRight() != i.getLeft()) throwNewCalculusException("The list given for the bins should be monotonically increasing with the extremes in common, while: "+prev+" and "+i+" are not");
                prev = i;
            }
        } else {
            nOfBins = (int) input.getFirst().getValue();
         
            ListIntervals binIntervals;
            binIntervals = new ListIntervals (new IntervalLiteral("_"));
            binIntervals.addAll(LINLIST._compute(new ListIntervals(new IntervalPoint(nOfBins+1)).append(new IntervalReal (minmax.getFirst().getLeft(), minmax.getLast().getRight()))));
            binIntervals = SPREAD._compute(binIntervals);        
            bins = binIntervals.toArray(new Interval[0]);
        }
        final int nOfBinsM1 = nOfBins-1;
        
        final int dataSize = data.size();
        int[] binFrequency = new int [nOfBins];
        Iterator<Interval> iData = data.iterator();
        
        final double min = minmax.getFirst().getLeft()-bins[0].getRange()/2;
        final double binSize = minmax.getLast().getRight() - minmax.getFirst().getLeft();        

        while (iData.hasNext()) {
            Interval dataGetPoint = iData.next();
            int position = (int) ((dataGetPoint.getValue()-min)*(nOfBinsM1)/binSize);

            //If both while are executed the given interval does not fit properly in neither of the two adiacent bins, a new alorithm should be implemented in that case
            while (position > 0 && dataGetPoint.getLeft() < bins[position].getLeft()) -- position;
            while (position < nOfBins-1 && dataGetPoint.getRight() > bins[position].getRight()) ++ position;

            ++ binFrequency[position];
        }

        ListIntervalsMatrix result = new ListIntervalsMatrix (2);
        final double area = returnFrequency ? 1 : dataSize * bins[0].getRange();
        for (int i=0; i<nOfBins; ++i) {
            result.add(bins[i]);
            if (returnFrequency) result.add(new IntervalPoint(binFrequency[i]));
            else result.add(new IntervalPoint(binFrequency[i]/area));
        }

        return result;
    }
}
