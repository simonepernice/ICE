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
import engcalculator.function.embedded.arithmetic.AriNegate;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.embedded.complex.CpxAbs;
import engcalculator.function.embedded.power.PowSqr;
import engcalculator.function.embedded.power.PowSqrt;
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
public abstract class FunctionPrefixPlySolveARootATime extends FunctionPrefix {

    private final static String HELP = "The algorithm try to find a zero after the other in the order given in input. Once a zero is found, the original polynomium is deflated dividing by the root just found. For numerical stability it is better starting from smaller roots.";
    private final static DomainList INTERVALDOMAIN = new DomainList(new DomainIntervalList(), new DomainIntervalList());

    protected final static AriSub SUB = new AriSub(); 
    protected final static AriSum SUM = new AriSum(); 
    protected final static AriNegate NEGATE = new AriNegate(); 
    protected final static AriDiv DIV = new AriDiv();
    protected final static AriMul MUL = new AriMul();
    protected final static PowSqrt SQRT = new PowSqrt();    
    protected final static PowSqr SQR = new PowSqr();    
    protected final static CpxAbs ABS = new CpxAbs();    
    protected final static PlyDiv PDIV = new PlyDiv();
    protected final static PlyDerivate DER = new PlyDerivate();
    protected final static PlyEvaluate EVAL = new PlyEvaluate();
    
    protected final static Parameter<Double> EPSILON;
    protected final static Parameter<Integer> MAXITERATIONS;    
    
    static {
        EPSILON = new Parameter<Double>("polynomial","Solve", "epsilon", "The maximum distance between the f(x) and zero, below the iterations stops", 1e-6, new ConvertIntervalToDouble (0,1));
        MAXITERATIONS = new Parameter<Integer>("polynomial","Solve", "maxIteration", "The maximum number of iterations, above which it stops", 100, new ConvertIntervalToInteger (1,1000));
        ParameterManager.addParameter(EPSILON);
        ParameterManager.addParameter(MAXITERATIONS);        
    }      
    
    public FunctionPrefixPlySolveARootATime(String name, String help, String[] example, String[] result ) {
        super("polynomial", name, (byte) 2, INTERVALDOMAIN, false, true, help+HELP, example, result);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        ListIntervals f =  input.getFirst().getListIntervals();    

        if (input.getLast().getListIntervals().size() != f.size()-1) throw new CalculusException ("The polynomial coefficients and initial root guess should have the same dimensions");
                        
        Iterator<Interval> initialGuess = input.getLast().getListIntervals().iterator();     
        
        ListIntervals roots = new ListIntervals ();
        
        while (initialGuess.hasNext()) {
            if (f.size() == 2) {//shortcut to save time avoiding last newton rapshon iteration
                roots.add(DIV._computeIntervals(NEGATE._computeIntervals(f.getLast()), f.getFirst()));
                break;
            }
            
            Interval root = _findNextRoot(f, initialGuess.next());
            
            if (root == null || root.isNAN()) throwNewCalculusException("Exceeded the number of iterations allowed without converging, found the following roots "+roots);
            
            roots.add(root);
            
            f = PDIV._compute(f, new ListIntervals().append(Interval.ONE).append(NEGATE._computeIntervals(root))).getFirst().getListIntervals();
        }

        return roots;
    }
    
    protected abstract Interval _findNextRoot (ListIntervals c, Interval guess) throws Exception;
    
}
