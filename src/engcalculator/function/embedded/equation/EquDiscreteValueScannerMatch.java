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
import engcalculator.function.CalculusException;
import engcalculator.function.embedded.logic.LgcBooleanInterval;
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
public final class EquDiscreteValueScannerMatch extends FunctionPrefix implements FunctionScanner {

    private final static String HELP = "... ($prefixBooleanFunc, val1, {val21, .., val2n}, .., valn-1, {valn1, .., valnm}) returns the {list of values} to get the values where all the element of the list returned by the function are true.\ntrue is 1 and can be computed with logical operator. If any parameter is given as itnerval it is used as is. The target may be an intervalList if the function returns a list. ... scans all the possible value therefore it can be very slow.";
    private final static String[] EXAMPLE = {";equationDiscreteValueScannerMatch(defineLambdaFunction('$x+$y == 5 , $x > $y'), {0 .. 10}, {0 .. 10})",";equationDiscreteValueScannerMatch(defineLambdaFunction('$x+$y == 5 , $x < $y'), {0 .. 10}, {0 .. 10})",";equationDiscreteValueScannerMatch(defineLambdaFunction('sqrt($x^2+$y^2) << 4.99_5.01 , $x > $y '), {listLinear(20, 0_10)}, {listLinear(20, 0_10)})"};
    private final static String[] RESULT = {"{5,  0,  4,  1,  3,  2} ","{2,  3,  1,  4,  0,  5}","{4.737,  1.579} "};
    private final static DomainList DOMAIN = new DomainList(new DomainFunctionPrefix(-1), new DomainLogicOr(new DomainIntervalReal(), new DomainIntervalList()), new DomainLogicOr(new DomainIntervalReal(), new DomainIntervalList()));

    private FunctionPrefix f;
    private ListIntervals xMatch;
    private ListIntervals result;

    public EquDiscreteValueScannerMatch() {
        super("equation", "DiscreteValueScannerMatch", (byte) -3, DOMAIN, true, true, HELP, EXAMPLE, RESULT);
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
        xMatch = null;
    }

    public void scanEnd() {
        result = new ListIntervals();
        if (xMatch != null) {
            result.add(new IntervalList(xMatch));
        } else {
            result.add(new IntervalList(new ListIntervals()));
        }
    }

    public void scanAt(ListIntervals x) throws Exception {        
        if (isTrue (f.compute(x)) ) {
            if (xMatch == null) xMatch = new ListIntervals(x);
            else xMatch.addAll(x);
        } 
    }

    private boolean isTrue(ListIntervals x) throws CalculusException {
        for (Interval i : x) 
            if (LgcBooleanInterval.TRUE != LgcBooleanInterval.i2ib(i)) return false;
        return true;
    }
    

}
