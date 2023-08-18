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

import engcalculator.domain.DomainList;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisGetElements extends FunctionInfix {
    private final static String HELP = "(list) ... 8index1, index2, ..) returns the element of the list at the given indexes.\nGiven a list ... is able to extract a set of values of the list pointed by the given indexes. The first element of the list has index 0. If an index is a range then the values from interval left to interval right (inclusive) are extracted. If the interval is not proper the values are extracted from the end toward the beginning. If an index is negative it is summed to the length of the list: therefore -1 is the last element, -2 the element before the last and so on. Given a matrix the '...' is able to extract an element of the list which is given by the interval rowstart_rowend, columnstart_columnend. A matrix is read left to right, up to down, row by row starting from element 0 up-left. If instead of row or column a negative number is given the count begin from the end.";
    private final static String[] EXAMPLE_LEFT = {
        "(1,2,3,4,5,6,7,8,9)#3", "(1,2,3,4,5,6,7,8,9)#3", "(1,2,3,4,5,6,7,8,9)#3", "(1,2,3,4,5,6,7,8,9)#3", "(1,2,3,4,5,6,7,8,9)#3", "(1,2,3,4,5,6,7,8,9)#3", 
        "1,2,3", "1,2,3,4", "1,2,3,4",
        "(1,2,3,4)#2","(1,2,3,4)#2","(1,2,3,4)#2",
        "1,2,3,4,5,6,7,8,9","1,2,3,4,5,6,7,8,9","1,2,3,4,5,6,7,8,9","1,2,3,4,5,6,7,8,9","1,2,3,4,5,6,7,8,9","1,2,3,4,5,6,7,8,9",
        ";(1,2,{3,4,{5,6},7},8) :: 2 :: (1,3)",
        ";(1,2,{3,4,{5,6},7},8) :: 2 :: 2 :: 0_-1",
        ";(1,2,{3,4,{5,6},7},8) :: 2 :: 2",
        ";(1,2,{3,4,{5,6},7},8) :: 2"};
    private final static String[] EXAMPLE_RIGHT = {
        "0_1,0_1","1_0,0_1","0_1,1_0","1_0,1_0","0_1,0_1,1_2,1_2","0_-1,0_-1", 
        "1","1,3","0,0,1",
        "0_1,1","1_0,0","1,0_1",
        "3 .. 6","3 .. 6 .. 2","3, 6","3_5","6_-1","3_0",
        "",
        "",
        "",
        ""};
    private final static String[] RESULT = {
        "(1,2,4,5)#2","(4,5,1,2)#2","(2,1,5,4)#2","(5,4,2,1)#2","(1,2,5,6,4,5,8,9)#4","(1,2,3,4,5,6,7,8,9)#3", 
        "2", "2,4", "1,1,2",
        "(2,4)#1","(3,1)#1","(3,4)#2", 
        "4,5,6,7","4,6","4,7","4,5,6","7,8,9","4,3,2,1",
        "4,7",
        "5,6",
        "{5,6}",
        "{3,4,{5,6},7}"};
    
    private final static LisBuilder LIST_BUILDER = new LisBuilder();
    private final static DomainList DOMAIN = new DomainList();

    public LisGetElements() {
        super ("list", "::", (byte) -1, (byte) -1, DOMAIN, DOMAIN, false, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        ListIntervals result = null;
        final int extractions = rightSide.size();
        
        if (leftSide.isMatrix()) {       
            ListIntervalsMatrix subMatrix;
            
            if ((extractions & 1) != 0) throwNewCalculusException ("An even set of (row, column) should have been provided while was found: "+rightSide);
            
            Iterator<Interval> iRightSide = rightSide.iterator();
            ListIntervals address = new ListIntervals ();
            while (iRightSide.hasNext()) {
                address.clear();
                address.add(iRightSide.next());
                address.add(iRightSide.next());

                LisElementIterator ei = new LisElementIterator(leftSide, address);

                subMatrix = new ListIntervalsMatrix(ei.getColumnSize());

                while (ei.next()) 
                    subMatrix.add(ei.get());

                if (result == null) result = subMatrix;
                else result = LIST_BUILDER._compute(result, subMatrix);
                
            }
        
            return result;
            
        } else {
            if (leftSide.size() == 1 && leftSide.getFirst().isIntervalList()) leftSide = leftSide.getFirst().getListIntervals();
            
            result = new ListIntervals();
            
            Iterator<Interval> iRightSide = rightSide.iterator();
            ListIntervals address = new ListIntervals ();            
            while (iRightSide.hasNext()) {
                address.clear();
                address.add(iRightSide.next());
                
                LisElementIterator ei = new LisElementIterator(leftSide, address);

                while (ei.next()) 
                    result.add(ei.get());                                    
            }            
            
        }
        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        if (! rightSide.getMeasurementUnit().equals(MeasurementUnit.PURE)) throwNewMeasurementUnitException("The measurement units on the right side should be a pure value while it was found: "+rightSide.toString());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(leftSide.getMeasurementUnit());
        return res;        
    }  

}
