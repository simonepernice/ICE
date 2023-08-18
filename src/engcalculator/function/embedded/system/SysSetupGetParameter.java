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

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class SysSetupGetParameter extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList(new DomainIntervalLiteral());
    private final static String HELP = "... ($symbol1, $parameter1, $parametern) show the parameter1 up to parametern values.\nIf '' is provided it returns all the symbols available for settings. If only the symbol is provided ... returns all the changable parameters associated with it. Those parameters can be changed with systemSetupSetParameter. It is possible to use just the beginning characters as far as the matching is unique.";
    private final static String[] EXAMPLE = {"'functionScanLinear'","'functionScanLinear','samples'"};
    private final static String[] RESULT = {"'functionScanLinear -> samples = 10000'","'functionScanLinear -> samples = 10000'"};
    
    public SysSetupGetParameter() {
        super("system", "SetupGetParameter", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {        
        Iterator<Interval> i = input.iterator();
        String group = i.next().getName();        
        ListIntervals res = new ListIntervals ();
        if ("".equals(group)) {
            for (String p : ParameterManager.getSymbols())
                res.append(new IntervalLiteral(p));
        } else if (i.hasNext()) {
            while (i.hasNext()) {
                String parameter = i.next().getName();
                Parameter p = ParameterManager.getParameter(group, parameter);
                if (p == null) throwNewCalculusException ("Not recognized parameter before "+parameter);
                res.append(new IntervalLiteral(p.toString()));
            }
        } else {
            Set<String> setp = ParameterManager.getParameters(group);
            if (setp == null) throwNewCalculusException ("There is not any symbol beginning with the given name "+group);
            for (String p : setp)
                res.add(new IntervalLiteral(p));
        }
        return res;

    }

}
