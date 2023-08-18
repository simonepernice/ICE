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

package engcalculator.function.embedded.list;

import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LisIndexOf extends FunctionPrefix {
    private final static String HELP = "... (test_function, list) returns the indexes of the list for which the test_function returns true.\nThe result list can be used with :: and ::= to get or manipolate those elements.";
    private final static String[] EXAMPLE = {"defineLambdaFunction ('$x>5'), listShuffle({1 .. 5},{6 .. 10})","test=1 .. 10;test :: (listIndexOf( defineLambdaFunction ('$x>5'), test))"};
    private final static String[] RESULT = {"1 .. 10 .. 2","test ? (test>5)"};
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(1), new DomainInterval());

    public LisIndexOf() {
        super("list", "IndexOf", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        FunctionPrefix testF = FunctionPrefix.getFunction(input.getFirst().getName());
        
        ListIntervals result =  new ListIntervals ();        
        final int s =input.size();
        
        for (int i=1; i<s; ++i) 
            if (isTrue(testF._compute(input.subList(i, i+1)))) result.append(new IntervalPoint(i-1));
        
        return result;
    }
    
    private boolean isTrue (ListIntervals li) {
        return li.size() == 1 && li.getFirst().getImaginaryPart() == null && li.getFirst().getRight() == 1.  && li.getFirst().getLeft() == 1.;
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws MeasurementUnitException {        
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(MeasurementUnit.PURE);
        return res;        
    }      

}
