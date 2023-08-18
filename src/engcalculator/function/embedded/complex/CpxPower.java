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

package engcalculator.function.embedded.complex;

import engcalculator.domain.*;
import engcalculator.function.prefix.FunctionPrefixByExpression;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CpxPower extends FunctionPrefixByExpression {

    private static final String EXP = "abs(c)^n*exp(I*complexArg(c)*n)";
    private static final String HELP = "... (complexNumber, n) computes the n-th power of the given complexNumber.\nIt uses the polar form to compute all the roots which may increase the size of the resulting interval.";
    private final static String[] VARS = {"c", "n"};
    private final static String[] GROUPS = {"complex"};

    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex(), new DomainLogicAnd(new DomainInteger(), new DomainIntervalPositive()));
    
    private final static String[] EXAMPLE = {"I, 2"};    
    private final static String[] RESULT = {"-1"};
    
    
    public CpxPower () {
        super("complex", "Power", (byte) 2, DOMAIN, false, HELP, EXAMPLE, RESULT, EXP, true, VARS, GROUPS);
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        LinkedList<MeasurementUnit> res = new LinkedList<MeasurementUnit>  ();
        res.add(new MeasurementUnitAccumulator(input.getFirst().getMeasurementUnit()).mul((float)input.getLast().getValue()).toMeasurementUnit());
        return res;
    }
    
}
