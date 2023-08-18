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

package engcalculator.expression;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class LabelsStorage {
    private final static boolean OPTIMIZEPOSITIONAFTEREVERYGETROW = true;
    
    private final LinkedList<Label> labels;

    public LabelsStorage() {
        labels = new LinkedList<Label>();
    }

    public boolean addLabel (Label l) {
        if (labels.contains(l)) return false;
        labels.addLast(l);
        return true;
    }
    
    public void clear () {
        labels.clear();
    }

    public int getRow (String label) {
        ListIterator<Label> li = labels.listIterator();
        while (li.hasNext()) {
            Label l = li.next();
            if (l.equalName(label)) {
                if (OPTIMIZEPOSITIONAFTEREVERYGETROW) {
                    li.remove();
                    labels.addFirst(l);
                }
                return l.getRow();
            }
        }
        return -1;
    }

}
