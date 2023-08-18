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

import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainFunctionInfix;
import engcalculator.domain.DomainFunctionVariable;
import engcalculator.domain.DomainInterval;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.variable.FunctionVariable;
import engcalculator.interval.Interval;
import java.util.Iterator;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class DifAccumulator extends FunctionPrefix {
    private final static DomainList INTERVALDOMAIN = new DomainList (new DomainFunctionVariable(), new DomainFunctionInfix(-1, -1), new DomainInterval());

    public DifAccumulator(String side, String help, String[] examples, String[] results) {
        super ("diffuse", "AccumulateOn"+side, (byte) -3, INTERVALDOMAIN, false,true, help, examples, results);
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        Iterator<Interval> in = input.iterator();        
        FunctionVariable acc = FunctionVariable.getFunction(in.next().getName());
        FunctionInfix f = FunctionInfix.getFunction(in.next().getName());
        ListIntervals rest = new ListIntervals(input.subList(2, input.size()));
        return _computeListIntervals (acc, f, rest);
    }
    
    public abstract ListIntervals _computeListIntervals (FunctionVariable acc, FunctionInfix f, ListIntervals rest) throws Exception;

}
