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
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class Token { //just a container for all types of components findable
    protected static final boolean DEBUG = Tokenizer.DEBUG;
    private static boolean definingFunction;

    public static boolean isDefiningFunction() {
        return definingFunction;
    }

    public static void setDefiningFunction(boolean definingFunction) {
        Token.definingFunction = definingFunction;
    }

    private String symbol;

    public Token () {}

    public Token (String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isSignificant () {
        return true;
    }

    public boolean isVariable () {
        return false;
    }

    abstract public Token generateToken (String symbol);

    abstract public boolean mayBeCompatible ( String symbol);

    public Token regenerateToken () {
        return this;
    }

    public ListIntervals getListInterval () throws Exception {
        return null;
    }

    public FunctionInfix getFunctionInfix () {
        return null;
    }

    public FunctionPrefix getFunctionPrefix () {
        return null;
    }

    public String getTerminal () {
        return null;
    }

    @Override
    public String toString () {
        if (DEBUG) return "<"+this.getClass().getSimpleName()+" \""+symbol+"\"> ";
        return symbol;
    }

    @Override //required for contains method in Interpeter
    public boolean equals(Object obj) {
        if (! (obj instanceof Token)) return false;
        return symbol.equals(((Token)obj).symbol);
    }

}
