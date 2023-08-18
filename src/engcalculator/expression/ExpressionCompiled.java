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

package engcalculator.expression;

import engcalculator.expression.token.TokenList;
import engcalculator.interval.ListIntervals;
import engcalculator.function.Function;
import engcalculator.function.prefix.FunctionPrefix;
import java.util.HashMap;
import java.util.LinkedList;

public final class ExpressionCompiled extends Expression {
    private final InterpeterCompiled compiler;

    public ExpressionCompiled (String expression, LinkedList<String> varfunc) throws Exception {
//        super ();
        initializer (new String[] {expression}, varfunc);
//        this.expression = expression;
//        interpeter = new InterpeterCompiled (tokenizer.tokenize(varfunc, expression));
        compiler = (InterpeterCompiled) interpeter[0];
    }

    @Override
    protected Interpeter getInterpeter(TokenList exp) {
        return new InterpeterCompiled(exp); 
    }

    @Override
    public ListIntervals evaluate(HashMap<String, FunctionPrefix> parameters ) throws Exception {
        if (DEBUG) System.out.println("Evaluating compiled expression: "+expression+" with parameters: "+parameters);
        
        tokenizer.setParameters(parameters);

        compiler.compile(parameters);
        final boolean isdc = Function.getInputSizeDomainCheck();
        Function.setInputSizeDomainCheck(false);//disable function checks
        ListIntervals result;
        try {
            result = compiler.execute(parameters);
        } finally {
            Function.setInputSizeDomainCheck(isdc); //set function checks as before
        }
        return result;

    }

    @Override
    public String toString(){
        String compiledCode = compiler.toString();
        if (compiledCode != null) return new StringBuilder ("Compiled expression code: ").append(compiledCode).toString();
        return new StringBuilder ("Compiled expression: ").append(expression).toString(); //the compiled expression is not yet available, it requires to be called once to generate the compiled code
    }
}
