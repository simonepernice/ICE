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

import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisBuilder extends FunctionInfix {
    private final static DomainList DOMAIN = new DomainList (new DomainInterval());
    
    private final static String HELP = "a ... b returns the list (a, b).\nIt gets two separate intervals to make a list of intervals. It works to joins two matrices, first try to joins by row (if they have the same number of rows), then by column (if the have the same number of column). To join by column when the number of row is the same see the command ,| or brake the matrices in two lists with #0, join the lists, eventually make again the matrices.";
    private final static String[] EXAMPLE_LEFT = {"6_7", "1_2,3_4", "(1,2)#1","[1,2],[3,4]","[1,2],[3,4]","a=(1,2);b=(3,a,4)","a={1,2};b=(3,a,4)",";(1,2)+(3,4,5,6)",";(1)+(3,4,5,6)"};
    private final static String[] EXAMPLE_RIGHT = {"4_5", "5_6", "(3,4)#1","[5],[6]","[1,2],[3,4]","", "", "", ""};
    private final static String[] RESULT = {"6_7,4_5", "1_2, 3_4, 5_6", "(1,3,2,4)#2","[1,2,5],[3,4,6]","[1,2,1,2],[3,4,3,4]","3,1,2,4","3,{1,2},4","1+3,2+4,1+5,2+6","1+3,1+4,1+5,1+6"};

    public LisBuilder() {
        super ("list", ",", (byte) -1, (byte) -1, DOMAIN, DOMAIN, false, true, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals left, ListIntervals right) throws Exception {        
        if (left==null || (left.size() == 1 && left.getFirst() == null)) return right;
        if (right==null || (right.size() == 1 && right.getFirst() == null)) return left;
        
        ListIntervals result = null;

        if (left.isMatrix() && right.isMatrix()) {
            final int rl = left.rowSize(), cl=left.columnSize();
            final int rr = right.rowSize(), cr = right.columnSize();

            if (cl == cr && (rl != rr || rl == 1)) {//in the expression on the left if rl==1 then rr == 1
                result = new ListIntervalsMatrix (cl);
                result.addAll(left);
                result.addAll(right);
                return result;
            }

            if (rl == rr) {
                result = new ListIntervalsMatrix(cl+cr);
                Iterator<Interval> iLeft, iRight;
                iLeft = left.iterator();
                iRight = right.iterator();
                while (iLeft.hasNext()) {
                    for (int i=0; i<cl; ++i) result.append(iLeft.next());
                    for (int i=0; i<cr; ++i) result.append(iRight.next());
                }

                return result;
            }
        }

        result = new ListIntervals();
        result.addAll(left);
        result.addAll(right);
        return result;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        Iterator<Interval> il = leftSide.iterator();
        Iterator<Interval> ir = rightSide.iterator();
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        while (il.hasNext())
            res.add(il.next().getMeasurementUnit());
        while (ir.hasNext())
            res.add(ir.next().getMeasurementUnit());
        return res;
    }            
}
