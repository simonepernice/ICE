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

package engcalculator.function.embedded.calculus;

import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalPoint;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainInterval;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.function.embedded.arithmetic.AriDiv;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class CalDerivate extends FunctionPrefix {
    private final static AriSub SUB = new AriSub();
    private final static AriDiv DIV = new AriDiv();

    private final static String HELP = "... ('f', x0, x1, .., xn ) computes the incremental ratio of the function f in the intervals xileft, xiright.\nFor the input variable expressed in terms of list of intervals the derivate is compute with higher order: ... ($sin, {1_1.1,1.1_1.2}) computes the second order derivate of function sin in the pont 1.1. If the input contains several set of point the derivate is computed for each one.";
    private final static String[] EXAMPLE = {"$sin, -0.0001_0.0001","$sin, 0+-0.0001, (PI/2)%0.1","defineLambdaFunction('2*$x+4*$y'),1%0.1,1, 1,1%0.1","defineLambdaFunction('2*$x*$y'),2%0.1,2%0.1, 2%0.1,2, 2,2%0.1 ","defineLambdaFunction('$x^2'),{1_1.1, 1.1_1.2} ","defineLambdaFunction('$x^3'),{1_1.1, 1.1_1.2} "};
    private final static String[] RESULT = {"1","1,0","2,4","2,4,4","2","6*1.1"};
    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(-1), new DomainInterval());

    private FunctionPrefix f;
    
    public CalDerivate() {
        super("calculus", "Derivate", (byte) -2, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }    

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        f = FunctionPrefix.getFunction(input.getFirst().getName());
        final int a = f.getNumbArgs();
        final int s = input.size();
        if ((s-1)%a!=0) throwNewCalculusException ("The number of input derivation points "+(s-1)+" does not match with function "+f.getSymbol()+" argument number: "+a);
        ListIntervals result = new ListIntervals();
        for (int i=1;i<s;i+=a) {
            ListIntervals subInput = input.subList(i, i+a);
            result.addAll(highOrderDerivate(subInput));
        }
        return result;        
    } 
    
    private ListIntervals highOrderDerivate (ListIntervals input) throws Exception {
        final int si = input.size();
        for (int i=0;i<si;++i) {         
            if (input.get(i).isIntervalList()) {//higher order derivate                
                ListIntervals li = input.get(i).getListIntervals();
                final int sj = li.size();                
                PointDerivate[] lod = new PointDerivate[sj];
                for (int j=0;j<sj;++j) {//compute the points of the first order derivate
                    ListIntervals nInput = new ListIntervals(input);
                    Interval in = li.get(j);
                    nInput.set(i, in);
                    lod[j]=new PointDerivate(highOrderDerivate(nInput), in.getValue());
                }
                for (int j=sj-1;j>0;--j) {//compute step by step all the higher order derivate
                    for (int k=0;k<j;++k) {
                        final int kp1 = k+1;
                        lod[k] = new PointDerivate(incrementalRatio(lod[k].value, lod[kp1].value, lod[k].point-lod[kp1].point), (lod[k].point+lod[kp1].point)/2);
                    }
                }
                return lod[0].value;
            }
        }
        return partialDerivate(input);
    }
    
    public ListIntervals partialDerivate (FunctionPrefix f, ListIntervals input) throws Exception{        
        this.f = f;
        return partialDerivate(input);
    }    
    
    private ListIntervals partialDerivate (ListIntervals input) throws Exception{        
        final int s = input.size();
        
        ListIntervals[] xlr;
        
        xlr = getFirstRightLeftRange (input);
        
        if (xlr == null) return f.compute(input);
        
        return incrementalRatio(partialDerivate(xlr[0]), partialDerivate(xlr[1]), xlr[2]);
    }    
    
    private ListIntervals incrementalRatio (ListIntervals fx0, ListIntervals fx1, double x0mx1) throws Exception {
        return incrementalRatio (fx0, fx1, new ListIntervals (new IntervalPoint(x0mx1)));
    }
    
    private ListIntervals incrementalRatio (ListIntervals fx0, ListIntervals fx1, ListIntervals x0mx1) throws Exception {
        return DIV.compute( SUB.compute(fx0, fx1), x0mx1);
    }

    private ListIntervals[] getFirstRightLeftRange(ListIntervals input) {
        final int s = input.size();
        for (int i=0;i<s;++i) {            
            if (! input.get(i).isIntervalPoint()) {//first partial derivate
                Interval in = input.get(i);

                ListIntervals xl, xr, range;
                xl = new ListIntervals(input);
                xr = new ListIntervals(input);
                
                xl.set(i, new IntervalPoint (in.getLeft()));
                xr.set(i, new IntervalPoint (in.getRight()));
                range = new ListIntervals(new IntervalPoint (in.getRange()));
                ListIntervals[] result = {xr, xl, range};
                return result;
            }
        }
        return null;
    }

    class PointDerivate {
        ListIntervals value;
        double point;

        public PointDerivate(ListIntervals value, double point) {
            this.value = value;
            this.point = point;
        }                
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }

    

}
