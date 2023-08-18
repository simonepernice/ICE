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

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculatorConsole.ecConsolePlotDisplay.Drawing;
import engcalculatorConsole.ecConsolePlotDisplay.EngCalculatorConsolePlotDisplay;
import engcalculatorConsole.ecConsolePlotDisplay.ecPlotOption.EngCalculatorConsolePlotOptions;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class ICEJPanel extends JPanel {

    private final static int XLABELDISPLACE = 2;
    private final static int YLABELDISPLACE = -2;
    private LinkedList<Drawing> drawings = null;
    private CartesianPlane cartesianPlane = null;

    public void setCartesianPlane(CartesianPlane cartesianPlane) {
        this.cartesianPlane = cartesianPlane;
        Point.setCartesianPlane(cartesianPlane);
        Rectangle.setCartesianPlane(cartesianPlane);
    }

    public CartesianPlane getCartesianPlane() {
        return cartesianPlane;
    }

    public void setDrawings(LinkedList<Drawing> drawings) {
        this.drawings = drawings;
    }

    @Override
    public void paint(Graphics g) {
        if (cartesianPlane == null) {
            return;
        }
        setBackground(EngCalculatorConsolePlotDisplay.colorConverter(cartesianPlane.getBackgroundCI()));
        super.paint(g);

        final Color bckGnd = EngCalculatorConsolePlotDisplay.colorConverter(cartesianPlane.getBackgroundCI());

        cartesianPlane.setPanelHeightWidthRatio(getHeight(), getWidth());

        if (drawings != null) {

            ListIntervals data = null;

            for (Drawing d : drawings) {

                if (!d.isVisible()) {
                    continue;
                }

                final boolean isColorSetByVal = EngCalculatorConsolePlotDisplay.isColorSetByVal(d.getColorIndex());
                final boolean isColorSetByTol = EngCalculatorConsolePlotDisplay.isColorSetByTol(d.getColorIndex());
                final boolean isVariableColor = isColorSetByTol || isColorSetByVal;                

                if (d.getData() == null) continue;
                data = d.getData().getValue();
                final int columns = data.columnSize();
                final boolean showLabel = d.isShowLabel() && (columns == 3 || columns == 4);

                if (isVariableColor && columns != 3) {
                    continue;
                }

                if (!isVariableColor) {
                    g.setColor(EngCalculatorConsolePlotDisplay.colorConverter(d.getColorIndex()));
                }

                Direction direction = Direction.UNKNOW, pDirection = Direction.UNKNOW, dir;

                Rectangle rect = new Rectangle(), pRect = new Rectangle();
                Polygon poligon = new Polygon();
                double angle;

                boolean firstPoint = true;
                Iterator<Interval> id = data.iterator();
                Interval[] datai = new Interval[columns];
                
                if (columns == 4) {//draw vectorial field                    
                    ListIntervals toPrint = new ListIntervals();
                    while (id.hasNext()) {
                        poligon.reset();
                        
                        for (int i=0;i<columns;++i) datai[i] = id.next();
                        
                        pRect.setVectorBegin(datai[0], datai[1]);
                        rect.setVectorEnd(datai[0], datai[1], datai[2], datai[3]);
                        
                        if (!(rect.isPlottable() && pRect.isPlottable())) {
                            continue;
                        }
                        
                        if (d.isJoined()) {

                            direction = Direction.findDirection(pRect, rect);

                            if (rect.isPoint() && pRect.isPoint()) {

                                g.setPaintMode();
                                g.drawLine(pRect.getXCornerUL(), pRect.getYCornerUL(), rect.getXCornerUL(), rect.getYCornerUL());

                            } else {

                                g.setXORMode(bckGnd);

                                dir = Direction.rotate90(direction);
                                pRect.getPoint(dir).addToPolygon(poligon);

                                dir = Direction.rotate180(dir);
                                pRect.getPoint(dir).addToPolygon(poligon);

                                dir = Direction.rotate270(direction);
                                rect.getPoint(dir).addToPolygon(poligon);

                                dir = Direction.rotate180(dir);
                                rect.getPoint(dir).addToPolygon(poligon);

                                g.fillPolygon(poligon);
                            }         
                        }
                        
                        g.setPaintMode();
                        rect.drawArrow(datai[2].getValue(), datai[3].getValue(), g);
                        
                        if (d.getPointType() != 0) {
                            g.setXORMode(bckGnd);
                            pRect.drawPoint(d.getPointType(), g);
                        }             
                        
                        if (showLabel) {
                            toPrint.clear();
                            toPrint.append(datai[2]);
                            toPrint.append(datai[3]);
                            g.drawString(toPrint.toString(), pRect.getXCornerUL(), pRect.getYCornerUL());
                        }                               
                        
                    }                               
                } else if (d.isJoined()) {//draw joined plot
                    while (id.hasNext()) {
                        poligon.reset();
                        
                        for (int i=0;i<columns;++i) datai[i] = id.next();

                        rect.setRectangle(datai[0], datai[1]);
                        if (!rect.isPlottable()) {
                            continue;
                        }
                        
                        if (showLabel) {
                            g.drawString(datai[2].toString(), rect.getXCornerUL(), rect.getYCornerUL());
                        }

                        if (firstPoint) {
                            firstPoint = false;
                        } else {

                            if (isColorSetByVal) {
                                g.setColor(Color.getHSBColor(cartesianPlane.transformC(datai[2].getValue()), 1, 1));
                            } else if (isColorSetByTol) {
                                g.setColor(Color.getHSBColor(cartesianPlane.transformC(datai[2].getTolerance()), 1, 1));
                            }

                            direction = Direction.findDirection(pRect, rect);

                            if (rect.isPoint() && pRect.isPoint()) {

                                g.setPaintMode();
                                g.drawLine(pRect.getXCornerUL(), pRect.getYCornerUL(), rect.getXCornerUL(), rect.getYCornerUL());

                            } else {

                                if (pDirection != direction && pDirection != Direction.UNKNOW) {
                                    g.setPaintMode();
                                } else {
                                    g.setXORMode(bckGnd);
                                }

                                dir = Direction.rotate90(direction);
                                pRect.getPoint(dir).addToPolygon(poligon);

                                dir = Direction.rotate180(dir);
                                pRect.getPoint(dir).addToPolygon(poligon);

                                dir = Direction.rotate270(direction);
                                rect.getPoint(dir).addToPolygon(poligon);

                                dir = Direction.rotate180(dir);
                                rect.getPoint(dir).addToPolygon(poligon);

                                g.fillPolygon(poligon);
                            }

                        }
                        
                        if (d.getPointType() != 0) {
                            g.setXORMode(bckGnd);
                            rect.drawPoint(d.getPointType(), g);
                        }
                        
                        pRect.setRectangle(rect);
                        pDirection = direction;
                    }
                } else {//draw a point plot
                    g.setXORMode(bckGnd);
                    Rectangle r = new Rectangle();
                    while (id.hasNext()) {
                        for (int i=0;i<columns;++i) datai[i] = id.next();
                        
                        r.setRectangle(datai[0], datai[1]);
                        if (!r.isPlottable()) {
                            continue;
                        }
                        
                        if (isColorSetByVal) {
                            g.setColor(Color.getHSBColor(cartesianPlane.transformC(datai[2].getValue()), 1, 1));
                        } else if (isColorSetByTol) {
                            g.setColor(Color.getHSBColor(cartesianPlane.transformC(datai[2].getTolerance()), 1, 1));
                        }
                        g.fillRect(r.getXCornerUL(), r.getYCornerUL(), r.getWidth(), r.getHeight());
                        if (showLabel) {
                            g.drawString(datai[2].toString(), r.getXCornerUL(), r.getYCornerUL());
                        }
                                                                     
                        if (d.getPointType() != 0) {
                            r.drawPoint(d.getPointType(), g);
                        }
                    }
                }
            }
        }

        g.setPaintMode();

        if (cartesianPlane.isShowGrid()) {

            g.setColor(EngCalculatorConsolePlotDisplay.colorConverter(cartesianPlane.getGridCI()));
            g.setXORMode(bckGnd);
            
            if (cartesianPlane.getXTick() != 0.) {
                final int ymin = cartesianPlane.transformYfromCPtoPX(cartesianPlane.getYMin());
                final int ymax = cartesianPlane.transformYfromCPtoPX(cartesianPlane.getYMax());                
                final double min;
                final double max;
                if (cartesianPlane.getXMax() < cartesianPlane.getXMin()) {
                    max = cartesianPlane.getXMin();
                    min = cartesianPlane.getXMax();
                } else {
                    max = cartesianPlane.getXMax();                    
                    min = cartesianPlane.getXMin();
                }
                final double tck =  Math.abs(cartesianPlane.getXTick());
                if (cartesianPlane.isxLog()) {
                    for (double x = min; x < max ; x +=tck * Math.pow(10d, Math.floor(Math.log10(x)))) {
                        int xt = cartesianPlane.transformXfromCPtoPX(x);

                        g.drawLine(xt, ymin, xt, ymax);

                        if (cartesianPlane.isShowTickLabels()) {
                            g.drawString(EngCalculatorConsolePlotOptions.setSignificantDigits(x, 2), xt+XLABELDISPLACE, ymin+YLABELDISPLACE);
                        }
                    }
                } else {
                    for (double x = Math.floor(min / tck) * tck; x < max; x += tck) {
                        int xt = cartesianPlane.transformXfromCPtoPX(x);

                        g.drawLine(xt, ymin, xt, ymax);

                        if (cartesianPlane.isShowTickLabels()) {
                            g.drawString(EngCalculatorConsolePlotOptions.setSignificantDigits(x, 2), xt+XLABELDISPLACE, ymin+YLABELDISPLACE);
                        }                        
                    }                
                }
            }                                                     
            
            if (cartesianPlane.getYTick() != 0.) {
                final int xmin = cartesianPlane.transformXfromCPtoPX(cartesianPlane.getXMin());
                final int xmax = cartesianPlane.transformXfromCPtoPX(cartesianPlane.getXMax());    
                final double min;
                final double max;
                if (cartesianPlane.getYMax() < cartesianPlane.getYMin()) {
                    max = cartesianPlane.getYMin();
                    min = cartesianPlane.getYMax();
                } else {
                    max = cartesianPlane.getYMax();                    
                    min = cartesianPlane.getYMin();
                }
                final double tck =  Math.abs(cartesianPlane.getYTick());                
                if (cartesianPlane.isyLog()) {
                    for (double y = min; y < max; y += tck * Math.pow(10d, Math.floor(Math.log10(y)))) {                
                        int yt = cartesianPlane.transformYfromCPtoPX(y);

                        g.drawLine(xmin, yt, xmax, yt);

                        if (cartesianPlane.isShowTickLabels()) {
                            g.drawString(EngCalculatorConsolePlotOptions.setSignificantDigits(y, 2), xmin+XLABELDISPLACE, yt+YLABELDISPLACE);
                        }                       
                    }                
                } else {
                    for (double y = Math.floor(min / tck) * tck; y < max; y += tck) {

                        int yt = cartesianPlane.transformYfromCPtoPX(y);

                        g.drawLine(xmin, yt, xmax, yt);

                        if (cartesianPlane.isShowTickLabels()) {
                            g.drawString(EngCalculatorConsolePlotOptions.setSignificantDigits(y, 2), xmin+XLABELDISPLACE, yt+YLABELDISPLACE);
                        }
                    }
                }
            }
        }

        if (cartesianPlane.isShowAxes()) {
            g.setColor(EngCalculatorConsolePlotDisplay.colorConverter(cartesianPlane.getAxesCI()));
            g.setXORMode(bckGnd);

            int zero = cartesianPlane.transformYfromCPtoPX(0);
            g.drawLine(cartesianPlane.transformXfromCPtoPX(cartesianPlane.getXMin()), zero, cartesianPlane.transformXfromCPtoPX(cartesianPlane.getXMax()), zero);

            zero = cartesianPlane.transformXfromCPtoPX(0);
            g.drawLine(zero, cartesianPlane.transformYfromCPtoPX(cartesianPlane.getYMin()), zero, cartesianPlane.transformYfromCPtoPX(cartesianPlane.getYMax()));

        }

        if (cartesianPlane.isShowDrawLabels() && drawings != null) {
            final int XPOINT = 10, XDISPLACEMENT = XPOINT + 10;

            FontMetrics fm = g.getFontMetrics();
            int maxwidth = 0;
            int rows = 0;

            for (Drawing d : drawings) {
                int width;
                if (d.isVisible()) {
                    width = fm.stringWidth(d.getName());
                    if (width > maxwidth) {
                        maxwidth = width;
                    }
                    ++rows;
                }
            }

            g.setColor(Color.WHITE);
            g.setPaintMode();
            g.fillRect(XPOINT, 0, maxwidth, rows * fm.getHeight());

            int drawingsNameRow = 0;
            Rectangle rect = new Rectangle();
            
            for (Drawing d : drawings) {
                if (d.isVisible()) {                    
                    g.setColor(EngCalculatorConsolePlotDisplay.colorConverter(d.getColorIndex()));
                    rect.setRectangle(XPOINT, drawingsNameRow+fm.getHeight()/2);
                    rect.drawPoint(d.getPointType(), g);
                    g.drawString(d.getName(), XDISPLACEMENT, drawingsNameRow += fm.getHeight());
                }
            }
        }

    }

}
