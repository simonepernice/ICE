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

package engcalculator.interval;

import engcalculator.function.Function;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class ListIntervals extends LinkedList<Interval> {
    final static Parameter<Integer> MAXNUMBEROFINTERVALTOPRINT;
    
    static {
        MAXNUMBEROFINTERVALTOPRINT = new Parameter<Integer> ("interval", "ALL", "maxNumberIntervalToPrint", "Set the maximum length of list to print. The value 0 stands for print all data.", 100, new ConvertIntervalToInteger(0, 10000));
        ParameterManager.addParameter(MAXNUMBEROFINTERVALTOPRINT);
    }        
    
    public static Parameter<Integer> getMaxNumberOfIntervalToPrint () {
        return MAXNUMBEROFINTERVALTOPRINT;
    }
    
    public ListIntervals () {
       super ();
    }

    public final MeasurementUnit getMeasurementUnit() throws MeasurementUnitException {
        if (size() == 0) return MeasurementUnit.PURE;
        Iterator<Interval> it = iterator();
        MeasurementUnit res = it.next().getMeasurementUnit();
        while (it.hasNext())
            if (! res.equals(it.next().getMeasurementUnit())) Function.throwNewMeasurementUnitException("The values inside the list do not have same measurement unit");

        return res;
    }      
    
    public final List<MeasurementUnit> getMeasurementUnitList()  {
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit>();
        for (Interval i : this)
            res.add(i.getMeasurementUnit());
        return res;
    }     

    public ListIntervals (Interval i) {
       super ();
       add (i);
    }

    public ListIntervals (ListIntervals i) {
       super (i);
    }

    public String getName () {
       return null;
    }

    @Override
    public String toString () {
        return toStringBuilder().toString();
    }
    
    public StringBuilder toStringBuilder () {
        StringBuilder result = new StringBuilder ();
        if (IntervalPoint.nicePrint.getVal()) {
            switch (size()) {
                case 0:
                    break;
                case 1:
                    if (getFirst() != null) result.append(getFirst().toString());
                    break;
                default:        
                    Iterator<Interval> it = iterator();
                    result.append(it.next().toString());

                    final int maxIntToPrint = MAXNUMBEROFINTERVALTOPRINT.getVal();
                    int i=0;
                    while ((maxIntToPrint == 0 || i++<maxIntToPrint) && it.hasNext()) {                
                        result.append(",  ").append(it.next().toString());

                    }

                    if (maxIntToPrint > 0 && i >= maxIntToPrint) result.append(", ...");                
            }        
        } else {
            result.append('(');
            Iterator<Interval> it = iterator();
            if (it.hasNext())
                while (true) {
                    result.append(it.next());
                    if (it.hasNext()) result.append(", ");
                    else break;
                }
            result.append(')');
        }
        
        return result;
    }

    public final ListIntervals getDetailedType () {
        ListIntervals result = new ListIntervals ();
        for (Interval i : this)
            result.append(new IntervalLiteral(i.getDetailedType()));
        return result;
    }

    @Override
    public ListIntervals subList(int fromIndex, int toIndex) {
        ListIntervals il = new ListIntervals ();
        il.addAll(super.subList(fromIndex, toIndex));
        return il;
    }

    public ListIntervals append (Interval a) {
        add (a);
        return this;
    }

    public int columnSize() {
        return size();
    }

    public int rowSize () {
        return 1;

    }

    public boolean isProperMatrix () {
        return false;
    }

    public boolean isMatrix() {
        return false;
    }

    public Interval get (int row, int column) {
        if (row != 0) throw new RuntimeException("Index out of bound");
        return get (column);
    }

    public void set(int row, int column, Interval val) {
        if (row != 0) throw new RuntimeException("Index out of bound");
        set (column, val);
    }
    
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if( ! (obj instanceof ListIntervals)) throw new RuntimeException("Equals was called with an object which is not a list of intervals");        
        ListIntervals b = (ListIntervals) obj;
        final int s = size();
        if (s != b.size()) return false;        
        Iterator<Interval> ithis = iterator();
        Iterator<Interval>  ib = b.iterator();
        while (ithis.hasNext())        
            if (! ithis.next().equals(ib.next())) return false;
        return true;
    }
    
    public boolean isMadeByRealIntervals () {
        for (Interval i : this) {
            if (! i.isComplexPointInterval()) return true;
        }
        return false;
    }    
    
    public boolean isMadeByIntervalList () {
        for (Interval i : this) {
            if (! i.isIntervalList()) return false;
        }
        return true;
    }     
    
    public double distance (ListIntervals listB) throws Exception {
        double error;
        if (size() != listB.size()) throw new Exception("Do not match in size");
        error = 0;
        Iterator<Interval> ia = iterator(), ib = listB.iterator();
        while (ia.hasNext()) {
            Interval a = ia.next(), b = ib.next();
            if (a.getName() != null && b.getName() != null) {
                if (! a.getName().equals(b.getName())) {
                    throw new Exception("Do not match in name");
                }
            } else if (a.isIntervalList()) {
                if (b.isIntervalList()) {
                    error += a.getListIntervals().distance(b.getListIntervals());
                } else {
                    throw new Exception ("Only one of the interval is an IntervalList");
                }
            } else if (b.isIntervalList()) {
                throw new Exception ("Only one of the interval is an IntervalList");
            } else {
                error += a.distance(b);
//                Math.abs(a.getRight()-b.getRight())+Math.abs(a.getLeft()-b.getLeft());
            }
        }
        if (! getMeasurementUnit().equals(listB.getMeasurementUnit())) throw new Exception ("The measurement units do not matches");
        return error;
    }
    
    public double compare (ListIntervals listB) throws Exception {
        double error;
        if (size() != listB.size()) throw new Exception("Do not match in size");
        error = 0;
        Iterator<Interval> ia = iterator(), ib = listB.iterator();
        while (ia.hasNext()) {
            Interval a = ia.next(), b = ib.next();
            if (a.getName() != null && b.getName() != null) {
                if (! a.getName().equals(b.getName())) {
                    throw new Exception("Do not match in name");
                }
            } else if (a.isIntervalList()) {
                if (b.isIntervalList()) {
                    error += a.getListIntervals().distance(b.getListIntervals());
                } else {
                    throw new Exception ("Only one of the interval is an IntervalList");
                }
            } else if (b.isIntervalList()) {
                throw new Exception ("Only one of the interval is an IntervalList");
            } else {
                error += a.compare(b);
//                Math.abs(a.getRight()-b.getRight())+Math.abs(a.getLeft()-b.getLeft());
            }
        }
        if (! getMeasurementUnit().equals(listB.getMeasurementUnit())) throw new Exception ("The measurement units do not matches");
        return error;
    }    
}
