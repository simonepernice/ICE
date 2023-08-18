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
import engcalculator.function.CalculusException;
import engcalculator.function.Lockable;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.SymbolWithHelp;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

public final class MeasurementUnit implements SymbolWithHelp, Lockable{   
    private static final boolean BRACKETSFORNICEPRINT = false;
    private static final boolean PRINTNEGATIVEEXP = true;
    public static final MeasurementUnit PURE ;              
       
    protected static final MeasurementUnitStorage MEASUREMENT_UNIT_STORAGE = new MeasurementUnitStorage(50);
    protected static final int BASESIZE = MEASUREMENT_UNIT_STORAGE.getBaseSize();
    protected static final List <MeasurementUnit> MEASUREMENTUNITLIST = MEASUREMENT_UNIT_STORAGE.getMeasurementUnits();
    protected static final List <MeasurementUnit> BASEMEASUREMENTUNITLIST = MEASUREMENT_UNIT_STORAGE.getBaseMeasurementUnits();
        
    static {//create the base for all other units   
     
        //The order is important: basic unit first
        try {
            
            {
                int[] base = {0,0,0,0,0,0,0};
                PURE = new MeasurementUnit("1", "pure", base, true);
            }
        } catch (Exception ce) {
            throw new RuntimeException ("Unexpected error in measurement unit static initialization "+Arrays.toString(ce.getStackTrace()));
        }
    }
        
    public static void initialize () {
        MEASUREMENT_UNIT_STORAGE.initialize();
    }    

    public static boolean isInitialized () {
        return MEASUREMENT_UNIT_STORAGE.isInitialized();
    }    
    
    public static boolean addMeasurementUnit (MeasurementUnit mToAdd) {
        return MEASUREMENT_UNIT_STORAGE.addMeasurementUnit(mToAdd);
    }

    public static boolean delMeasurementUnit (String symbol) {
        return MEASUREMENT_UNIT_STORAGE.delMeasurementUnit(symbol);
    }    
      
    public static MeasurementUnit getMeasurementUnit (String symbol) { 
        return MEASUREMENT_UNIT_STORAGE.getMeasurementUnit(symbol);
    }
    
    public static List<MeasurementUnit> getMeasurementUnits () { 
        return MEASUREMENT_UNIT_STORAGE.getMeasurementUnits();
    }    

    public static MeasurementUnit parseMeasurementUnitNoInternalException (String symbol) { 
        try {
            return MEASUREMENT_UNIT_STORAGE.parseMeasurementUnit(symbol);
        } catch (Exception e) {
            throw new RuntimeException ("Internal error tring to parse measurement unit "+symbol+". Message: "+e.getMessage());
        }
    }
    
    public static MeasurementUnit parseMeasurementUnit (String symbol) throws CalculusException, ParsingException, MeasurementUnitException{ 
        return MEASUREMENT_UNIT_STORAGE.parseMeasurementUnit(symbol);
    }
             
    public static MeasurementUnit parseMeasurementUnit (String symbol, String description, String definition, boolean locked) throws ParsingException, CalculusException, MeasurementUnitException {
        return MEASUREMENT_UNIT_STORAGE.parseMeasurementUnit(symbol, description, definition, locked);
    }   
    
    final ExponentAccumulator[] base;
    Symbols symbols;
    String definition;
    boolean locked;   
    
    public MeasurementUnit (MeasurementUnit mu) {
        this.symbols = new Symbols (mu.symbols);        

        this.definition = mu.definition;
        
        base = createBase(mu.base);

        this.locked = mu.locked;
    }
    
    protected MeasurementUnit (String symbol, String description, String definition, boolean locked) {
        this.symbols = new Symbols ();        
        this.symbols.addSymbolDescription (symbol, description);
        
        this.locked = locked;
        
        this.definition = definition;

        base = createBase();              
    }  
    
    MeasurementUnit (String symbol, String description, int[] base, boolean locked) throws CalculusException {
        this.symbols = new Symbols ();        
        this.symbols.addSymbolDescription (symbol, description);
        
        definition = "System defined by base";
        
        this.base = createBase(base);
        
        this.locked = locked;
    }    
    
    private ExponentAccumulator[] createBase ()  {
        ExponentAccumulator[] res = new ExponentAccumulator[BASESIZE];
        for (int i=0; i < BASESIZE; ++i)
            res[i] = new ExponentAccumulator();
        return res;
    }    
    
    private ExponentAccumulator[] createBase (ExponentAccumulator[] toCopy)  {
        ExponentAccumulator[] res = new ExponentAccumulator[BASESIZE];
        for (int i=0; i < BASESIZE; ++i)
            res[i] = new ExponentAccumulator(toCopy[i]);
        return res;
    }

    private ExponentAccumulator[] createBase (int[] toCopy)  throws CalculusException {
        ExponentAccumulator[] res = new ExponentAccumulator[BASESIZE];
        for (int i=0; i < BASESIZE; ++i)
            res[i] = new ExponentAccumulator(toCopy[i]);
        return res;
    }
    
    private byte getNormalBase () {
        int res = -1;
        for (int i=0;i<BASESIZE;++i) {
            if (base[i].equals(Exponent.ONE)) {
                if (res == -1) 
                    res = i;
                else 
                    return -1;                
            } else {
                if (! base[i].equals(Exponent.ZERO)) return -1;
            }
        }
        return (byte) res;
    }        

    public String getSymbol() {
        return symbols.getSymbol();
    }
    
    void addSymbol (Symbols sym) {
        symbols.addSymbols (sym);
    }
    
    boolean delSymbol (String sym) {
        return symbols.delSymbol(sym);
    }    
            
    boolean hasSymbol (String sym) {
        return symbols.hasSymbol(sym);
    }       
    
    boolean hasAnySymbol () {
        return symbols.hasAnySymbol();
    }

    public String getDefinition() {
        return definition;
    }        

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }
    
    int totalAbsExpVal () {
        int comp = base[0].absVal();
        for (int i=1;i<BASESIZE;++i) {
            comp += base[i].absVal();
        }
        return comp;
    } 
    
    @Override
    public boolean equals (Object o) {
        if (o == null) return false;
        if (this == o) return true;
        MeasurementUnit m = (MeasurementUnit) o;
        for (int i=0;i<BASESIZE;++i) 
            if (! base[i].equals(m.base[i])) return false;
        return true;
    }           
    
    public StringBuilder toStringBuilder () {
        List<MeasurementUnit> muList = new LinkedList <MeasurementUnit> ();
        List<ExponentAccumulator> pwList = new LinkedList <ExponentAccumulator> ();
        MeasurementUnitAccumulator muaTest = new MeasurementUnitAccumulator(this);
        ExponentAccumulator bestRatio = null;
        MeasurementUnit bestMU = null;
        for (MeasurementUnit m : MEASUREMENTUNITLIST) {//Try to fit with power closer to 1 of a known measurement unit
            ExponentAccumulator ratio = muaTest.hasSamePwrRatio(m);
            if (ratio != null) {
                if (ratio.equals(Exponent.ONE)) {
                    bestRatio = ratio;
                    bestMU = m;
                    muaTest.set(PURE);
                } else {
                    if (bestRatio == null || ratio.distance(Exponent.ONE) < bestRatio.distance(Exponent.ONE)) {
                        bestRatio = ratio;
                        bestMU = m;
                    } 
                }
            }
            if (muaTest.equals(PURE)) break;
        }
        
        if (bestRatio != null) {//It was found an integer power of a known measurement unit
            muList.add(bestMU);
            pwList.add(bestRatio);
        } else {//If not possible to fit with any integer power known, so it is split on the base measurement units 
            for (MeasurementUnit m : BASEMEASUREMENTUNITLIST) {
                ExponentAccumulator cbp = muaTest.getCurrentBase(m.getNormalBase());
                if (! cbp.equals(Exponent.ZERO)) {
                    muList.add(m);
                    pwList.add(cbp); 
                    muaTest.sub(m);
                }
            }            
        }
  
        StringBuilder res = new StringBuilder();
        final int s = muList.size();
        boolean findup = false, finddown = false;
        for (int i=0;i<s;++i) {
            ExponentAccumulator e = pwList.get(i);
            if (PRINTNEGATIVEEXP || e.isPositive()) {
                findup = true;
                res.append(muList.get(i).getSymbol());                
                if (! e.equals(Exponent.ONE)) res.append(e.toString());
                res.append(' ');
            } else {
                finddown = true;
            }
        }        
        
        if (!PRINTNEGATIVEEXP && finddown) {
            if (findup) res.append("/ ");
            else res.append("1 / ");
        
            for (int i=0; i<s;++i) {
                ExponentAccumulator e = pwList.get(i);
                if (! e.isPositive()) {
                    res.append(muList.get(i).getSymbol());
                    if (! e.equals(Exponent.MINUSONE)) res.append(new ExponentAccumulator().sub(e).toString());
                    res.append(' ');
                }
            }
        }
        
        int l = res.length();
        if (l > 0 && res.charAt(-- l) == ' ') res.setLength(l);

        
        return res;
    }
    
    @Override
    public String toString () {
        StringBuilder res = toStringBuilder();
        
        res.insert(0, ' ');
        
        if (BRACKETSFORNICEPRINT && res.length() > 0) {            
            res.insert(0, '(');
            res.append(')');
        }
        
        
        return res.toString();        
    }
    
    public String getHelp () {
        StringBuilder res = new StringBuilder ();
        res.append("\nGroup: measurementUnit");
        res.append("\nName: ");
        res.append(getSymbol());
        res.append("\nDefinition: ");
        res.append(getDefinition());        
        res.append("\nType: Measurement Unit");
        res.append("\nSynonyms:\n");
        res.append(symbols.toString());
        res.append("Base value: ");
        for (MeasurementUnit m : BASEMEASUREMENTUNITLIST) {
            ExponentAccumulator e = base[m.getNormalBase()];
            if (e.equals(Exponent.ZERO)) continue;
            res.append(m.getSymbol());
            res.append(e.toString());
            res.append(" ");            
        }
        res.append('\n');
        return res.toString();
    }

}
