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

package engcalculator.function.embedded.statistic;


import engcalculator.domain.DomainIntervalProper;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainListMatrix;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.function.parameter.ConvertIntervalToDouble;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.ListIntervalsMatrix;
import java.util.Iterator;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaInvertMeasure extends FunctionPrefix {
    private final static AriSum SUM = new AriSum();
    private final static AriMul MUL = new AriMul();
    private final static AriDiv DIV = new AriDiv();
    private final static String HELP = "... [xExpectedPoint, yMeasuredInterval] where x is expected (usually a point interval) and y is measure (must be an interval) then it compute the inverse function table: [yMeasuredIntervalReduced, xExpectedPoint].\n The x does not require to be ordered. Basically the y is scanned to find the min, max and minimuminterval range. The an intergal of size range/2 goes from y min to y max. For each step it computes the average of the x values weighted with the percentage of intersection of y.";
    private final static String[] EXAMPLE = {"[ 4,  1_3  ],[ 6, 2_4  ],[  8 , 3.5_6  ],[ 10 , 7_8  ],[ 12, 7.5_9  ],[ 14, 8.5_11]"};
    private final static String[] RESULT = {"[1_2,  4  ],[2_3 , 5  ],[3_3.5 ,  6  ],[ 3.5_4, 7  ],[ 4_6, 8  ],[ 7_7.5 ,10  ],[ 7.5_8 ,11  ],[8_8.5, 12  ],[ 8.5_9, 13],[ 9_11,14  ]"};
    private final static DomainList DOMAIN = new DomainListMatrix (new DomainIntervalProper());
   
    final static Parameter<Double> EPSON;
    
    static {
        EPSON = new Parameter<Double> ("statistic", "InvertMeasure", "epson", "Set the maximum distance between two ys to consider them equal.", 0.0100, new ConvertIntervalToDouble(0., 1000.));
        ParameterManager.addParameter(EPSON);
    }
    
    public StaInvertMeasure() {
        super("statistic", "InvertMeasure", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        if (input.columnSize() != 2) throwNewCalculusException ("A 2 column matrix was expected while it was found "+input.columnSize()+" columns");
        if (input.size() < 2) throwNewCalculusException ("At least 2 elements are required while they were found "+input.size());
                
        double min=0, max=0, range=0;
        
        Iterator<Interval> iter;
        
        iter = input.iterator();
        boolean firstIteration = true;
        while (iter.hasNext()) {
            iter.next();
            Interval n = iter.next();
            if (firstIteration) {
                firstIteration = false;
                min = n.getLeft();
                max = n.getRight();
                range = n.getRange();                
            } else {
                if (n.getLeft() < min) min = n.getLeft();
                if (n.getRight() > max) max = n.getRight();
                if (n.getRange() < range) range = n.getRange();
            }
        }
        if (range == 0.) throwNewCalculusException("It was find a point interval, only real intervals are accepted");
        range /= 2.;
                                         
        ListIntervalsMatrix resultPar = new ListIntervalsMatrix (2);
        Interval acc;
        double n;
        for (double xp = min; xp < max; ) {
            acc = new IntervalPoint(0.);
            n = 0.;
            final double xpPrev = xp;
            xp += range;
            Interval x = new IntervalReal (xpPrev, xp);
            
            iter = input.iterator();
            while (iter.hasNext()){
                Interval y = iter.next();
                double in = intersection (x, iter.next());
                if (in > 0.) {
                    n += in;
                    acc = SUM._computeIntervals(acc, MUL._computeIntervals(y, new IntervalPoint(in)));
                }                
            }
            if (n > 0) {
                resultPar.add(x);
                resultPar.add(DIV._computeIntervals(acc, new IntervalPoint (n)));
            }
        }
            
        ListIntervalsMatrix result = new ListIntervalsMatrix (2);
        
        iter = resultPar.iterator();
        firstIteration = true;
        Interval x=null, y=null;
        final double epson = EPSON.getVal();
        int ni=1;        
        acc = null;
        while (iter.hasNext())  {
            if (firstIteration) {
                firstIteration = false;
                x = iter.next();
                y = iter.next();
                ni = 1;
                acc = y;
            } else {
                Interval xn = iter.next();
                Interval yn = iter.next();         
                if (y.distance(yn) < epson) {//here it is averaging through the equivalent y
                    x = join(x,xn);
                    acc = SUM._computeIntervals(acc, yn);
                    ++ni;
                } else {
                    result.add (x);
                    if (ni == 1) result.add (y);
                    else result.add (DIV._computeIntervals(acc, new IntervalPoint(ni)));
                    x = xn;
                    y = yn;
                    ni = 1;
                    acc = y;
                }            
            }           
        }
        result.add (x);
        if (ni == 1) result.add (y);
        else result.add (DIV._computeIntervals(acc, new IntervalPoint(ni)));  
        
        return result;

    }
    
    private double intersection (Interval a, Interval b) {
        if (a.getRight() >= b.getLeft() && a.getRight() <= b.getRight()) {
            if (a.getLeft() <= b.getRight() && a.getLeft() >= b.getLeft()) return 1.;
            return (a.getRight()-b.getLeft())/a.getRange();
        }
        if (a.getLeft() <= b.getRight() && a.getLeft() >= b.getLeft()) {
            return (b.getRight()-a.getLeft())/a.getRange();
        }
        return 0.;
    }
    
    private Interval join (Interval a, Interval b) {
        return new IntervalReal (Math.min(a.getLeft(), b.getLeft()), Math.max(a.getRight(), b.getRight()));
    }
    
}
