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
import engcalculator.function.prefix.FunctionPrefix;
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
public final class FilLoadCSVPointMatrix extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral());
    private final static String HELP = "... ('filename') returns the matrix of real values contained in the ASCII file called 'filename'.\nThe values in the file has to be separated by ;. The first row is used to read the number of columns. If the next rows have more columns they are scraped, if less they are set to 0. Every row becomes a row of the returned matrix.";
    private final static String[] EXAMPLE = {"slex = (statisticRandom(listClone(10, 1_6))#2);diffuseCumulate ('+', slex - fileLoadCsvPointMatrix(fileSaveCsvPointMatrix('savelisttest', {slex})))"};
    private final static String[] RESULT = {"0"};    

    public FilLoadCSVPointMatrix() {
        super("file", "LoadCsvPointMatrix", (byte) 1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervalsMatrix result;
        try {
            File workingFile = FileLink.getFileLink(input.getFirst().getName());

            FileInputStream fis = new FileInputStream (workingFile);

            result = new ListIntervalsMatrix ();
            
            int c, col = 0, oldcol = 0;
            StringBuilder data = new StringBuilder ();
            
            c = fis.read();
            while (c != -1) {
                
                while (Character.isDigit(c) || c == '+' || c == '-' || c == 'e' || c == 'E' || c == '.') {//read next value with a little of precheck for the parsing
                    if (c == 'E') data.append ('e');
                    else data.append((char) c);
                    c = fis.read();
                }
                
                //Try to convert the last data read to a point interval
                if (data.length() > 0) {
                    try {             
                        result.add(IntervalPoint.valueOf(data.toString()));
                        ++col;
                    } catch (Exception pe) {}
                    data.setLength(0);
                }
                
                while (! (Character.isDigit(c) || c == '+' || c == '-' || c == '.' || c == -1)) {//looking for the next number to parse
                    if (c == '\n') {//if the line is completed check if enough columns were found
                        if (oldcol == 0) oldcol = col;
                        else if (col < oldcol) while (col < oldcol) { ++ col; result.add(new IntervalPoint(0));}
                        else if (oldcol < col) throwNewCalculusException ("Two rows with different size were found: "+oldcol+" , "+col);
                        col = 0;                          
                    }      
                    c = fis.read();
                } 
            

            }

            fis.close();
            
            while (result.size() % oldcol != 0) throwNewCalculusException ("Two rows with different size were found: total size "+result.size()+" , last column "+oldcol);
            
            result.setColumnsNumber(oldcol);

            return result;

        } catch (IOException e) {
            throwNewCalculusException ("An error happens trying to read the file: "+e.getMessage());
            return null;//this will never be reached unless error check are disabled
        } 

    }

}
