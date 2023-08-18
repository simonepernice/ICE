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

import engcalculator.expression.ParsingException;
import engcalculator.function.parameter.ConvertIntervalToBoolean;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
import engcalculator.interval.measurementUnit.MeasurementUnit;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public class IntervalPoint extends Interval {

    public final static String ENGUNITS = "yzafpnumkMGTPEZY";
    public final static String NUMBNOTATION = "0123456789.e";    
    private final static String UNITS = "yzafpnum kMGTPEZY";

    private static DecimalFormat dfVal= new DecimalFormat ("####.###", new DecimalFormatSymbols (Locale.ENGLISH));
        
    private static double fractionDigits = -Math.pow(0.1, 3);
    
    private final static Parameter<Integer> valDigits;
    private final static Parameter<Boolean> engFormat;
    final static Parameter<Boolean> nicePrint;

    static {
        valDigits = new Parameter<Integer> ("interval", "ALL", "valueDigits", "Set the  decimal digits to print.", 3, new ConvertIntervalToIntegerValDigits(0, 30));
        engFormat = new Parameter<Boolean> ("interval", "ALL", "engineerFormat", "Set 0 to print scientific notation or 1 to print engineer format.", true, new ConvertIntervalToBoolean());
        nicePrint = new Parameter<Boolean> ("interval", "ALL", "nicePrint", "Set 1 to produce more readable output for user.\nIf it is set to 0 it will provide an output can be copied and pasted as new input. If nice printing is disabled all other printing settinga are ignored.", true, new ConvertIntervalToBoolean());
        ParameterManager.addParameter(valDigits);
        ParameterManager.addParameter(engFormat);
        ParameterManager.addParameter(nicePrint);
    }    

    public static void setValDigits (int valDigits) {
        //this is called by the converter after setting valDigits Parameter

        fractionDigits = -Math.pow(0.1, valDigits);
        StringBuilder val = new StringBuilder ("####.");
        while (valDigits -- > 0) val.append('#');
        dfVal = new DecimalFormat (val.toString(), new DecimalFormatSymbols (Locale.ENGLISH));
    }
    
    public static Parameter<Boolean> isNicePrint() {
        return nicePrint;
    }    

    public static IntervalPoint valueOf (String engnotation) throws ParsingException {
        final int length = engnotation.length();

        int nextIndex, currentIndex;
        char currChar;
        nextIndex = currentIndex = 0;

        //skip the initial sign
        currChar = engnotation.charAt(0);
        if (currChar == '-' || currChar == '+') {
            currentIndex = 1;
        }

        //look for the left
        while (currentIndex < length && (Character.isDigit(currChar = engnotation.charAt(currentIndex)) || ".e-".indexOf(currChar) != -1)) {
            ++currentIndex;
        }

        //convert the left
        double val;
        
        try {
            val = Double.parseDouble(engnotation.substring(nextIndex,currentIndex));
        } catch (NumberFormatException nfe) {
            throw new ParsingException (nfe.getMessage());
        }

        //look if a engineer coefficient is present and get the power
        if (currentIndex < length) {
            int power = UNITS.indexOf(engnotation.charAt(currentIndex));
            if (power == -1)
                power = 8;
            else
                ++currentIndex;

            //apply the power
            val *= Math.pow(10, 3*(power-8));
        }

        if (currentIndex < length) throw new ParsingException ("It was expected the end of the interval.");
        return new IntervalPoint(val);
    }

    protected double left;
    protected MeasurementUnit measurementUnit;

    @Override
    public final boolean hasMeasurementUnit() {
        return (measurementUnit != null && ! measurementUnit.equals(MeasurementUnit.PURE));
    }

    @Override
    public final MeasurementUnit getMeasurementUnit() {
        if (measurementUnit == null) return MeasurementUnit.PURE;
        return measurementUnit;
    }   
    
    @Override
    protected final void setMeasurementUnit(MeasurementUnit m) {
        if (m != null) measurementUnit = m;
    }

    public IntervalPoint (Interval i) {
        this (i.getLeft());
        if (! i.isIntervalPoint()) throw new RuntimeException ("The algorithm is trying to cast some kind of Interval to a point interval: "+i.toString());
        if (i.hasMeasurementUnit()) this.measurementUnit = i.getMeasurementUnit();
    }

    public IntervalPoint (double val) {
        left = val;
    }

    @Override
    public double getRight() {
        return left;
    }

    @Override
    public double getLeft() {
        return left;
    }

    @Override
    public double getValue () {
        return left;
    }

    @Override
    public IntervalReal getRealPart() {
        return new IntervalReal(this);
    }

    @Override
    public IntervalReal getImaginaryPart() {
        return null;
    }

    protected final static StringBuilder toEngString (double a) {
        if (! nicePrint.getVal()) return new StringBuilder(Double.toString(a).replace('E', 'e'));
        
        if (! engFormat.getVal()) return new StringBuilder(dfVal.format(a));

        double olda = a;
        int coeff;

        double log = Math.log10(Math.abs(a));
        coeff = (int) (log/3.);

        if (log<fractionDigits) coeff -= 1;
        a /= Math.pow(10, coeff*3);

        StringBuilder result = new StringBuilder ();
        if (coeff >= 8 || coeff <= -8) {
            coeff = 0;
            a = olda;
            if (a == 0.0) result.append('0');
            else {
                String sval = Double.toString(a);
                sval = sval.replace('E', 'e');
                result.append(sval);
            }
        } else result.append(dfVal.format(a));
        if (coeff!=0) result.append(UNITS.charAt(coeff+8));
        return result;
    }

    protected final void addMeasurementUnit (StringBuilder result) {
        if (nicePrint.getVal()) {
            if (hasMeasurementUnit()) result.append(measurementUnit.toString());            
        } else {
            if (hasMeasurementUnit()) result.append(" @ '").append(measurementUnit.toStringBuilder()).append("'");
        }
    }
    
    public StringBuilder toStringBuilder () {        
        return toEngString(left);    
    }
    
    @Override
    public final String toString () {        
        StringBuilder result = toStringBuilder();

        addMeasurementUnit(result);
        
        return result.toString();
    }

    public double getTolerance() {
        return 0.0;
    }

    public double getRange() {
        return 0.0;
    }

    public boolean isIntervalPoint() {
        return true;
    }

    @Override
    public boolean isIntervalReal() {
        return true;
    }

    @Override
    public boolean isIntervalComplex() {
        return true;
    }

    public String getName() {
        return null;
    }

    public IntervalPoint computeRoundings() {
        return this;
    }

    @Override
    public ListIntervals getListIntervals() {
        return null;
    }

    @Override
    public boolean isIntervalList() {
        return false;
    }

    @Override
    public boolean isIntervalLiteral() {
        return false;
    }

}
