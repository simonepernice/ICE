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
 
 package engcalculator.interval.measurementUnit;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */


public class MUTest {
    
        public static void main( String[] args ) throws Exception {
            
//            String s = new String ("simone");
//            System.out.println("Simone "+s);
//            StringBuffer sb = new StringBuffer(s);
//            sb.append(" pernice");
//            s = sb.toString();
//            System.out.println("Simone Pernice "+s);
//            sb.append(" fabio");
//            System.out.println("Simone Pernice Fabio "+sb);
//            System.out.println("Simone Pernice Fabio "+s);
            MeasurementUnit.initialize();
            
            MeasurementUnitAccumulator a = new MeasurementUnitAccumulator(MeasurementUnit.parseMeasurementUnit("s"));
            System.out.println("time "+a.toString());

            a = new MeasurementUnitAccumulator(MeasurementUnit.parseMeasurementUnit("N2"));
            System.out.println("square newton "+a.toString());
            
            a = new MeasurementUnitAccumulator(MeasurementUnit.parseMeasurementUnit("m"));
            System.out.println("length "+a.toString());
            
            a.sub(MeasurementUnit.parseMeasurementUnit("s"));
            System.out.println("speed "+a.toString());
            
            a.sub(MeasurementUnit.parseMeasurementUnit("s"));
            System.out.println("acceleration "+a.toString());            
            
            a.add(MeasurementUnit.parseMeasurementUnit("kg"));
            System.out.println("force "+a.toString());            
            
            a.add(MeasurementUnit.parseMeasurementUnit("m"));
            System.out.println("energy "+a.toString());            
            
            a.sub(MeasurementUnit.parseMeasurementUnit("s"));
            System.out.println("power "+a.toString());               
//            
            MeasurementUnit.parseMeasurementUnit("Hz");
            
            MeasurementUnit b;
            
            b = MeasurementUnit.parseMeasurementUnit("calc", "", "1/s", false);            
            System.out.println("frequency "+b.toString());    
            
            b = MeasurementUnit.parseMeasurementUnit("calc", "", "V/m", false);            
            System.out.println("field "+b.toString());    
            
            b = MeasurementUnit.parseMeasurementUnit("calc", "", "rad", false);            
            System.out.println("pure "+b.toString());                
            
            b = MeasurementUnit.parseMeasurementUnit("calc", "", "N N", false);            
            System.out.println("newton square "+b.toString());                 
            
            b = MeasurementUnit.parseMeasurementUnit("calc", "", "(s-1/m-1)", false);            
            System.out.println("speed "+b.toString());               
            
            b = MeasurementUnit.parseMeasurementUnit("calc", "", "m/1 /(1 / s)/s", false);            
            System.out.println("acceleration "+b.toString());                           
            
            b = MeasurementUnit.parseMeasurementUnit("calc", "", "(m /((1 / s)/s)  s4)", false);            
            System.out.println("acceleration "+b.toString());                           
            
            b = MeasurementUnit.parseMeasurementUnit("calc", "", "N / kg", false);            
            System.out.println("acceleration "+b.toString());                           
            
            b = MeasurementUnit.parseMeasurementUnit("calc", "", "mol / m3", false);            
            System.out.println("density of substance "+b.toString());                                       
            
            
//            v = parseMeasurementUnit("vel", "cd/s", true);            
//            System.out.println(v.getSymbol() + "=" +v.toString()+"="+v.toBaseString());
//            
//            v = MeasurementUnit.parseMeasurementUnit("s");
//            System.out.println(v.getSymbol() + "=" + v.toString());
//            
//            v = MeasurementUnit.parseMeasurementUnit("N");
//            System.out.println(v.getSymbol() + "=" + v.toString());
//            
//            v = MeasurementUnit.parseMeasurementUnit("W");
//            System.out.println(v.getSymbol() + "=" + v.toString());            
//            
//            MeasurementUnit m;
//                int[] base = {0,0,0,0,0,0,0};
//            int[] base = {1,0,0,0,0,0,0};
//            MeasurementUnit m=new MeasurementUnit("m", base, true);            
//            MeasurementUnit.addMeasurementUnit (m);            
//
//
//            System.err.println(m.getSymbol() + "=" + m.toString());
        }

    
}
