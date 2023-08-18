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
import engcalculator.function.embedded.list.LisLinear;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.diffuse.DifSpread;
import engcalculator.function.embedded.interval.ItvRange;
import engcalculator.function.embedded.interval.ItvValue;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class CalIntegrator extends FunctionPrefixExpandLast {
    private static final AriMul MUL = new AriMul();
    private static final LisLinear LIN_LIST = new LisLinear();
    private static final ItvRange RANGE = new ItvRange();
    private static final DifSpread SPREAD = new DifSpread();
    private final static ItvValue VALUE = new ItvValue(); 

    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(1), new DomainLogicAnd(new DomainIntervalPoint(), new DomainInteger(), new DomainIntervalPositive()), new DomainIntervalReal());

    public CalIntegrator(String group, String name, String help, String[] example, String[] result) {
        super(group, name, (byte) 3, DOMAIN, true, true, help, example, result);
    }
    
    protected ListIntervals computeIntegrateFunction (ListIntervals input) throws Exception {
        ListIntervals sectors = computeSectors (input);

        FunctionPrefix f = FunctionPrefix.getFunction(input.getFirst().getName());
        
        return MUL.compute(RANGE.compute(sectors), f.compute(VALUE.compute(sectors)));        
    }
    
    protected ListIntervals computeSectors (ListIntervals input) throws Exception {
        Interval range = input.get(2), noi = input.get(1);

        ListIntervals s = new ListIntervals(new IntervalLiteral ("_"));
        s.addAll(LIN_LIST._compute(new ListIntervals (new IntervalPoint((int) noi.getLeft()+1)).append(range)));
        ListIntervals sectors = SPREAD._compute(s);
        
        return sectors;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }    

}
