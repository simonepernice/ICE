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

package engcalculator.function.embedded.information.help;

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.Function;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;
import engcalculator.function.CalculusException;
import engcalculator.function.SymbolWithHelp;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervalsMatrixNamed;
import java.util.Collection;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class InfHelpExact extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... provides the help of the functions matching exactly the literal provided in its input list.\nFor a more expanded help use 'help'. Ex.: help 'sin'.";    
    
    public InfHelpExact () {
        super ("information", ".helpExact", (byte) -1, DOMAIN, false,true, HELP, null, null);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {

        if (input.size() == 0) throwNewCalculusException ("It was required at list one input while found 0");

        ListIntervalsMatrixNamed help = new ListIntervalsMatrixNamed ("Help Exact");
        String fhelp;

       Collection<SymbolWithHelp> functions = Function.getAllSymbolsWithHelp();
       for (SymbolWithHelp f : functions) {
           for (Interval in : input) {
               if (f.getSymbol().equals(in.getName())) {
                    fhelp = f.getHelp();
                    if (!fhelp.equals("")) help.add(new IntervalLiteral(fhelp));
                    break;
               }
           }
       }

        return new ListIntervals ( new IntervalLiteral(help.toString()));
    }

}
