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
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalLiteral;
import engcalculator.interval.ListIntervals;
import engcalculator.reactiveExpression.ReactiveExpression;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class RexShow extends FunctionPrefix  {
    private final static DomainList DOMAIN = new DomainList ();
    
    private final static String HELP = "... show all reactive expressions currently stored.\nThe expressions are showed in the order in which they are computed.";    
    private final static String[] EXAMPLES =  null;
    private final static String[] RESULTS = null;

    public RexShow() {
        super ("reactiveExpression", "Show", (byte) 0, DOMAIN, false, true, HELP, EXAMPLES, RESULTS);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        List<ReactiveExpression> reList = ReactiveExpression.getReactiveExpressions();
        
        StringBuilder result = new StringBuilder ();
        for (ReactiveExpression re : reList)
            result.append(re.toString()).append('\n');
        
        return new ListIntervals(new IntervalLiteral(result.toString()));
    }

}
