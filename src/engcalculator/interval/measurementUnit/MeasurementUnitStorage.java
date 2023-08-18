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

package engcalculator.interval.measurementUnit;

import engcalculator.expression.ParsingException;
import engcalculator.expression.token.TknFunctionPrefix;
import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

public final class MeasurementUnitStorage {   
    private static final boolean DECLAREUNITSSAMEVALUE = false; //if yes it gives problem with recognition
    private static final boolean CREATEVARIABLEWITHUNITNAME = true; //if yes it gives problem with recognition
    private static final TknFunctionPrefix TOKENFUNCTIONPREFIX = new TknFunctionPrefix();           
    
    private static final int BASESIZE = 7;
    private final List <MeasurementUnit> measurementUnitList;
    private final List <MeasurementUnit> baseMeasurementUnitList;

    public MeasurementUnitStorage(int size) {
        measurementUnitList = new ArrayList <MeasurementUnit> (50);
        baseMeasurementUnitList = new ArrayList <MeasurementUnit> (BASESIZE+1);        
    }
    
    public int getBaseSize () {
        return BASESIZE;
    }
    
    public List <MeasurementUnit> getBaseMeasurementUnits () {
        return baseMeasurementUnitList;
    }
    
    public List <MeasurementUnit> getMeasurementUnits () {
        return measurementUnitList;
    }
    
    public boolean isInitialized () {
        return baseMeasurementUnitList.size() > 0;
    }
        
    public void initialize () {
        //The order is important: basic unit first 
        measurementUnitList.clear();
        baseMeasurementUnitList.clear();
        boolean allUnitsAdded = true;
        try {
            {
                int[] base = {0,0,0,0,0,0,0};
                allUnitsAdded &=  addMeasurementUnit (new MeasurementUnit("1", "pure", base, true)); //cannot use PURE because it changes with symbols rad srad so at a new kernel boot it would rise an exception
            }            
            {
                int[] base = {1,0,0,0,0,0,0};
                MeasurementUnit m = new MeasurementUnit("m", "metre, length", base, true);
                baseMeasurementUnitList.add(m);
                allUnitsAdded &= addMeasurementUnit(m);
            }
            {
                int[] base = {0,1,0,0,0,0,0};
                MeasurementUnit kg = new MeasurementUnit("kg", "kilogram, mass", base, true);
                baseMeasurementUnitList.add(kg);
                allUnitsAdded &= addMeasurementUnit(kg);
            }
            {
                int[] base = {0,0,1,0,0,0,0};
                MeasurementUnit s = new MeasurementUnit("s", "second, time", base, true);
                baseMeasurementUnitList.add(s);
                allUnitsAdded &= addMeasurementUnit(s);
            }
            {
                int[] base = {0,0,0,1,0,0,0};
                MeasurementUnit A = new MeasurementUnit("A", "ampere, electric current", base, true);
                baseMeasurementUnitList.add(A);
                allUnitsAdded &= addMeasurementUnit(A);
            }
            {
                int[] base = {0,0,0,0,1,0,0};
                MeasurementUnit K = new MeasurementUnit("K", "kelvin, temperature", base, true);
                baseMeasurementUnitList.add(K);
                allUnitsAdded &= addMeasurementUnit(K);
            }
            {
                int[] base = {0,0,0,0,0,1,0};
                MeasurementUnit mol = new MeasurementUnit("mol", "mole, amount of substance", base, true);
                baseMeasurementUnitList.add(mol);
                allUnitsAdded &= addMeasurementUnit(mol);
            }
            {
                int[] base = {0,0,0,0,0,0,1};
                MeasurementUnit cd = new MeasurementUnit("cd", "candela, luminescent intensity", base, true);
                baseMeasurementUnitList.add(cd);
                allUnitsAdded &= addMeasurementUnit(cd);
            }          
            
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("rad", "radian, angle", "m/m", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("sr", "steradian, solid angle", "m2/m2", true));
            
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("Bq", "becquerel, radioactivity (decays per unit time)", "1/s", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("Hz", "hertz, frequency", "1/s", true));
            
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("N", "newton, force and weight", "kg m / s2", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("Pa", "pascal, pressure stress", "N/m2", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("J", "jule, energy or work or heat", "N m", true));                        
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("W", "watt, power or radiant flux", "J/s", true));            
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("C", "coulumb, electric charge or quantity of electircity", "s A",true));                                    
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("V", "volt, voltage or electromotive force", "W/A", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("F", "farad, electric capacitance", "C/V", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("Ohm", "ohm, electric impedance or resistance or reactance", "V/A", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("S", "siemens, electric conductance", "A/V", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("Wb", "weber, magnetic flux", "V s", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("T", "tesla, magnetic flux strength", "Wb/m2", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("H", "henry, inductance", "Wb/A", true));
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("lm", "lumen, luminos flux", "cd", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("lx", "lux, illuminance", "cd/m2", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("Gy", "gray, absorbed dose of ionizing radiation", "J/kg", true));
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("Sv", "sievert, equivalent dose of ionizing radiation", "J/kg", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit("kat", "katal, catalytic activity", "mol/s", true));
            
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "area", "m2", true));
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "volume", "m3", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "speed", "m/s", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "acceleration", "m/s2",  true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "jerk", "m/s3", true));
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "angular velocity", "rad/s", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "momentum", "N s", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "angolar momentum", "N m s",  true));
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "torque", "N m ", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "density", "kg/m3", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "amount of substance concentration", "mol/m3 ", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "molar volume", "m3/mol ", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "entropy", "J/K", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "molar heat capacity", "J/K mol", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "specific entropy", "J/K kg", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "molar energy", "J/mol", true));
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "specific energy", "J/kg", true));
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "energy density", "J/m3", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "surface tension", "N/m", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "heat flux density", "W/m2", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "thermal conductivity", "W/m K", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "kinematik viscosity", "m2/s", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "dynamic viscosity", "Pa s", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "conductivity", "S/m", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "molar conductivity", "S m2/mol", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "permittivity", "F/m", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "permeability", "H/m", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "electric field strength", "V/m", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "magnetic field strength", "A/m", true));
            if (DECLAREUNITSSAMEVALUE) allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "luminance", "cd/m2", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "exposure (X and gamma rays)", "C/kg", true));
            allUnitsAdded &= addMeasurementUnit(MeasurementUnit.parseMeasurementUnit(null, "absorbed dose rate", "Gy/s", true));
            
            if (! allUnitsAdded) throw new RuntimeException ("Internal Error: it was not possible to add all the measurements unit");
        } catch (Exception ce) {
            throw new RuntimeException ("Unexpected error in measurement unit static initialization "+Arrays.toString(ce.getStackTrace()));
        } 
        
    }    
    
    public boolean addMeasurementUnit (MeasurementUnit mToAdd) {
        int i;
        final int s = measurementUnitList.size(); 
        for (i=0; i<s; ++i) {//refuse if symbol already present
            if (measurementUnitList.get(i).hasSymbol(mToAdd.getSymbol())) return false;
        }
        
        for (i=0; i<s; ++i) {//add symbol if measurement unit already present            
            MeasurementUnit mStored = measurementUnitList.get(i);            
            if (mToAdd.equals(mStored)) {
                //System.out.println(mToAdd.getSymbol()+" has an equivalent measurement unit already present: "+mStored.getSymbol());
                mStored.addSymbol(mToAdd.symbols);
                AddMeasurementUnitVariable (mToAdd.symbols);
                return true;
            }            
        }        
            
        final int mToAddP = mToAdd.totalAbsExpVal();
    
        for (i=0; i<s; ++i) {//add in total abs exponent val decreasing order (to match better later on toString)
            MeasurementUnit mStored = measurementUnitList.get(i);            
            int mStoredP = mStored.totalAbsExpVal();
            if (mToAddP > mStoredP) {
                measurementUnitList.add(i, mToAdd);
                break;
            } else if (mToAddP == mStoredP) {
                measurementUnitList.add(i, mToAdd);
                break;                
            }
        }
        if (i == s) measurementUnitList.add(mToAdd);
        
        AddMeasurementUnitVariable (mToAdd.symbols);
        
        return true;
    }

    public boolean delMeasurementUnit (String symbol) {
        int i;
        MeasurementUnit m=null;
        for (i=0; i<measurementUnitList.size(); ++i) {
            if ((m=measurementUnitList.get(i)).hasSymbol(symbol)) break;
        }
        if (i == measurementUnitList.size()) return false;
        m.delSymbol(symbol);
        if (! m.hasAnySymbol()) measurementUnitList.remove(i);
        DelMeasurementUnitVariable (symbol);
        return true;
    }    
    
    private void AddMeasurementUnitVariable (Symbols s) {
        if (CREATEVARIABLEWITHUNITNAME) {
            for (String sym : s.getSymbols()) {
                if (TOKENFUNCTIONPREFIX.mayBeCompatible(sym)) {
                    if (FunctionVariable.getFunction(sym) == null) {
                        FunctionVariable muName = new FunctionVariable (sym, new ListIntervals(new IntervalLiteral(sym)), true, "Created automatically when measurementUnit group is loaded: "+s.getDescription());
                        try {
                            FunctionVariable.addFunction(muName);
                        } catch (Exception ex) {
                            //this should not happen because the name is checked to be well formed
                        }
                    }
                }
            }
        }        
    }
    
    private void DelMeasurementUnitVariable (String sym) {
        if (CREATEVARIABLEWITHUNITNAME) {
            if (TOKENFUNCTIONPREFIX.mayBeCompatible(sym)) {
                FunctionVariable fv = FunctionVariable.getFunction(sym);
                if (sym.equals(fv.getValue().getFirst().getName())) {
                    fv.setLocked(false);
                    FunctionVariable.delFunction(sym);
                }
            }
        }        
    }    
    
    public MeasurementUnit getMeasurementUnit (String symbol) { 
        for (MeasurementUnit m : measurementUnitList) 
            if (m.hasSymbol(symbol)) return m;
        return null;
    }  
             
    public MeasurementUnit parseMeasurementUnit (String symbol) throws CalculusException, ParsingException, MeasurementUnitException{ 
        MeasurementUnit mu = MeasurementUnit.parseMeasurementUnit("", "", symbol, true);
        
        for (MeasurementUnit m : measurementUnitList) 
            if (m.equals(mu)) return m;
            
        return mu;
    }
             
    public MeasurementUnit parseMeasurementUnit (String symbol, String description, String definition, boolean locked) throws ParsingException, CalculusException, MeasurementUnitException {
        definition = definition.trim();
        if (symbol == null || symbol.length() == 0) {
            StringBuilder s = new StringBuilder ();
            boolean prevSpace = false;
            for (char c : definition.toCharArray()) {
                if (c != ' ') {
                    s.append(c);
                    prevSpace = false;
                } else if (! prevSpace) {
                    s.append(' ');
                    prevSpace = true;
                }
            }
            symbol = new StringBuilder().append('(').append(s).append(')').toString();
        }
        MeasurementUnitAccumulator result = new MeasurementUnitAccumulator(new MeasurementUnit(symbol, description, definition, locked));
        
        StringBuilder def = new StringBuilder (definition);

        boolean up = true;
        MeasurementUnit mu;
        MeasurementUnitAccumulator mua;
        while ((mu = readNextMeasurementUnit(def)) != null){
            float pow = readNextPower (def);
            if (! up) pow = - pow;
            if (pow == 1.) {
                result.add(mu);
            } else if (pow == -1.) {
                result.sub(mu);
            } else if (pow == 0.) {
                continue;
            } else {
                mua = new MeasurementUnitAccumulator(mu);
                mua.mul(pow);
                result.add(mua);                
            }
                
            if (isNextDivisionSymbol(def)) up = (! up);            
        }        
        
        if (def.length() > 0) throw new ParsingException ("Not understood measurement unit: "+def.toString());
        
        return result.toMeasurementUnit();
    }   
    
    private static void skipSpaces (StringBuilder def) {        
        while (def.length() > 0 && def.charAt(0) == ' ') {
            def.deleteCharAt(0);
        }
    }

    private MeasurementUnit readNextMeasurementUnit(StringBuilder def) throws CalculusException, ParsingException, MeasurementUnitException {        
        skipSpaces (def);        
        
        StringBuilder mus = new StringBuilder ();
        char c;
        while (def.length() > 0 && (Character.isLetter(c=def.charAt(0)) || c == '1' || c == '(')) {
            if (c == '(') {
                int par = 1;
                def.deleteCharAt(0);
                while (def.length() > 0) {
                    if ((c=def.charAt(0)) == ')') -- par;
                    else if (c == '(') ++par;
                    if (par == 0) break;
                    mus.append(c);
                    def.deleteCharAt(0);
                }
                if (def.length() > 0) def.deleteCharAt(0);
                return MeasurementUnit.parseMeasurementUnit(mus.toString());
            }
            def.deleteCharAt(0);
            mus.append(c);
            if (c=='1') break;
        }
        
        if (mus.length() <= 0) return null;
        MeasurementUnit mu = getMeasurementUnit(mus.toString());        
        if (mu == null) Function.throwNewMeasurementUnitException("Not recognized measurement unit "+mus);
        return mu;
    }

    private float readNextPower(StringBuilder def) throws MeasurementUnitException {
        skipSpaces (def);        
        
        StringBuilder mus = new StringBuilder ();
        char c;
        while (def.length() > 0 && (Character.isDigit(c=def.charAt(0)) || c == '.' || c == '-')) {
            def.deleteCharAt(0);
            mus.append(c);
        }
        
        if (mus.length() <= 0) return 1;
        float result;
        try {
            result=Float.valueOf(mus.toString());
        } catch (NumberFormatException nfe) {
            Function.throwNewMeasurementUnitException("Not recognized measurement unit exponent "+mus);
            return 0.f;
        }
        return result;
    }

    private boolean isNextDivisionSymbol(StringBuilder def) {
        skipSpaces (def);
        
        if (def.length() > 0 && def.charAt(0) == '/') {
            def.deleteCharAt(0);
            return true;
        }

        return false;
    }                

}
