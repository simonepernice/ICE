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
package engcalculator.function.embedded.polynomial;

import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.parameter.ConvertIntervalToDouble;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class PlySolveDK extends FunctionPrefix {

    private final static String HELP = "... ({an, an-1, .., a1, a0}, {rn-1, rn-2, .., r1, r0}) given the coefficients of polynomial and a first guess of the roots it tries to find iterativaly all the available roots at the same time.\nThe algorithm is based on Durand–Kerner method described on https://en.wikipedia.org/wiki/Durand%E2%80%93Kerner_method. To find complex roots the starting point should be complex number and they should be inside some not-too-large circle containing also the roots of ƒ(x) and they should not be close to each other: do not use the same starting points for two roots.";
    private final static String[] EXAMPLE = {"{1,5,6},{-1,-4}", "{(1, -6) ***** (1, -3)}, {1,10}"};
    private final static String[] RESULT = {"-2,-3", "3, 6"};
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalList(), new DomainIntervalList());

    private final static AriMul MUL = new AriMul(); 
    private final static AriSub SUB = new AriSub(); 
    private final static AriDiv DIV = new AriDiv();
    private final static PlyEvaluate EVAl = new PlyEvaluate();
    
    private final static Parameter<Double> EPSILON;
    private final static Parameter<Integer> MAXITERATIONS;    
    
    static {
        EPSILON = new Parameter<Double>("polynomial","SolveDK", "epsilon", "The maximum distance between two iterations, below this value it stops", 1e-6, new ConvertIntervalToDouble (0,1));
        MAXITERATIONS = new Parameter<Integer>("polynomial","SolveDK", "maxIteration", "The maximum number of iterations, above which it stops", 100, new ConvertIntervalToInteger (1,1000));
        ParameterManager.addParameter(EPSILON);
        ParameterManager.addParameter(MAXITERATIONS);        
    }      
    
    public PlySolveDK() {
        super("polynomial", "SolveDK", (byte) 2, INTERVALDOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals c =  input.getFirst().getListIntervals();  
        
        //if it is required it sets the first coefficient to one
        {
            Iterator<Interval> ii = c.iterator();
            Interval fc = ii.next();
            if (! fc.equals(Interval.ONE)) {
                ListIntervals cn = new ListIntervals ();
                cn.add(Interval.ONE);
                while (ii.hasNext()) {
                    cn.add(DIV._computeIntervals(ii.next(), fc));
                }
                c = cn;
            }
        }
        
        ListIntervals x0 ; 
        ListIntervals x1 = input.getLast().getListIntervals();
        
        if (x1.size() != c.size()-1) throw new CalculusException ("The polynomial coefficients and initial root guess should have the same dimensions");
        
        final int maxIteration = MAXITERATIONS.getVal();
        final double epsilon = EPSILON.getVal();
        
        int iterations = 0;
        do {
            ++iterations;
            x0 = x1;
            x1 = new ListIntervals ();
            for (Interval x : x0) {
                Interval acc = Interval.ONE;
                for (Interval y : x0)
                    if (x != y) acc = MUL._computeIntervals(acc, SUB._computeIntervals(x, y));

                x1.add(SUB._computeIntervals(x, DIV._computeIntervals(EVAl._computeIntervals(c, x), acc)));        
            }
        } while (x0.distance(x1) > epsilon && iterations < maxIteration);
        
        if (iterations >= maxIteration) throwNewCalculusException("Exceeded the number of iterations allowed.");

        return x1;
    }
}
