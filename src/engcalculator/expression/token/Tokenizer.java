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

import engcalculator.expression.ParsingException;
import engcalculator.function.prefix.FunctionPrefix;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class Tokenizer { //just a container for all types of components findable
    public static final boolean DEBUG = false;
    
    private final static TknParameterLateBinding tplb;
    private final static LinkedList<Token> tokens;    
    static {
        //Warning the order of insertion is list is also the priority order for token recognizing do not change it!!!
        tokens = new LinkedList<Token> ();
        tokens.add(new TknIntervalPoint());
        tokens.add(tplb = new TknParameterLateBinding());
        tokens.add(new TknVariableLateBinding());
        tokens.add(new TknFunctionPrefix());
        tokens.add(new TknFunctionInfixPrefix());
        tokens.add(new TknLiteral());
        tokens.add(new TknLiteralFunctionVariable());
        tokens.add(new TknLiteralFunctionVariableLateBinding());
        tokens.add(new TknTerminal());
        tokens.add(new TknSpace());        
    }        
    
    private HashMap <String, FunctionPrefix> parameters;
    private LinkedList <String> parametersName;
    
    public Tokenizer () {
        //this may be wronkg because if a function is called withing another when it returns lose the previous parameters
        //the simple fix would be to avoid tokens to be static but that increases the memory requirement
        tplb.setTokenizer (this);         
    }

    public HashMap<String, FunctionPrefix>  getParameters() {
        return parameters;
    }

    public LinkedList<String> getParametersName() {
        return parametersName;
    }

    public void setParameters(HashMap<String, FunctionPrefix> parameters) {
        this.parameters = parameters;
    }

    // Create the list of tokens required to interpreter the expression
    public TokenList tokenize (LinkedList <String> parametersName, String expression) throws Exception {
        this.parametersName = parametersName;
        Token.setDefiningFunction (parametersName != null);


        TokenList tokenizedExpression = new TokenList ();

        Token token;
        String tokenSymbol;
        int index;
        boolean found;

        while (expression.length() > 0) {

            index = 0;
            found = false;
            while (++index <= expression.length()) {
                found = false;
                token = null;
                tokenSymbol = expression.substring(0, index);
                for (Token t : tokens)
                    if (t.mayBeCompatible(tokenSymbol)) {
                        found = true;
                        break;
                    }
                if (! found) break; //it was not found any item compatible
            }
            if ( !found && index == 1) throw new ParsingException("It is not possible find any token at "+expression);

            do {
                --index;
                tokenSymbol = expression.substring(0, index);

                token = null;
                for (Token t : tokens)
                    if (t.mayBeCompatible(tokenSymbol)) {
                        token = t.generateToken(tokenSymbol);
                        if (token != null) {
                            if (token.isSignificant()) {
                                tokenizedExpression.add(token);
                            }
                            break;
                        }
                    }
            } while (token == null && index > 1);
            if (token == null) throw new ParsingException("It is not possible recognize the token at "+expression);
            expression = expression.substring(index);

        }

        if (DEBUG) System.out.println ("Tokenized: "+tokenizedExpression.toString());

        return tokenizedExpression;
    }

    public static void retokenize (TokenList tl) {
        ListIterator<Token> li = tl.listIterator();
        Token t;
        while (li.hasNext()) {
            t = li.next();
            li.set(t.regenerateToken());
        }
  
        tl.resync();
        if (DEBUG) System.out.println ("Retokenized: "+tl.toString());
    }
}
