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

package engcalculatorConsole.iceJPanel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class OutputTextData extends OutputStreamWriter {
    
    public OutputTextData(OutputStream out) {
        super (out);
    }    

    public void writelnComment (String s) throws IOException {
        write("# ");
        write(s);
        write('\n');
    }
    
    public void writeln (String label) throws IOException {
        write(label);
        write('\n');
    }
    
    public void writeln (String label, String s) throws IOException {
        writelnComment(label);
        write(s);
        write('\n');
    }    

    public void writeln (String label, double d) throws IOException {
        writelnComment(label);
        write(Double.toString(d));
        write('\n');
    }

    public void writeln (String label, int i) throws IOException {
        writelnComment(label);
        write(Integer.toString(i));
        write('\n');
    }

    public void writeln (String label, boolean b) throws IOException {
        writelnComment(label);
        write(Boolean.toString(b));
        write('\n');
    }
}
