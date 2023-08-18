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
import engcalculator.interval.IntervalList;
import engcalculator.interval.ListIntervals;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisSetElements extends FunctionInfix {
    private final static String HELP = "(list) ... (index, value, index2, value2, ..) in the given lists (or variable) it sets the required value at the given index.\nGiven a list ... is able to set some values of the list pointed by the given indexes at the given values. The first element of the list has index 0. If an index is a range then the values from interval left to interval right (inclusive) are set. If the interval is not proper the values are set from the end toward the beginning. If an index is negative it is summed to the length of the list: therefore -1 is the last element, -2 the element before the last and so on. Given a matrix the '...' is able to set some elements of the list which are given by the interval rowstart_rowend, columnstart_columnend. A matrix is read left to right, up to down, row by row starting from element 0 up-left. If instead of row or column a negative number is given the count begin from the end.";
    private final static String[] EXAMPLE_LEFT = {
        "(1 .. 9)#3", "(1 .. 9)#3", "(1 .. 9)#3", "(1 .. 9)#3", "(1 .. 9)#3", 
        "1,2,3", "1,2,3,4", "1,2,3,4",
        "(1 .. 4)#2","(1 .. 4)#2","(1 .. 4)#2",
        "1 .. 9","1 .. 9","1 .. 9","1 .. 9","1 .. 9",
        "1,2,{3,4},5","1,2,{3,4},5","1,2,{3,4},5",
        "{1,2,3,4}"};
    private final static String[] EXAMPLE_RIGHT = {
        "0_1,0_1, 11 .. 14","1_0,0_1, 11,12,14,15","0_1,1_0, 11,12,14,15","1_0,1_0, 11,12,14,15","0_-1,0_-1, 2 .. 10", 
        "1,12","1,12,3,14","0,1,0,2,1,3",
        "0_1,1,12,14","1_0,0,11,13","1,0_1,13,14",
        "3_6, 14 .. 17","3, 14, 6, 17","3_5, 14 .. 16","6_-1, 17 .. 19","3_0, 11 .. 14",
        "2, 2", "2, {1,2}", "2, {1,2,3}",
        "2,5"};
    private final static String[] RESULT = {
        "(11,12,3,13, 14,6,7,8,9)#3","(14,15,3,11,12,6,7,8,9)#3","(12,11,3,15,14,6,7,8,9)#3","(15,14,3,12,11,6,7,8,9)#3","(2,3,4,5,6,7,8,9, 10)#3", 
        "1,12,3", "1,12,3,14", "2,3,3,4",
        "(1,12,3,14)#2","(13,2,11,4)#2","(1,2,13,14)#2", 
        "1,2,3,14,15,16,17,8,9","1,2,3,14,5,6,17,8,9","1,2,3,14,15,16,7,8,9","1,2,3,4,5,6,17,18,19","14,13,12,11,5,6,7,8,9",
        "1,2, 2,5", "1,2,{1,2},5", "1,2,{1,2,3},5",
        "{1,2,5,4}"};

    private final static DomainList DOMAIN = new DomainList();
    
    public LisSetElements() {
        super ("list", "::=", (byte) -1, (byte) -1, DOMAIN, DOMAIN, false, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        final int extractions = rightSide.size();
        final String varName = leftSide.getName();
        
        if (leftSide.isMatrix()) {       
            if (extractions < 3) throwNewCalculusException ("The data list is not long enough, it is required at least 3 element while where found "+extractions);
            for (int index = 0; index < extractions; ++ index) {

                LisElementIterator ei = new LisElementIterator(leftSide, rightSide.subList(index, index + 2));         
                
                ++index;
                if (extractions <= index) throwNewCalculusException ("The data list is not long enough.");
                
                while (ei.next()) {
                    ++index;
                    if (extractions <= index) throwNewCalculusException ("The data list is not long enough.");
                    ei.set(rightSide.get(index));                    
                }
                
            }        
            
        } else {            
            final boolean isIL = (leftSide.size() == 1 && leftSide.getFirst().isIntervalList());
            
            if (isIL) leftSide = leftSide.getFirst().getListIntervals();
            
            if (extractions < 2) throwNewCalculusException ("The data list is not long enough.");
            
            for (int index=0; index<extractions; ++ index) {                                
                LisElementIterator ei = new LisElementIterator(leftSide, rightSide.subList(index, index + 1));

                while (ei.next()) {
                    ++index;
                    if (extractions <= index) throwNewCalculusException ("The data list is not long enough.");
                    ei.set(rightSide.get(index));                    
                }
                                   
            }
            
            if (isIL) leftSide = new ListIntervals (new IntervalList (leftSide));
            
        }
        
        if (varName != null) {
            if (! FunctionVariable.addFunction(new FunctionVariable(varName, leftSide, false, "User defined variable."))) throwNewCalculusException("It was not possible to update the given variable");
        }

        return leftSide;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        if (! rightSide.getMeasurementUnit().equals(MeasurementUnit.PURE)) throwNewMeasurementUnitException("The measurement units on the right side should be a pure value while it was found: "+rightSide.toString());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(leftSide.getMeasurementUnit());
        return res;   
    }     
}
