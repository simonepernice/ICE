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

public final class WorkFlow {    
    
    private final WorkFlowSubExpression nextSubExpression;
    private final WorkFlowExpression nextExpression;
    private final String label;

    public WorkFlow(WorkFlowSubExpression nextSubExpression) {
        this.nextSubExpression = nextSubExpression;
        this.nextExpression = WorkFlowExpression.GOTO_NEXT_EX;
        this.label = null;
    }

    public WorkFlow(WorkFlowExpression nextExpression, String label) {
        this.nextSubExpression = WorkFlowSubExpression.FLOW_EX;
        this.nextExpression = nextExpression;
        this.label = label;
    }

    public WorkFlowSubExpression getNextSubExpression() {
        return nextSubExpression;
    }

    public WorkFlowExpression getNextExpression() {
        return nextExpression;
    }        

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        if (nextSubExpression != WorkFlowSubExpression.FLOW_EX) {
            switch (nextSubExpression) {
                case NEXT_SUBEX:
                    return "Execute the next sub expression";
                case NEXT_SUBEX_THEN_THIS_SUBEX:
                    return "Execute the next sub expression then go back to this";
                case PREVIOUS_SUBEX:
                    return "Execute the previous sub expression ";
                case RETURN_THIS_SUBEX:
                    return "Return the value of this sub expression";
                case SKIP_NEXT_SUBEX:
                    return "Go to after next sub expression";
                case STOP_SUBEX:
                    return "Stop executing with this sub expression ";
                case THIS_SUBEX:
                    return "Repeate the execution of this sub expression ";                    
                default:
                    throw new RuntimeException ("Internal error in expression workFlow.toString()");
            }
        } else {
            switch (nextExpression) {
                case GOSUB_LABEL_EX:
                    return "Go Sub label "+label;
                case GOTO_LABEL_EX:
                    return "Go To label "+label;
                case RETURN_EX:
                    return "Return from Go Sub";
                case GOTO_BEGIN_EX:
                    return "Go To Begin ";
                case GOTO_BEFORE_PREV_EX:
                    return "Go To Before Previous";
                case GOTO_PREV_EX:
                    return "Go To Previous";                    
                case GOTO_THIS_EX:
                    return "Repeat this expression";                    
                case GOTO_NEXT_EX:
                    return "Go To Next Expression";                                        
                case GOTO_AFTER_NEXT_EX:
                    return "Go To After Next Expression";                                        
                case GOTO_END_EX:
                    return "Go To End";
                default:
                    throw new RuntimeException ("Internal error in expression workFlow.toString()");
            }        
        }
    }
    
    
    
}
