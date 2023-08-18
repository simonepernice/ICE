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

import engcalculator.Calculator;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;
import engcalculator.domain.DomainListLiteral;
import engcalculator.expression.ExpressionCompiled;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.userDefined.UDeFunctionPrefixByExpression;
import engcalculator.expression.Expression;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.embedded.userDefined.UDeCurrentGroup;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class DefFunctionVariable extends FunctionInfix {
    private final static DomainListLiteral DOMAIN = new DomainListLiteral ();
    
    private final static String HELP = "$a ... 5 defines the variable a to 5. ($f, $x0, .., $xn)  ... 'expression of x0 .. xn' defines the functin f(x0, .., xn) as expression (x0, .., xn).\nIt sets a variable or a function value 'a'...(1,2,3%2). For a variable write the variable name between commas then ... and then the value. It is allowed a multiple assignement if a list of literals is set equal to a list of intervals: ('a','b','c') ... (1,2,3). For functions write ( the function name between commas, the variable names between commas) ... the function body: ('f','x','y')...'x+y' sets the function f(x,y) ... x+y. If the function name or parameter names appear for the first time it is possible to avoid the use of commas: a...5; (f,x)...'2*x' gives error only if a, f or x were already defined. The set operation on variables returns the value and on functions the function names in order to be immediately used in the current operation. Please note when a function or variable is defined all other variables involved (with exception of parameters) must be known because their value is stored (i.e.: ('f','x')...'2*a*x', if you later change the variable 'a' value, that will not affect f(x)). When a function is defined by default itself is given like parameter in order to create recursive functions, i.e.: (f,x) = '1,returnthisif(x<=1);x*f(x-1). It is possible to add multiple-line expressions. To do so just use the char sequence \n to separate the lines or the multiple line literal syntax where each literal ends with '+ until the string is not completed. The instructions on a multiline use intermediate variable to share the computation, however the user should take care not using variable alrady defined otherwise the value will be fixed at the one of the definition time. To overwrite ICE functions see overwriteembeddedfunctions.";
    private final static String[] EXAMPLESLEFT = {"$a=(1,2,3);a+1","($f,$x,$y)='x+y';f(5,6)","($g,$x)='x,2*x';g(2)","($l,$x)='2*x';($n,$x)='l(x+1)';n(3)","$a=0;5+($a=3),a",";equationFindRoot(($l,$x)='2*x+2',-2_0,0)","defineDelete($n);($n,$x)='1,flowControlReturnThisIf (x <= 1);x*n(x-1)';n (5)","($a,$b)=(1,2);($a,$b)=(b,a);(a==2,b==1)",";justCreatedVariable = 0, justCreatedVariable +1",";($justCreatedFunction, $x)='2*x', justCreatedFunction 4","($multiLineFunc, $a) = 'zza = a * 2 \n zzb = zza * 3 \n zzb * 4';multiLineFunc 3","($t1, $a)='2*a';($t2, $a)='3*t1 a, 3*t1 a';($t3, $a)='4*t2 a, 4*t2 a'; t3 1"};
    private final static String[] EXAMPLESRIGHT = {"","","","","","","","", "", "", "",""};
    private final static String[] RESULTS = {"(2,3,4)","11","(2,4)","8","8,3","-1","120","1,1","0,1","'justCreatedFunction', 8","3*2*3*4","24,24,24,24"};
    
    static {
        UDeCurrentGroup t = new UDeCurrentGroup (); //this is just to initialize the class to store the parameter
    }
    
    public DefFunctionVariable() {
        super ("define", "=", (byte) -1, (byte) -1, DOMAIN,  DOMAIN, false, true, HELP, EXAMPLESLEFT, EXAMPLESRIGHT, RESULTS);
    }

    public DefFunctionVariable(String name, String help, String[] examplesleft, String[] examplesright, String[] results) {
        super ("define", name, (byte) -1, (byte) -1, DOMAIN, DOMAIN, false, true, help, examplesleft, examplesright, results);
    }
    public boolean isCompilatedFunction () {
        return false;
    }

    @Override
    public final ListIntervals _compute(ListIntervals left, ListIntervals right) throws Exception {
        if (left.size() < 1) throwNewCalculusException ("It is expected a literal to be set as variable instead found "+left);

        String fname = left.getName();
        boolean isvariable = true;
        if (fname == null) {
            fname = left.getFirst().getName();
            isvariable = false;
        }
        if (fname == null) throwNewCalculusException ("It is expected a literal to be set as variable instead found "+left);

        if (isvariable || left.size() == 1) {//variable definition
            if (FunctionPrefix.getFunction(fname) != null) throwNewCalculusException ("There is a function already defined with the same name "+fname+" of this variable.");
            
            FunctionVariable var = new FunctionVariable (UDeCurrentGroup.getGroup(fname), UDeCurrentGroup.getName(fname), right, false, "User defined variable.");

            if (! FunctionVariable.addFunction(var)) throwNewCalculusException ("There is a variable already defined with the same "+fname+" name which is constant.");

            return var.getValue();
        } else if (left.size() > 1 && right.size() == 1) { //function definition
            LinkedList<String> varnames = new LinkedList<String> ();

            final int s = left.size();           

            for (int i=1; i<s; ++i) {
                String varname;
                varname = left.get(i).getName();
                if (varname == null) throwNewCalculusException("It was expected a literal and not a number as a function definition variable.");
                varnames.add(varname);
            }

            varnames.add(fname); //the function name is passed as well for recursive functions

            String fbody = null;
            if (right.size() > 1 || (fbody = right.getFirst().getName()) == null) throwNewCalculusException("It was expected a literal showing the defined function operations instad found "+right.getFirst());
            Expression e;
            if (isCompilatedFunction()) e = new ExpressionCompiled(fbody, varnames); //use compiled expression to be faster
            else {                
                if (fbody.contains("\n")) {//multi line expression
                    ArrayList<String> expressionString = new ArrayList<String> ();
                    Calculator.splitInLines(fbody, expressionString);
                    e = new Expression(expressionString.toArray(new String[0]), varnames);
                } else {//single line expression
                    e = new Expression(fbody, varnames);
                }
            }

            StringBuilder help = new StringBuilder();
            help.append(left.getFirst().getName()).append('(');
            for (String v : varnames)
                help.append(v).append(',');

            help.setCharAt(help.length()-1, ')');
            help.append(" = ").append(e.toString());
            if (FunctionVariable.getFunction(left.getFirst().getName()) != null) throwNewCalculusException ("There is a variable already defined with the same "+left.getFirst().getName()+" name of this function.");

            LinkedList<String> dup = e.listOfDuplicatedVariables();
            if (dup.size() != 0) {
                Calculator.addWarning("\nWarning: there are duplicated variables in some input function sub expression, which may lead to overestimation of the result. Try to rewrite the input sub expressions avoiding the following duplications for a smaller result: ".concat(dup.toString()));

            }

            UDeFunctionPrefixByExpression f = new UDeFunctionPrefixByExpression (left.getFirst().getName(), (byte) (s-1), true, help.toString(), e, varnames);
            if (! FunctionPrefix.addFunction(f)) throwNewCalculusException ("There is a function already defined with the same name which is constant.");


            return new ListIntervals (new IntervalLiteral (f.getSymbol()));
        } else if (left.size() == right.size()) {//multivariable definition
            final int s = left.size();
            ListIntervals result = new ListIntervals ();
            for (int i=0; i<s; ++i) {
                fname = left.get(i).getName();
                if (fname == null) throwNewCalculusException ("It is expected a literal to be set as variable instad it was found "+fname);
                if (FunctionPrefix.getFunction(fname) != null) throwNewCalculusException ("There is a function already defined with the same "+fname+" name of this variable.");
                FunctionVariable var = new FunctionVariable (UDeCurrentGroup.getGroup(fname), UDeCurrentGroup.getName(fname), right.subList(i, i+1), false, "User defined variable.");
                 if (! FunctionVariable.addFunction(var)) throwNewCalculusException ("There is a variable already defined with the same name "+fname+ " which is locked.");
                 result.append(var.getValue().getFirst());
            }
            return result;
        }
   
        throwNewCalculusException("Not well formed setting: "+left+getSymbol()+right);
        return null;//this will never be reached unless error check are disabled
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        return null;
    }     
}
