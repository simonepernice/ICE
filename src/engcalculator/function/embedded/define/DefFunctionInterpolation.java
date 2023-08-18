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

package engcalculator.function.embedded.define;

import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;
import engcalculator.domain.DomainListLiteral;
import engcalculator.domain.DomainListMatrix;
import engcalculator.function.CalculusException;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.userDefined.UDeFunctionPrefixByPoints;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefFunctionInterpolation extends FunctionInfix {
    private final static DomainListLiteral DOMAINLEFT = new DomainListLiteral( new DomainIntervalLiteral());
    private final static DomainListMatrix DOMAINRIGHT = new DomainListMatrix ();

    private final static String HELP = "$function ... [[xMatrixColumn],[yMatrixColumn]] defines the function as interpolation of the give matrix of points.\nThe function is an interpolation of a 2 columns graphics, like the one can be used to plot draws on the screen. Each row of the matrix is composed by the following couple of intervals xi f(xi). Every row needs to have xi+1 > xi, the only interception accepted is right(xi) == left(xi+1). The output f(x) will be equal to f(xi) while x is a subset of xi. If x is between xi and xi+1 the output depends on the interpolator selected on defineFunctionByPoints, interpolationType. 0 will use the average between f(xi) and f(xi+1), 1 a linear conjunction and 2 a cubic constrained spline. If xi and xi+1 has the same right and left extreme (right(xi)==left(xi+1)=ex) then f(ex) may return f(xi) or f(xi+1), the result is not predictable. The operation returns the function names in order to be immediately used in the current operation.";
    private final static String[] EXAMPLESLEFT = {"$intfunc:=[[1,2],[3,4],[5,6]];intfunc(-1.5), intfunc(1.5),intfunc(3),intfunc(10), intfunc(1_3), intfunc(1.5_3.5),intfunc(3_1), intfunc(3.5_1.5)","$intfunc:=[[1_5,2],[5_6,4],[6_10,6]];intfunc(-1.5), intfunc(3),intfunc(4.9),intfunc(10),intfunc(4_5.9), intfunc(5.9_4)"};
    private final static String[] EXAMPLESRIGHT = {"",""};
    private final static String[] RESULTS = {"2,3,4,6,2_4, 3_5,4_2,5_3","2,2,2,6,2_4,4_2"};

    static {
        UDeFunctionPrefixByPoints.initialize();
    }
    
    public DefFunctionInterpolation() {
        super ("define", ":=", (byte) 1, (byte) -4, DOMAINLEFT,  DOMAINRIGHT, false, true, HELP, EXAMPLESLEFT, EXAMPLESRIGHT, RESULTS);
    }

    @Override
    public final ListIntervals _compute(ListIntervals left, ListIntervals right) throws Exception {
        String fname = left.getFirst().getName();
        
        UDeFunctionPrefixByPoints.checkXFxProperty (right, false, false);

        StringBuilder help = new StringBuilder();
        help.append(fname).append(" :=\n").append(right.toString()).append('\n');

        UDeFunctionPrefixByPoints f = new UDeFunctionPrefixByPoints (fname, help.toString(), right);
        if (! FunctionPrefix.addFunction(f)) throwNewCalculusException ("There is a function already defined with the same name "+fname+" which is locked.");

        return new ListIntervals (new IntervalLiteral (f.getSymbol()));

    }
    
    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(MeasurementUnit.PURE);
        return res;
    }        
    
}
