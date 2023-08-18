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
package engcalculator.function.embedded.equation;

import engcalculator.domain.DomainFunctionPrefix;
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainIntervalReal;
import engcalculator.domain.DomainLogicOr;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.interval.IntervalList;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EquDiscreteValueScannerLowest extends FunctionPrefix implements FunctionScanner {

    private final static String HELP = "... ($prefixFunc, val1, {val21, .., val2n}, .., valn-1, {valn1, .., valnm}) returns the {list of values} to get the lowest values of the function.\ndIf any parameter is given as itnerval it is used as is. The target may be an intervalList if the function returns a list. ... scans all the possible value therefore it can be very slow. To find the highest just run with -f.";
    private final static String[] EXAMPLE = {";equationDiscreteValueScannerLowest(defineLambdaFunction('(3-$x)^2+(4-$y)^2'),{0 .. 10},{0 .. 10})",";equationDiscreteValueScannerLowest(defineLambdaFunction('((3-$x)*(5-$x))^2+(4-$y)^2'),{0 .. 10},{0 .. 10})"};
    private final static String[] RESULT = {"( {3,  4})","{3,4,5,4}"};
    private final static DomainList DOMAIN = new DomainList(new DomainFunctionPrefix(-1), new DomainLogicOr(new DomainIntervalReal(), new DomainIntervalList()), new DomainLogicOr(new DomainIntervalReal(), new DomainIntervalList()));

    private FunctionPrefix f;
    private ListIntervals xMin, yMin;
    private boolean firstMin;
    private ListIntervals result;

    public EquDiscreteValueScannerLowest() {
        super("equation", "DiscreteValueScannerLowest", (byte) -3, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        f = FunctionPrefix.getFunction(input.getFirst().toString());

        if (f.getNumbArgs() != input.size() - 1) {
            throwNewCalculusException("The input function " + f.getNumbArgs() + "requires " + f.getNumbArgs() + " arguments but were provided only " + (input.size() - 1));
        }

        IperPlaneScanner ips = new IperPlaneScanner(input.subList(1, input.size()));

        ips.scanPlane(this);

        return result;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return input.getMeasurementUnitList();
    }

    public void scanBegin() {
        firstMin = true;
        xMin = null;
        yMin = null;    
    }

    public void scanEnd() {
        result = new ListIntervals();
        if (!firstMin) {
            result.add(new IntervalList(xMin));
        } else {
            result.add(new IntervalList(new ListIntervals()));
        }
    }

    public void scanAt(ListIntervals x) throws Exception {
        if (firstMin) {
            xMin = new ListIntervals(x);
            yMin = f.compute(x);
            firstMin = false;
        } else {
            double c = f.compute(x).compare(yMin);

            if (c < 0) {
                xMin = new ListIntervals(x);
                yMin = f.compute(x);
            } else if (c == 0) {
                xMin.addAll(x);
            }
        }
    }

}
