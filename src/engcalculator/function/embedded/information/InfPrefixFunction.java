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

package engcalculator.function.embedded.information;

import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class InfPrefixFunction extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList ();
    private String outputText;

    public InfPrefixFunction(String name, String help) {
        super("information", name, (byte) 0, DOMAIN, false, true, help, null, null);
    }

    public InfPrefixFunction(String name, String help, String output) {
        super("information", name, (byte) 0, DOMAIN, false, true, help, null, null);
        outputText = output;
    }

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        return new ListIntervals ( new IntervalLiteral (outputText));
    }

    protected String setOutputTextFromFile (String fileName, String replace, String replacement) {
        setOutputTextFromFile(fileName);
        outputText = outputText.replace(replace, replacement);
        return outputText;
    }
    
    protected String setOutputTextFromFile (String fileName) {
        InputStreamReader is;
        try {
            is = new InputStreamReader(getClass().getResourceAsStream(fileName), "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            outputText = "";
            return outputText;
        }
        StringBuilder result = new StringBuilder ();
        int c;
        try {
            while ((c = is.read()) != -1) result.append((char)c);
        } catch (IOException ioe) {
            outputText = "";
            return outputText;
        }
        outputText = result.toString();
        return outputText;
    }
}
