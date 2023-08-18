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

package engcalculator.interval.workFlow;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

public enum WorkFlowExpression {    
    GOTO_LABEL_EX, 
    GOSUB_LABEL_EX, 
    RETURN_EX,
    GOTO_END_EX, 
    GOTO_BEGIN_EX, 
    GOTO_BEFORE_PREV_EX, 
    GOTO_PREV_EX,     
    GOTO_THIS_EX, 
    GOTO_NEXT_EX,
    GOTO_AFTER_NEXT_EX;
}
