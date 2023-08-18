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

import engcalculator.domain.DomainFunctionInfix;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainInteger;
import engcalculator.domain.DomainIntervalPoint;
import engcalculator.domain.DomainIntervalPositive;
import engcalculator.domain.DomainIntervalProper;
import engcalculator.domain.DomainLogicAnd;
import engcalculator.domain.DomainLogicOr;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.list.LisLinear;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public abstract class CalFuncFind extends FunctionPrefix {

    private final static DomainList DOMAIN = new DomainList (new DomainFunctionPrefix(-1), new DomainLogicOr(new DomainFunctionPrefix(-1) ,new DomainFunctionInfix(-1, -1)), new DomainLogicAnd(new DomainInteger(), new DomainIntervalPoint(), new DomainIntervalPositive()), new DomainIntervalProper());
    private final static LisLinear LIS_LINEAR = new LisLinear();
    
    public CalFuncFind(String name, String help, String[] example, String[] result) {
        super("calculus", "Find"+name, (byte) -4, DOMAIN, false, true, help, example, result);
    }    

    private FunctionPrefix funcToAnalyze;
    private int funcDimensions;
    
    private FunctionInfix funcInfixToTest;
    private FunctionPrefix funcPrefixToTest;
    
    private int[] xListIndex;
    private ListIntervals[] xList;
    
    private int numberOfPoints;
    private ListIntervals x, y;       
    
    protected void scanInput (ListIntervals input) throws Exception {
        funcToAnalyze = FunctionPrefix.getFunction(input.getFirst().getName());
        funcDimensions = funcToAnalyze.getNumbArgs();
        
        String nm = input.get(1).getName();
        funcInfixToTest = FunctionInfix.getFunction(nm);        
        funcPrefixToTest = FunctionPrefix.getFunction(nm);        
        
        final int s = input.size();
        
        if (s-3!=funcDimensions) throwNewCalculusException ("The number of input intervals "+(s-3)+" should be the double of function "+funcToAnalyze.getSymbol()+" arguments number: "+funcDimensions);
        xList = new ListIntervals[funcDimensions];

        numberOfPoints = (int) input.get(2).getValue();
        
        ListIntervals nopl = input.subList(2,3);
        for (int i=3, j=0;i<s;i+=1) {
            xList[j++] = LIS_LINEAR._compute(nopl.append(input.get(i)));
        }
        
        resetScanIndex();
        
        computeX();     
        computeY();
    }         
    
    private void resetScanIndex () {
        xListIndex = new int[funcDimensions];
    }
    
    protected boolean isPrefixFuncToTestDefined () throws Exception{
        return (funcPrefixToTest != null);
        
    }

    protected boolean compare (ListIntervals yi) throws Exception{
        if (funcInfixToTest != null) {
            for (Interval i : funcInfixToTest.compute(y, yi))
                if (LgcBooleanInterval.i2ib(i) != LgcBooleanInterval.TRUE) return false;
        } else {
            ListIntervals yt = new ListIntervals(y);
            yt.addAll(yi);
            for (Interval i : funcPrefixToTest.compute(yt))
                if (LgcBooleanInterval.i2ib(i) != LgcBooleanInterval.TRUE) return false;            
        }
        return true;
    }
    
    protected boolean compare (ListIntervals[] ys) throws Exception{
        if (ys == null) return false;

        for (ListIntervals yi : ys)
            if (! compare(yi)) return false;
        
        return true;
    }    
    
    protected ListIntervals[] getY(ListIntervals[] xs) throws Exception{
        if (xs == null) return null;
        final int s = xs.length;
        ListIntervals[] ys = new ListIntervals[s];
        for (int i=0;i<s;++i)
            ys[i] = funcToAnalyze.compute(xs[i]);
        return ys;
    }    
    
    protected ListIntervals getY() {
        return y;
    }    
    
    protected ListIntervals getX() {
        return x;
    }
    
    protected ListIntervals[] getCloserXs() {
        final int s = funcDimensions*2;
        ListIntervals[] result = new ListIntervals[s];
        int j=0;
        final int nopm1 = numberOfPoints - 1;
        
        for (int i=0;i<funcDimensions;++i) {
            if (xListIndex[i]>0) result[j++] = computeX(i, -1);
            else return null;
            
            if (xListIndex[i]<nopm1) result[j++] = computeX(i, +1);
            else return null;
        }

        return result;
    }    

    protected boolean nextX() throws Exception {   
        
        if (! nextIndex()) return false;
        
        computeX();
        
        computeY();
        
        return true;
    }
    
    private boolean nextIndex () {
        final int s = funcDimensions-1;
        xListIndex[0]++;
        for (int i=0;i<s;++i)
            if (xListIndex[i]>=numberOfPoints) {
                xListIndex[i]=0;
                ++xListIndex[i+1];
            } else break;

        return xListIndex[s] < numberOfPoints;
    }
    
    private void computeX () {
        x = new ListIntervals();
        for (int i=0;i<funcDimensions;++i)
            x.add(xList[i].get(xListIndex[i]));    
    }
    
    private ListIntervals computeX (int iChange, int delta) {
        ListIntervals xi = new ListIntervals();
        for (int i=0;i<funcDimensions;++i)
            xi.add(xList[i].get(i != iChange ? xListIndex[i] : xListIndex[i] +delta));
        
        return xi;        
    }    
    
    private void computeY() throws Exception {
        y = funcToAnalyze.compute(x);        
    }    
    
    public List<MeasurementUnit> _computeMeasurementUnit (ListIntervals input) throws Exception {
        return null;
    }    
}
