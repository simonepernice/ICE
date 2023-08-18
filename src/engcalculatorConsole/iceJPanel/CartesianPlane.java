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

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CartesianPlane implements SaveLoadToTextData {

    public final static float MAXHUE = 0.9f;//MINHUE is 0
    
    private double xMin, xMax, yMin, yMax, cMin, cMax, xTick, yTick, maxVect;
    int nVect;
    private boolean xLog, yLog, cLog, vLog;
    private boolean showAxes, showTick, showTickLabels, showDrawLabels;
    
    private double panelWidthRatio, panelHeightRatio, vectorRatio;
    private final double colorRatio;
    private int backgroundCI, axesCI, gridCI;
    private int panelWidth, panelHeight;

    public CartesianPlane(double xMin, double xMax, double yMin, double yMax, double cMin, double cMax, int nVect, double maxVect, double xTick, double yTick, boolean showAxes, boolean showTick, boolean showTickLabels, boolean showDrawLabels, int backgroundCI, int axesCI, int gridCI, boolean xLog, boolean yLog, boolean cLog, boolean vLog) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.cMin = cMin;
        this.cMax = cMax;
        this.maxVect = maxVect;
        this.nVect = nVect;
        this.xTick = xTick;
        this.yTick = yTick;
        this.showAxes = showAxes;
        this.showTick = showTick;
        this.showTickLabels = showTickLabels;
        this.showDrawLabels = showDrawLabels;
        this.backgroundCI = backgroundCI;
        this.axesCI = axesCI;
        this.gridCI = gridCI;
        this.xLog = xLog;
        this.yLog = yLog;
        this.cLog = cLog;
        this.vLog = vLog;
        colorRatio = MAXHUE / (cMax - cMin);//sinc hue 0 is == hue 1 I decided to go up to MAXHUE
    }
    
    public void setPlaneZoomInFromWindowsData(double x1, double y1, double x2, double y2) {
        setPlaneDisplacementFromWindowsData((panelWidth - (x1 + x2)) / 2.d, (panelHeight - (y1 + y2)) / 2.d);

        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);

        dx /= panelWidthRatio;
        dy /= panelHeightRatio;

        xMin += dx;
        xMax -= dx;
        yMin += dy;
        yMax -= dy;
    }

    public void setPlaneZoomRectFromWindowsData(double x1, double y1, double x2, double y2) {
        final double xmin = Math.min(x1, x2);
        final double xmax = Math.max(x1, x2);
        final double ymin = Math.min(y1, y2);
        final double ymax = Math.max(y1, y2);

        this.xMax = xmax / panelWidthRatio + this.xMin;
        this.xMin = xmin / panelWidthRatio + this.xMin;
        this.yMin = -ymax / panelHeightRatio + this.yMax;
        this.yMax = -ymin / panelHeightRatio + this.yMax;
    }

    public void setPlaneDisplacementFromWindowsData(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        setPlaneDisplacementFromWindowsData(dx, dy);
    }

    private void setPlaneDisplacementFromWindowsData(double dx, double dy) {
        dx /= panelWidthRatio;
        dy /= panelHeightRatio;

        xMin -= dx;
        xMax -= dx;
        yMin += dy;
        yMax += dy;
    }

    public void setPlaneZoomOutFromWindowsData(double x1, double y1, double x2, double y2) {
        setPlaneDisplacementFromWindowsData((panelWidth - (x1 + x2)) / 2, (panelHeight - (y1 + y2)) / 2);

        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);

        dx /= panelWidthRatio;
        dy /= panelHeightRatio;

        xMin -= dx;
        xMax += dx;
        yMin -= dy;
        yMax += dy;
    }

    public void setPanelHeightWidthRatio(int panelHeight, int panelWidth) {
        this.panelHeight = panelHeight;
        panelHeightRatio = panelHeight / getHeight();
        
        this.panelWidth = panelWidth;
        panelWidthRatio = panelWidth / getWidth();
            
        if (maxVect != 0 && nVect != 0) {
            double mV = maxVect;            
            if (vLog) mV = Math.log10(mV);
            vectorRatio = Math.min (panelHeight/(nVect*2.d)/mV,panelWidth/(nVect*2.d)/mV);        
        }
    }

    public int transformXfromCPtoPX(double x) {        
        if (xLog) x = Math.log10(x) - Math.log(xMin);
        else x = x - xMin;
        return (int) (panelWidthRatio * x + 0.5d);
    }
    
    public double transformXfromPXtoCP(double x) {
        if (xLog) x = Math.pow(10d, x);
        return x / panelWidthRatio + xMin;
    }

    public int transformYfromCPtoPX(double y) {        
        if (yLog) y = Math.log10(yMax) - Math.log10(y);
        else y = yMax - y;
        return (int) (panelHeightRatio * y + 0.5d);
    }
    
    public double transformYfromPXtoCP(double y) {
        if (yLog) y = Math.pow(10.d, y);
        return yMax - y / panelHeightRatio;
    }

    public float transformC(double c) {
        if (c < cMin) {
            return 0f;
        }
        c -= cMin;
        if (cLog) c = Math.log10(c);
        if (c > cMax) {
            return MAXHUE;
        }
        return (float) (colorRatio * c);
    }
    
    public int transformVxfromCPtoPX (double x) {
        if (vLog) x = Math.log10(x);
        return (int) (x * vectorRatio + 0.5d);
    }
    
    public int transformVyfromCPtoPX (double y) {
        if (vLog) y = Math.log10(y);
        return (int) (-y * vectorRatio + 0.5d);
    }    

    private double getHeight() {
        if (yLog) return (Math.log10(yMax)-Math.log10(yMin));
        return yMax - yMin;
    }

    private double getWidth() {
        if (xLog) return (Math.log10(xMax)-Math.log10(xMin));
        return xMax - xMin;
    }

    public int zoomX(double x) {
        return (int) (panelWidthRatio * x + 0.5d);
    }

    public int zoomY(double y) {
        return (int) (panelHeightRatio * y + 0.5d);
    }        

    @Override
    public void saveToFileStream(OutputTextData otd) throws IOException {
        otd.writelnComment("Cartesian Plane Save Data");

        otd.writeln("xMin", xMin);
        otd.writeln("xMax", xMax);
        otd.writeln("yMin", yMin);
        otd.writeln("yMax", yMax);
        otd.writeln("cMin", cMin);
        otd.writeln("cMax", cMax);        
        otd.writeln("nVect", nVect);
        otd.writeln("maxVect", maxVect);        
        otd.writeln("xTick", xTick);
        otd.writeln("yTick", yTick);
        otd.writeln("showAxes", showAxes);
        otd.writeln("showTick", showTick);
        otd.writeln("showTickLabels", showTickLabels);
        otd.writeln("showDrawLabels", showDrawLabels);
        otd.writeln("backgroundCI", backgroundCI);
        otd.writeln("axesCI", axesCI);
        otd.writeln("gridCI", gridCI);
        otd.writeln("xLog", xLog);
        otd.writeln("yLog", yLog);
        otd.writeln("cLog", cLog);
        otd.writeln("vLog", vLog);
    }

    @Override
    public void loadFromFileStream(InputTextData itd) throws IOException {
        xMin = itd.readDouble("xMin", -10);
        xMax = itd.readDouble("xMax", 10);
        yMin = itd.readDouble("yMin", -10);
        yMax = itd.readDouble("yMax", 10);
        cMin = itd.readDouble("cMin", -1);
        cMax = itd.readDouble("cMax", 1);
        nVect = itd.readInteger("nVect", 0);
        maxVect = itd.readDouble("maxVect", 1);
        xTick = itd.readDouble("xTick", 2);
        yTick = itd.readDouble("yTick", 2);
        showAxes = itd.readBoolean("showAxes", true);
        showTick = itd.readBoolean("showTick", true);
        showTickLabels = itd.readBoolean("showTickLabels", true);
        showDrawLabels = itd.readBoolean("showDrawLabels", true);
        backgroundCI = itd.readInteger("backgroundCI", 0);
        axesCI = itd.readInteger("axesCI", 1);
        gridCI = itd.readInteger("gridCI", 2);
        xLog = itd.readBoolean("xLog", false);
        yLog = itd.readBoolean("yLog", false);
        cLog = itd.readBoolean("cLog", false);
        vLog = itd.readBoolean("vLog", false);        
    }    

    public boolean isShowAxes() {
        return showAxes;
    }

    public boolean isShowDrawLabels() {
        return showDrawLabels;
    }

    public boolean isShowGrid() {
        return showTick;
    }

    public boolean isShowTickLabels() {
        return showTickLabels;
    }

    public double getXMax() {
        return xMax;
    }

    public double getXMin() {
        return xMin;
    }

    public double getXTick() {
        return xTick;
    }

    public double getYMax() {
        return yMax;
    }

    public double getYMin() {
        return yMin;
    }

    public double getYTick() {
        return yTick;
    }

    public double getMaxVect() {
        return maxVect;
    }

    public int getnVect() {
        return nVect;
    }        

    public double getCMax() {
        return cMax;
    }

    public double getCMin() {
        return cMin;
    }

    public int getAxesCI() {
        return axesCI;
    }

    public int getBackgroundCI() {
        return backgroundCI;
    }

    public int getGridCI() {
        return gridCI;
    }

    public boolean isxLog() {
        return xLog;
    }

    public boolean isyLog() {
        return yLog;
    }

    public boolean iscLog() {
        return cLog;
    }

    public boolean isvLog() {
        return vLog;
    }    
    
}
