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
import engcalculator.interval.IntervalLiteral;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class TknLiteral extends Token {
    protected final static TknFunctionPrefix PREFIX_FUNCTION_TKN = new TknFunctionPrefix ();
    private final static TknVariableLateBinding VARIABLE_TKN = new TknVariableLateBinding();

    private final IntervalLiteral literal;
    private final boolean notConvertibleToVarFun;

    public TknLiteral() {
        notConvertibleToVarFun = false;
        literal = null;
    }

    protected TknLiteral(String literal, boolean notConvertibleToVarFun) {
        super (literal);
        this.literal = new IntervalLiteral (literal);
        this.notConvertibleToVarFun = notConvertibleToVarFun;
    }

    @Override
    public Token regenerateToken() {
        if (notConvertibleToVarFun) return super.regenerateToken();
        Token t;
        t = VARIABLE_TKN.generateToken(getSymbol());
        if (t != null) return t;
        t = PREFIX_FUNCTION_TKN.generateToken(getSymbol());
        if (t != null) return t;
        return super.regenerateToken();
    }

    @Override
    public ListIntervals getListInterval() {
        return new ListIntervals (literal);
    }

    @Override
    public Token generateToken(String symbol) {
        if (symbol.length() < 2) return null;
        
        char startc = symbol.charAt(0), endc = symbol.charAt(symbol.length()-1);
        if (endc == '\''  && startc == '\'') {
            symbol = symbol.substring(1,  symbol.length()-1);

            symbol = symbol.replace("\\'", "'"); //if '' are embedded in the expression they are cleaned to proper expression
            
            symbol = symbol.replace("\\n", "\n"); //if \n is embedded in the expression it is transformed in a new line 

            TknLiteral lt = new TknLiteral(symbol, true); //if something is between quotation marks it cannot be converted back to variable of function
            return lt;                    
        }

        return null;
    }

    @Override
    public boolean mayBeCompatible( String symbol) {
        if (symbol.charAt(0) == '\'') {
            if (symbol.length() > 1) {
                int ea = symbol.lastIndexOf('\'');
                if (ea > 0 && ea < symbol.length()-1 && symbol.charAt(ea-1) != '\\') return false; //if \' is found as last char of the expression, it still returns true (mayBeCompatible) because the literal is not completed, it must finish with '
            }
            return true;

        }
        return false;
    }

}
