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

import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.Domain;
import engcalculator.domain.DomainIntervalComplex;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DifSumUp extends DifInfixToPrefix {
    private final static AriSum SUM = new AriSum();
    private final static String HELP = "... (x1, .., xn) sums the intervals contained in the list.\nIt return x1+ .. +xn.";
    private final static String[] EXAMPLE = {"1, 2, 3"};
    private final static String[] RESULT = {"6"};
    private final static Domain INTERVALDOMAIN = new DomainIntervalComplex();

    public DifSumUp() {//not used as explicit ICE function
        super ("diffuse", "SumUp", INTERVALDOMAIN, HELP, EXAMPLE, RESULT);
    }

    @Override
    protected Interval __compute(ListIntervals input) throws Exception {
        return SUM._compute(input.subList(0, 1), input.subList(1, 2)).getFirst();
    }
}
