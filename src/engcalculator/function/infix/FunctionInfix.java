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

package engcalculator.function.infix;


import engcalculator.Calculator;
import engcalculator.domain.DomainList;
import engcalculator.expression.Expression;
import engcalculator.expression.ParsingException;
import engcalculator.function.CalculusException;
import engcalculator.function.Function;
import engcalculator.function.MeasurementUnitException;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionInfix extends Function {
    private static final FunctionInfixStorage FUNCTION_INFIX_STORAGE = new FunctionInfixStorage(100);

    public static String getPriorityList() {
        return FUNCTION_INFIX_STORAGE.getPriorityList();
    }
    private final double MAX_CHECK_ERROR = 1e-3;

    public static boolean addFunction (FunctionInfix func, String priorityName) throws Exception {
        return FUNCTION_INFIX_STORAGE.addFunction(func, priorityName);
    }

    public static boolean delFunction(String symbol) {
        return FUNCTION_INFIX_STORAGE.delFunction(symbol);
    }

    public static FunctionInfix getFunction (String symbol) {
        return FUNCTION_INFIX_STORAGE.getFunction(symbol);
    }

    public static Collection<FunctionInfix> getInfixFunctions () {
        return FUNCTION_INFIX_STORAGE.getInfixFunctions();
    }

    public static byte getMaxPriority () {
        return FUNCTION_INFIX_STORAGE.getMaxPriority();
    }

    public static void addPriority (Priority p) {
        FUNCTION_INFIX_STORAGE.addPriority(p);
    }

    public static boolean isLeftAssociative (int priority) {
        return FUNCTION_INFIX_STORAGE.isLeftAssociative(priority);
    }
    
    public static void initialize () throws Exception {
        FUNCTION_INFIX_STORAGE.initialize();
    }   

    private static String buildHelp (String name, boolean expandsToList, String basehelp, String[] examplesleft, String[] examplesright, String[] results) {
        StringBuilder help = new StringBuilder (basehelp);
        if (expandsToList) help.append(" If the arguments are lists they must have the same number of arguments. If one element is a list and the other is a scalar, the scalar will be automatically expanded to a list with the proper number of elements. The result is the list of the results.");
        if (examplesleft != null && examplesright != null && results != null && examplesleft.length == results.length && examplesright.length == results.length)  {
            help.append("\nExamples: ");
            for (int i=0; i<examplesleft.length; ++i)
                if (examplesleft[i].indexOf(';') == -1) {
                    help.append ("\n(").append(examplesleft[i]).append(')').append(name).append('(').append(examplesright[i]).append(')').append(" == (").append(results[i]).append(")");
                } else {
                    help.append ("\n(").append(examplesleft[i]).append(')').append(" == (").append(results[i]).append(")");
                }
            }
        return help.toString();
    }

    private final byte numbLeftArgs;            //number of left arguments, <0 for variable
    private final byte numbRightArgs;           //number of right arguments, <0 for variable
    byte priority;                              //priority 0 lowest, <0 for infix
    private final boolean expandsScalarInput;   //if expansible (and number of arguments is not variable) it is automatically applied to all the components of the list
    private final String[] examplesLeft;
    private final String[] examplesRight;
    private final String[] results;
    private final DomainList domainLeft;
    private final DomainList domainRight;

    public FunctionInfix(java.lang.String group, String name, byte numbLeftArgs, byte numbRightArgs, DomainList domainLeft, DomainList domainRight, boolean expandsScalarInput, boolean locked, String help, String[] examplesLeft, String[] examplesRight, String[] results) {
        super(group, name, locked, buildHelp(name, numbLeftArgs > 0 && numbRightArgs > 0 && expandsScalarInput, help, examplesLeft, examplesRight, results));
        this.numbLeftArgs = numbLeftArgs;
        this.numbRightArgs = numbRightArgs;
        this.expandsScalarInput = expandsScalarInput;
        this.examplesLeft = examplesLeft;
        this.examplesRight = examplesRight;
        this.results = results;
        this.domainLeft = domainLeft;
        this.domainRight = domainRight;
        priority = 0;
    }

    public byte getPriority() {
        return priority;
    }

    @Override
    public boolean hasExample() {
        return examplesLeft != null && examplesRight != null && results != null;
    }

    @Override
    protected String selfCheck() {
        ListIntervals computedRes, expectedRes;
        String expression = null;
        double error;
        
        if (domainLeft == null || domainRight == null) return new StringBuilder ("Function ").append(getSymbol()).append(" misses the domain requirement.").toString();

        if (domainLeft != null && numbLeftArgs > 0 && domainLeft.getSize() != numbLeftArgs) return new StringBuilder ("Function ").append(getSymbol()).append(" has input and domain left length not matching").toString();
        if (domainRight != null && numbRightArgs > 0 && domainRight.getSize() != numbRightArgs) return new StringBuilder ("Function ").append(getSymbol()).append(" has input and domain right length not matching").toString();

        if (examplesLeft == null && examplesRight == null && results == null ) 
            if (ERRORONSELFCHECKWITHOUTEXAMPLE) return new StringBuilder ("Function ").append(getSymbol()).append(" was not tested because the example and results are missing").toString();
            else return null;
        
        if (examplesLeft != null && examplesRight != null && results != null) {
            if (examplesLeft.length != results.length || examplesRight.length != results.length) return new StringBuilder("Examples and results for ").append(getSymbol()).append(" do not match in size.").toString();
            System.out.print("Test:");
            for (int i=0; i<examplesLeft.length; ++i) {
                System.out.print(" "+i);
                try {
                    if (examplesLeft[i].indexOf(';') == -1 && examplesRight[i].indexOf(';') == -1) expression = new StringBuilder("(").append(examplesLeft[i]).append(") ").append(getSymbol()).append(" (").append(examplesRight[i]).append(")").toString();
                    else expression = new StringBuilder(examplesLeft[i]).append(examplesRight[i]).toString();

                    computedRes = new Expression(expression, null).evaluate(null);                    

                } catch (Exception e) {
                    return new StringBuilder("Exception computing expression: ").append(e.getMessage()).append(" for the expression ").append(expression).toString();
                }
                
                try {
                    expectedRes = new Expression(new StringBuilder("(").append(results[i]).append(")").toString(), null).evaluate(null);
                    
                    error = computedRes.distance(expectedRes);    
                } catch (Exception e) {
                    return new StringBuilder("Exception computing result: ").append(e.getMessage()).append(" for the result ").append(results[i]).toString();
                }

                                
                if (error > MAX_CHECK_ERROR || Double.isNaN(error)) return new StringBuilder(expression).append(" == ").append(computedRes.toString()).append(" != ").append(results[i]).toString();
            }
        } else return new StringBuilder("Examples size for ").append(getSymbol()).append(" do not match.").toString();
        return null;
    }

    public byte getNumbLeftArgs() {
        return numbLeftArgs;
    }

    public byte getNumbRightArgs() {
        return numbRightArgs;
    }

    @Override
    public final ListIntervals compute (ListIntervals... inputArray) throws Exception {        
        try {
            ListIntervals leftSide = inputArray[0], rightSide = inputArray[1];
            
            if (DEBUG) {
                System.out.println("Evaluating infix function: " + getSymbol() + " with left input: " + leftSide + " right input: " + rightSide);
            }
            
            final boolean INPUTSIZEDOMAINCHECK = inputSizeDomainCheck.getVal();

            if (inputArray.length != getComputeNumbArg()) {
                throw new RuntimeException("It was expected just two arguments for an infix function");
            }            

            if (expandsScalarInput) {
                int lss = -1;
                int rss = -1;
                if (leftSide != null) lss = leftSide.size();
                if (rightSide != null) rss = rightSide.size();
                if (lss >= 1 && rss > lss && rss % lss == 0) {
                    int columns = rightSide.columnSize();
                    if (rightSide.isMatrix()) {
                        leftSide = new ListIntervalsMatrix(leftSide, columns); //to avoid to affect input
                    } else {
                        leftSide = new ListIntervals(leftSide);
                    }
                    final ListIntervals l1 = new ListIntervals(leftSide);
                    final int rs = rss / lss;
                    for (int ls = 1; ls < rs; ++ls) {
                        leftSide.addAll(l1);
                    }
                } else if (rss >= 1 && lss > rss && lss % rss == 0) {
                    int columns = leftSide.columnSize();
                    if (leftSide.isMatrix()) {
                        rightSide = new ListIntervalsMatrix(rightSide, columns); //to avoid to affect the input
                    } else {
                        rightSide = new ListIntervals(rightSide);
                    }
                    final ListIntervals r1 = new ListIntervals(rightSide);
                    final int ls = lss / rss;
                    for (int rs = 1; rs < ls; ++rs) {
                        rightSide.addAll(r1);
                    }
                }
            }
            
            ListIntervals result;
            if ((numbLeftArgs < 0 || (leftSide != null && leftSide.size() == numbLeftArgs)) && (numbRightArgs < 0 || (rightSide != null && rightSide.size() == numbRightArgs))) {
                if (INPUTSIZEDOMAINCHECK) domainCheck(leftSide, rightSide);
                result = _compute(leftSide, rightSide);
            } else if (!INPUTSIZEDOMAINCHECK || (expandsScalarInput && leftSide != null && rightSide != null && leftSide.size() / numbLeftArgs == rightSide.size() / numbRightArgs && leftSide.size() % numbLeftArgs == 0)) {                
                if (leftSide.isMatrix() && rightSide.isMatrix()) {
                    result = new ListIntervalsMatrix(leftSide.columnSize());
                } else {
                    result = new ListIntervals();
                }
                
                boolean atLeastOneComputed = false;
                CalculusException exception = null;
                
                Iterator<Interval> il = leftSide.iterator();
                Iterator<Interval> ir = rightSide.iterator();
                ListIntervals rightSubList = new ListIntervals();
                ListIntervals leftSubList = new ListIntervals();
                while (il.hasNext()) {
                    leftSubList.clear();
                    rightSubList.clear();
                    for (int j=0; j<numbLeftArgs; ++j)
                        leftSubList.append(il.next());
                    
                    for (int j=0; j<numbRightArgs; ++j)
                        rightSubList.append(ir.next());
                            
                    if (INPUTSIZEDOMAINCHECK) domainCheck(leftSubList, rightSubList);
                    
                    try {
                        result.addAll(_compute(leftSubList, rightSubList));
                        atLeastOneComputed = true;
                    } catch (CalculusException ce) {
                        Calculator.addWarning(ce.getMessage() + " while computing with arguments " + leftSubList + " , " + rightSubList);
                        result.add(new IntervalLiteral("Not Computed"));
                        exception = ce;
                    }
                }
                
                if (!atLeastOneComputed) {
                    if (exception != null) throw exception;
                    else throw new CalculusException ("It was not possible to understand the input expression. ");
                }
                
            } else {
                throw new CalculusException("Wrong number of arguments evaluating the function");
            }
            
            if (USEMEASUREMENTUNIT) {
                List<MeasurementUnit> mus = _computeMeasurementUnit(leftSide, rightSide);
                if (mus != null) {
                    Iterator<Interval> ri = result.iterator();
                    Iterator<MeasurementUnit> mi = mus.iterator();
                    MeasurementUnit last=null;
                    int i=0;                    
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
            throw new CalculusException("Calculus exception in the infix function "+getSymbol()+"\n"+ce.getMessage());
        } catch (ParsingException pe) {
            throw new ParsingException("Parsing exception in the infix function "+getSymbol()+"\n"+pe.getMessage());
        } catch (MeasurementUnitException me) {
            throw new ParsingException("Measurement unit exception in the infix function "+getSymbol()+"\n"+me.getMessage());
        }
    }
    
    private void domainCheck (ListIntervals leftSubList, ListIntervals rightSubList) throws Exception{
        if (((!expandsScalarInput && numbLeftArgs > 0 && leftSubList.size() != numbLeftArgs) || (numbLeftArgs != IGNORENUMBEROFARGUMENTS && numbLeftArgs < 0 && leftSubList.size() < -numbLeftArgs))) {
            throw new CalculusException("Wrong number of arguments on the left side of function expected " + (numbLeftArgs >= 0 ? String.valueOf(numbLeftArgs) : new StringBuilder("at least ").append(String.valueOf(-numbLeftArgs)).toString()) + " received " + leftSubList.size());
        }
        if (((!expandsScalarInput && numbRightArgs > 0 && rightSubList.size() != numbRightArgs) || (numbRightArgs != IGNORENUMBEROFARGUMENTS && numbRightArgs < 0 && rightSubList.size() < -numbLeftArgs))) {
            throw new CalculusException("Wrong number of arguments on the right side of function  expected " + (numbRightArgs >= 0 ? String.valueOf(numbRightArgs) : new StringBuilder("at least ").append(String.valueOf(-numbRightArgs)).toString()) + " received " + rightSubList.size());
        }
        if ((rightSubList.size() == 0) || (leftSubList.size() == 0)) {
            throw new CalculusException("There is not any operand on one side of function "+getSymbol());
        }

        if (domainLeft != null && !domainLeft.isCompatible(leftSubList)) {
            throw new CalculusException("Error: requires " + domainLeft.toString() + " left input domain while it was found " + leftSubList.toString());
        }
        if (domainRight != null && !domainRight.isCompatible(rightSubList)) {
            throw new CalculusException("Error: requires " + domainRight.toString() + " right input domain while it was found " + rightSubList.toString());
        }

    }

    public abstract ListIntervals _compute (ListIntervals leftSide, ListIntervals rightSide) throws Exception;
    
    public abstract List<MeasurementUnit> _computeMeasurementUnit (ListIntervals leftSide, ListIntervals rightSide) throws Exception;
    
    @Override
    public String getHelp() {
        if (getBasicHelpMessage().equals("")) return "";

        StringBuilder result = new StringBuilder();
        
        result.append("\nGroup: ");
        String g = getGroup();
        if (g == null || g.length() == 0) result.append("NOT DEFINED");
        else result.append (g);
        
        result.append("\nName: ").append(getSymbol());

        result.append("\nType: infix function\nNumber of left args: ");
        if (numbLeftArgs < 0) result.append(" variable");
        else result.append(numbLeftArgs);

        result.append("\nNumber of right args: ");
        if (numbRightArgs < 0) result.append(" at least ").append(-numbRightArgs);
        else result.append(numbRightArgs);

        result.append("\nSupported left domain: ");
        if (domainLeft != null) result.append(domainLeft.toString());
        else result.append(" everything");

        result.append("\nSupported right domain: ");
        if (domainRight != null) result.append(domainRight.toString());
        else result.append(" everything");

        result.append("\nPriority: ").append(priority).append(", Min Priority: 0, Max Priority: ").append(getMaxPriority()).append("\nAssociativity: ");
        if (isLeftAssociative(priority)) result.append("left to right");
        else result.append("right to left");

        if (expandsScalarInput) result.append("\nAutomatically extends scalar input to list");
        else result.append("\nDoes not extend scalar input to list");

        if (isLocked()) result.append("\nThis function is locked: cannot be changed by user");
        else result.append("\nThis function can be changed by user");

        result.append("\nHelp: ").append(getBasicHelpMessage()).append('\n');
        return result.toString();
    }

    @Override
    public final int getComputeNumbArg() {
        return 2;
    }

}
