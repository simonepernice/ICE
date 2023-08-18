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
package engcalculator.function.embedded.setList;

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.embedded.compare.ComGreater;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervalsMatrix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SLsSort extends FunctionPrefix {

    private final static String HELP = "... (list) returns an ordered list with the same elements of the given one.\nIt runs with the BubbleSort algorithm taken from http://www.dreamincode.net/code/snippet513.htm with a change to swap a full row in case of matrix. If the input is a matrix it order the first column, the elements in the other rows are arranged accordingly with first row. For more powerful sorting algorithm check matrixSort.";
    private final static String[] EXAMPLE = {"1, 4, 2, 3, 5, 6","(1, 4, 2, 3, 5, 6)#1,(1 .. 6)#1"};
    private final static String[] RESULT = {"1, 2, 3, 4, 5, 6","(1 .. 6)#1,(1,3,4,2,5,6)#1"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalReal());
    private final static ComGreater GREATER = new ComGreater();

    public SLsSort() {
        super("setList", "Sort", (byte) -1, INTERVALDOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
                
        final int clmnSize;
        ListIntervals result;
        if (input.isMatrix()) {
            clmnSize = input.columnSize();
            result = new ListIntervalsMatrix(clmnSize);
        } else {
            clmnSize = 1;
            result = new ListIntervals();
        }
        
        result.addAll(input);

        int counter, index;
        final int lengthmcs = result.size() - clmnSize;

        for (counter = 0; counter < lengthmcs; counter+=clmnSize) { //Loop once for each element in the array.
            for (index = 0; index < lengthmcs - counter; index+=clmnSize) { //Once for each element, minus the counter.
                if (GREATER.compareForTrue(result.get(index), result.get(index + clmnSize))) { //Test if need a swap or not.
                    //swap (result, index, cs);
                    Interval temp;
                    for (int i=0; i<clmnSize; ++i) {
                        int ipin = index+i;
                        int ipinpcs = ipin + clmnSize;
                        temp = result.get(ipin); //These three lines just swap the two elements:
                        result.set(ipin, result.get(ipinpcs));
                        result.set(ipinpcs, temp);
                    }
                }
            }
        }

        return result;
    }
}
