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
import java.util.Arrays;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionInfixMultipleDomain extends FunctionInfix {
    private final DomainList[] rightDomains;
    private final DomainList[] leftDomains;
    
    public FunctionInfixMultipleDomain(String group, String name, byte leftArg, byte rightArg, DomainList[] leftDomains, DomainList[] rightDomains, boolean expandsToLists, boolean locked, String help, String[] leftExamples, String[] rightExamples, String[] results) {
        super(group, name, leftArg, rightArg, null, null, expandsToLists, locked, help, leftExamples, rightExamples, results);
        this.leftDomains = leftDomains;
        this.rightDomains = rightDomains;
    }  

    @Override
    public final ListIntervals _compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        int domainMatchingFunction;
        
        for (domainMatchingFunction = 0; domainMatchingFunction < leftDomains.length; ++domainMatchingFunction)
            if ((leftDomains[domainMatchingFunction] == null || leftDomains[domainMatchingFunction].isCompatible(leftSide)) && 
                 (rightDomains[domainMatchingFunction] == null || rightDomains[domainMatchingFunction].isCompatible(rightSide))) break;
        
        if (domainMatchingFunction >= leftDomains.length) 
            throwNewCalculusException("The function requires any of the following " + Arrays.toString(leftDomains) + " left side input domain and " + Arrays.toString(rightDomains) + " as right side while it was found " + leftSide.toString() + " and " + rightSide.toString());
        
        return _computeDomain(domainMatchingFunction, leftSide, rightSide);        
    }

    public abstract ListIntervals _computeDomain(int domainMatchingFunction, ListIntervals leftSide, ListIntervals rightSide);

}
