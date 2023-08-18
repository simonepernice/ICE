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

package engcalculator.function.embedded.userDefined;


import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.interval.ListIntervals;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class UDeFunctionPrefixByFunction extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainInterval());
    private final FunctionPrefix function;
    private final ListIntervals baseParameters;
    private final int[] positionParameters;

    public UDeFunctionPrefixByFunction(String name, FunctionPrefix function, ListIntervals baseParameters, int[] positionParameters) {
        super (UDeCurrentGroup.getGroup(name), UDeCurrentGroup.getName(name), (byte)(function.getNumbArgs()-baseParameters.size()), DOMAIN, true, false, "Subset argument function defined by function: "+function.getSymbol()+". The function help is: "+function.getBasicHelpMessage(), null, null);
        this.function = function;
        this.baseParameters = baseParameters;
        this.positionParameters = positionParameters;       
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        input = new ListIntervals(input); //that inportant to avoid to change the input to the caller

        for (int i = 0; i < positionParameters.length; ++i) {
            input.add (positionParameters[i], baseParameters.get(i));
        }

        return function.compute(input);
    }

    @Override
    public String getHelp() {
        return new StringBuilder(super.getHelp()).append("\nUser defined prefix function by function ").append(function.getSymbol()).append("( ..., ").append(baseParameters).append(")").toString();
    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }    

}
