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

package engcalculator.function.embedded.userDefined;


import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.userDefined.interpolator.Interpolator;
import engcalculator.function.embedded.userDefined.interpolator.InterpolatorCubicSplineConstrained;
import engcalculator.function.embedded.userDefined.interpolator.InterpolatorLinear;
import engcalculator.function.embedded.userDefined.interpolator.InterpolatorStep;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalReal;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class UDeFunctionPrefixByPoints extends FunctionPrefix {

    public static void checkXFxProperty(ListIntervals right, boolean positive, boolean point) throws CalculusException{        
        if (right.columnSize() != 2) throwNewCalculusException ("It was expected a 2-column matrix to be interpolated while was found "+right.columnSize());
        if (right.rowSize() < 2) throwNewCalculusException ("It was expected at least 2-rows matrix to be interpolated while was found "+right.rowSize());

        int j=2;
        for (int i = 0; j < right.size(); j = i+2 ) {
            if (! right.get(i).isProper()) throwNewCalculusException ("The x coordinates should be proper number while at "+i+"was found "+right.get(i));
            if (right.get(i).getRight() > right.get(j).getLeft()) throwNewCalculusException ("The x coordinates are not monotonic or the x intervals have some intersection at "+i+"was found "+right.get(i));
            i = j;
        }
        
        if (positive || point) {
            for (int i=1; i<right.size(); i += 2) {
                if (positive && right.get(i).isNegative()) throwNewCalculusException ("Some y coordinates is negative at "+i+"was found "+right.get(i));
                if (point && ! right.get(i).isIntervalPoint()) throwNewCalculusException ("Some y coordinates is not point interval at "+i+"was found"+right.get(i));
            }
        }
    }
    
    private final static Parameter<Integer> INTERPOLATORTYPE;
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalReal());

    private final Interpolator interpolator;
        
    public static void initialize () {} //used to trigger static initializator
    
    static {
        INTERPOLATORTYPE = new Parameter<Integer>("define","FunctionByPoints", "interpolationType", "The type of interpolation algorithm to be used to fit region between intervals. By default if the x interval is matched the y stored is used. If the x input falls between 2 inputs: 0 is the default and uses the average (can fit points and intervals), 1 uses a linear regression, 2 use a cubic constrained spline (Constrained Cubic Spline Interpolation for Chemical Engineering Applications by CJC Kruger: http://www.korf.co.uk/spline.pdf and Spline interpolation From Wikipedia, the free encyclopedia http://en.wikipedia.org/wiki/Spline_interpolation#Algorithm_to_find_the_interpolating_cubic_spline", 0, new ConvertIntervalToInteger(0,2));        
        ParameterManager.addParameter(INTERPOLATORTYPE);
    }
    
    public UDeFunctionPrefixByPoints(String name, String help,  ListIntervals x_fx) {
        super (UDeCurrentGroup.getGroup(name), UDeCurrentGroup.getName(name), (byte) 1, DOMAIN, true, false, help, null, null);

        switch (INTERPOLATORTYPE.getVal()) {
            case 0:
                interpolator = new InterpolatorStep (x_fx);        
                break;
            case 1:
                interpolator = new InterpolatorLinear(x_fx);        
                break;
            case 2:
                interpolator = new InterpolatorCubicSplineConstrained(x_fx);        
                break;
            default:
                throw new RuntimeException ("Unexpected interpolator type in UDeFunctionPrefixByPoints");
        }
    }

    public double getXmin() {
        return interpolator.getXmin();
    }

    public double getXrange() {
        return interpolator.getXrange();
    }

    public Interval getFx(int i) {
        return interpolator.getFx(i);
    }

    public int getSize() {
        return interpolator.getSize();
    }
    
    public Interval getFx(double x) throws Exception {
        return interpolator.getFx(x);
    }    

    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        final Interval element = input.getFirst();        

        Interval left = interpolator.getFx(element.getLeft());
        
        if (element.isIntervalPoint()) return new ListIntervals(left);             
        
        return new ListIntervals(new IntervalReal(left.getValue(), interpolator.getFx(element.getRight()).getValue()));
    }

    @Override
    public String getHelp() {
        return new StringBuilder (super.getHelp()).append("\nUser defined prefix function by interpolation points: ").toString();
    }
 
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return null;
    }    
}

