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

package engcalculator.function.embedded.decibel;

import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.logarithm.LogLog;
import engcalculator.function.embedded.arithmetic.AriMul;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class DecLinToDB extends FunctionPrefix {
    private static final DomainList DOMAIN = new DomainList (new DomainIntervalPositive());

    private ListIntervals coefficient;
    private final static LogLog LOG = new LogLog();
    private final static AriMul MUL = new AriMul();

    public DecLinToDB(String name, int coefficient, String[] examples, String[] results) {
        super ("decibel", name, (byte) 1, DOMAIN, true, true, "... converts a linear interval to a logarithmic value.\nIt returns: "+coefficient+"*log a.", examples, results);
        this.coefficient = new ListIntervals(new IntervalPoint(coefficient));
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        return MUL._compute(coefficient, LOG._compute(input));
    }
}
