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

import engcalculator.function.SymbolWithHelp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

public final class ParameterManager {//static class
    private final static int EXPECTEDSYMBOLS = 20;
    private final static int EXPECTEDPARAMETERS = 5;
    private final static boolean MATCHWHOLEWORDS = false;
    
    private static final HashMap<String, HashMap<String, Parameter>> SYMBOLS = new HashMap<String, HashMap<String, Parameter>> (EXPECTEDSYMBOLS);    
    
    private ParameterManager() {} //it is not instantiabile

    public static void addParameter (Parameter par) {
        HashMap<String, Parameter> symbol = SYMBOLS.get(par.getSymbol());
        if (symbol == null) {
            symbol = new HashMap<String, Parameter>  (EXPECTEDPARAMETERS);
            SYMBOLS.put(par.getSymbol(), symbol);
        }
        if (  symbol.put(par.getParameter(), par) != null ) throw new RuntimeException ("The same parameter was used twice: "+par.getHelp());
    }

    public static Parameter getParameter (String ssymbol, String sparameter) {
        HashMap<String, Parameter> symbol = getSymbol(ssymbol);
        return getParameter (sparameter, symbol);
    }
    
    public static boolean delParameter (String ssymbol, String sparameter) {
        HashMap<String, Parameter> symbol = SYMBOLS.get(ssymbol);
        if (symbol == null) return false;
        return symbol.remove(sparameter) != null;
    }
    
    public static boolean delSymbol (String ssymbol) {
        return SYMBOLS.remove(ssymbol) != null;
    }    
    
    public static Set<String> getSymbols () {
        return SYMBOLS.keySet();
    }
    
    public static Set<String> getParameters (String ssymbol) {
        HashMap<String, Parameter> symbol = getSymbol(ssymbol);
        if (symbol != null) {
            Set<String> res = new HashSet<String> ();
            for (Parameter p : symbol.values())
                res.add(p.toString());
            return res;
        }
        return null;
    }    
    
    private static HashMap<String, Parameter> getSymbol (String ssymbol) {
        if (ssymbol == null) return null;
        HashMap<String, Parameter> symbol;
        symbol = SYMBOLS.get(ssymbol);
        if (symbol == null) {
            if (MATCHWHOLEWORDS) return null;
            else {
                String s=null;
                for (String sym : SYMBOLS.keySet())
                    if (sym.startsWith(ssymbol)) {
                        if (s==null) s = sym;
                        else return null; //undefined because at least two symbols begins with same strings
                    }
                if (s == null) return null;
                symbol = SYMBOLS.get(s);
            }
        }
        return symbol;
    }
    
    private static Parameter getParameter (String sparameter, HashMap<String, Parameter> symbol) {
        if (symbol == null) return null;        
        Parameter parameter = symbol.get(sparameter);
        if (parameter == null) {
            if (MATCHWHOLEWORDS) return null;
            else {
                String p=null;
                for (String par : symbol.keySet() )
                    if (par.startsWith(sparameter)) {
                        if (p==null) p = par;
                        else return null;//undefined because at least two parameters begins with same strings
                    }
                if (p == null) return null;
                parameter = symbol.get(p);
            }
        }
        return parameter;
    }
    
    public static List<SymbolWithHelp> getParameters () {
        LinkedList<SymbolWithHelp> res = new LinkedList<SymbolWithHelp> ();
        for (HashMap<String, Parameter> ps : SYMBOLS.values())
            for (Parameter p : ps.values())
                res.add(p);
        return res;
    }
    
    public static void initialize () {
        for (HashMap<String, Parameter> symbol : SYMBOLS.values())
            for (Parameter par : symbol.values())
                par.setDefaultValue();
    }
    
}
