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

import engcalculator.interval.ListIntervals;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainListMatrix;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.list.LisGetElements;
import engcalculator.function.embedded.matrix.MatInvert;
import engcalculator.function.embedded.matrix.MatMul;
import engcalculator.function.embedded.matrix.MatTranspose;
import engcalculator.interval.IntervalPoint;
import engcalculator.interval.IntervalReal;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaGeneralRegression extends FunctionPrefix {
    private final static MatMul MATRIX_MUL = new MatMul();
    private final static MatTranspose MATRIX_TRANSPOSE = new MatTranspose();
    private final static MatInvert MATRIX_INVERT = new MatInvert();
    private final static LisGetElements GET_SUB_MATRIX = new LisGetElements();
    private final static DomainList DOMAIN = new DomainListMatrix ();

    private final static String HELP = "... ([columnx0, columnx1, .., columnxn, columny]) returns the coefficient of a generic linear regression (a0, a1, .., an) to get the best minimum square approximation of  (a0 * columnx0 + a1 * columnx1 + .. + an * comumnxn ) as close as possible to columny.\nThe initial columns are xi and the last one is y. y = b0 x0  + b1 x1 + .. For example a standard simple linear regression would be y =  b0 * 1 + b1 * xi: therefore the matrix rows will be (1 xi yi). yi is found with the least square algorithm. For a quadratic regression y = b0 + b1 xi + b2 xi^2 the rows will be (1 xi xi^2 y). Those rows can be built with an ad hoc function ($f, $t)='1, t, t^2', then the matrix will be f(x)#3,y. Using this tool it is possible to apply on several regressions like power, multi variable, logarithm.";
    private final static String[] EXAMPLE = {"xy = (1,3,4,9,5,11)#2;$xr = (xy :: (0_-1, 0));$yr = (xy :: (0_-1, 1)); ($reg, $x)='1, x'; statisticRegressionGeneral ((reg(xr))#2,yr) "};
    private final static String[] RESULT = {"statisticRegressionLinear (xy)"};

    public StaGeneralRegression() {
        super("statistic", "RegressionGeneral", (byte) -1, DOMAIN, false, true, HELP, EXAMPLE, RESULT);
    }

    @Override
    public ListIntervals _compute(ListIntervals input) throws Exception {
        if (input.columnSize() < 2) throwNewCalculusException ("At least two columns are required while were found "+input.columnSize());
        ListIntervals x = GET_SUB_MATRIX._compute(input, new ListIntervals (new IntervalReal(0, -1)).append(new IntervalReal(0, -2)));
        ListIntervals y = GET_SUB_MATRIX._compute(input, new ListIntervals (new IntervalReal(0, -1)).append(new IntervalPoint(-1)));
        return new ListIntervals(MATRIX_MUL._compute(MATRIX_MUL._compute(MATRIX_INVERT._compute(MATRIX_MUL._compute(MATRIX_TRANSPOSE._compute(x),x)), MATRIX_TRANSPOSE._compute(x)), y));
    }
}
