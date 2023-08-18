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

package engcalculator.function.embedded.calculus;

import engcalculator.function.prefix.FunctionPrefixExpandLast;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.embedded.list.LisInteger;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class CalSeries extends FunctionPrefixExpandLast {
    private final static LisInteger INT_LIST = new LisInteger();

    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(1), new DomainInteger());

    public CalSeries(String symbol, String help, String[] example, String[] result) {
        super("calculus","Series"+symbol, (byte) 2, DOMAIN, false, true, help, example, result);
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        ListIntervals list = INT_LIST._computeIntervals(input.get(1));
        ListIntervals result = f.compute(list);
        while (result.size()>1) {
            result.push(operation(result.pop(), result.pop()));
        }
        return result;
    }
    
    abstract protected Interval operation (Interval a, Interval b) throws Exception;
  
}
