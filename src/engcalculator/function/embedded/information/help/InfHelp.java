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
import engcalculator.function.SymbolWithHelp;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervalsMatrixNamed;
import java.util.Collection;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class InfHelp extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList(new DomainIntervalLiteral());
    private final static String HELP = "... () provides the help of all the functions.\nIt returns all the functions staring with the literal input. Use informationFunctionList to see all the functions available also if not loaded. For a more concise output use exacthelp which provide only the exact match. Ex.: help 'sin'";

public InfHelp() {
        super("information", ".help", (byte) 1, DOMAIN, true, true, HELP, null, null);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervalsMatrixNamed help = new ListIntervalsMatrixNamed("help");
        String fhelp;
        String lookingFor = input.getFirst().getName().trim();
        if (lookingFor.endsWith("()")) lookingFor = lookingFor.substring(0, lookingFor.length()-2);
        else if(lookingFor.endsWith("(")) lookingFor = lookingFor.substring(0, lookingFor.length() - 1);

        Collection<SymbolWithHelp> functions = Function.getAllSymbolsWithHelp();
        for (SymbolWithHelp f : functions) {
            if (f.getSymbol().startsWith(lookingFor)) {
                fhelp = f.getHelp();
                if (fhelp != null && !fhelp.equals("")) {
                    help.add(new IntervalLiteral(fhelp));
                }
            }
        }

        return help;
    }
}
