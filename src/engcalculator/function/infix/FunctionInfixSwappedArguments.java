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

package engcalculator.function.infix;

import engcalculator.function.Function;
import engcalculator.interval.ListIntervals;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

//Sometime a compiled programs has infix with its arguments swapped in the stack, in that case this function can accept them and send them swapped to the required infix function (its a kind of stack swap)
public final class FunctionInfixSwappedArguments extends Function {
    private FunctionInfix fun;

    public FunctionInfixSwappedArguments(FunctionInfix fun) {
        super("", "Swapped Args "+fun.getSymbol(), true, "");
        this.fun = fun;
    }
    
    @Override
    public boolean hasExample() {
        return fun.hasExample();
    }    

    @Override
    protected String selfCheck() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getComputeNumbArg() {
        return 2;
    }

    @Override
    public ListIntervals compute(ListIntervals... arguments) throws Exception {
        return fun.compute(arguments[1], arguments[0]);
    }

    @Override
    public String getHelp() {
        return getBasicHelpMessage();
    }



}
