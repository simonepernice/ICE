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

import java.awt.Polygon;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class Point {

    private static CartesianPlane cartesianPlane;

    public static void setCartesianPlane(CartesianPlane cartesianPlane) {
        Point.cartesianPlane = cartesianPlane;
    }
    
    private int x, y;

    public Point() {
    }

    public void setPointByDouble(double x, double y) {
        this.x = cartesianPlane.transformXfromCPtoPX(x);
        this.y = cartesianPlane.transformYfromCPtoPX(y);
    }

    public void setPointByInteger(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addToPolygon(Polygon p) {
        p.addPoint(x, y);
    }
}
