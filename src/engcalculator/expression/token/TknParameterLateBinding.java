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
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class TknParameterLateBinding extends TknFunctionPrefix {//late binding function known by name
    private Tokenizer tokenizer;

    public TknParameterLateBinding () {
        tokenizer = null;
    }
    
    public void setTokenizer (Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    private TknParameterLateBinding (String functionName, Tokenizer tokenizer) {
        super (functionName, null);
        this.tokenizer = tokenizer;
    }

    @Override
    public FunctionPrefix getFunctionPrefix () {
        FunctionPrefix f = tokenizer.getParameters().get(getSymbol());
        if (f == null || f.getNumbArgs() == 0) return null;
        return f;
    }

    @Override
    public ListIntervals getListInterval() throws Exception {
        FunctionPrefix f = tokenizer.getParameters().get (getSymbol());
        if (f == null || f.getNumbArgs() > 0) return null;
        return f.compute();
    }

    @Override
    public Token generateToken(String symbol) {
        if (! (isDefiningFunction() && tokenizer.getParametersName().contains(symbol))) return null;
        return new TknParameterLateBinding(symbol, tokenizer);
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    @Override
    public Token regenerateToken() {
        if (tokenizer.getParameters().get(getSymbol()) != null) return this;
        return super.regenerateToken();
    }

    @Override
    public boolean mayBeCompatible(String symbol) {
        return isDefiningFunction() && super.mayBeCompatible(symbol); 
    }
    
    
}
