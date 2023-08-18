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

package engcalculator.reactiveExpression;


import engcalculator.function.CalculusException;
import engcalculator.interval.ListIntervals;
import engcalculator.function.variable.FunctionVariable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ReactiveExpressionStorage {
    private final LinkedList<ReactiveExpression> reactiveExpressionList;
    private final VariableSet changedVariables;

    public ReactiveExpressionStorage() {
        reactiveExpressionList = new LinkedList<ReactiveExpression> () ;
        changedVariables = new VariableSet ();
    }
    
    public String getReactiveExpressionSummary () {
        StringBuilder result = new StringBuilder ();
        for (ReactiveExpression re : reactiveExpressionList) {
            result.append(re.getInputs().toString());
            result.append(re.toString()).append(" => ");
            result.append(FunctionVariable.getFunction(re.getOutput()).getValue().toString()).append("\n");                        
        }        
        return result.toString();
    }    
    
    
    public void addChangedVariable (String v) {
        changedVariables.add(v);
    }
    
    public ListIntervals addReactiveExpression (ReactiveExpression re) throws Exception {
        ReactiveExpression reDeleted = forcedDelReactiveExpression(re.getOutput());
        
        int i = reactiveExpressionList.size()-1;
        
        for (;i>-1;--i) {
            if (re.getInputs().contains(reactiveExpressionList.get(i).getOutput())) break;
        }
        reactiveExpressionList.add(++i, re);

        final int addedIndex = i;
        for (;i>-1;--i) {
            if (reactiveExpressionList.get(i).getInputs().contains(re.getOutput())) {//reactive expresison loop found
                reactiveExpressionList.remove(addedIndex);
                if (reDeleted != null) addReactiveExpression(reDeleted); //go back to the previous stat reactive expression that was deleted
                return null;
            } 

        }

        FunctionVariable vf = re.compute();
        if (! isUsedAsInput(re.getOutput())) changedVariables.remove(re.getOutput()); //to avoid to trigger reactive expression on itself
        return vf.getValue();        
    }
    
    public boolean isUsedAsInput (String var) {
        Iterator<ReactiveExpression> i = reactiveExpressionList.iterator();
        while (i.hasNext()) {
            ReactiveExpression re = i.next();
            if (re.getInputs().contains(var)) return true;
        }
        return false;
    }    
    
    public boolean delReactiveExpression (String output) {
        if (isUsedAsInput(output)) return false;
        return forcedDelReactiveExpression (output) != null;
    }
    
    private ReactiveExpression forcedDelReactiveExpression (String output) {
        Iterator<ReactiveExpression> i = reactiveExpressionList.iterator();
        while (i.hasNext()) {
            ReactiveExpression re = i.next();
            if (re.getOutput().equals(output)) {
                i.remove();
                return re;
            }
        }
        return null;
    }
    
    public String computeReactiveExpressions (boolean all) throws Exception{   
        StringBuilder result = new StringBuilder();
        Iterator<ReactiveExpression> i = reactiveExpressionList.iterator();
        
        while (i.hasNext()) {
            ReactiveExpression re = i.next();            
            if (all || re.getInputs().containsAny(changedVariables)) {
                FunctionVariable vf=null;
                try {
                    vf = re.compute();                    
                } catch (CalculusException e) {
                    result.append("\n\nReactive Expression Triggered> ").append(re.toString());
                    result.append("\n Raised an exception: ").append(e.getMessage());                    
                }
                if (vf != null) {
                    result.append("\n\nReactive Expression Triggered> ").append(re.toString());
                    result.append('\n').append(vf.getValue().toString());
                }                
            } else if (changedVariables.contains(re.getOutput())) {
                result.append("\n\nWarning: Reactive Expression Skipped> ").append(re.toString());                
                result.append('\n').append(FunctionVariable.getFunction(re.getOutput()).getValue().toString());
                
            }                        
        }
        
        changedVariables.clear();
        
        return result.toString();
    }    

    public LinkedList<ReactiveExpression> getReactiveExpressions() {
        return reactiveExpressionList;
    }

    public ReactiveExpression getReactiveExpression(String name) {
        ReactiveExpression re = null;
        for (ReactiveExpression r : reactiveExpressionList) {
            if (r.getOutput().equals(name)) {
                re = r;
                break;
            }
        }
        return re;
    }
    
    public void initialize() {
        reactiveExpressionList.clear();
        changedVariables.clear();
    }
}
