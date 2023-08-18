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

import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DomainFunctionInfix extends Domain {
    private final int inputLeftSize, inputRightSize;

    public DomainFunctionInfix(int inputLeftSize, int inputRightSize) {
        super("Infix Function with "+(inputLeftSize >= 0 ? inputLeftSize : "at least "+(-inputLeftSize))+" left arguments and with "+(inputRightSize >= 0 ? inputRightSize : "at least "+(-inputRightSize))+" right arguments");
        this.inputLeftSize = inputLeftSize;
        this.inputRightSize = inputRightSize;
    }

    @Override
    public boolean isCompatible(Interval i) {
        FunctionInfix f;
        f = FunctionInfix.getFunction(i.getName());
        return f != null && ((inputLeftSize >=0 && f.getNumbLeftArgs() == inputLeftSize) || (inputLeftSize < 0 && Math.abs(f.getNumbLeftArgs()) >= -inputLeftSize) || (inputRightSize >=0 && f.getNumbRightArgs() == inputRightSize) || (inputRightSize < 0 && Math.abs(f.getNumbRightArgs()) >= -inputRightSize));
    }

}
