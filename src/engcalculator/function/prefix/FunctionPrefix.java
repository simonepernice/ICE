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

package engcalculator.function.prefix;

import engcalculator.Calculator;
import engcalculator.domain.DomainList;
import engcalculator.expression.Expression;
import engcalculator.expression.ParsingException;
import engcalculator.function.*;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionPrefix extends Function {
    private static final FunctionPrefixStorage FUNCTION_PREFIX_STORAGE = new FunctionPrefixStorage(500);
    private static final double MAX_CHECK_ERROR = 1e-3;    

    public static boolean addFunction (FunctionPrefix func) throws Exception {
        return FUNCTION_PREFIX_STORAGE.addFunction(func);
    }

    public static boolean delFunction(String symbol) {
        return FUNCTION_PREFIX_STORAGE.delFunction(symbol);
    }

    public static FunctionPrefix getFunction (String symbol) {
        return FUNCTION_PREFIX_STORAGE.getFunction(symbol);
    }

    public static Collection<FunctionPrefix> getPrefixFunctions () {
        return FUNCTION_PREFIX_STORAGE.getPrefixFunctions();
    }

    public static void initialize () throws Exception {
        //This is called at the beginning in case something has to be set up
        FUNCTION_PREFIX_STORAGE.initialize();
        Function.getGroupSearchPath().getGroupPath().clear();
    }
    
    public static int beginContext () {
        return FUNCTION_PREFIX_STORAGE.beginContext(15);
    }
    
    public static int endContext () throws Exception {
        return FUNCTION_PREFIX_STORAGE.endContext();
    }    
    
    private static String buildHelp (String name, boolean expandsToList, String basehelp, String[] examples, String[] results) {
        StringBuilder help = new StringBuilder (basehelp);
        if (expandsToList) help.append("\nIf the argument is a list the function is automatically applied to all the elements of the list providing as result the list of the results.");
        if (examples != null && results != null && examples.length == results.length)  {
            help.append("\nExamples: ");
            for (int i=0; i<examples.length; ++i) {
                if( examples[i].indexOf(';') == -1) help.append ('\n').append(name).append('(').append(examples[i]).append(')');
                else help.append ('\n').append(examples[i]);
                help.append(" == ").append(results[i]);
            }
        }
        return help.toString();
    }

    private final byte numbArgs;          //number of arguments, <0 for variable
    private final boolean expandsToLists; //if expandable (and number of arguments is not variable) it is automatically applied to all the components of the list
    private final String[] examples;
    private final String[] results;
    private final DomainList domain;

    public FunctionPrefix(java.lang.String group, String name, byte numbArgs, DomainList domain, boolean expandsToLists, boolean locked, String help, String[] examples, String[] results) {
        super(group, buildName(group, name), locked, buildHelp (buildName(group, name), numbArgs > 0 && expandsToLists, help, examples, results));
        this.numbArgs = numbArgs;
        this.expandsToLists = expandsToLists;
        this.examples = examples;
        this.results = results;
        this.domain = domain;
    }

    @Override
    public boolean hasExample() {
        return examples != null  && results != null;
    }
    
    protected String selfCheck () {
        ListIntervals computedRes, expectedRes;
        String expression=null;
        double error;
        
        if (domain == null && numbArgs != 0) return new StringBuilder ("Function ").append(getSymbol()).append(" misses the domain requirement.").toString();

        if (domain != null && numbArgs > 0 && domain.getSize() != numbArgs) return new StringBuilder ("Function ").append(getSymbol()).append(" has input and domain length not matching").toString();

        if (examples == null && results == null ) 
            if (ERRORONSELFCHECKWITHOUTEXAMPLE) return new StringBuilder ("Function ").append(getSymbol()).append(" was not tested because the example and results are missing").toString();
            else return null;

        if (examples != null && results != null) {
            if (examples.length != results.length) return new StringBuilder("Examples and results for ").append(getSymbol()).append(" do not match in size.").toString();
            System.out.print("Test:");
            for (int i=0; i<examples.length; ++i) {
                System.out.print(" "+i);
                try {
                    if (examples[i].contains(";") || examples[i].contains("\n")) expression = examples[i];
                    else expression = new StringBuilder(getSymbol()).append(" (").append(examples[i]).append(')').toString();

                    if (expression.contains("\n")) {//multi line expression
                        ArrayList<String> expressionString = new ArrayList<String> ();
                        Calculator.splitInLines(expression, expressionString);
                        Iterator<String> il = expressionString.iterator();
                        while (il.hasNext())
                            if (il.next().equals("multilineExpressionStatement"))
                                il.remove();
                        System.out.print("The multi line self check expression is: ");
                        System.out.println(expressionString);
                        computedRes = new Expression(expressionString.toArray(new String[0]), null).evaluate(null);
                    } else {//single line expression
                        computedRes = new Expression(expression, null).evaluate(null);
                    }
                                    
                } catch (Exception e) {
                    return new StringBuilder("Exception computing expression: ").append(e.getMessage()).append(" for the expression ").append(expression).toString();
                }
                
                try {
                    expectedRes = new Expression(results[i], null).evaluate(null);
                    
                    error = computedRes.distance(expectedRes);                
                } catch (Exception e) {
                    return new StringBuilder("Exception computing result: ").append(e.getMessage()).append(" for the result ").append(results[i]).toString();
                }
                    
                if (error > MAX_CHECK_ERROR || Double.isNaN(error)) return new StringBuilder(expression).append(" == ").append(computedRes).append(" != ").append(results[i]).toString();
            }
        } else return new StringBuilder("Examples size for ").append(getSymbol()).append(" do not match.").toString();
        return null;
    }

    public byte getNumbArgs() {
        return numbArgs;
    }     

    @Override
    public ListIntervals compute (ListIntervals... inputArray) throws Exception {
        try {
            if (getComputeNumbArg() == 0) {
                return _compute(null);
            }
            
            ListIntervals input = inputArray[0];
            
            if (DEBUG) {
                System.out.println("Evaluating prefix function: " + getSymbol() + " with input: " + input);
            }
            
            final boolean INPUTSIZEDOMAINCHECK = inputSizeDomainCheck.getVal();
            
            
            if (inputArray.length != getComputeNumbArg()) {
                throw new RuntimeException("It was expected just one argument for a prefix function");
            }
                        
            ListIntervals result;
            if (numbArgs < 0 || numbArgs == input.size()) {//the following statement lets every function called with EXACT number of argument to return an extension of ListIntervals which may be required!!!
                if (INPUTSIZEDOMAINCHECK) {                        
                    if (       (numbArgs > 0 && (!expandsToLists) && input.size() != numbArgs)
                            || (numbArgs > 0 && expandsToLists && input.size() % numbArgs != 0)
                            || (numbArgs == 0 && input.size() > 0)
                            || (numbArgs != IGNORENUMBEROFARGUMENTS && numbArgs < 0 && input.size() < -numbArgs)) {
                        throw new CalculusException("Wrong number of arguments expected " + (numbArgs >= 0 ? String.valueOf(numbArgs) : new StringBuilder("at least ").append(String.valueOf(-numbArgs)).toString()) + " received " + input.size());
                    }
                    if (domain != null && !domain.isCompatible(input)) {
                        throw new CalculusException("The function requires " + domain.toString() + " input domain while it was found " + input.toString());
                    }

                }   
                
                result = _compute(input);
            } else if (! INPUTSIZEDOMAINCHECK || (numbArgs > 0 && numbArgs < input.size() && expandsToLists)) {                
                
                if (input.isMatrix()) {
                    result = new ListIntervalsMatrix(input.columnSize());
                } else {
                    result = new ListIntervals();
                }

                if (INPUTSIZEDOMAINCHECK) {                        
                    if (       (numbArgs > 0 && (!expandsToLists) && input.size() != numbArgs)
                            || (numbArgs > 0 && expandsToLists && input.size() % numbArgs != 0)
                            || (numbArgs == 0 && input.size() > 0)
                            || (numbArgs != IGNORENUMBEROFARGUMENTS && numbArgs < 0 && input.size() < -numbArgs)) {
                        throw new CalculusException("Wrong number of arguments expected " + (numbArgs >= 0 ? String.valueOf(numbArgs) : new StringBuilder("at least ").append(String.valueOf(-numbArgs)).toString()) + " received " + input.size());
                    }
                }    

                boolean atLeastOneComputed = false;
                CalculusException exception = null;
                                     
                ListIntervals subInput = new ListIntervals();
                Iterator<Interval> i = input.iterator();
                while (i.hasNext()) {
                    subInput.clear();
                    
                    for (int j = 0; j < numbArgs; ++j) {
                        subInput.append(i.next());
                    }
                                
                    if (INPUTSIZEDOMAINCHECK) {                        
                        if (domain != null && !domain.isCompatible(subInput)) {
                            throw new CalculusException("The function requires " + domain.toString() + " input domain while it was found " + subInput.toString());
                        }
                    }                    
                    
                    try {
                        result.addAll(_compute(subInput));
                        atLeastOneComputed = true;
                    } catch (CalculusException ce) {
                        Calculator.addWarning(ce.getMessage() + " while computing " + subInput);
                        result.add(new IntervalLiteral("Not Computed"));
                        exception = ce;
                    }
                    
                }
                
                if (!atLeastOneComputed) {
                    throw exception;
                }
            } else {
                throw new CalculusException("Wrong number of arguments expected " + (numbArgs >= 0 ? String.valueOf(numbArgs) : new StringBuilder("at least ").append(String.valueOf(-numbArgs)).toString()) + " received " + input.size());
            }

            if (USEMEASUREMENTUNIT) {
                List<MeasurementUnit> mus = _computeMeasurementUnit(input);
                if (mus != null) {
                    Iterator<Interval> ri = result.iterator();
                    Iterator<MeasurementUnit> mi = mus.iterator();
                    MeasurementUnit last=null;
                    int i = 0;
                    while (ri.hasNext()) {
                        Interval ipmu;
                        if (mi.hasNext()) ipmu = Interval.createIntervalWithMeasurementUnit(ri.next(), last = mi.next());                    
                        else ipmu = Interval.createIntervalWithMeasurementUnit(ri.next(), last);                    
                        if (ipmu != null) result.set(i, ipmu);
                        ++i;                  
                    }
                }                
            }            

            return result;            
        } catch (CalculusException ce) {
            throw new CalculusException("Calculus exception in the prefix function "+getSymbol()+"\n"+ce.getMessage());
        } catch (ParsingException pe) {
            throw new ParsingException("Parsing exception in the infix function "+getSymbol()+"\n"+pe.getMessage());
        } catch (MeasurementUnitException me) {
            throw new ParsingException("Measurement unit exception in the prefix function "+getSymbol()+"\n"+me.getMessage());
        }

    }

    public abstract ListIntervals _compute (ListIntervals input) throws Exception;

    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws Exception {
        if (! input.getMeasurementUnit().equals(MeasurementUnit.PURE)) throwNewMeasurementUnitException("It was expected an argument without measurement unit");
        LinkedList<MeasurementUnit> res = new LinkedList<MeasurementUnit>();
        res.add(MeasurementUnit.PURE);
        return res;
    }
    
    @Override
    public String getHelp() {
        if (getBasicHelpMessage().equals("")) return "";

        StringBuilder result = new StringBuilder();
        
        result.append("\nGroup: ");
        String g = getGroup();
        if (g == null || g.length() == 0) result.append("NOT DEFINED");
        else result.append (g);
        
        result.append("\nName: ").append(getSymbol());

        result.append("\nType: prefix function\nNumber of args: ");
        if (numbArgs < 0) result.append("at least ").append(-numbArgs);
        else result.append(numbArgs);

        result.append("\nSupported domain: ");
        if (domain != null) result.append(domain.toString());
        else result.append(" everything");       

        if (expandsToLists) result.append("\nAutomatically extends to list");
        else result.append("\nDoes not extend to list");

        if (isLocked()) result.append("\nThis function is locked: cannot be changed by user");
        else result.append("\nThis function can be changed by user");

        result.append("\nHelp: ").append(getBasicHelpMessage()).append('\n');
        return result.toString();
    }

    @Override
    public int getComputeNumbArg() {
        return 1;
    }
}
