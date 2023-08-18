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

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class MainFrameFocusTraversalPolicy extends FocusTraversalPolicy {

    private final ArrayList<Component> components;

    MainFrameFocusTraversalPolicy() {
        components = new ArrayList<Component>();
    }

    void addComponent(Component c) {
        components.add(c);
    }

    @Override
    public Component getComponentAfter(Container aContainer, Component aComponent) {
        int i = components.indexOf(aComponent);

        if (i < components.size() - 1) {
            ++i;
        } else {
            i = 0;
        }

        return components.get(i);
    }

    @Override
    public Component getComponentBefore(Container aContainer, Component aComponent) {
        int i = components.indexOf(aComponent);

        if (i > 0) {
            --i;
        } else {
            i = components.size() - 1;
        }

        return components.get(i);
    }

    @Override
    public Component getFirstComponent(Container aContainer) {
        return components.get(0);
    }

    @Override
    public Component getLastComponent(Container aContainer) {
        return components.get(components.size() - 1);
    }

    @Override
    public Component getDefaultComponent(Container aContainer) {
        return components.get(0);
    }
}
