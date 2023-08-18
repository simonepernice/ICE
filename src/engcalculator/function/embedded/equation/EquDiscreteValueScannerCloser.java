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
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalList;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EquDiscreteValueScannerCloser extends FunctionPrefix implements FunctionScanner {

    private final static String HELP = "... ($prefixFunc, val1, {val21, .., val2n}, .., valn-1, {valn1, .., valnm}, target) returns the {list of values} to get the function values closer less to the target, the {list of values} equals and the {list of values} closer and higher of the target.\ndIf any parameter is given as itnerval it is used as is. The target may be an intervalList if the function returns a list. ... scans all the possible value therefore it can be very slow.";
    private final static String[] EXAMPLE = {";equationDiscreteValueScannerCloser(defineLambdaFunction('$a+$b'),{1 .. 6},{1 .. 6},11.5)"};
    private final static String[] RESULT = {"( {6,  5, 5, 6}, {}, { 6,  6 })"};
    private final static DomainList DOMAIN = new DomainList(new DomainFunctionPrefix(-1), new DomainLogicOr(new DomainIntervalReal(), new DomainIntervalList()), new DomainLogicOr(new DomainIntervalReal(), new DomainIntervalList()));

    private FunctionPrefix f;
    private ListIntervals yTarget;
    private ListIntervals xFloor, xCeil, xEqual;
    private double cmpFloor, cmpCeil;
    private boolean firstFloor, firstCeil, firstEqual;
    private ListIntervals result;

    public EquDiscreteValueScannerCloser() {
        super("equation", "DiscreteValueScannerCloser", (byte) -3, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        f = FunctionPrefix.getFunction(input.getFirst().toString());

        if (f.getNumbArgs() != input.size() - 2) {
            throwNewCalculusException("The input function " + f.getNumbArgs() + "requires " + f.getNumbArgs() + " arguments but were provided only " + (input.size() - 1));
        }

        {
            Interval in = input.getLast();
            yTarget = in.isIntervalList() ? in.getListIntervals() : new ListIntervals(in);
        }

        IperPlaneScanner ips = new IperPlaneScanner(input.subList(1, input.size() - 1));

        ips.scanPlane(this);

        return result;
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals input) throws Exception {
        return input.getMeasurementUnitList();
    }

    public void scanBegin() {
        xFloor = null;
        xCeil = null;
        xEqual = null;        
        cmpFloor = 0; 
        cmpCeil = 0;        
        firstFloor = true;
        firstCeil = true;
        firstEqual = true;
    }

    public void scanEnd() {
        result = new ListIntervals();
        if (!firstFloor) {
            result.add(new IntervalList(xFloor));
        } else {
            result.add(new IntervalList(new ListIntervals()));
        }
        if (!firstEqual) {
            result.add(new IntervalList(xEqual));
        } else {
            result.add(new IntervalList(new ListIntervals()));
        }
        if (!firstCeil) {
            result.add(new IntervalList(xCeil));
        } else {
            result.add(new IntervalList(new ListIntervals()));
        }
    }

    public void scanAt(ListIntervals x) throws Exception {
        double c = f.compute(x).compare(yTarget);

        if (c < 0) {
            if (firstFloor) {
                cmpFloor = c;
                xFloor = new ListIntervals(x);
                firstFloor = false;
            } else if (c > cmpFloor) {
                cmpFloor = c;
                xFloor = new ListIntervals(x);
            } else if (c == cmpFloor) {
                xFloor.addAll(x);
            }
        } else if (c == 0) {
            if (firstEqual) {
                xEqual = new ListIntervals(x);
                firstEqual = false;
            } else {
                xEqual.addAll(x);
            }
        } else { //if (c > 0) {
            if (firstCeil) {
                cmpCeil = c;
                xCeil = new ListIntervals(x);
                firstCeil = false;
            } else if (c < cmpCeil) {
                cmpCeil = c;
                xCeil = new ListIntervals(x);
            } else if (c == cmpCeil) {
                xCeil.addAll(x);
            }
        }
    }

}
