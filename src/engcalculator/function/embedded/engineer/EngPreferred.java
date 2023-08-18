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
package engcalculator.function.embedded.engineer;

import engcalculator.function.embedded.interval.ItvByRelativeToleranceBuilder;
import engcalculator.interval.Interval;
import engcalculator.interval.IntervalPoint;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class EngPreferred {

    private final static int[] PREFERRED_TOLERANCE = {10, 25, 50, 100, 200, 500, 1000, 2000};
    private final static int[][] PREFERRED_VALUES = {
        {100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 117, 118, 120, 121, 123, 124, 126, 127, 129, 130, 132, 133, 135, 137, 138, 140, 142, 143, 145, 147, 149, 150, 152, 154, 156, 158, 160, 162, 164, 165, 167, 169, 172, 174, 176, 178, 180, 182, 184, 187, 189, 191, 193, 196, 198, 200, 203, 205, 208, 210, 213, 215, 218, 221, 223, 226, 229, 232, 234, 237, 240, 243, 246, 249, 252, 255, 258, 261, 264, 267, 271, 274, 277, 280, 284, 287, 291, 294, 298, 301, 305, 309, 312, 316, 320, 324, 328, 332, 336, 340, 344, 348, 352, 357, 361, 365, 370, 374, 379, 383, 388, 392, 397, 402, 407, 412, 417, 422, 427, 432, 437, 442, 448, 453, 459, 464, 470, 475, 481, 487, 493, 499, 505, 511, 517, 523, 530, 536, 542, 549, 556, 562, 569, 576, 583, 590, 597, 604, 612, 619, 626, 634, 642, 649, 657, 665, 673, 681, 690, 698, 706, 715, 723, 732, 741, 750, 759, 768, 777, 787, 796, 806, 816, 825, 835, 845, 856, 866, 876, 887, 898, 909, 920, 931, 942, 953, 965, 976, 988, 1000},
        {100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 117, 118, 120, 121, 123, 124, 126, 127, 129, 130, 132, 133, 135, 137, 138, 140, 142, 143, 145, 147, 149, 150, 152, 154, 156, 158, 160, 162, 164, 165, 167, 169, 172, 174, 176, 178, 180, 182, 184, 187, 189, 191, 193, 196, 198, 200, 203, 205, 208, 210, 213, 215, 218, 221, 223, 226, 229, 232, 234, 237, 240, 243, 246, 249, 252, 255, 258, 261, 264, 267, 271, 274, 277, 280, 284, 287, 291, 294, 298, 301, 305, 309, 312, 316, 320, 324, 328, 332, 336, 340, 344, 348, 352, 357, 361, 365, 370, 374, 379, 383, 388, 392, 397, 402, 407, 412, 417, 422, 427, 432, 437, 442, 448, 453, 459, 464, 470, 475, 481, 487, 493, 499, 505, 511, 517, 523, 530, 536, 542, 549, 556, 562, 569, 576, 583, 590, 597, 604, 612, 619, 626, 634, 642, 649, 657, 665, 673, 681, 690, 698, 706, 715, 723, 732, 741, 750, 759, 768, 777, 787, 796, 806, 816, 825, 835, 845, 856, 866, 876, 887, 898, 909, 920, 931, 942, 953, 965, 976, 988, 1000},
        {100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 117, 118, 120, 121, 123, 124, 126, 127, 129, 130, 132, 133, 135, 137, 138, 140, 142, 143, 145, 147, 149, 150, 152, 154, 156, 158, 160, 162, 164, 165, 167, 169, 172, 174, 176, 178, 180, 182, 184, 187, 189, 191, 193, 196, 198, 200, 203, 205, 208, 210, 213, 215, 218, 221, 223, 226, 229, 232, 234, 237, 240, 243, 246, 249, 252, 255, 258, 261, 264, 267, 271, 274, 277, 280, 284, 287, 291, 294, 298, 301, 305, 309, 312, 316, 320, 324, 328, 332, 336, 340, 344, 348, 352, 357, 361, 365, 370, 374, 379, 383, 388, 392, 397, 402, 407, 412, 417, 422, 427, 432, 437, 442, 448, 453, 459, 464, 470, 475, 481, 487, 493, 499, 505, 511, 517, 523, 530, 536, 542, 549, 556, 562, 569, 576, 583, 590, 597, 604, 612, 619, 626, 634, 642, 649, 657, 665, 673, 681, 690, 698, 706, 715, 723, 732, 741, 750, 759, 768, 777, 787, 796, 806, 816, 825, 835, 845, 856, 866, 876, 887, 898, 909, 920, 931, 942, 953, 965, 976, 988, 1000},
        {100, 102, 105, 107, 110, 113, 115, 118, 121, 124, 127, 130, 133, 137, 140, 143, 147, 150, 154, 158, 162, 165, 169, 174, 178, 182, 187, 191, 196, 200, 205, 210, 215, 221, 226, 232, 237, 243, 249, 255, 261, 267, 274, 280, 287, 294, 301, 309, 316, 324, 332, 340, 348, 357, 365, 374, 383, 392, 402, 412, 422, 432, 442, 453, 464, 475, 487, 499, 511, 523, 536, 549, 562, 576, 590, 604, 619, 634, 649, 665, 681, 698, 715, 732, 750, 768, 787, 806, 825, 845, 866, 887, 909, 931, 953, 976, 1000},
        {100, 105, 110, 115, 121, 127, 133, 140, 147, 154, 162, 169, 178, 187, 196, 205, 215, 226, 237, 249, 261, 274, 287, 301, 316, 332, 348, 365, 383, 402, 422, 442, 464, 487, 511, 536, 562, 590, 619, 649, 681, 715, 750, 787, 825, 866, 909, 953, 1000},
        {100, 110, 120, 130, 150, 160, 180, 200, 220, 240, 270, 300, 330, 360, 390, 430, 470, 510, 560, 620, 680, 750, 820, 910, 1000},
        {100, 120, 150, 180, 220, 270, 330, 390, 470, 560, 680, 820, 1000},
        {100, 150, 220, 330, 470, 680, 1000}
    };
    private final static ItvByRelativeToleranceBuilder TOLERANCE = new ItvByRelativeToleranceBuilder();        

    public static Interval getCeilingPreferred(Interval i) {
        int itol = getRoundToleranceIndex(i.getTolerance());
        return getIntervalByTolerance(getCeilingPreferred(itol, i.getValue()), itol);
    }

    public static Interval getFloorPreferred(Interval i) {
        int itol = getRoundToleranceIndex(i.getTolerance());
        return getIntervalByTolerance(getFloorPreferred(itol, i.getValue()),  itol);
    }

    public static Interval getRoundPreferred(Interval i) {
        int itol = getRoundToleranceIndex(i.getTolerance());
        return getIntervalByTolerance(getRoundPreferred(itol, i.getValue()), itol);

    } 
    
    protected static Interval getIntervalByTolerance (double value, int tolIndex) {
        try {
            return TOLERANCE._computeIntervals(new IntervalPoint(value), new IntervalPoint((PREFERRED_TOLERANCE[tolIndex]/100.)));        
        } catch (Exception e) {
            throw new RuntimeException ("Error in EngPreferred while executing getIntervalByTolerance "+e.getMessage());
        }
    }

    protected static double getCeilingPreferred(int tolIndex, double val) {
        double exp = getExponent(val);
        return PREFERRED_VALUES[tolIndex][getCeilingPreferredIndex(tolIndex, val/exp)] * exp;
    }
    
    protected static int getCeilingPreferredIndex (int tolIndex, double normalizedVal) {
        int val = (int) normalizedVal;
        int i = getClosestIndex(PREFERRED_VALUES[tolIndex], val);
        if (PREFERRED_VALUES[tolIndex][i] < val && i < PREFERRED_VALUES[tolIndex].length-1) {
            ++i;
        }
        return i;
    }    

    protected static double getFloorPreferred(int tolIndex, double val) {
        double exp = getExponent(val);
        return PREFERRED_VALUES[tolIndex][getFloorPreferredIndex(tolIndex, val/exp)] * exp;
    }
    
    protected static int getFloorPreferredIndex (int tolIndex, double normalizedVal) {
        int val = (int) normalizedVal;
        int i = getClosestIndex(PREFERRED_VALUES[tolIndex], val);
        if (PREFERRED_VALUES[tolIndex][i] > val && i > 0) {
            --i;
        }
        return i;
    }    
    
    protected static int getExponentIndex(double val) {
        int res = (int) Math.log10(val);
        if (res >= 0) return res - 2;
        return res - 3;
    }    

    protected static double getExponent(double val) {
        return getExponent(getExponentIndex(val));
    }
    
    protected static double getExponent(int valIndex) {
        return Math.pow(10., valIndex);
    }
    

    protected static double getRoundPreferred(int tolIndex, double val) {
        double exp = getExponent(val);
        return getClosest(PREFERRED_VALUES[tolIndex], (int) (val / exp)) * exp;
    }

    public static int getRoundToleranceIndex(double tol) {
        return getClosestIndex(PREFERRED_TOLERANCE, (int) (tol * 100.));
    }

    private static int getClosestIndex(int[] a, int x) {
        int low = 0;
        int high = a.length - 1;

        while (low < high) {
            int mid = (low + high) / 2;
            assert (mid < high);
            int d1 = Math.abs(a[mid] - x);
            int d2 = Math.abs(a[mid + 1] - x);
            if (d2 <= d1) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return high;
    }

    private static int getClosest(int[] a, int x) {
        return a[getClosestIndex(a, x)];
    }
        
    protected static double getPreferredValue (int tolIndex, int valIndex, int expIndex) {
        return PREFERRED_VALUES[tolIndex][valIndex] * getExponent(expIndex);
    }    
    
    protected static int getMaxIndex (int tolIndex) {
        return PREFERRED_VALUES[tolIndex].length-1;
    }

}
