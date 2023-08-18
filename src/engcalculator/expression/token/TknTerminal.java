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
public final class TknTerminal extends Token {
    public static final String TERMINALS = ";()[]{}";

    public TknTerminal() {}
    
    private TknTerminal(String terminal) {
        super (terminal);
    }

    @Override
    public String getTerminal() {
        return getSymbol();
    }

    @Override
    public Token generateToken(String symbol) {
        if  (!TERMINALS.contains(symbol) || symbol.length() != 1) return null;
        return new TknTerminal(symbol);
    }

    @Override
    public boolean mayBeCompatible( String symbol) {
        return (TERMINALS.contains(symbol) && symbol.length() == 1);
    }

}
