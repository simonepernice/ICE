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
import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.function.variable.FunctionVariable;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class TknVariableLateBinding extends Token {
    private final static TknFunctionPrefix PREFIX_FUNCTION_TKN = new TknFunctionPrefix ();
    private final static TknLiteral LITERAL_TKN = new TknLiteralFunctionVariableLateBinding();

    private final FunctionVariable variable;
    private final boolean earlyBinding;

    public TknVariableLateBinding() {
        variable = null;
        earlyBinding = false;
    }

    private TknVariableLateBinding(String name, FunctionVariable variable, boolean earlyBinding) {
        super (name);
        this.variable = variable;
        this.earlyBinding = earlyBinding;
    }

    @Override
    public ListIntervals getListInterval() throws CalculusException {   
        FunctionVariable v = variable;
        if (! earlyBinding) v = FunctionVariable.getFunction(getSymbol()); //early binding to freeze variables if locker or if defining functions
        if (v == null) {
            Function.throwNewCalculusException ("The variable called "+getSymbol()+" does not exist any longer.");
            return null;
        }
        return v.getValue();                                                   
    }

    @Override
    public Token generateToken(String symbol) {
        FunctionVariable v = FunctionVariable.getFunction(symbol);
        if (v != null) {
            return new TknVariableLateBinding (symbol, v, isDefiningFunction() || v.isLocked());
        }
        return null;
    }

    //That is used if the variable was deleted previously
    @Override
    public Token regenerateToken() {
        if (FunctionVariable.getFunction(getSymbol()) == null) return LITERAL_TKN.generateToken(getSymbol());
        return super.regenerateToken();
    }

    @Override
    public boolean mayBeCompatible(String symbol) {
        return PREFIX_FUNCTION_TKN.mayBeCompatible(symbol);
    }

    @Override
    public boolean isVariable() {
        return true;
    }
    
}
