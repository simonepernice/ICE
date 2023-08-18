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

package engcalculator.function.embedded.system;

import engcalculator.domain.DomainList;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SysSetupSetParameter extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList();
    private final static String HELP = "... ('symbol', 'param1', val1, .. , 'paramN', valN) set the parameters of the given symbol.\nSome symbol (usually function) in ICE has a static setting (sead parameter). Usually a symbol is a function and parameters are its settingsup the working parameters for a prefix function. It is possible to use just the beginning characters as far as the matching is unique for symbol and parameter. If the parameter is 'DEFAULT' it is set at the value ICE sets at startup.";
    private final static String[] EXAMPLE = {"; listSize functionScanIntervalSplitRandom (1_19)", "systemSetupSetParameter ($functionScanIntervalSplitRandom, 'samples', 19); listSize functionScanIntervalSplitRandom (1_19)",
                                             "a=5_10;systemSetupSetParameter($intervalALL, 'avoidRoundings', 0),b=(a-intervalDual(a)==0,sqrt(a^2)==a),systemSetupSetParameter($intervalALL, 'avoidRoundings', 1),c=(a-intervalDual(a)==0,sqrt(a^2)==a);b,c",
                                              "a = statisticRandom( listClone (10, 0_1)); b = statisticRandom( listClone (10, 0_1)); diffuseCumulate ('&&', a == b)","systemSetupSetParameter($statisticRandomGenerator, 'seed', 1234), systemSetupSetParameter($statisticRandomGenerator, 'type', 1); a = statisticRandom( listClone (10, 0_1)); systemSetupSetParameter($statisticRandomGenerator, 'type', 1); b = statisticRandom( listClone (10, 0_1)); diffuseCumulate ('&&', a == b)",
                                              "systemSetupSetParameter('defineALL', 'currentGroup', 'pippo'), a=5, systemSetupSetParameter('defineALL', 'currentGroup', 'DEFAULT');pippoa"};
    private final static String[] RESULT = {"1000", "19",
                                            "0,0,1,1",
                                            "false", "true",
                                            "5"};
    
    public SysSetupSetParameter() {
        super("system", "SetupSetParameter", (byte) -3, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {        
        Iterator<Interval> i = input.iterator();
        String group = i.next().getName();
        StringBuilder result = new StringBuilder ();
        while (i.hasNext()) {
            String parameter = i.next().getName();
            Parameter s = ParameterManager.getParameter(group, parameter);
            if (s == null) throwNewCalculusException ("The given group or parameter were not recognized: "+group+" "+parameter);
            Interval setting = i.next();
            if ("DEFAULT".equals(setting.getName())) s.setDefaultValue();
            else s.setVal(setting);
            result.append(s.toString());
            if (i.hasNext()) result.append (" , ");
        }
        return new ListIntervals(new IntervalLiteral(result.toString()));
    }

}
