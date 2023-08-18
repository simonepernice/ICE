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

import engcalculatorConsole.iceJPanel.CartesianPlane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class GradientJPanel extends JPanel {
    private boolean showGradient = false;

    public void setShowGradient(boolean showGradient) {
        this.showGradient = showGradient;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (showGradient) {
            final Dimension size = getSize();
            final float dColor = CartesianPlane.MAXHUE/size.width;
            float color = -dColor;
            for (int i=0;i<size.width;++i) {
                g.setColor(Color.getHSBColor(color+=dColor, 1f, 1f));
                g.drawLine(i, 0, i, size.height);
            }
        }
    }

}
