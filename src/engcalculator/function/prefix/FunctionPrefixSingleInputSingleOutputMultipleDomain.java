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

package engcalculator.function.prefix;

import engcalculator.interval.Interval;
import engcalculator.domain.DomainList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class FunctionPrefixSingleInputSingleOutputMultipleDomain extends FunctionPrefixSingleInputSingleOutput {
    public FunctionPrefixSingleInputSingleOutputMultipleDomain (java.lang.String group, String name, DomainList domain, boolean expandsToLists, boolean locked, String help, String[] examples, String[] results) {
        super(group, name, domain, expandsToLists, locked, help, examples, results);
    }

    public final Interval _computeIntervals (Interval input) throws Exception {
        if (input.isIntervalPoint()) return _computeIntervalPoint (input);
        if (input.isIntervalReal()) return _computeIntervalReal (input);
        if (input.isIntervalLiteral()) return _computeIntervalLiteral (input);
        return _computeIntervalComplex (input);
    }

    public abstract Interval _computeIntervalPoint(Interval input) throws Exception;

    public abstract Interval _computeIntervalReal(Interval input) throws Exception;

    public abstract Interval _computeIntervalLiteral(Interval input) throws Exception; 

    public abstract Interval _computeIntervalComplex(Interval input) throws Exception;
    

}
