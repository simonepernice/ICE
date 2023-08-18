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

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.function.embedded.power.PowSqrt;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class VecModule extends VecPrefixFunction {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());
    
    private final static String HELP = "... (vector) returns the module of the given vector.\nIt is the scalar prodct to itself.";
    private final static String[] EXAMPLE = {"(1,2,3,4)"};
    private final static String[] RESULT = {"sqrt(1+4+9+16)"};
    private final static VecProductScalar SCPR = new VecProductScalar();
    private final static PowSqrt SQRT = new PowSqrt();

    public VecModule() {
        super ("Module", (byte) -1, DOMAIN, false, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals input) throws Exception {                
        return SQRT.compute(SCPR.compute(input, input));
    }
}
