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
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.Domain;
import engcalculator.domain.DomainList;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class DifInfixToPrefix extends FunctionPrefix {

    public DifInfixToPrefix(java.lang.String group, String symbol, Domain domain, String help, String[] examples, String[] results) {
        super (group, symbol, (byte) -2, new DomainList (domain), false,true, help, examples, results);
    }


    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals acc = new ListIntervals();
        Iterator<Interval> it = input.iterator();
        acc.add(it.next());
        while (it.hasNext()) {
            acc.add(it.next());
            Interval res =__compute(acc);
            acc.clear();
            acc.add(res);
        }
        return acc;
    }

    protected abstract Interval __compute (ListIntervals input) throws Exception;

}
