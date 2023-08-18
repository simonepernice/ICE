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

package engcalculator.function.embedded.measurementUnit;

import engcalculator.domain.DomainIntervalLogic;
import engcalculator.domain.DomainList;
import engcalculator.function.Function;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.function.prefix.FunctionPrefixSingleInputSingleOutput;
import engcalculator.interval.Interval;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MUnManage extends FunctionPrefixSingleInputSingleOutput {
    private static boolean firstIntance = true;    
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLogic());
    
    private final static String HELP = "... [true|false] : define if ICE has to manage the measurement units.\nBy default ICE does not manage measurement unit although they are set. However when the meaurement unit group is loaded this seting is changed to true but it is still possible disable the check with ... .";
    
    public MUnManage() {
        super ("measurementUnit", "Manage", DOMAIN, false, true, HELP, null, null);
        if (firstIntance) {//If this function is loaded the measurement unit management is enabled
            Function.setUseMeasurementUnit(true);
            firstIntance = false;
        }        
    }

    @Override
    public Interval _computeIntervals(Interval input) throws Exception {
        if (LgcBooleanInterval.i2ib(input) == LgcBooleanInterval.TRUE) {
            Function.setUseMeasurementUnit(true);
            return LgcBooleanInterval.ib2i(LgcBooleanInterval.TRUE);
        }
        Function.setUseMeasurementUnit(false);
        return LgcBooleanInterval.ib2i(LgcBooleanInterval.FALSE);        
    }

}
