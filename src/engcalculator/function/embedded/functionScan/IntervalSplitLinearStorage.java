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
package engcalculator.function.embedded.functionScan;

import engcalculator.interval.ListIntervals;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.list.LisLinear;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IntervalSplitLinearStorage {
    private final static Parameter<Integer> SAMPLES;
    private final LisLinear LIS_LINEAR;
    private final LinkedList<String> LISTVARSNAME ;
    private final LinkedList<Interval> LISTVARSVALUE ;

    static {
        SAMPLES = new Parameter<Integer>("functionScan","IntervalSplitLinear", "samples", "The number of samples in which the interval has to be split. When new intervals are added the split increases.", 3, new ConvertIntervalToInteger (2,10));        
        ParameterManager.addParameter(SAMPLES);        
    }
        
    public IntervalSplitLinearStorage() {
        LIS_LINEAR = new LisLinear();
        LISTVARSNAME = new LinkedList<String> ();
        LISTVARSVALUE = new LinkedList<Interval> ();    
    }
    
    public boolean deleteLinearVar (String name) throws Exception {
        int i = LISTVARSNAME.indexOf(name);
        if (i == -1) return false;
        
        LISTVARSNAME.remove(i);
        LISTVARSVALUE.remove(i);
        
        computeNewVarsValues();

        return true;
    }
    
    public boolean addLinearVar (String name, Interval value) throws Exception {
        FunctionVariable v = new FunctionVariable(name, new ListIntervals(), false, "Temporary variable to check if it is possible to add a var with given name");
        if (! FunctionVariable.addFunction(v)) throw new CalculusException("It was not possible to add the variable withname "+name);
        
        int i = LISTVARSNAME.indexOf(name);
        if (i == -1) {
            LISTVARSNAME.add(name);
            LISTVARSVALUE.add(value);
        } else {
            LISTVARSVALUE.set(i, value);
        }
        
        computeNewVarsValues ();
        return true;
    }
    
    private void computeNewVarsValues () throws Exception{
        final int sSamples = SAMPLES.getVal();
        
        ArrayList<Interval[]> tmp = new ArrayList<Interval[]> ();  
        {
            final Interval nSamples = new IntervalPoint(sSamples);
            for (Interval i : LISTVARSVALUE) {
                try {
                    tmp.add(LIS_LINEAR._compute(new ListIntervals().append(nSamples).append(i)).toArray(new Interval[0]));
                } catch (Exception ce) {
                    System.out.println ("Internal error in computeNewVarsValues in IntervalSplitLinearStorage: "+ce.getMessage());
                }
            }
        }
        
        LinkedList<ListIntervals> newLists = new LinkedList<ListIntervals> ();
           
        {
            final int sVars = LISTVARSNAME.size();
            final int sSamplesTimesVars = (int) Math.pow(sSamples, sVars);
            int howManyTimes = 1;
            int element = 0;
            for (int j=0; j<sVars; ++j) {
                ListIntervals var = new ListIntervals();            
                for (int k=0; k<sSamplesTimesVars;) {
                    for (int l=0; l<howManyTimes; ++l, ++k ){
                        var.add(tmp.get(j)[element]);       
                    }
                    ++element;
                    if (element >= sSamples) element = 0;
                }
                newLists.add(var);
                howManyTimes *= sSamples;
            }
        }

        Iterator<ListIntervals> i=newLists.iterator(); 
        for (String n : LISTVARSNAME) {//first of all delete all the vars previously build, if something goes wrong the user tried with not modifible variable so the exception is correct
            FunctionVariable v;
            v = FunctionVariable.getFunction(n);
            v.setLocked(false);            
            v = new FunctionVariable(n, i.next(), true, "List automatically build by function interval split linear");            
            FunctionVariable.addFunction(v);                        
        }
        
    }
    
    public void initialize () {
        LISTVARSNAME.clear();
        LISTVARSVALUE.clear();
    }    
}
