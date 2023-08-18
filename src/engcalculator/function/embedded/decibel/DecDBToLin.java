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

import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.power.Pow;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class DecDBToLin extends FunctionPrefixSingleInputSingleOutput {
    private static final Interval TEN = new IntervalPoint (10);
    private static final DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    private Interval coefficient;
    private final static Pow POW = new Pow();
    private final static AriDiv DIV = new AriDiv();

    public DecDBToLin(String name, int coefficient, String[] examples, String[] results) {
        super ("decibel", name, DOMAIN, true, true, "... converts decibel to linear value.\nIt converts a logarithmic interval a to a linear one returning: 10^(a/"+coefficient+").", examples, results);
        this.coefficient = new IntervalPoint(coefficient);
    }

    @Override
    public final Interval _computeIntervals (Interval input) throws Exception {
        return POW._computeIntervals(TEN, DIV._computeIntervals(input, coefficient));
    }
}
