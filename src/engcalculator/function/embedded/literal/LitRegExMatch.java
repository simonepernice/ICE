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
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class LitRegExMatch extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral(), new DomainIntervalLiteral());

    private final static String HELP = "... ('regular expression', lit2, .. litn) returns true for each literal matching the given Java regular expression, false otherwise.\nUsed in conjunction with ? can select from a list only the literal matching it. For example it can be used to unlock a group. For instance to unlock the group decibel: defineLockClear (defineShowAllFunctions ? (literalMatch('decibel.*',defineShowAllFunctions )))"+
            "A short explanation of Java Regular Expression follows." +
            "\nJava Regular Expression Description: . Matches any character; ^regex Finds regex that must match at the beginning of the line; regex$ 	Finds regex that must match at the end of the line; [abc] Set definition; can match the letter a or b or c; [abc][vz] Set definition, can match a or b or c followed by either v or z; [^abc] When a caret appears as the first character inside square brackets, it negates the pattern; This can match any character except a or b or c; [a-d1-7] Ranges: matches a letter between a and d and figures from 1 to 7, but not d1; X|Z Finds X or Z; XZ 	Finds X directly followed by Z; $ Checks if a line end follows." +
            "\nMetacharacters: The following metacharacters have a pre-defined meaning and make certain common patterns easier to use, e.g., \\d instead of [0..9]; Regular Expression 	Description: \\d Any digit, short for [0-9]; \\D A non-digit, short for [^0-9]; \\s A whitespace character, short for [ \\t\\n\\x0b\\r\\f]; \\S A non-whitespace character, short for [^\\s]; \\w A word character, short for [a-zA-Z_0-9]; \\W A non-word character [^\\w]; \\S+ Several non-whitespace characters; \\b Matches a word boundary where a word character is [a-zA-Z0-9_]." +
            "\nQuantifier: A quantifier defines how often an element can occur. The symbols ?, *, + and {} define the quantity of the regular expressions. Regular Expression 	Description Examples: * Occurs zero or more times, is short for {0,} X* finds no or several letter X,; .* finds any character sequence; + Occurs one or more times, is short for {1,} X+ - Finds one or several letter X; ? Occurs no or one times, ? is short for {0,1}. X? finds no or exactly one letter X; {X} Occurs X number of times, {} describes the order of the preceding liberal \\d{3} searches for three digits, .{10} for any character sequence of length 10; {X,Y} Occurs between X and Y times, \\d{1,4} means \\d must occur at least once and at a maximum of four; *? ? after a quantifier makes it a reluctant quantifier. It tries to find the smallest match." +
            "\nGrouping and Backreference: You can group parts of your regular expression. In your pattern you group elements with round brackets, e.g., (). This allows you to assign a repetition operator to a complete group. " +
            "\nNegative Lookahead provides the possibility to exclude a pattern. With this you can say that a string should not be followed by another string. Negative Lookaheads are defined via (?!pattern). For example, the following will match \"a\" if \"a\" is not followed by \"b\". a(?!b)";
    private final static String[] EXAMPLE = {"'.*man', 'ironman', 'hulk', 'spiderman'"};
    private final static String[] RESULT = {"true, false, true"};

    public LitRegExMatch() {        
        super("literal", "RegularExpressionMatch", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
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
            if (m.matches()) result.add(Interval.ONE);
            else result.add(Interval.ZERO);
        }                
        return result;
    }

}
