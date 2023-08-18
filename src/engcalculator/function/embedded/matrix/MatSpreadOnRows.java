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

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MatSpreadOnRows extends FunctionPrefix {
    private final static String HELP = "... ($func, {[mat]}) applies the given prefix function func to the following matrix mat row by row.\nFor example ... ($average,{2, 3, 4, 5}, 2) = (average(2,3), average(4,5)). It is also possible to apply to a list of matrices { matrix1 }, { matrix2 }, .. ";
    private final static String[] EXAMPLE = {"$statisticAverage, {(2 .. 7)#2}","$statisticAverage, {(2 .. 7)#3}", "$a = (1 .. 9)#3;matrixSpreadOnRows($statisticAverage,{a})"};
    private final static String[] RESULT = {"2.5, 4.5, 6.5", "3, 6","2,5,8"};
    private final static DomainList INTERVALDOMAIN = new DomainList (new DomainFunctionPrefix(-1), new DomainIntervalList());

    public MatSpreadOnRows() {
        super ("matrix", "SpreadOnRows", (byte) -2, INTERVALDOMAIN, false,true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals result = new ListIntervals();
        
        final FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        
        final int s = input.size();
        for (int i=1; i<s; ++i) {        
            final ListIntervals data = input.get(i).getListIntervals();
            final int size = data.size();
            final int group = data.columnSize();

            if (size%group != 0) throwNewCalculusException ("The given group size "+group+" does not match with list length "+size);
            
            for (int j=0; j<size; j += group) {
                result.addAll(f.compute(data.subList(j, j+group)));            
            }
        }
        return result;
    }

}
