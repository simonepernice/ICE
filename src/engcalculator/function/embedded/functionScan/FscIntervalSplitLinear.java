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
package engcalculator.function.embedded.functionScan;

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.domain.DomainIntervalProper;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.function.prefix.FunctionPrefix;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class FscIntervalSplitLinear extends FunctionPrefix {
    private final static IntervalSplitLinearStorage INTERVAL_SPLIT_LINEAR_STORAGE = new IntervalSplitLinearStorage();
        
    private final static String HELP = "... (variableName, interval) create a new variable with (variableName) made by a set of interval (3 by default) uniformly distribuited within the given interval.\n Every time a new variable is added it automatically rebuilds all the previous variables added variables so that all the possible combinations are tested in a calculus. With Join function it is possible to increase the variables. If those variable are used in reactive expression it is possible to automatically recompute all the previous values. With systemSetup ($..., numberOfSamples) it is possible to set the default number of samples. If the functions user are linear 2 would be enough otherwise the more the better for accuracy although as the number of variables increases also the values stored in the variables increase exponentially.";
    private final static String[] EXAMPLE = {"functionScanIntervalSplitLinear ($z1, 1_5, $z2, 5_7);functionScanIntervalJoin (z1/z2)"};
    private final static String[] RESULT = {"1_5/5_7"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalLiteral(), new DomainIntervalProper());
    
    public static void initialize () {
        INTERVAL_SPLIT_LINEAR_STORAGE.initialize();
    }
    
    public static boolean deleteLinearVar (String name) throws Exception {
        return INTERVAL_SPLIT_LINEAR_STORAGE.deleteLinearVar(name);
    }
    
    public FscIntervalSplitLinear() {
        super("functionScan", "IntervalSplitLinear", (byte) 2, INTERVALDOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }
        
    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        //System.out.println("FscIntervalSplitLinear input "+input);
        INTERVAL_SPLIT_LINEAR_STORAGE.addLinearVar(input.getFirst().getName(), input.getLast());
        return input.subList(0, 1);
    }

}
