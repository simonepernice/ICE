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
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class InputTextData extends InputStreamReader {

    private final static boolean DEBUG = true;
    private String lastLabel, lastValue;

    public InputTextData(InputStream is) {
        super(is);
    }

    private String readLine() throws IOException {
        StringBuilder result = new StringBuilder();
        int c;
        while ((c = read()) != -1 && c != '\n') {
            result.append((char) c);
        }

        if (c == -1) {
            return null;
        }
        return result.toString();
    }
    
    private String readValue (String label) throws IOException {
        if (lastLabel != null) {
            if (lastLabel.equals(label)) {
                lastLabel = null;
                return lastValue;
            } 
            //System.out.println("Do not found label "+label+" , in memory "+lastLabel);
            return null;
        }
        lastValue = null;
        do {
            String v = readLine();
            if (v == null) return null;
            if (v.length() > 0) {
                if (v.charAt(0) == '#') lastLabel = v.substring(2);
                else lastValue = v;           
            }
        } while (lastValue == null);
        if (lastLabel.equals(label)) {
            lastLabel = null;
            return lastValue;
        }
        //System.out.println("Do not found label "+label+" , in memory "+lastLabel);  
        return null;        
    }

    public String readString() throws IOException {
        String result;

        do {
            result = readLine();
            if (result == null) {
                return null;
            }
            result = result.trim();
        } while (result.length() == 0 || result.charAt(0) == '#');

        return result;
    }

    public String readString(String label, String def) throws IOException {
        String line = readValue(label);
        if (line == null) {
            if (DEBUG) System.out.println("Read string not found, default value used");
            return def;
        }
        return line;
    }
    
    public double readDouble(String label, double def) throws IOException {
        try {
            String line = readValue(label);
            if (line == null) {
                if (DEBUG) System.out.println("Read double not found, default value used");
                return def;
            }
            return Double.parseDouble(line);
        } catch (NumberFormatException nfe) {
            throw new IOException(nfe.getMessage());
        }
    }

    public int readInteger(String label, int def) throws IOException {
        try {
            String line = readValue(label);
            if (line == null) {
                if (DEBUG) System.out.println("Read integer not found, default value used");
                return def;
            }            
            return Integer.parseInt(line);
        } catch (NumberFormatException nfe) {
            throw new IOException(nfe.getMessage());
        }
    }

    public boolean readBoolean(String label, boolean def) throws IOException {
        try {
            String line = readValue(label);
            if (line == null) {
                if (DEBUG) System.out.println("Read boolean not found, default value used");
                return def;
            }          
            return Boolean.parseBoolean(line);
        } catch (NumberFormatException nfe) {
            throw new IOException(nfe.getMessage());
        }
    }

}
