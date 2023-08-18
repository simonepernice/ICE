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

package engcalculator.domain;

import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DomainListMatrix extends DomainList {
    private final int colMinusRow;

    public DomainListMatrix (int colMinusRow, Domain... listOfDomains) {
        super(listOfDomains);
        this.colMinusRow = colMinusRow;
    }

    public DomainListMatrix (Domain... listOfDomains) {
        super(listOfDomains);
        this.colMinusRow = -1;
    }

    @Override
    public boolean isCompatible (ListIntervals il) {
        if (! il.isProperMatrix()) return false;
        if (colMinusRow != -1 && il.columnSize()-il.rowSize() != colMinusRow) return false;

        return super.isCompatible(il);
    }

    @Override
    public String toString() {
        if (colMinusRow < 0) return "Matrix: "+super.toString();
        if (colMinusRow == 0) return "Square matrix: "+super.toString();
        return "Matrix with "+colMinusRow+" columns more than rows: "+super.toString();
    }


}
