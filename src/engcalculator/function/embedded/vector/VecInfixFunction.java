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

package engcalculator.function.embedded.vector;

import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.infix.FunctionInfixSubList;
import engcalculator.interval.ListIntervals;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class VecInfixFunction extends FunctionInfixSubList {    
    private final static String HELP = " This function can be also applied to a set of sublist of ({vectL1}, {vectL2}, ..) ... ({vectR1}, {vectR2}, ..)";

    public VecInfixFunction(String name, byte largs, byte rargs, DomainList domainLeft, DomainList domainRight, boolean expandToList, String help, String[] examplesl, String[] examplesr, String[] results) {
        super("vector", name, largs, rargs, new DomainList(), new DomainList(), expandToList, help+HELP, examplesl, examplesr, results);
    }
    
    @Override
    public final ListIntervals _compute (ListIntervals leftSide, ListIntervals rightSide) throws Exception {
        if (leftSide.size() != rightSide.size()) throwNewCalculusException ("The left "+leftSide.size()+" and right "+rightSide.size()+" hands sizes do not match");
        return super._compute(leftSide, rightSide);
    }

    
}
