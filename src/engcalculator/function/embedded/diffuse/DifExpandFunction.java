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
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import java.util.Iterator;


/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DifExpandFunction extends FunctionPrefix {
    private final static String HELP = "... ($f, {a21, .., a2n}, .., {an1, .., ann}) returns (f(a21, .., a2n), .., (an1, .., ann)).\nUsually ICE performs that expansion autonumosly: sin (1,2,3) == sin 1, sin 2, sin 3. However that is not the case for the function which may accept an unlimited set of arguments like for listSize.";
    private final static String[] EXAMPLE = {"a=(1,2,3), b=(4,5), c=(5); diffuseExpandFunction ($listSize,  {a}, {b}, {c})",";listSize ({a}, {b}, {c})",";listSize (a,b,c)"};
    private final static String[] RESULT = {"3,2,1","3","6"};
    private final static DomainList INTERVALDOMAIN = new DomainList (new DomainFunctionPrefix(-1), new DomainInterval());
    
    public DifExpandFunction() {
        super ("diffuse", "ExpandFunction", (byte) -2, INTERVALDOMAIN, false,true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        Iterator<Interval> i = input.iterator();
        FunctionPrefix f = FunctionPrefix.getFunction(i.next().getName());
        ListIntervals result = new ListIntervals ();
        while (i.hasNext()) {
            result.addAll(f.compute(i.next().getListIntervals()));
        }
        return result;
    }

}
