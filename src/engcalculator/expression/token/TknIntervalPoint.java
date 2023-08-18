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

package engcalculator.expression.token;

import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.expression.ParsingException;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class TknIntervalPoint extends Token {

    private final ListIntervals intervalList;

    public TknIntervalPoint() {
        intervalList = null;
    }

    private TknIntervalPoint(String interval) throws ParsingException {
        super (interval);
        this.intervalList = new ListIntervals (IntervalPoint.valueOf(interval));
    }

    @Override
    public ListIntervals getListInterval() {
        return intervalList;
    }

    @Override
    public Token generateToken(String symbol) {
        try {
            return new TknIntervalPoint(symbol);
        } catch (ParsingException e) {
            return null;
        } 
    }

    @Override
    public boolean mayBeCompatible(String symbol) {
        int digits = 0, points = 0;
        boolean exp = false, engcost = false, neg = false;
        for (char c : symbol.toCharArray()) {
            if (Character.isDigit(c)) {
                ++digits;
            } else if (c == '.') {
                if (exp || points > 2) return false;
                else ++points;
            } else if (c == 'e') {
                if (exp || engcost || digits == 0) return false;
                else exp = true;
            } else if (c == '-') {
                if (!exp || neg || digits == 0) return false;
                else neg = true;
            } else if (IntervalPoint.ENGUNITS.indexOf(c) > -1) {
                if (engcost) return false;
                else engcost = true;
            } else {
                return false;
            }
        }
        return true;
    }

}
