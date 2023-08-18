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

package engcalculator.function.embedded.literal;

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LitRegExFind extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral(), new DomainIntervalLiteral());

    private final static String HELP = "... ('regular expression', literal) looks for sub-literals matching the regular expression within the input literal and returns them.\nIt is possible to apply to several literals. For regular expression syntax see literalRegularExpressionMatch help.";
    private final static String[] EXAMPLE = {"'\\w+man', 'ironman,hulk,spiderman,superman,mr.incredible'"};
    private final static String[] RESULT = {"'ironman','spiderman','superman'"};


    public LitRegExFind() {        
        super("literal", "RegularExpressionFind", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }


    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        Pattern p;
        try {
            p = Pattern.compile (input.getFirst().getName());
        } catch (PatternSyntaxException pse) {
            throwNewCalculusException ("Regular expression syntax is not correct: "+input.getFirst().getName()+" "+pse.getMessage());
            return null;//this will never be reached unless error check are disabled
        }
        final int s = input.size();
        ListIntervals result = new ListIntervals();
        for (int i=1; i< s; ++i) {
            Matcher m = p.matcher(input.get(i).getName());
            while (m.find())
                result.add(new IntervalLiteral(m.group()));
        }                
        return result;
    }

}
