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

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.interval.IntervalList;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionInfixSubList extends FunctionInfix {
    private final DomainList  domainLeft, domainRight;

    public FunctionInfixSubList(String group, String name, byte largs, byte rargs, DomainList domainLeft, DomainList domainRight, boolean expandToList, String help, String[] examplesl, String[] examplesr, String[] results) {
        super(group, name, largs, rargs, new DomainList(), new DomainList(), expandToList, true, help, examplesl, examplesr, results);
        this.domainLeft = domainLeft;
        this.domainRight = domainRight;
    }

    @Override
    public ListIntervals _compute (ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        if (leftSide.isMadeByIntervalList() && rightSide.isMadeByIntervalList()) {            
            ListIntervals result = new ListIntervals ();
            if (leftSide.size() != rightSide.size()) throwNewCalculusException ("The left and right hand elements sizes do not matches: "+leftSide.size()+" vs "+rightSide.size());
            final int s = leftSide.size();
            for (int j=0; j<s; ++j) {
                ListIntervals l = leftSide.get(j).getListIntervals();
                ListIntervals r = rightSide.get(j).getListIntervals();
                if (domainLeft != null && ! domainLeft.isCompatible(l)) throwNewCalculusException ("Error: "+getSymbol()+" requires "+domainLeft.toString()+" left input domain while it was found "+l);
                if (domainRight != null && ! domainRight.isCompatible(r)) throwNewCalculusException ("Error: "+getSymbol()+" requires "+domainRight.toString()+" right input domain while it was found "+r);                           
                result.append(new IntervalList(__compute(l, r)));
            }        
            return result;
        }
        if (domainLeft != null && ! domainLeft.isCompatible(leftSide)) throwNewCalculusException ("Error: "+getSymbol()+" requires "+domainLeft.toString()+" left input domain while it was found "+leftSide);
        if (domainRight != null && ! domainRight.isCompatible(rightSide)) throwNewCalculusException ("Error: "+getSymbol()+" requires "+domainRight.toString()+" right input domain while it was found "+rightSide);            
        return __compute(leftSide, rightSide);
    }
    
    public abstract  ListIntervals __compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception;
    
}
