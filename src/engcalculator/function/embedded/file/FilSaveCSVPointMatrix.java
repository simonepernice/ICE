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

import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FilSaveCSVPointMatrix extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral(), new DomainIntervalList());
    private final static String HELP = "... ('fileName', {matrix}) saves the given matrix in fileName as CSV.\n';' is used as separator. Requires the file name, the {sub-list} to be saved. If the file already exists it is overwritten. The value of each interval is saved, if the interval is a literal it is saved between \"";
    public FilSaveCSVPointMatrix() {
        super("file", "SaveCsvPointMatrix", (byte) 2, DOMAIN, false, true, HELP, null, null);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        try {
            File workingFile = FileLink.getFileLink(input.getFirst().getName());
            final ListIntervals toSave = input.get(1).getListIntervals();
            final int col = (int) toSave.columnSize();

            if (workingFile.exists()) workingFile.delete();
            workingFile.createNewFile();

            FileOutputStream fos = new FileOutputStream (workingFile);            
            
            int j = 0;
            for (Interval i : toSave) {
                ++j;
                char[] interv;
                interv = (i.isIntervalLiteral() ? new StringBuilder().append('\"').append(i.getName()).append('\"').toString() : Double.toString(i.getValue())).toCharArray();
                for (char c : interv)
                    fos.write(c);
                fos.write(' ');
                fos.write(';');
                fos.write(' ');
                if (j >= col) {
                    fos.write(' ');
                    fos.write('\n'); 
                    j = 0;
                }
            }
            
            fos.close();

        } catch (Exception e) {
            throwNewCalculusException ("It was not possible to save the file: "+e.getMessage());
        }
        return input.subList(0, 1);
    }

}
