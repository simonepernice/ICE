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

package engcalculator.function.embedded.file;

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.CalculusException;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.ConvertIntervalToString;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.ListIntervalsMatrix;
import engcalculator.interval.IntervalPoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FilLoadPointMatrix extends FunctionPrefix {

    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... ('filename') loads a matrix of real values from the ASCII 'filename', reading readLines and skipping skipLines, using the given dotSeparator, commaSeparator, and string delimitator.\nIt is a kind of brute force high efficency reader: it tries to read as more as possible. It can be used to read data created with other programs like log files. Every row is expected to end with a new line. A number is [+-.][0..9]+ and it is translated in a point interval. If it is not possible to translate it is converted to literal. Every input row becomes a matrix row. It is possible to se several parameters by systemSetupSetParameter: the number of rows to read and then the ones to skip, the character used as decimal separator, the character used as value separator and the string delimitator.";
    private final static String[] EXAMPLE = {"slex = (statisticRandom(listClone(10, 1_6)));diffuseCumulate ('+', slex - fileLoadPointMatrix(fileSaveCsvPointMatrix('savelisttest',{slex})))"};
    private final static String[] RESULT = {"0"};
    
    private final static Parameter<Integer> READLINES;    
    private final static Parameter<Integer> SKIPLINES;    
    private final static Parameter<String> DECSEP;    
    private final static Parameter<String> VALSEP;    
    private final static Parameter<String> STRSEP;    
    
    
    static {
        SKIPLINES = new Parameter<Integer> ("file", "LoadPointMatrix", "skipLines", "Sets the number of lines to skip after the read lines. Use 0 to get all the matrix.", 0, new ConvertIntervalToInteger(0, 1000000));
        READLINES = new Parameter<Integer> ("file", "LoadPointMatrix", "readLines", "Sets the number of lines to read before skipping lines.", 1, new ConvertIntervalToInteger(1, 1000000));        
        DECSEP = new Parameter<String> ("file", "LoadPointMatrix", "decimalSeparator", "It is used to separate number from decimal part.", ".", new ConvertIntervalToString(1));        
        VALSEP = new Parameter<String> ("file", "LoadPointMatrix", "valueSeparator", "It is used tu separate values on one line.", ";", new ConvertIntervalToString(1));        
        STRSEP = new Parameter<String> ("file", "LoadPointMatrix", "stringDelimitator", "It is used to delimit literal.", "\"", new ConvertIntervalToString(1));        
        ParameterManager.addParameter(SKIPLINES);
        ParameterManager.addParameter(READLINES);
        ParameterManager.addParameter(DECSEP);
        ParameterManager.addParameter(VALSEP);
        ParameterManager.addParameter(STRSEP);
    }    
    
    public FilLoadPointMatrix() {
        super("file", "LoadPointMatrix", (byte) 1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervalsMatrix result;
        try {
            File workingFile = FileLink.getFileLink(input.getFirst().getName());

            FileInputStream fis = new FileInputStream (workingFile);

            int columns = -1;
            final int rowRead = READLINES.getVal();
            final int rowSkip = SKIPLINES.getVal();
            final char decSep = DECSEP.getVal().charAt(0);
            final char valSep = VALSEP.getVal().charAt(0);
            final char strSep = STRSEP.getVal().charAt(0);
            
     
            result = new ListIntervalsMatrix (columns);
            
            int c = 0, col = 0, rr = 0;
            StringBuilder data = new StringBuilder ();
                        
            while (c != -1) {
                c = fis.read();
                
                while (c != valSep && c != '\n' && c != -1) {//read next value
                    data.append((char) c);
                    c = fis.read();
                }
                
                //Try to convert the last data read to a point interval

                String val = data.toString().trim();
                if (val.length() > 0 ) {
                    try {             
                        if (val.charAt(0) == strSep && val.charAt(val.length()-1) == strSep) result.add(new IntervalLiteral(val));
                        else {
                            if (decSep != '.') val = val.replace(decSep, '.');
                            result.add(new IntervalPoint(Double.valueOf(val)));
                        }
                    } catch (Exception pe) {
                        result.add(new IntervalLiteral (val));
                    }
                    ++col;
                    data.setLength(0);
                }                 
                
                
                //at the end of line store the column number
                if (c == '\n') {               
                    if (columns == -1) {
                        columns = col;
                        result.setColumnsNumber(columns);
                    } else {
                        while (col < columns) {
                            ++ col;
                            result.add(new IntervalPoint(0.));
                        }
                    }
                    col = 0;
                    ++rr;
                }
                
                if (rr >= rowRead) {
                    if (rowSkip > 0) {
                        rr = rowSkip;
                        while (rr > 0) {
                            c = fis.read();
                            while (c != -1 && c != '\n') c = fis.read();                            
                            
                            -- rr;
                        }                      
                    }
                    rr = 0;                    
                }                

            }

            fis.close();

            return result;

        } catch (IOException e) {
            throwNewCalculusException ("An error happens trying to read the file: "+e.getMessage());
            return null;//this will never be reached unless error check are disabled
        } 

    }

}
