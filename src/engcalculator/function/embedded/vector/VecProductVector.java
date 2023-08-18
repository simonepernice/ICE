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

package engcalculator.function.embedded.vector;

import engcalculator.function.embedded.arithmetic.AriMul;
import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainIntervalComplex;
import engcalculator.domain.DomainList;
import engcalculator.function.MeasurementUnitException;
import engcalculator.function.embedded.arithmetic.AriSub;
import engcalculator.function.embedded.arithmetic.AriSum;
import engcalculator.interval.Interval;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import engcalculator.interval.measurementUnit.MeasurementUnitAccumulator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class VecProductVector extends VecInfixFunction {
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalComplex());
    private final static String HELP = "(vectorA) ... (vectorB) compute the vectorial product of the vectors A and B represented by their lists.\nThe product is defined only for 3 dimensions vectors.";    
    private final static String[] EXAMPLE_LEFT = {"v1 = (1,2,3), v2 = (4,5,6); v1 *: v2 == -(v2 *: v1)","a = statisticRandom (listClone(3, 0_10)); b = statisticRandom (listClone(3, 0_10)); c = statisticRandom (listClone(3, 0_10)); abs( a *. (b *: c) - b *. (c *: a)) <0.001, abs(b *. (c *: a) - c *. (a *: b)) < 0.001"};
    private final static String[] EXAMPLE_RIGHT = {"", ""};
    private final static String[] RESULT = {"true,true,true", "true, true"};
    private final static AriMul MUL = new AriMul();
    private final static AriSum SUM = new AriSum();
    private final static AriSub SUB = new AriSub();

    public VecProductVector() {
        super ("*:", (byte) -1, (byte)  -1, DOMAIN, DOMAIN, false, HELP, EXAMPLE_LEFT, EXAMPLE_RIGHT, RESULT);
    }

    @Override
    public ListIntervals __compute(ListIntervals leftSide, ListIntervals rightSide) throws Exception {                
                if (leftSide.size() != 3 || rightSide.size() != 3) throw new Exception ("Vectorial product is defined for 3 dimensions vectors");
        return new ListIntervals(subMatDet(leftSide.get(1),leftSide.get(2),rightSide.get(1),rightSide.get(2))).append(subMatDet(leftSide.get(2),leftSide.get(0),rightSide.get(2),rightSide.get(0))).append(subMatDet(leftSide.get(0),leftSide.get(1),rightSide.get(0),rightSide.get(1)));
    }
    
    private Interval subMatDet (Interval a00, Interval a01, Interval a10, Interval a11) throws Exception {
        return SUB._computeIntervals(MUL._computeIntervals(a00, a11), MUL._computeIntervals(a01, a10));
    }

    @Override
    public List<MeasurementUnit> _computeMeasurementUnit(ListIntervals leftSide, ListIntervals rightSide) throws MeasurementUnitException {
        MeasurementUnitAccumulator mua = new MeasurementUnitAccumulator(leftSide.getMeasurementUnit());
        mua.add(rightSide.getMeasurementUnit());
        List<MeasurementUnit> res = new LinkedList<MeasurementUnit> ();
        res.add(mua.toMeasurementUnit());
        return res;          
    }
}
