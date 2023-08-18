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

package engcalculator.expression;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class Label {
    
    private final String name;
    private final int row;

    public Label(String name, int row) {
        this.name = name;
        this.row = row;
    }

    public String getName() {
        return name;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((Label) obj).name); 
    }            
    
    public boolean equalName (String l)  {
        return name.equals(l);
    }
}
