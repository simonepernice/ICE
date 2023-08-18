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
public enum Direction {

    UNKNOW, LEFT_UP, RIGHT_UP, RIGHT_DOWN, LEFT_DOWN;

    public static Direction rotate90(Direction d) {
        switch (d) {
            case UNKNOW:
                return UNKNOW;
            case LEFT_UP:
                return LEFT_DOWN;
            case RIGHT_UP:
                return LEFT_UP;
            case RIGHT_DOWN:
                return RIGHT_UP;
            case LEFT_DOWN:
                return RIGHT_DOWN;
        }
        throw new RuntimeException("Unexpected condition at Direction.rotate90");
    }

    public static Direction rotate180(Direction d) {
        return rotate90(rotate90(d));
    }

    public static Direction rotate270(Direction d) {
        return rotate90(rotate90(rotate90(d)));
    }

    public static Rotation detectRotation(Direction pd, Direction d) {
        if (pd == d) {
            return Rotation.STRIGHT;
        }
        if (rotate90(pd) == d) {
            return Rotation.COUNTERCLOCKWISE;
        }
        if (rotate270(pd) == d) {
            return Rotation.CLOCKWISE;
        }
        return Rotation.UNKNOW;
    }

    public static Direction findDirection (Rectangle pRect, Rectangle rect) {
        if (pRect.getYCenterPoint() >= rect.getYCenterPoint()) {//y change direction as integer!!!
            if (pRect.getXCenterPoint() < rect.getXCenterPoint()) {
                return Direction.RIGHT_UP;
            } else {
                return Direction.LEFT_UP;
            }
        } else {
            if (pRect.getXCenterPoint() >= rect.getXCenterPoint()) {
                return Direction.LEFT_DOWN;
            } else {
                return Direction.RIGHT_DOWN;
            }
        }
    }
}
