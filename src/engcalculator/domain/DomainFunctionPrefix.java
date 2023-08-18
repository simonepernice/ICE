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

package engcalculator.domain;

import engcalculator.interval.Interval;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DomainFunctionPrefix extends Domain {
    private final int inputSize;

    public DomainFunctionPrefix(int inputSize) {
        super("Prefix Function with "+(inputSize >= 0 ? inputSize : "at least "+(-inputSize))+" arguments");
        this.inputSize = inputSize;
    }

    @Override
    public boolean isCompatible(Interval i) {
        FunctionPrefix f;
        f = FunctionPrefix.getFunction(i.getName());
        return f != null && ((inputSize >=0 && f.getNumbArgs() == inputSize) || (inputSize < 0 && Math.abs(f.getNumbArgs()) >= -inputSize));
    }

}
