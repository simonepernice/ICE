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

package engcalculatorConsole;

/**
 *
 * @author Stefania Giaconia
 */

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public final class EngCalculatorConsoleApp extends SingleFrameApplication {
    private EngCalculatorConsoleMainFrame console;

    /**
     * At start up create and show the main frame of the application.
     */
    @Override protected void startup() {
        console = new EngCalculatorConsoleMainFrame(this);
        show(console);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    @Override
    protected void end() {
        if (console.doesUserWantsToExit()) super.end(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void closeProgram () {
        end ();
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of EngCalculatorConsoleApp
     */
    public static EngCalculatorConsoleApp getApplication() {
        return Application.getInstance(EngCalculatorConsoleApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(EngCalculatorConsoleApp.class, args);
    }
}
