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
import engcalculator.interval.ListIntervals;
import engcalculator.function.variable.FunctionVariable;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class TknLiteralFunctionVariableLateBinding extends TknLiteral {    

    public TknLiteralFunctionVariableLateBinding() {
        super ();
    }

    protected TknLiteralFunctionVariableLateBinding(String literal, boolean notConvertibleToVarFun) {
        super (literal, notConvertibleToVarFun);
    }

    @Override
    public ListIntervals getListInterval() {
        FunctionVariable fv = FunctionVariable.getFunction(getSymbol());
        if (fv != null) return fv.getValue();
        return super.getListInterval(); //It will return a literal interval...
    }      

    @Override
    public FunctionPrefix getFunctionPrefix() {
        FunctionPrefix fp = FunctionPrefix.getFunction(getSymbol());
        if (fp != null) return fp; 
        return null;
    }
        
    @Override
    public boolean isVariable() {
        return FunctionVariable.getFunction(getSymbol()) != null;
    }
        
    @Override
    public Token generateToken(String symbol) {
        return new TknLiteralFunctionVariableLateBinding(symbol, false);
    }

    @Override
    public boolean mayBeCompatible( String symbol) {
        return (PREFIX_FUNCTION_TKN.mayBeCompatible(symbol));
    }

}
