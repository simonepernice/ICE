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


public final class IntervalList extends Interval {
    private final ListIntervals listIntervals;
    
    public IntervalList (ListIntervals li) {
        listIntervals = li;
    }
    
    public IntervalList () {
        listIntervals = new ListIntervals();
    }

    @Override
    public double getRight() {
        throw new RuntimeException("getRight Not supported for list.");
    }

    @Override
    public double getLeft() {
        throw new RuntimeException("getLeft Not supported for list.");
    }

    @Override
    public double getTolerance() {
        throw new RuntimeException("getTolerance Not supported for list.");
    }

    @Override
    public double getValue() {
        throw new RuntimeException("getValue Not supported for list.");
    }

    @Override
    public double getRange() {
        throw new RuntimeException("getRange Not supported for list.");
    }

    @Override
    public IntervalReal getRealPart() {
        throw new RuntimeException("getRealPart Not supported for list.");
    }

    @Override
    public IntervalReal getImaginaryPart() {
        throw new RuntimeException("getImaginaryPart Not supported for list.");
    }

    @Override
    public boolean isIntervalPoint() {
        return false;
    }

    @Override
    public boolean isIntervalReal() {
        return false;
    }

    @Override
    public boolean isIntervalComplex() {
        return false;
    }

    @Override
    public String getName() {
        return listIntervals.getName();
    }

    @Override
    public Interval computeRoundings() {
        throw new RuntimeException("computeRoundings Not supported for list.");
    }

    @Override
    public ListIntervals getListIntervals() {
        return listIntervals;
    }

    @Override
    public boolean isIntervalList() {
        return true;
    }

    @Override
    public boolean isIntervalLiteral() {
        return false;
    }
    
    @Override
    public String toString () {
        StringBuilder result = new StringBuilder();
        result.append(" {").append(listIntervals.toString()).append("} ");
        return result.toString();
    }
    
}
