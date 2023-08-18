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

package engcalculator.function.embedded.calculus;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CalJacobian extends FunctionPrefix {
    private final static CalDerivate DER = new CalDerivate();

    private final static String HELP = "... ({'f1', .., 'fn'},{ x1, .., xn }) given n functions of n arguments (x1, .., xn) and n initial intervals computes the Jacobian in the given intervals.";
    private final static String[] EXAMPLE = {"{defineLambdaFunction ('2*$x+$y'), defineLambdaFunction ('$x+4*$y')},{(0,1)+-0.01}"};
    private final static String[] RESULT = {"(2,1,1,4)#2"};
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalList (new DomainFunctionPrefix(-1)), new DomainIntervalList(new DomainIntervalReal()));
    
    public CalJacobian() {
        super("calculus", "Jacobian", (byte) 2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        return jacobian(functions(input.getFirst().getListIntervals()), input.getLast().getListIntervals());
    }
    
    public ListIntervals jacobian (List<FunctionPrefix> fp, ListIntervals x0) throws Exception{
        final int s = x0.size();
        if (s != fp.size()) throwNewCalculusException ("The function and interval list should be of the same size");
        ListIntervals result = new ListIntervalsMatrix (s);        
        for (FunctionPrefix f : fp) {
            int i = 0;
            while (i < s) {
                int j=0;
                ListIntervals x1 = new ListIntervals ();
                for (Interval in : x0) {
                    if (j == i) x1.add(in);
                    else x1.add(new IntervalPoint(in.getValue()));
                    ++j;
                }
                result.addAll(DER.partialDerivate(f, x1));
                ++i;
            }
        }
        return result;        
    }
    
    public List<FunctionPrefix> functions (ListIntervals i) {
        List<FunctionPrefix> fp = new LinkedList<FunctionPrefix> ();
        for (Interval fname : i) {
            fp.add(FunctionPrefix.getFunction(fname.getName()));
        }    
        return fp;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }    
}
