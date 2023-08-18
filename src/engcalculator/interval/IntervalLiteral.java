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
public final class IntervalLiteral extends Interval {
    private final String name;

    public IntervalLiteral(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getRight() {
        return Double.NaN;
    }

    @Override
    public double getLeft() {
        return Double.NaN;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(name.length()+2);
        boolean np = IntervalPoint.nicePrint.getVal();
        if (! np) res.append("'");
        res.append(name);
        if (! np) res.append("'");
        return res.toString();
    }

    @Override
    public double getTolerance() {
        return Double.NaN;
    }

    @Override
    public double getValue() {
        return Double.NaN;
    }

    @Override
    public double getRange() {
        return Double.NaN;
    }

    @Override
    public IntervalReal getRealPart() {
        return null;
    }

    @Override
    public IntervalReal getImaginaryPart() {
        return null;
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
    public boolean isIntervalComplex () {
        return false;
    }

    @Override
    public Interval computeRoundings() {
        return this;
    }
    
    @Override
    public boolean isIntervalLiteral () {
        return true;
    }

    @Override
    public ListIntervals getListIntervals() {
        return null;
    }

    @Override
    public boolean isIntervalList() {
        return false;
    }
    
    

}
