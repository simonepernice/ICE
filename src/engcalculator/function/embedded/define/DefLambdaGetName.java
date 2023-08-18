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

import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public final class DefLambdaGetName extends FunctionPrefix {
    private static final LinkedList<Integer> tempFunctions = new LinkedList<Integer> ();
    private static int nextTempFunctionIndex = 0;
    private final static String TMPFUNCNAME = "temporaryLambdaFunction";
    
    public static String getNextLambdaFunctionNameString () {
        String tfName;        
        do {
            tfName = TMPFUNCNAME+nextTempFunctionIndex;
            ++nextTempFunctionIndex;
        } while (FunctionPrefix.getFunction(tfName) != null);
        tempFunctions.add(nextTempFunctionIndex-1);
        return tfName;
    }
    
    public static ListIntervals getNextLambdaFunctionName () {
        return new ListIntervals (new IntervalLiteral (getNextLambdaFunctionNameString()));
    }
    
    public static String getPreviousLambdaFunctionString (int i) throws Exception {
        i += tempFunctions.size();
        if (i < 0 || i >= tempFunctions.size()) throwNewCalculusException ("The given index "+i+" is out of range, it should be between 0 and "+tempFunctions.size());
        String tfName = TMPFUNCNAME + tempFunctions.get(i);
        return tfName;
    }
    
    public static ListIntervals getPreviousLambdaFunctionName (int i) throws Exception {
        return new ListIntervals (new IntervalLiteral (getPreviousLambdaFunctionString(i)));
    }    
    
    public static void delAllLambdaFunctions () {
        while (! tempFunctions.isEmpty()) {
            nextTempFunctionIndex = tempFunctions.pollLast();
            String tfName = TMPFUNCNAME + nextTempFunctionIndex;            
            if (!FunctionPrefix.delFunction(tfName)) //All those three if may be false if the Name was not used
                if (!FunctionVariable.delFunction(tfName)) 
                    FunctionInfix.delFunction(tfName);
            
        }
    }
    
    private final static String[] EXAMPLES = {";programCallFunction((defineLambdaGetName 0, $x)='2*x+2',3,5)",";plotStandard((defineLambdaGetName 0, $x)='3*x-1',4, 1_4) ","; defineLambdaGetName 0 = listLinear (10, 1_10); literalToExpression(defineLambdaGetName (-1)) # 1, sin (literalToExpression(defineLambdaGetName (-1))) # 1"};
    private final static String[] RESULTS = {"8, 12","listShuffle({1 .. 4}, {2,5,8,11})","plotStandard($sin, 10, 1_10)"};
    private final static String HELP = "... (x) provides a new temporary name (to be used for declare function or variable) or it returns the ones already generated.\nIt builds the names for the next Lambda function name if x >=0. If x < 0 it provides the previous lamba function name generated on this command session. Lambda functins are temporary functions which are automatically deleted at the end of the current expression execution. They are useful when it is necessary to create a function for some purpose but it is not required to save the function itself. The typical example is to plot a function.";
    private final static DomainList DOMAIN = new DomainList(new DomainLogicAnd(new DomainIntervalPoint(), new DomainInteger()));
    
    public DefLambdaGetName() {
        super("define", "LambdaGetName", (byte) 1, DOMAIN, false, true, HELP, EXAMPLES, RESULTS);
    }    
    
    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        int i = (int) input.getFirst().getValue();
        if (i >= 0) return getNextLambdaFunctionName();
        return getPreviousLambdaFunctionName(i);
    }
    
}

