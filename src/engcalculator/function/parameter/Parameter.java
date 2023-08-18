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

package engcalculator.function.parameter;

import engcalculator.function.CalculusException;
import engcalculator.function.SymbolWithHelp;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 * @param <T>
 */

public final class Parameter<T> implements SymbolWithHelp{
    private final String group;
    private final String symbol;
    private final String parameter;
    private final String help;
    
    private final ConvertIntervalTo<T> converter;
    
    private T value;
    private final T defaultValue;
        
    public Parameter (String group, String symbol, String parameter, String help, T val, ConvertIntervalTo<T> converter) {
        this.value = converter.convertSameType(val);
        this.defaultValue = val;
        this.group = group;
        this.symbol = symbol;
        this.parameter = parameter;
        this.converter = converter;
        this.help = help;
    }

    public T getVal() {
        return value;
    }

    public void setVal(T val) {
        value = converter.convertSameType(val);
    }

    public void setVal(Interval i) throws CalculusException {
        value = converter.convertInterval(i);
    }
    
    public void setDefaultValue () {
        value = converter.convertSameType(defaultValue);
    }
    
    public String getSymbol () {
        return group+symbol;
    }

    public String getParameter () {
        return parameter;
    }

    @Override
    public String toString() {
        return new StringBuilder (getSymbol()).append(" -> ").append(parameter).append(" = ").append(value.toString()).toString();
    }    

    public String getHelp() {
        StringBuilder res = new StringBuilder ();
        res.append("\nGroup: ");
        res.append(group);
        res.append("\nName: ");
        res.append(getSymbol());
        res.append("\nParameter: ");
        res.append(parameter);
        res.append("\nValue: ");
        res.append(value);      
        res.append("\nExpected Input: ");
        res.append(converter.toString());              
        res.append("\nDefault: ");
        res.append(defaultValue);
        res.append("\nHelp: ");
        res.append(help);        
        res.append('\n');
        return res.toString();
    }
    
}
