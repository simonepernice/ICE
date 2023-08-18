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

package engcalculator.function.embedded.engineer;

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainLogicOr;
import engcalculator.function.embedded.equation.EquDiscreteValueScannerCloser;
import engcalculator.function.embedded.equation.EquDiscreteValueScannerLowest;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalList;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngPreferredValueScannerLowest extends FunctionPrefix {
    private final static String HELP = "... ($prefixFunc, {start1_stop1, tol1}, .., {startn_stopn, toln}) returns the {list of preferred values} gives the lowest value of the function $prefixFunction.\n It can be used to optimize a set of values. If any parameter is given as itnerval it is taken the closer couple of preferred values where it fits. The target may be an intervalList if the function return list.";
    private final static String[] EXAMPLE = {"($seriesToOptimize, $a, $b) = 'abs(a+b-19k)'; engineerPreferredValueScannerLowest($seriesToOptimize, {1k_100k, 10}, {1k_100k, 10})"};
    private final static String[] RESULT = {"{18k, 1k, 1k, 18k}"};
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix (-1),  new DomainLogicOr(new DomainIntervalReal(), new DomainIntervalList()), new DomainLogicOr(new DomainIntervalReal(), new DomainIntervalList()));
    private final static EquDiscreteValueScannerLowest EDVS = new EquDiscreteValueScannerLowest ();
    
    public EngPreferredValueScannerLowest() {
        super (  "engineer", "PreferredValueScannerLowest", (byte) -3, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute (ListIntervals input) throws Exception { 
        ListIntervals nInput = new ListIntervals ();
        Iterator<Interval> it = input.iterator();
        nInput.append(it.next());       //skip function name
        final int s = input.size()-1;
        for (int i=0; i<s; ++i) {//transform all the rest in list of intervals with exception of the last element the target
            final Interval tmp = it.next();
            ListIntervals li;
            if (tmp.isIntervalList()) {
                li = tmp.getListIntervals();
                if (li.size() > 2 ) throwNewCalculusException("It is required to provide list of intervals made by 2 elements while was found: "+li.toString());
            } else {
                li = new ListIntervals();
                li.append(tmp);
            }
            final double begin = li.getFirst().getLeft();
            final double end = li.getFirst().getRight();
            final double tol = li.getLast().getValue();

            final EngPreferredIteratorIntervalPoint epiip = new EngPreferredIteratorIntervalPoint(begin, end, tol);
            
            final ListIntervals li2 = new ListIntervals ();
            while (epiip.hasNext()) li2.append(epiip.next());
            
            if (li2.size() == 1) nInput.append(li2.getFirst());
            else nInput.append(new IntervalList (li2));
        }

        
        //run equation discrete value scanner
        return EDVS.compute(nInput);     
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws Exception {
        return input.getMeasurementUnitList();
    }
    
}
