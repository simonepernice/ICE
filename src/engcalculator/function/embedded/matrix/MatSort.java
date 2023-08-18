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
package engcalculator.function.embedded.matrix;

import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.embedded.compare.ComGreater;
import engcalculator.function.embedded.compare.ComSmaller;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatSort extends FunctionPrefix {

    private final static String HELP = "... ({[matrix]}, SortColumnOrder1, SortColumnOrder2, .., SortColumnOrderN  ) returns a matrix where the rows were sorted in ascendin order the give sequence (SortColumnOrder1, .., SortColumnOrderN).\nSortColumnOrder is the column to compare for the sorting. If SortColumnOrder is between 0 ans columnSize-1 it means ascending order. If SortColumnOrder is between columnSize and 2*ColumnSize-1 it means descending order.";
    private final static String[] EXAMPLE = {"{[[1,2,3],[3,2,1],[2,1,3],[1,3,2]]},1,2"};
    private final static String[] RESULT = {" [[2,1,3],[3,2,1],[1,2,3],[1,3,2]]"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalList(), new DomainLogicAnd (new DomainIntervalPositive(), new DomainIntervalPoint (), new DomainInteger()));
    private final static ComGreater GREATER = new ComGreater();
    private final static ComSmaller SMALLER = new ComSmaller();

    public MatSort() {
        super("matrix", "Sort", (byte) -1, INTERVALDOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervalsMatrix mat = (ListIntervalsMatrix) input.getFirst().getListIntervals();
        final int clmnSize = mat.columnSize();
        
        final int nClmnToSort = input.size()-1;
        if (nClmnToSort < 1 || nClmnToSort >= clmnSize) throwNewCalculusException ("The number of column to sort makes no sense "+nClmnToSort);
        
        int[] clmnSortOrdr = new int[nClmnToSort];        
        boolean[] clmnSortAscending = new boolean[nClmnToSort];        
        for (int i=0; i<nClmnToSort; ++i) {
            int c = (int) input.get(i+1).getValue();                
            if (c >= 2*clmnSize-1 || c < 0) throwNewCalculusException ("The "+i+"th order column is out of range: "+c);
            if (c >= clmnSize) {
                c -= clmnSize;
                clmnSortAscending[i] = false;
            } else {
                clmnSortAscending[i] = true;
            }
            clmnSortOrdr[i] = c ;
        }

        ListIntervals result;

        result = new ListIntervalsMatrix(mat);

        int counter, index;
        final int lengthmcs = result.size() - clmnSize;

        for (counter = 0; counter < lengthmcs; counter+=clmnSize) { //Loop once for each element in the array.
            for (index = 0; index < lengthmcs - counter; index+=clmnSize) { //Once for each element, minus the counter.
                for (int j=0; j<nClmnToSort; ++j) {
                    int ipcsoj = index + clmnSortOrdr[j];
                    int ipcsojcs = ipcsoj + clmnSize;
                    if ((clmnSortAscending[j] && SMALLER.compareForTrue(result.get(ipcsoj), result.get(ipcsojcs))) ||
                        (! clmnSortAscending[j] && GREATER.compareForTrue(result.get(ipcsoj), result.get(ipcsojcs))))    {//If it is in the correct order no swap required
                        break;
                    }
                    if ((clmnSortAscending[j] && GREATER.compareForTrue(result.get(ipcsoj), result.get(ipcsojcs))) ||
                        (! clmnSortAscending[j] && SMALLER.compareForTrue(result.get(ipcsoj), result.get(ipcsojcs))))    { //If it is in the wrong order all the rows are swapped
                        //swap (result, index, cs);
                        Interval temp;
                        for (int i=0; i<clmnSize; ++i) {
                            int ipin = index+i;
                            int ipinpcs = ipin + clmnSize;
                            temp = result.get(ipin); //These three lines just swap the two elements:
                            result.set(ipin, result.get(ipinpcs));
                            result.set(ipinpcs, temp);
                        }
                        break;
                    }
                    //If it was not in correct neither wrong order then it tries on the next column to check
                }
            }
        }

        return result;
    }
}
