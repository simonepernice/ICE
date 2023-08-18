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

package engcalculatorConsole.iceJTextArea;

import java.util.LinkedList;


public class EditStepStorage {
    private final static int MAXSIZE = 100;
    private final LinkedList<EditStep> undoStepList ;
    private final LinkedList<EditStep> redoStepList ;

    public EditStepStorage() {
        undoStepList = new LinkedList<EditStep> ();
        redoStepList = new LinkedList<EditStep> ();
    }
    
    public void clear () {
        undoStepList.clear();
        redoStepList.clear();
    }
    
    public void addUndoStep (EditStep us) {        
        EditStep u = us.getNewUndoStep(undoStepList.peek());
        if (u != null) {
            undoStepList.push(u);
            redoStepList.clear();
            while (undoStepList.size() > MAXSIZE) undoStepList.removeLast();
        }
    }
    
    public EditStep undoStep () {
        if (undoStepList.size() <= 1) return null; //If one current state is not preserved it is not possible to redo because the void state is stored a new
        EditStep currState = undoStepList.poll();
        if (currState != null) redoStepList.push(currState);
        currState = undoStepList.peek();
        return currState;
    }
    
    public EditStep redoStep () {
        EditStep currState = redoStepList.poll();
        if (currState != null) undoStepList.push(currState);
        return currState;        
    }    
    
}
