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
 *     along with this program.  ProIf not, see <http://www.gnu.org/licenses/>.
 */

package engcalculator.function.embedded.conditional;

import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.setInterval.SInSub;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
abstract class CndPrefixFunction extends FunctionPrefix {
    private final static SInSub SIN_SUB = new SInSub();
    private FunctionPrefix[] inpFunc;
    private Interval[] inpInt;
    private Interval argument;
    private int s;
    
    private final static String HELP = "In ICE all the inputs of a function are evaluated before calling it. That does not help in loops where the loop condition would never be checked again neither in conditional statements where all the options would be evaluated not only the correct one. However all the conditional expression accepts prefix function name or interval as arguments. A prefix function has the advantage to be evaluable later and several  evaluations may provide different results. All the conditinal functions has aa interval interval (called argumen) which is set to the value of the first argument. Then it is used as input to the check prefix function or as input to the computation prefix functions whose result is again stored in that argument. If the condition is an interval it is just verified the argument lies in it, if it is a prefix function it is evaluate at argument and its result is returned.";

    final static Parameter<Integer> MAXITERATIONS;
    
    static {
        MAXITERATIONS = new Parameter<Integer>("conditional","ALLFUNCTIONS", "maxIteration", "The maximum number of iterations, above which the conditional function stops", 300, new ConvertIntervalToInteger (1,1000));
        ParameterManager.addParameter(MAXITERATIONS);        
    }     
    
    public CndPrefixFunction (String name, byte nArgs, DomainList domain, String help, String[] examples, String[] results) {
        super("conditional", name, nArgs, domain, false, true, help+HELP, examples, results);
    }
    
    private void readInput (ListIntervals inp) throws Exception {
        s = inp.size();
        inpFunc = new FunctionPrefix[s];
        inpInt = new Interval[s];
        for (int i = 0; i < s; ++i) {
            if (inp.get(i).isIntervalLiteral()) {
                inpFunc[i] = FunctionPrefix.getFunction(inp.get(i).getName());
                if (inpFunc[i] == null) inpInt[i] = inp.get(i);
            } else {
                inpInt[i] = inp.get(i);
            }
        }
        if (inpInt[0] == null) throwNewCalculusException ("The first input has to be the initialization value for the internal iteration argument, instead it was found: "+inp.getFirst());
        argument = inpInt[0];
    }
    
    protected Interval getInput (int i, Interval... extraArguments) throws Exception {
        if (i < 0 || i >= s) throwNewCalculusException ("Reached the "+i+"th condition which was not defined.");
        if (inpInt[i] != null) return inpInt[i];
        ListIntervals input = new ListIntervals (argument);
        for (Interval in : extraArguments)
            input.append(in);
        ListIntervals result = inpFunc[i].compute(input); 
        //if (result.size()>1) throwNewCalculusException ("The function should return a list of size 1.");
        return result.getFirst();
    }
    
    protected Interval getCondition (int i) throws Exception {
        if (i < 0 || i >= s) throwNewCalculusException ("Reached the "+i+"th condition which was not defined.");
        if (inpInt[i] != null) return SIN_SUB._computeIntervals(argument, inpInt[i]);
        return getInput(i);
    }
    
    protected int getInputSize () {
        return s;
    }    
    
    protected void setArgument (Interval i) throws Exception {
        argument = i;
    }    
    
    protected Interval getArgumen () {
        return argument;
    }
    
    protected abstract Interval _computeInterval () throws Exception;

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        readInput(input);
        return new ListIntervals (_computeInterval());    
    }
}
