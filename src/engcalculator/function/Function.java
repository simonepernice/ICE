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

package engcalculator.function;

import engcalculator.function.embedded.functionScan.FscIntervalSplitLinear;
import engcalculator.interval.ListIntervals;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.parameter.ConvertIntervalToBoolean;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.reactiveExpression.ReactiveExpression;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class Function implements SymbolWithHelp, Lockable {
    private final static GroupSearchPath GROUP_SEARCH_PATH = new GroupSearchPath();
    protected final static boolean DEBUG = false;
    protected final static boolean ERRORONSELFCHECKWITHOUTEXAMPLE = false;
    public final static byte IGNORENUMBEROFARGUMENTS = Byte.MIN_VALUE;
    
    protected static boolean USEMEASUREMENTUNIT = false;
    
    protected final static Parameter<Boolean> inputSizeDomainCheck;

    static {        
        inputSizeDomainCheck = new Parameter<Boolean> ("function", "ALL", "inputSizeAndDomainCheck", "It defines if ICE has to verify input domain and size before executing the required function.\nBy default the check is performed (with exception of compiled functions). Moreover disabling this check some internal error can happen. However it increases the execution speed.", true, new ConvertIntervalToBoolean());
        ParameterManager.addParameter(inputSizeDomainCheck);
    }
    
    public static GroupSearchPath getGroupSearchPath () {
        return GROUP_SEARCH_PATH;
    }
    
    public static int beginContext () {
        final int i = FunctionVariable.beginContext();
        if (i  != FunctionPrefix.beginContext()) throw new RuntimeException ("Internal error in begin context");
        return i;
    }
    
    public static int endContext () throws Exception {
        final int i = FunctionVariable.endContext();
        if (i  != FunctionPrefix.endContext()) throw new RuntimeException ("Internal error in end context");
        return i;

    }    
    
    public static LinkedList<SymbolWithHelp> getAllSymbolsWithHelp () {
        LinkedList<SymbolWithHelp> result = new LinkedList<SymbolWithHelp> ();
        result.addAll(FunctionPrefix.getPrefixFunctions());
        result.addAll(FunctionInfix.getInfixFunctions());
        result.addAll(FunctionVariable.getVariables());
        result.addAll(MeasurementUnit.getMeasurementUnits());
        result.addAll(ParameterManager.getParameters());
        return result;
    }    
            
    public static LinkedList<Function> getFunctions () {
        LinkedList<Function> result = new LinkedList<Function> ();
        result.addAll(FunctionPrefix.getPrefixFunctions());
        result.addAll(FunctionInfix.getInfixFunctions());
        result.addAll(FunctionVariable.getVariables());
        return result;
    }

    public static LinkedList<String> getFunctionSymbols (boolean justNames) {
        LinkedList<String> result = new LinkedList<String> ();

        for (FunctionPrefix f : FunctionPrefix.getPrefixFunctions()) {
            if (justNames) result.add(f.getSymbol());
            else if (f.getNumbArgs() == 1 || f.getNumbArgs() == 0) result.add(new StringBuilder(f.getSymbol()).append(' ').toString());
            else result.add(new StringBuilder(f.getSymbol()).append('(').toString());
        }
        
        for (FunctionInfix f : FunctionInfix.getInfixFunctions())
            result.add(f.getSymbol());

        for (FunctionVariable v : FunctionVariable.getVariables())
            result.add(v.getSymbol());

        return result;
    }

    public static String checkFunctions () {
        int tst=0, pass=0, fail=0, skip=0;
        StringBuilder result = new StringBuilder ();
        String check;
        for (Function pf : getFunctions ()) {
            System.out.print("\nTesting function "+pf.getSymbol()+" ");
             ++ tst;
            if ((check = pf.selfCheck()) != null) {
                System.out.print(" FAILED");
                result.append("\n**** FAILED ").append("Function ").append(pf.getSymbol()).append(" :  ").append(check);
                ++ fail;
            } else {                                
                if (pf.hasExample()) {
                    System.out.print(" PASSED");
                    ++ pass;
                } else {
                    System.out.print(" SKIPPED");
                    ++skip;
                }
            }
        }
        result.append("\nTested ").append(tst).append(" functions");
        result.append("\nPassed ").append(pass).append(" functions");
        result.append("\nSkipped ").append(skip).append(" functions");
        result.append("\nFailed ").append(fail).append(" functions");        
        return result.toString();
    }
    
    protected static String buildName (String group, String name) {
        if (name.length() == 0) return "";
        if (name.charAt(0) == '.') return name.substring(1);
        return new StringBuffer(group).append(name).toString();
    }    

    public static void setInputSizeDomainCheck(boolean INCREASE_EXECUTION_SPEED) {
        inputSizeDomainCheck.setVal(INCREASE_EXECUTION_SPEED);
    }
    
    public static boolean getInputSizeDomainCheck() {
        return inputSizeDomainCheck.getVal();
    }    
    
    public static void setUseMeasurementUnit(boolean USEMEASUREMENTUNIT) {
        Function.USEMEASUREMENTUNIT = USEMEASUREMENTUNIT;
    }    
        
    public static String delete (String fname) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean found = ReactiveExpression.isUsedAsInput(fname);
        
        if (found) return result.append("Variable ").append(fname).append(" cannot be deleted because used as input in a reactive expression").toString();
                
        found = ReactiveExpression.delReactiveExpression(fname);
        
        if (found) {
            result.append("Reactive expression ").append(fname).append(" deleted, ").toString();

            found = FunctionVariable.delFunction(fname);

            if (found) return result.append("Variable ").append(fname).append(" deleted").toString();
            
        }
                
        found = FunctionVariable.delFunction(fname);
        
        if (found) FscIntervalSplitLinear.deleteLinearVar (fname);

        if (found) return result.append("Variable ").append(fname).append(" deleted").toString();

        found = FunctionPrefix.delFunction(fname);

        if (found) return result.append("Prefix function ").append(fname).append(" deleted").toString();

        found = FunctionInfix.delFunction(fname);

        if (found) return result.append("Infix function ").append(fname).append(" deleted").toString();
        
        found = MeasurementUnit.delMeasurementUnit(fname);
        
        if (found) return result.append("Measurement unit ").append(fname).append(" deleted").toString();

        return null;
    }    

    public static String deleteAll () {
        StringBuilder result = new StringBuilder("The following reactive expressions: ");   
        {
            Iterator<ReactiveExpression> i = new LinkedList<ReactiveExpression> (ReactiveExpression.getReactiveExpressions()).descendingIterator();
            while (i.hasNext()) {
                ReactiveExpression re = i.next();
                if (ReactiveExpression.delReactiveExpression(re.getOutput())) result.append(re.getOutput()).append(", ");
                else throw new RuntimeException ("It was not possible to delete "+re.toString());
            }
        }
        result.append("; and variables: ");               
        {
            Iterator<FunctionVariable> i = new LinkedList<FunctionVariable> (FunctionVariable.getVariables()).iterator();
            while (i.hasNext()) {
                FunctionVariable v = i.next();
                if (! v.isLocked()) if (FunctionVariable.delFunction(v.getSymbol())) result.append(v.getSymbol()).append(", ");
            }
        }
        result.append("; and functions: ");
        {
            Iterator<FunctionPrefix> i = new LinkedList<FunctionPrefix> (FunctionPrefix.getPrefixFunctions()).iterator();
            while (i.hasNext()) {
                FunctionPrefix f = i.next(); 
                if (! f.isLocked()) if (FunctionPrefix.delFunction(f.getSymbol())) result.append(f.getSymbol()).append(", ");
            }
        }        
        return result.append("; were deleted.").toString();        
    }
    
    private final String group;           //it is the group of a given set of symbols
    private String symbol;          //it is the symbol to be find reading a text
    private String help;            //the help showed for the variable
    private boolean locked;         //if the function is constant it cannot be overwritten neither deleted

    public Function(java.lang.String group, String symbol, boolean locked, String help) {
        this.group = group;
        this.symbol = symbol;
        this.help = help;
        this.locked = locked;
    }

    public final String getGroup() {
        return group;
    }

    public final String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public final String toString() { 
        return symbol;
    }

    public final String getBasicHelpMessage() {
        if (help != null && help.contains("...")) help = help.replace("...", symbol);
        return help;
    }

    public abstract String getHelp ();
    
    public abstract boolean hasExample () ;

    public boolean isLocked() {
        return locked;
    }
    
    public static final void throwNewCalculusException (String text) throws CalculusException {
        if (text != null && inputSizeDomainCheck.getVal()) throw new CalculusException (text);
    }
    
    public static final void throwNewMeasurementUnitException (String text) throws MeasurementUnitException {
        if (text != null && inputSizeDomainCheck.getVal()) throw new MeasurementUnitException (text);
    }    

    protected abstract String selfCheck ();

    protected String removeSpaces (String input) {
        StringBuilder output = new StringBuilder ();
        for (char c : input.toCharArray())
            if (c != ' ') output.append (c);
        return output.toString();
    }
    
    public void setLocked (boolean locked) {
        this.locked = locked;
    }

    public abstract int getComputeNumbArg ();

    public abstract ListIntervals compute (ListIntervals... arguments) throws Exception;

}
