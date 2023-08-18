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

import engcalculator.interval.Interval;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class Rectangle {

    private static final int DISPLACEMENTSCALE = 2;
    private final static double ARROWANGLE = Math.PI/8.;
    private final static double ARROWLENGTH = 4d;
    
    private static CartesianPlane cartesianPlane;
    private final Polygon pointShape = new Polygon();

    public static void setCartesianPlane(CartesianPlane cartesianPlane) {
        Rectangle.cartesianPlane = cartesianPlane;
    }
    
    private final Point p;
    private int xl, xr, yl, yr, xc, yc;
    private boolean plottable;

    public Rectangle() {
        p = new Point();
    }

    public void setRectangle(Interval x, Interval y) {
        xl = cartesianPlane.transformXfromCPtoPX(x.getLeft());
        xr = cartesianPlane.transformXfromCPtoPX(x.getRight());
        yl = cartesianPlane.transformYfromCPtoPX(y.getLeft());
        yr = cartesianPlane.transformYfromCPtoPX(y.getRight());
        computeCenterPoint();
        plottable = x.isIntervalReal() && y.isIntervalReal();
    }
    
    public void setVectorBegin (Interval x, Interval y) {
        int centerx = cartesianPlane.transformXfromCPtoPX(x.getValue());
        int centery = cartesianPlane.transformYfromCPtoPX(y.getValue());
        int dx = cartesianPlane.transformVxfromCPtoPX(x.getRange()/2d);
        int dy = cartesianPlane.transformVyfromCPtoPX(y.getRange()/2d);
        xl = centerx - dx;
        xr = centerx + dx;
        yl = centery - dy;
        yr = centery + dy;
        computeCenterPoint();
        plottable = x.isIntervalReal() && y.isIntervalReal();
    }

    public void setVectorEnd (Interval x0, Interval y0, Interval dx, Interval dy) {
        int centerx = cartesianPlane.transformXfromCPtoPX(x0.getValue());
        int centery = cartesianPlane.transformYfromCPtoPX(y0.getValue());
        int ddx = cartesianPlane.transformVxfromCPtoPX(dx.getRange()/2d);
        int ddy = cartesianPlane.transformVyfromCPtoPX(dy.getRange()/2d);        
        xl = centerx-ddx+cartesianPlane.transformVxfromCPtoPX(dx.getLeft());
        xr = centerx+ddx+cartesianPlane.transformVxfromCPtoPX(dx.getRight());
        yl = centery-ddy+cartesianPlane.transformVyfromCPtoPX(dy.getLeft());
        yr = centery+ddy+cartesianPlane.transformVyfromCPtoPX(dy.getRight());
        computeCenterPoint();
        plottable = x0.isIntervalReal() && y0.isIntervalReal();
    }
    
    public void setRectangle(int x, int y) {
        xl = xr = x;
        yl = yr = y;
        computeCenterPoint();
        plottable = true;
    }    

    public void setRectangle(Rectangle r) {
        xl = r.xl;
        xr = r.xr;
        yl = r.yl;
        yr = r.yr;
        computeCenterPoint();
        plottable = r.plottable;
    }
    
    private void computeCenterPoint () {
        xc = (xl + xr) / 2;
        yc = (yl + yr) / 2;
    }

    public boolean isPlottable() {
        return plottable && xl <= xr && yr <= yl;
    }

    public boolean isPoint() {
        return xl == xr && yl == yr;
    }

    public int getXCenterPoint() {
        return xc;
    }

    public int getYCenterPoint() {
        return yc;
    }

    public int getXCornerUL() {
        return xl;
    }

    public int getYCornerUL() {
        return yr;
    }

    public int getWidth() {//return the width of the rectangle if it is > 0, otherwise it returns 1 because it would not draw anything
        int r = xr - xl;
        if (r>0) return r;
        return 1;
    }

    public int getHeight() {//return the height of the rectangle if it is > 0, otherwise it returns 1 because it would not draw anything
        int r = yl - yr;
        if (r>0) return r;
        return 1;
    }
    
    private int getXDisplacedBy (int steps) {
        return xc + steps * DISPLACEMENTSCALE;
    }
    
    private int getYDisplacedBy (int steps) {
        return yc - steps * DISPLACEMENTSCALE;
    }

    private Point getXYDisplacedByPoint (int xsteps, int ysteps) {
        p.setPointByInteger(getXDisplacedBy(xsteps), getYDisplacedBy(ysteps));
        return p;
    }
    
    public Point getPoint(Direction d) {
        switch (d) {
            case LEFT_UP:
                p.setPointByInteger(xl, yr);
                break;
            case RIGHT_UP:
                p.setPointByInteger(xr, yr);
                break;
            case LEFT_DOWN:
                p.setPointByInteger(xl, yl);
                break;
            case RIGHT_DOWN:
                p.setPointByInteger(xr, yl);
                break;
            default:
                throw new RuntimeException("Unexpected condition at FunctionDrawerJPanel.getPoint");
        }
        return p;
    }
    
    public void drawPoint (int pointIndex, Graphics g) {
        switch (pointIndex) {
            case 0: //dot is already plot by default
                break;
            case 1: //plus
                g.drawLine (getXDisplacedBy(-1), getYDisplacedBy(0), getXDisplacedBy(1), getYDisplacedBy(0));
                g.drawLine (getXDisplacedBy(0), getYDisplacedBy(-1), getXDisplacedBy(0), getYDisplacedBy(1));
                break;
            case 2: //cross
                g.drawLine (getXDisplacedBy(-1), getYDisplacedBy(-1), getXDisplacedBy(1), getYDisplacedBy(1));
                g.drawLine (getXDisplacedBy(-1), getYDisplacedBy(1), getXDisplacedBy(1), getYDisplacedBy(-1));
                break;
            case 3: //square
                pointShape.reset();
                getXYDisplacedByPoint(-1, -1).addToPolygon(pointShape);
                getXYDisplacedByPoint(1, -1).addToPolygon(pointShape);
                getXYDisplacedByPoint(1, 1).addToPolygon(pointShape);
                getXYDisplacedByPoint(-1, 1).addToPolygon(pointShape);
                g.drawPolygon(pointShape);
                break;
            case 4: //diamond
                pointShape.reset();
                getXYDisplacedByPoint(-1, 0).addToPolygon(pointShape);
                getXYDisplacedByPoint(0, 1).addToPolygon(pointShape);
                getXYDisplacedByPoint(1, 0).addToPolygon(pointShape);
                getXYDisplacedByPoint(0, -1).addToPolygon(pointShape);
                g.drawPolygon(pointShape);
                break;
            case 5: //circle
                pointShape.reset();
                getXYDisplacedByPoint(0, 2).addToPolygon(pointShape);
                getXYDisplacedByPoint(1, 2).addToPolygon(pointShape);
                getXYDisplacedByPoint(2, 1).addToPolygon(pointShape);
                getXYDisplacedByPoint(2, -1).addToPolygon(pointShape);
                getXYDisplacedByPoint(1, -2).addToPolygon(pointShape);
                getXYDisplacedByPoint(-1, -2).addToPolygon(pointShape);
                getXYDisplacedByPoint(-2, -1).addToPolygon(pointShape);
                getXYDisplacedByPoint(-2, 1).addToPolygon(pointShape);
                getXYDisplacedByPoint(-1, 2).addToPolygon(pointShape);
                g.drawPolygon(pointShape);
                break;
            case 6: //triangle
                pointShape.reset();
                getXYDisplacedByPoint(-1, -1).addToPolygon(pointShape);
                getXYDisplacedByPoint(0, 1).addToPolygon(pointShape);
                getXYDisplacedByPoint(1, -1).addToPolygon(pointShape);
                g.drawPolygon(pointShape);
                break;    
            default:
                throw new RuntimeException ("Internal Error point type is not defined");
        }    
    }    

    void drawArrow(double vectx, double vecty, Graphics g) {        
        double baseAngle = Math.PI + Math.atan2(vecty, vectx);
        
        pointShape.reset();
        getXYDisplacedByPoint(0, 0).addToPolygon(pointShape);
        
        double angle = baseAngle + ARROWANGLE;
        getXYDisplacedByPoint((int) (ARROWLENGTH*Math.cos(angle) + 0.5d), (int)  (ARROWLENGTH*Math.sin(angle)+0.5d)).addToPolygon(pointShape);
       
        angle = baseAngle - ARROWANGLE;
        getXYDisplacedByPoint((int) (ARROWLENGTH*Math.cos(angle)+0.5d), (int)  (ARROWLENGTH*Math.sin(angle)+0.5d)).addToPolygon(pointShape);
        
        g.drawPolygon(pointShape);        
        g.fillPolygon(pointShape);        
    }
}
