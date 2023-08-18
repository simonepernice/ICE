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

package engcalculator.function.embedded.engineer;

import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngToColorCode extends EngColorCode {
    private final static String[] EXAMPLES = {"100k%10", "33%5"};
    private final static String[]  RESULTS = {"brown, black, yellow, silver","orange, orange, black, gold"};

    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());


    public EngToColorCode() {
        super ("engineer", "ToColorCode", (byte) 1, DOMAIN, true, true, "... converts the given value to a color code, use preferred function to be sure the color code does exists commercially. For example: ...(100K%10)=('brown','black','yellow','silver').", EXAMPLES, RESULTS);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        final int digits;
        double tol = input.getFirst().getTolerance();
        
        if (tol > 3.5) digits = 2;
        else digits = 3;
        if (tol < 0) throwNewCalculusException ("Tolerance value "+tol+" is out of range.");
        
        double val = input.getFirst().getValue();
        final double max = Math.pow(10, digits);
        final double min = Math.pow(10, digits-1);
        
        int exp = 0;
        while (val >= max) {
            val /= 10;
            ++exp;
        }
        while (val < min) {
            val *= 10;
            --exp;
        }
        
        if (exp > 9 || exp < 0) throwNewCalculusException ("Exponent range "+exp+" is out of range.");
        
        String sVal = Integer.toString((int) val);
        ListIntervals result = new ListIntervals();
        for (int i=0;i<digits;++i) 
            result.append(new IntervalLiteral(COLORS[sVal.charAt(i)-'0']));
        
        result.append(new IntervalLiteral(COLORS[(int) exp]));
        
        result.append(new IntervalLiteral(COLORS[findCloserIndexTolerance((float) tol, false)]));
        
        return result;

    }

}
