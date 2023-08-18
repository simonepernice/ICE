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

package engcalculatorConsole.ecConsolePlotDisplay;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

import engcalculator.interval.ListIntervals;
import engcalculator.function.variable.FunctionVariable;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class Drawing {
    private final String name;
    private boolean joined;
    private boolean visible;
    private boolean showLabel;    
    private int pointType;
    private int colorIndex;
    
    public static boolean isASuitableDrawing (FunctionVariable v) {        
        final ListIntervals li = v.getValue();
        final int col = li.columnSize() ;
        return ( li.isMatrix() && col >= 2 && col <= 4);        
    }

    public Drawing(String name) {
        this.name = name;
        final int c = getColumns();
        
        this.joined = c != 3;
        
        this.visible = false;
        
        this.pointType = 0;
        
        this.colorIndex = (c == 3 ? EngCalculatorConsolePlotDisplay.COLOR_SET_BY_VAL : 0);
        
        this.showLabel = false;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isJoined() {
        return joined;
    }    

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public void setJoined(boolean joined) { 
        this.joined = joined;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public int getPointType() {
        return pointType;
    }

    public void setPointType(int pointIndex) {
        this.pointType = pointIndex;
    }

    public boolean isShowLabel() {
        return showLabel;
    }      

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    public FunctionVariable getData() {
        return FunctionVariable.getFunction(name);
    }
    
    private int getColumns () {
        return FunctionVariable.getFunction(name).getValue().columnSize();
    }

}
