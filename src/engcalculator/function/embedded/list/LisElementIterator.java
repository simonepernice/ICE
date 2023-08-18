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

import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisElementIterator {
    private final int rd, rs, re;
    private final int cd, cs, ce;
    private int c, r;
    private final ListIntervals data;
    private boolean completed;
    
    public LisElementIterator(ListIntervals data, ListIntervals extremes) throws Exception {
        this.data = data;
        
        Interval i = extremes.getFirst();
        
        if (data.isMatrix()) {
            int[] row = getExtremes(i, data.rowSize(), "Row");
            
            rs = row[0];
            re = row[1];
            rd = row[2];
                  
            i = extremes.get(1);
        } else {
            rs = 0;
            re = rd = 1;
        }
        
        int[] col = getExtremes(i, data.columnSize(), "Column");

        ce = col[1];
        cd = col[2];
        
        cs = col[0]-cd;
        
        r = rs;
        c = cs;
        
        completed = false;
    }
    
    private int[] getExtremes (Interval i, int size, String  type) throws Exception {
        //j[0] = start, j[1] = end, j[2] = delta step
        int[] j = new int[3];
        j[0] = (int) i.getLeft();
        j[1] = (int) i.getRight();            

        if (j[0] < 0) j[0] += size;
        if (j[1] < 0) j[1] += size; 

        if (j[0] < 0 || j[0] >= size || j[1] < 0 || j[1] >= size) Function.throwNewCalculusException (type+" out of range, required element starting at: "+j[0]+", ending at: "+j[1]+" while the size is "+size);

        if (j[0] < j[1]) j[2] = 1; else j[2] = -1;
        j[1] += j[2];         
        
        return j;
    }
    
    public int getColumnSize () {
        return cd == 1 ? ce - cs - cd : cs + cd - ce;
    }

    public boolean next () {
        if (completed) return false;
        c += cd;
        if (c == ce) {
            r += rd;
            if (r == re) {
                completed = true;
                return false;
            }
            c = cs + cd;
        }
        return true;
    }
    
    public Interval get() {        
        return data.get(r, c);        
    }
    
    public void set (Interval i) {   
        data.set(r, c, i);
    }
    
}
