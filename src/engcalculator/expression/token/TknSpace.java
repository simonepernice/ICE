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

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class TknSpace extends Token {
    public TknSpace() {}

    @Override
    public Token generateToken(String symbol) {
        return new TknSpace();
    }

    @Override
    public boolean mayBeCompatible( String symbol) {
        for (char c : symbol.toCharArray())
            if (! Character.isWhitespace(c)) return false;
        return true;
    }

    @Override
    public boolean isSignificant() {
        return false;
    }
}
