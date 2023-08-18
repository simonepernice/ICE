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

import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class TknFunctionInfixPrefix extends Token {
    private static final String OPERATORS = "+-*/^=,<>!&|:_%#.?@";
    private final static TknLiteral LITERAL_TKN = new TknLiteralFunctionVariableLateBinding();

    private final FunctionInfix functionInfix;
    private final FunctionPrefix functionPrefix;

    public TknFunctionInfixPrefix() {
        functionInfix = null;
        functionPrefix = null;
    }

    protected TknFunctionInfixPrefix(String function, FunctionInfix infixFunction, FunctionPrefix prefixFunction) {
        super (function);
        this.functionInfix = infixFunction;
        this.functionPrefix = prefixFunction;
    }
    
    public static boolean isOperator (char op) {
        return OPERATORS.indexOf(op) != -1;
    }

    @Override
    public FunctionInfix getFunctionInfix () {
        return functionInfix;
    }

    @Override
    public FunctionPrefix getFunctionPrefix () {//that is important for operators used as unary one like minus
        return functionPrefix;
    }
    
    @Override
    public Token generateToken(String symbol) {
        FunctionInfix ifun = FunctionInfix.getFunction (symbol);
        if (ifun != null) return new TknFunctionInfixPrefix(symbol, ifun, FunctionPrefix.getFunction (symbol));
        return null;

    }
    
    //That is used if the function was deleted previously
    @Override
    public Token regenerateToken() {
        if (FunctionInfix.getFunction(getSymbol()) == null && FunctionPrefix.getFunction(getSymbol()) == null) return LITERAL_TKN.generateToken(getSymbol());               //if the function was deleted
        if (FunctionInfix.getFunction(getSymbol()) != functionInfix || FunctionPrefix.getFunction(getSymbol()) != functionPrefix) return generateToken(getSymbol());        //if the function was re-defined
        return super.regenerateToken();
    }    

    @Override
    public boolean mayBeCompatible(String symbol) {
        for (Character c : symbol.toCharArray())
            if (OPERATORS.indexOf(c) == -1) return false;
        return true;
    }
}
