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

package engcalculator.function.embedded.diffuse;

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.list.LisBuilder;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DifExpandParameters extends FunctionPrefix {
    private final static String HELP = "... ($f, a1, {a21, .., a2n}, .., {an1, .., ann}) returns the list given by applying f to each of its input expanding sub-list: f (a1, a21, .., an1), f(a1, a22, .., an2), .., f(a1, a2n, .., ann))\nThis function is useful to apply a prefix function (of several arguments) where some arguments can change in a list. Note that is performed automatically by ICE in most cases (1 argument function and on last arguments). If some argument of the function needs to be a sub-list it should be boxed within a sublist (adding a couple of extra curly brackets), ... will understand it is not a parameter to expand and will unbox for the calculus.";
    private final static String[] EXAMPLE = {"$sin, {1,2,3}"," $equationFindRoot, $sin, {-1_1,1_4,4_7}, 0","$listClone, {2,3}, {3,4}","$listClone, {2,3}, {{3,4}}"};
    private final static String[] RESULT = {"sin(1,2,3)","0,PI,2*PI","listClone (2, 3), listClone(3,4)","listClone (2, {3,4}), listClone(3,{3,4})"};
    private final static DomainList INTERVALDOMAIN = new DomainList (new DomainFunctionPrefix(-1), new DomainInterval());
    private final static LisBuilder LB = new LisBuilder();
    
    public DifExpandParameters() {
        super ("diffuse", "ExpandParameters", (byte) -2, INTERVALDOMAIN, false,true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        final int sm1 = input.size()-1;
        
        if (f.getNumbArgs() >= 0) {
            if(sm1 != f.getNumbArgs()) throwNewCalculusException ("The function "+f.getSymbol()+" requires "+f.getNumbArgs()+" arguments while it received just "+(sm1));
        } else {
            if (sm1 < -f.getComputeNumbArg()) throwNewCalculusException ("The function "+f.getSymbol()+" requires at least "+(-f.getNumbArgs())+" arguments while it received just "+(sm1));
        }
                
        ListIntervals parameters = new ListIntervals(input.subList(1, input.size())); //new is required because some element of the list may be changed
        
        boolean[] isIL = new boolean[sm1];
        for (int i=0;i<sm1;++i) {
            if (parameters.get(i).isIntervalList()) {
                ListIntervals tli = parameters.get(i).getListIntervals();
                if (tli.size() == 1 && tli.getFirst().isIntervalList()) {//sub-list unboxing
                    parameters.set(i, tli.getFirst());                  //sub-list unboxing
                    isIL[i] = false;
                } else isIL[i] = true;
            } else isIL[i] = false;
        }
        
        int length = 1;
        for (int i=0; i <sm1; ++i)
            if (isIL[i])
                if (length == 1) length = parameters.get(i).getListIntervals().size();
                else 
                    if (length != parameters.get(i).getListIntervals().size()) throwNewCalculusException ("The function "+f.getSymbol()+" should have all the list intervals of the same size while it was found sizes "+length+" and "+parameters.get(i).getListIntervals().size()+" at position "+i);
                
        ListIntervals result = new ListIntervals();
        for (int j=0; j<length; ++j) {
            ListIntervals in = new ListIntervals ();
            for (int i=0; i<sm1; ++i)
                if (isIL[i]) in.add(parameters.get(i).getListIntervals().get(j));
                else in.add(parameters.get(i));
            if (j==0) result = f.compute(in);   //thi is important if the return value is a matrix, without this if LB would discard the first matrix because trying to join with empty list
            else result = LB._compute(result, f.compute(in));
        }

        return result;
    }

}
