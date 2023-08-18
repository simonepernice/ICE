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

package engcalculator.interval;

import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class ListIntervalsMatrix extends ListIntervals {
    private int columnsNumber;

    public ListIntervalsMatrix() {
        this.columnsNumber = -1;
    }

    public ListIntervalsMatrix(int columnsNumber) {
        this.columnsNumber = columnsNumber;
    }

    public ListIntervalsMatrix (Interval i, int columnsNumber) {
       add (i);
       this.columnsNumber = columnsNumber;
    }

    public ListIntervalsMatrix (ListIntervals i) {
       super (i);
       if (i.isMatrix()) columnsNumber = i.columnSize(); //very important
       else columnsNumber = -1;
    }

    public ListIntervalsMatrix (ListIntervals i, int columnsNumber) {
       super (i);
       this.columnsNumber = columnsNumber;
    }

    public ListIntervalsMatrix (ListIntervalsMatrix i) {
       super (i);
       columnsNumber = i.columnsNumber;
    }

    @Override
    public int columnSize() {
        if (columnsNumber <= 0) return size();
        return columnsNumber;
    }

    @Override
    public int rowSize () {
        if (columnsNumber <= 0) return 1;
        return size()/columnsNumber;
    }

    public void setColumnsNumber(int columnsNumber) throws Exception {
        if (size() % columnsNumber != 0) throw new RuntimeException ("It is not possible to set the given number of columns");
        this.columnsNumber = columnsNumber;
    }

    @Override
    public Interval get (int row, int column) {
        if (row >= rowSize() || column >= columnSize() || row < 0 || column < 0) throw new RuntimeException("Index out of bound");
        return get (row*columnsNumber+column);
    }

    @Override
    public void set(int row, int column, Interval val) {
        if (row > rowSize() || column > columnSize()) throw new RuntimeException("Index out of bound");
        set (row*columnsNumber+column, val);
    }

    @Override
    public boolean isProperMatrix () {
        return isMatrix() && size()%columnsNumber==0;
    }

    @Override
    public boolean isMatrix() {
        return columnsNumber > 0;
    }

    @Override
    public String toString () {
        if (! IntervalPoint.nicePrint.getVal()) {
            if (columnsNumber > 0) return super.toStringBuilder().append(" # ").append(columnsNumber).toString();
            return super.toStringBuilder().toString();
        }
        
        if (! isMatrix()) return super.toString();

        StringBuilder result = new StringBuilder ();
        if (size() == 0) return result.toString();
        int col = columnsNumber;
        
        Iterator<Interval> it = iterator();
        
        result.append ("\n[");
        result.append(it.next().toStringSpaced());
        
        final int maxNumberOfIntervalToPrint = MAXNUMBEROFINTERVALTOPRINT.getVal();

        int i = 0;
        while((maxNumberOfIntervalToPrint == 0 || i++<maxNumberOfIntervalToPrint) && it.hasNext()) {
            result.append("  ");

            if (columnsNumber > 0 && --col <= 0) {
                result.append("]\n[");
                col = columnsNumber;
            }

            result.append(it.next().toStringSpaced());
        }
        
        if (maxNumberOfIntervalToPrint > 0 && i >= maxNumberOfIntervalToPrint) result.append(", .....");                
        
        result.append("  ]\n");       
        
        return result.toString();
    }

}
