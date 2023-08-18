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
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.CalculusException;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FilSaveList extends FunctionPrefix {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLiteral(), new DomainIntervalList());
    private final static String HELP = "... ('fileName', {value}) saves the value (list or matrix) in ICE syntax to fileName.\nIt can be loaded in a new execution of ICE. If the file already exists it is overwritten. It requires a literal with the file name and then the { sub-list } to be saved. The data is save in ASCII text mode.";
    private final static String[] EXAMPLE = {"slex = (statisticRandom(listClone(10, 1_6)));diffuseCumulate ('+', slex - fileLoadList(fileSaveList('savelisttest',{slex})))"};
    private final static String[] RESULT = {"0"};
    
    public FilSaveList() {
        super("file", "SaveList", (byte) 2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        try {
            File workingFile = FileLink.getFileLink(input.getFirst().getName());

            if (workingFile.exists()) workingFile.delete();
            workingFile.createNewFile();

            FileOutputStream fos = new FileOutputStream (workingFile);

            final Parameter<Boolean> nicePrint = IntervalPoint.isNicePrint();
            
            final boolean iNicePrint = nicePrint.getVal();

            nicePrint.setVal(false);

            char[] toSave = input.get(1).getListIntervals().toString().toCharArray();

            nicePrint.setVal(iNicePrint);
            
            for (char c : toSave)
                fos.write(c);
            fos.close();

        } catch (Exception e) {
            throwNewCalculusException ("It was not possible to save the file: "+e.getMessage());
        }
        return input.subList(0, 1);
    }

}
