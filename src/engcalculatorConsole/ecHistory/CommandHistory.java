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
package engcalculatorConsole.ecHistory;

import engcalculatorConsole.iceJPanel.OutputTextData;
import engcalculatorConsole.iceJPanel.InputTextData;
import engcalculator.Calculator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CommandHistory {

    private final ArrayList<String> commands;
    private boolean modified;
    private String justLoaded;
    private int index;
    private boolean backward = true;

    public CommandHistory() {
        commands = new ArrayList<String>();
        modified = false;
        justLoaded = null;
        index = -1;
    }

    public boolean isModified() {
        return modified;
    }

    public void saveToFile(File workingFile) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(workingFile);
        OutputTextData otd = new OutputTextData(fos);

        for (String s : commands) {
            otd.writeln(s);
        }

        otd.close();
        fos.close();

        modified = false;
    }

    public String loadFromFile(File workingFile) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(workingFile);
        InputTextData itd = new InputTextData (fis);
        StringBuilder inputline = new StringBuilder(fis.available());

        String s;
        while ((s = itd.readString()) != null) {
            inputline.append(s);
            inputline.append('\n');
        }

        itd.close();
        fis.close();
        
        justLoaded = inputline.toString();
        return justLoaded;
    }

    public void addCommand(String inputline) {
        if (justLoaded != null) {
            if (commands.isEmpty() && justLoaded.equals(inputline+'\n')) {
                modified = false;
            } else {
                modified = true;
            }
            justLoaded = null;
        } else {
            modified = true;
        }

        addCommandSequence (inputline);
    }

    private void addCommandSequence (String inputline) {
        for (String line : Calculator.splitInLines(inputline, new ArrayList<String> ()))
            commands.add(line);

        index = commands.size();
    }

    public boolean hasPrevious () {
        return index > 0;
    }

    public boolean hasNext () {
        return index < commands.size() - 1;
    }

    public String getNextCommand() {
        if (commands.isEmpty()) {
            return null;
        }

        if (backward) backward = false;
        else ++index;
        
        if (index < 0) {
            index = 0;
            return "";
        }        
        
        if (index >= commands.size()) {
            index = commands.size()-1;
            return "";
        }

        return commands.get(index);
    }

    public String getPreviousCommand() {
        if (commands.isEmpty()) {
            return null;
        }

        if (backward) --index;
        else backward = true;
        
        if (index < 0) {
            index = 0;
            return "";
        }
        
        if (index >= commands.size()) {
            index = commands.size()-1;
            return "";
        }

        return commands.get(index);
    }

    public String getAllCommands() {
        StringBuilder history = new StringBuilder();
        for (String s : commands) {
            history.append(s).append('\n');
        }
        return (history.toString());
    }

    public void deleteAllCommands() {
        commands.clear();
        index = 0;
        modified = true;
    }

    void setAllCommands(String text) {
        deleteAllCommands();
        addCommandSequence(text);
    }
}
