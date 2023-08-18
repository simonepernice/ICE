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

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ListIntervalsMatrixNamed extends ListIntervalsMatrix {
    private final String name;

    public ListIntervalsMatrixNamed (String name) {
       this.name = name;
    }

    public ListIntervalsMatrixNamed (Interval i, String name) {
       add (i);
       this.name = name;
    }

    public ListIntervalsMatrixNamed (ListIntervals i, String name) {
       super (i);
       this.name = name;
    }

    public ListIntervalsMatrixNamed (ListIntervalsMatrix i, String name) {
       super (i);
       this.name = name;
    }

   public ListIntervalsMatrixNamed (ListIntervalsMatrixNamed i) {
       super (i);
       this.name = i.name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString () {
        final int s = size();
        if (s > 1 ) {
            if (name != null) return new StringBuilder(name).append(" = ( ").append(super.toString()).append(" )").toString();
            return super.toString();
        } 
        if (s > 0) {
            if (name != null) return new StringBuilder(name).append(" = ").append(super.toString()).toString();
            return super.toString();            
        }
        return name;
    }
}
