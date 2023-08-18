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
public final class TknLiteralFunctionVariable extends TknLiteral {

    @Override
    public Token generateToken(String symbol) {
        if (symbol.length() <= 1 || symbol.charAt(0) != '$') return null;
        symbol = symbol.substring(1,  symbol.length());
        
        TknLiteral lt = new TknLiteral(symbol, true); //if something is between quotation marks it cannot be converted back to variable of function
        return lt;
    }

    @Override
    public boolean mayBeCompatible( String symbol) {
        if (symbol.charAt(0) == '$') {            
            if (symbol.length() > 1) {
                char a = symbol.charAt(1);
                if (! (Character.isLetter(a) || TknFunctionInfixPrefix.isOperator(a) )) return false;            
                if (symbol.length() > 2) {                
                    for (int i=2;i<symbol.length();++i) {
                        if (! isFunctionCharAndSameType (a, symbol.charAt(i))) return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private boolean isFunctionCharAndSameType (char a, char b) {
        if (Character.isLetter(a) && Character.isLetterOrDigit(b)) return true;
        return (TknFunctionInfixPrefix.isOperator(a) && TknFunctionInfixPrefix.isOperator(b));
    }

}
