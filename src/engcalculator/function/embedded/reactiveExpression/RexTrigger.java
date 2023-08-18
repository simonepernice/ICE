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

package engcalculator.function.embedded.reactiveExpression;

import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalLogic;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import engcalculator.reactiveExpression.ReactiveExpression;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class RexTrigger extends FunctionPrefix  {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalLogic());
    
    private final static String HELP = "... (allReactiveExpression) if allReactiveExpression is false triggers a recalculation of the reactive expression whose input variable were changed, otherwise it computes all of them.\nIf the input is false only the reactive expression with changed input are recalculated (if any). True is useful to reset the status if some reactiveExpression output was overwritten or to compute a RE depending on external factors (like random or time). A trigger is automatically given at the end of every expression execution.";    
    private final static String[] EXAMPLES =  {"$are=1,$bre=2;$cre==='are+bre';$bre=1;cre==3","reactiveExpressionTrigger(false);cre==2","$cre==='statisticRandom(0_1)+are',$dre = cre, reactiveExpressionTrigger(false); dre == cre","reactiveExpressionTrigger(true); dre != cre"};
    private final static String[] RESULTS = {"true","true","true","true"};

    public RexTrigger() {
        super ("reactiveExpression", "Trigger", (byte) 1, DOMAIN, false, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {        
        return new ListIntervals(new IntervalLiteral(ReactiveExpression.computeReactiveExpressions(input.getFirst().getValue() == 1d)));
    }

}
