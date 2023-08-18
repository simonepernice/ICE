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

import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class TknFunctionPrefix extends Token {
    private final static TknLiteral LITERAL_TKN = new TknLiteralFunctionVariableLateBinding();

    private final FunctionPrefix functionPrefix;

    public TknFunctionPrefix() {
        functionPrefix = null;
    }

    protected TknFunctionPrefix(String functionName, FunctionPrefix prefixFunction) {
        super (functionName);
        this.functionPrefix = prefixFunction;
    }

    @Override
    public FunctionPrefix getFunctionPrefix () {
        return functionPrefix;
    }

    @Override
    public Token generateToken(String symbol) {
        FunctionPrefix f = FunctionPrefix.getFunction (symbol);
        if (f != null) return new TknFunctionPrefix(symbol, f);
        return null;
    }

    //That is used if the function was deleted previously
    @Override
    public Token regenerateToken() {
        if (FunctionPrefix.getFunction(getSymbol()) == null) return LITERAL_TKN.generateToken(getSymbol());         //if the function was deleted
        if (FunctionPrefix.getFunction(getSymbol()) != functionPrefix) return generateToken(getSymbol());           //if the function was re-defined
        return super.regenerateToken();
    }


    @Override
    public boolean mayBeCompatible( String symbol) {
        final char ch = symbol.charAt(0);
        if ( ! (Character.isLowerCase(ch) || Character.isUpperCase(ch) ) ) return false;

        for (char c : symbol.toCharArray())
            if (! (Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c))) return false;
        return true;
    }
}
