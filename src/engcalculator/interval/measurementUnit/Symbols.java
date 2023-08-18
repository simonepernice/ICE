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

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class Symbols {
    private final List<String> symbols;
    private final List<String> description;
    private int lastSymbolUsed;
    
    public Symbols () {
        symbols = new LinkedList<String> ();
        description = new LinkedList<String> ();
        lastSymbolUsed = -1;
    }
    
    public Symbols (Symbols mus) {
        symbols = new LinkedList<String>(mus.symbols);
        description = new LinkedList<String>(mus.description);
        lastSymbolUsed = mus.lastSymbolUsed;
    }
    
    public String getSymbol() {
        if (lastSymbolUsed < 0) return null;
        return symbols.get(lastSymbolUsed);
    } 
    
    public List<String> getSymbols () {
        return symbols;
    }    
    
    public String getDescription () {
        if (lastSymbolUsed < 0) return null;
        return description.get(lastSymbolUsed);
    }    
    
    public void addSymbols (Symbols sym) {
        final int s = sym.symbols.size();
        for (int i=0;i<s;++i)
            addSymbolDescription(sym.symbols.get(i), sym.description.get(i));
    }
    
    public void addSymbolDescription (String sym, String desc) {
        int i;
        for (i=0;i<symbols.size();++i)
            if (sym.length()<symbols.get(i).length()) {
                symbols.add(i,sym);
                description.add(i, desc);
                break;
            }
        if (i==symbols.size()) {
            symbols.add(sym);
            description.add(desc);
        }
        lastSymbolUsed = i;
    }  
    
    public boolean delSymbol (String sym) {
        int i = symbols.indexOf(sym);

        if (i < 0) return false;
        symbols.remove(i);
        description.remove(i);
        
        if (lastSymbolUsed >= i) -- lastSymbolUsed;
        if (lastSymbolUsed < 0) lastSymbolUsed = 0;

        return true;
    }     
    
    public boolean hasSymbol (String sym) {
        int i = symbols.indexOf(sym);
        if (i < 0) return false;
        lastSymbolUsed = i;
        return true;
    }     
    
    public boolean hasAnySymbol () {
        return symbols.size() > 0;
    }    

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i=0; i<symbols.size(); ++i) 
            res.append(" Description: ").append(description.get(i)).append("\n  Symbol: ").append(symbols.get(i)).append("\n");
        return res.toString();    
    }
    
    
}
