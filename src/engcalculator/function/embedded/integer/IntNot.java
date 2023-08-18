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

package engcalculator.function.embedded.integer;


import engcalculator.domain.DomainIntegerLong;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class IntNot extends FunctionPrefixSingleInputSingleOutput {
    private final static String HELP = "... (a) is the bit operator NOT working  on a integer point interval value a.\nThe NOT operator works on 64 bits, the initial not written bits (at 0 to fill 64 bits) are negated as well.";
    private final static String[] RESULTS = {"integerFromBaseToBaseTen(2, '1111111111111111111111111111111111111111111111111111111111001100')"};
    private final static String[] EXAMPLES = {"integerFromBaseToBaseTen(2, '0110011')"};
    private final static DomainList DOMAIN = new DomainList (new DomainLogicAnd(new DomainIntervalPoint(), new DomainIntegerLong()));

    public IntNot() {
        super("integer", ".!!!", DOMAIN, true, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        return new IntervalPoint(~ (long) input.getLeft());
    }

}
