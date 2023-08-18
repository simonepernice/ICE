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

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class TokenList extends LinkedList <Token> {
    private final static boolean DEBUG = false;

    private Token currentTkn, lastUnderstoodToken;
    private ListIterator<Token> listIterator;
    private boolean isInList, directionForward;
    
    public TokenList () {
    }
    
    public Token get() {
        if (DEBUG) System.out.println(currentTkn);
        return currentTkn;
    }

    public boolean isInList () {
        return isInList;
    }

    private void setLastUnderstoodToken () {
        try {
            if (currentTkn.getListInterval() == null && !TknTerminal.TERMINALS.contains(currentTkn.getSymbol())) lastUnderstoodToken = currentTkn;
        } catch (Exception ex) {
        }
    }

    public void nextToken () {
        nextTokenWOUnderstand();
        setLastUnderstoodToken();
    }

    public void previousToken () {
        previousTokenWOUnderstand();
        setLastUnderstoodToken();
    }

    private void nextTokenWOUnderstand () {
        if (! directionForward) {
            directionForward = true;
            if (listIterator.hasNext()) currentTkn = listIterator.next();
        }
        if (listIterator.hasNext()) {
            currentTkn = listIterator.next();
        } else {
            isInList = false;
        }
    }

    public void previousTokenWOUnderstand () {
        if (directionForward) {
            directionForward = false;
            if (listIterator.hasPrevious()) currentTkn = listIterator.previous();
        }
        if (listIterator.hasPrevious()) {
            currentTkn = listIterator.previous();
        } else {
            isInList = false;
        }        
    }
    
    public void previousSubExpression () {
        thisSubExpression();
        previousToken();
        thisSubExpression();
    }

    public void thisSubExpression () {        
        while (isInList) {
            previousTokenWOUnderstand();
            if (";".equals(currentTkn.getTerminal())) {
                nextToken();
                return;
            }
        }
    }

    public void nextSubExpression () {
        while (isInList) {
            if (";".equals(currentTkn.getTerminal())) {
                nextToken();
                return;
            }
            nextTokenWOUnderstand();
        }     
    }

    public void goToEnd() {
        while (listIterator.hasNext()) currentTkn = listIterator.next();
        setLastUnderstoodToken ();
        isInList = false;
    }

    public void reset () {
        listIterator = listIterator();
        if (listIterator.hasNext()) {
            currentTkn = listIterator.next();
            isInList = true;
        }   
        else {
            currentTkn = null;
            isInList = false;
        }        
        directionForward = true;
    }
    
    public void resync () {//this will update current token which may be altered by the set on retokenize
        if (directionForward) {
            if (listIterator.hasPrevious()) {
                listIterator.previous();
                currentTkn = listIterator.next();
            } else currentTkn = getLast();
        } else {
            if (listIterator.hasNext()) {
                listIterator.next();
                currentTkn = listIterator.previous();
            } else currentTkn = getFirst();
        }
    }    

    @Override
    public String toString  () {
        StringBuilder result = new StringBuilder ();
        for (Token t : this)
            result.append(t.toString());
        result.append('\n');
        return result.toString();
    }

    public String notUnderstoodToString() {
        StringBuilder result = new StringBuilder ();
        boolean found=false;
        for (Token t : this) {
            if (found) result.append(t.toString());
            else if (t == lastUnderstoodToken) found = true;            
        }
        result.append('\n');
        return result.toString();
    }
    
    public String isALabel () {
        String t;
        if (size() == 2 && getFirst() instanceof TknLiteral && (t=getLast().getTerminal()) != null && t.equals(";")) return getFirst().getSymbol();
        return null;
    }
}
