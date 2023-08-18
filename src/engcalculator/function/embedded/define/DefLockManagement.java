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

package engcalculator.function.embedded.define;

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.expression.ParsingException;
import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.function.Lockable;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class DefLockManagement extends FunctionPrefixSingleInputSingleOutput {
    private final static DomainList DOMAIN = new DomainList(new DomainIntervalLiteral());    
    
    public DefLockManagement(String name, String help, String[] EXAMPLE, String[] RESULT) {
        super("define", name, DOMAIN, true, true, help, EXAMPLE, RESULT);
    }    
    
    protected final String setLock (String fn, boolean locked) throws CalculusException, ParsingException, MeasurementUnitException{
        Lockable f;
        f = FunctionPrefix.getFunction(fn);
        StringBuilder result = new StringBuilder ();
        if (f != null) {
            f.setLocked(locked);
            result.append("Prefix function ").append(fn).append(" lock is ");
            if (locked) result.append ("set");
            else result.append("cleared");
        }
        f = FunctionInfix.getFunction(fn);
        if (f != null) {//there are functions like +,- that are infix and prefix
            f.setLocked(locked);
            result.append(" Infix function ").append(fn).append(" lock is ");
            if (locked) result.append ("set");
            else result.append("cleared");            
        }
        f = FunctionVariable.getFunction(fn);
        if (f != null) {
            f.setLocked(locked);
            result.append(" Variable function ").append(fn).append(" lock is ");
            if (locked) result.append ("set");
            else result.append("cleared");            
        }
        f = MeasurementUnit.getMeasurementUnit(fn);
        if (f != null) {
            f.setLocked(locked);
            result.append(" Measurement unit ").append(fn).append(" lock is ");
            if (locked) result.append ("set");
            else result.append("cleared");            
        }
        if (result.length() != 0) return result.toString();
        return null;
    }
    
    protected final String setAllFunctionsLocks (boolean locked) {
        LinkedList<Function> allFunctions = Function.getFunctions();        
        for (Function f : allFunctions)
            f.setLocked(locked);
        
        List<MeasurementUnit> allMeasUnit  = MeasurementUnit.getMeasurementUnits();        
        for (MeasurementUnit m : MeasurementUnit.getMeasurementUnits())
            m.setLocked(locked);

        StringBuilder result = new StringBuilder ();
        result.append("The locks of all ").append(allFunctions.size()).append(" functions (infix, prefix) and variables plus ");
        result.append(allMeasUnit.size()).append(" measurement units were ");
        if (locked) result.append("set.");
        else result.append("cleared.");                
        
        return result.toString();                
    }    
}
