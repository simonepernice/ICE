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

package engcalculator.function.embedded.list;

import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalList;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.CalculusException;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisPick extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalList(new DomainInteger()), new DomainIntervalReal());

    private final static String HELP = "... ({index1, index2, index3, .., indexn, module}, list element) pick index1, index2, index3, indexn every module elment of the following list.";
    private final static String[] EXAMPLE = {"{0,2},1 .. 6","{1,2},1 .. 6"};
    private final static String[] RESULT = {"1,3,5","2,4,6"};

    public LisPick() {
        super("list", "Pick", (byte) -2, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals ruleLI = input.getFirst().getListIntervals();
        int module = (int) ruleLI.getLast().getValue();        
        
        if ((input.size()-1)%module != 0) throw new CalculusException ("The given module "+module+" is not a multiple of the list length "+(input.size()-1));        
        
        int[] rules = new int[ruleLI.size()-1];
        
        int i = 0;        
        for (Interval j : ruleLI)
            if (i<rules.length) rules[i++] = (int) j.getValue();                
        
        ListIntervals result = new ListIntervals();
        Iterator<Interval> data = input.iterator();
        data.next();//skip rules
        i=0;
        while (data.hasNext()) {
            Interval in = data.next();
            for (int j : rules) {
                if (j == i) {
                    result.append(in);
                    break;
                }
            }
            ++ i;
            if (i==module) i=0;
        }
            
        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws MeasurementUnitException {      
        
        ListIntervals ruleLI = input.getFirst().getListIntervals();
        int module = (int) ruleLI.getLast().getValue();         
        int[] rules = new int[ruleLI.size()-1];
        
        int i = 0;        
        for (Interval j : ruleLI)
            if (i<rules.length) rules[i++] = (int) j.getValue();                
        
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        Iterator<Interval> data = input.iterator();
        data.next();//skip rules
        i=0;
        while (data.hasNext()) {
            Interval in = data.next();
            for (int j : rules) {
                if (j == i) {
                    res.add(in.getMeasurementUnit());
                    break;
                }
            }
            ++ i;
            if (i==module) i=0;
        }
            
        return res;        
    }      

}
