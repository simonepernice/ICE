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

package engcalculator.expression;

import engcalculator.expression.token.TokenList;
import engcalculator.interval.ListIntervals;
import engcalculator.function.Function;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.function.infix.FunctionInfixSwappedArguments;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.function.embedded.system.SysConstantIntervalFunction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class InterpeterCompiled extends Interpeter {

    private final static boolean DEBUG = false;
    private final LinkedList<Function> compiledProgram;
    private HashMap<String, FunctionPrefix> parameters;
    private LinkedList<Parameter> parameterTable;

    public InterpeterCompiled(TokenList tokenizedExpression) {
        super(tokenizedExpression);
        compiledProgram = new LinkedList<Function>();
    }

    @Override
    protected ListIntervals computeInfixFunction(FunctionInfix function, ListIntervals argument1, ListIntervals argument2) throws Exception {
        if (argument1 != null && argument2 != null && !parameters.containsKey(argument1.getName()) &&  !parameters.containsKey(argument2.getName())) return function.compute(argument1, argument2); //optimization, not strictly required just faster compilation

        if (argument2 != null) {
            if (argument1 != null) compiledProgram.addLast(new SysConstantIntervalFunction(argument1));
            compiledProgram.addLast(new SysConstantIntervalFunction(argument2));
            compiledProgram.addLast(function);
        } else if (argument1 != null) {
            compiledProgram.addLast(new SysConstantIntervalFunction(argument1));
            compiledProgram.addLast(new FunctionInfixSwappedArguments(function));
        } else {
            compiledProgram.addLast(function);
        }

        return null;
    }

    @Override
    protected ListIntervals computePrefixFunction(FunctionPrefix function, ListIntervals argument) throws Exception {
        if (argument != null && !parameters.containsKey(argument.getName())) return function.compute(argument); //optimization, not required for compilation
        if (argument != null) {
            compiledProgram.addLast(new SysConstantIntervalFunction(argument));
        }
        compiledProgram.addLast(function);
        return null;
    }

    public void compile(HashMap<String, FunctionPrefix> parameters) throws Exception {
        if (compiledProgram.size() > 0) {
            return; //program already compiled
        }
        this.parameters = parameters;
        ListIntervals last = super.parse();
        if (last != null) {
            compiledProgram.addLast(new SysConstantIntervalFunction(last));
        }

        if (DEBUG) {
            System.out.println("Compiled program: " + compiledProgram);
        }

        computeParameterTable();
    }

    public ListIntervals execute(HashMap<String, FunctionPrefix> currentParameters) throws Exception {
        if (DEBUG) System.out.println("Executing compiled expression: "+compiledProgram+" with parameters: "+parameters);

        LinkedList<ListIntervals> argumentsStack = new LinkedList<ListIntervals>();
        ListIntervals[] arguments = null;

        for (Parameter p : parameterTable) {
            compiledProgram.set(p.positions, currentParameters.get(p.name));
        }

        if (DEBUG) {
            System.out.println("Execution");
        }

        for (Function f : compiledProgram) {
            arguments = null;

            if (f.getComputeNumbArg() > 0) {
                arguments = new ListIntervals[f.getComputeNumbArg()];
                for (int i = arguments.length - 1; i >= 0; --i) {
                    arguments[i] = argumentsStack.pop();
                }
            }

            if (DEBUG) {
                System.out.println("Executing: " + f.getSymbol() + " with arguments: " + Arrays.toString(arguments));
            }
            
            argumentsStack.push(f.compute(arguments));

            if (DEBUG) {
                System.out.println("Stack: " + argumentsStack);
            }

        }

        return argumentsStack.getFirst();
    }

    private void computeParameterTable() throws Exception {
        parameterTable = new LinkedList<Parameter>();
        for (int position = 0; position < compiledProgram.size(); ++position) {
            Function f = compiledProgram.get(position);
            if (f.getComputeNumbArg() > 0) {
                continue;
            }
            String name = f.compute().getName();
            if (parameters.containsKey(name)) {
                parameterTable.add(new Parameter(name, position));
            }
        }
    }

    @Override
    public String toString() {
        if (compiledProgram == null || compiledProgram.size() < 1) return null;
        StringBuilder result = new StringBuilder ();
        for (Function f : compiledProgram)
            result.append('\'').append(f).append("',");
        result.deleteCharAt(result.length()-1);
        return result.toString();
        
    }

    class Parameter {

        public String name;
        public int positions;

        public Parameter(String name, int positions) {
            this.name = name;
            this.positions = positions;
        }
    }
}


