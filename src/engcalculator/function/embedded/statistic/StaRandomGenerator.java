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

import edu.cornell.lassp.houle.RngPack.*;
import engcalculator.function.parameter.ConvertIntervalToInteger;
import engcalculator.function.parameter.ConvertIntervalToLong;
import engcalculator.function.parameter.Parameter;
import engcalculator.function.parameter.ParameterManager;
/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class StaRandomGenerator {
    private static RandomSeedable RANDOMGENERATOR = new RanMT(RandomSeedable.ClockSeed());
    
    private final static Parameter<Integer> NUMELEMENTSFORSIMULATION;    
    private final static Parameter<Long> SEED;    
    private final static Parameter<Integer> TYPE;
    
    static {
        SEED = new Parameter<Long> ("statistic", "RandomGenerator", "seed", "It is also possible to set the seed for the random generator, use 0 for the current time in ms. The seed is used when a new random generator is selected, so to make it active it is required to select the random generator type after setting seed.", 0l, new ConvertIntervalToLong(0, Long.MAX_VALUE));        
        TYPE = new Parameter<Integer> ("statistic", "RandomGenerator", "type", "Sets the random generator uses the library edu.cornell.lassp.houle.RngPack version 1.1 available on edu.cornell.lassp.houle.RngPack. ... can be used to set the required random generator. Use 0 to keep the previus random generator, 1 for Mersenne twister period 2^19937-1 time 7.1s, 2 for RANLUX which is an advanced pseudo-random number generator based on the RCARRY algorithm proposed in 1991 by Marsaglia and Zaman period 10^171 time 14.7 s, 3 Ranecu is an advanced multiplicative linear congruential random number generator period 10^18 time 5.8 s, 4 RANMAR is a lagged Fibonacci generator proposed by Marsaglia and Zaman and is a good research grade generator 10^43 time 4.2 s. ", 1, new ConvertIntervalToIntegerRandomGenerator());
        NUMELEMENTSFORSIMULATION = new Parameter<Integer> ("statistic", "RandomGenerator", "elementsForSimulation", "It is also possible to set the numbero of elements to be used in simulation  (like montecarlo, random, ?=, ..).", 8000, new ConvertIntervalToInteger(1, 10000));        
        ParameterManager.addParameter(SEED);
        ParameterManager.addParameter(TYPE);
        ParameterManager.addParameter(NUMELEMENTSFORSIMULATION);
    }
    
    
    public static int getNUMELEMENTSFORSIMULATION() {
        return NUMELEMENTSFORSIMULATION.getVal();
    }
    
    public static double nextUniformRandom () {
        return RANDOMGENERATOR.raw();
    }
    
    public static int nextUniformRandomInteger (int min, int max) {
        return RANDOMGENERATOR.choose(min, max);
    }
    
    public static double nextGaussianRandom () {
        return RANDOMGENERATOR.gaussian();
    }
    
    public static void setRandomGenerator (int gen) {
        long seed = SEED.getVal();
        if (seed == 0) seed = RandomSeedable.ClockSeed();
        
        switch (gen) {
            case 1:
                RANDOMGENERATOR = new RanMT(seed);
//                System.out.println("Random MT generator with seed :"+seed);
                break;
            case 2:
                RANDOMGENERATOR = new Ranlux (4, seed);
//                System.out.println("Random lux generator with seed :"+seed);
                break;
            case 3:
                RANDOMGENERATOR = new Ranecu(seed);
//                System.out.println("Random ecu generator with seed: "+seed);
                break;
            case 4:
                RANDOMGENERATOR = new Ranmar(seed);
//                System.out.println("Random mar generator with seed: "+seed); 
                break;
            default:
                throw new RuntimeException ("The random generator provided is out of range: "+gen);
        }
    }
            
}
