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

package engcalculator.function.embedded.define;

import engcalculator.Calculator;
import engcalculator.domain.DomainIntervalList;
import engcalculator.domain.DomainList;
import engcalculator.domain.DomainListLiteral;
import engcalculator.domain.DomainIntervalLiteral;
import engcalculator.function.infix.FunctionInfix;
import engcalculator.interval.ListIntervals;
import engcalculator.interval.IntervalLiteral;
import engcalculator.function.CalculusException;
import engcalculator.function.prefix.FunctionPrefix;
import engcalculator.expression.Expression;
import engcalculator.function.embedded.userDefined.UDeFunctionInfixByExpression;
import java.util.LinkedList;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class DefFunctionInfix extends FunctionPrefix  {
  
    private final static DomainList DOMAIN = new DomainList (new DomainIntervalList(), new DomainIntervalLiteral(), new DomainIntervalList(), new DomainIntervalLiteral(), new DomainIntervalLiteral());    
    private final static DomainListLiteral DOMAIN_LIST_LITERAL =  new DomainListLiteral();

    private final static String HELP = "... ({$leftVar1, ..}, '+*/', {$rightVar1, ..}, 'expression with left and right vars', 'priority') defines the new infix function (leftVar1, ..) +*/ ($rightVar1, ..) = expression at given precedence,\nThe priority is a literal of the following list (priority aschending) ";
    private final static String[] EXAMPLESLEFT = {"defineFunctionInfix({$a}, '*/*', {$b}, 'a*b/(a+b)', 'sum'); diffuseCumulate('*/*',5, 10)"};    
    private final static String[] RESULTS = {"5*10/15"};

    public DefFunctionInfix() {
        super ("define", "FunctionInfix", (byte) 5, DOMAIN, false, true, HELP+ FunctionInfix.getPriorityList(), EXAMPLESLEFT, RESULTS);
    }


    @Override
    public final ListIntervals _compute(ListIntervals input) throws Exception {
        String fname = input.get(1).getName();

        LinkedList<String> varNamesLeft = new LinkedList<String> ();

        ListIntervals left = input.getFirst().getListIntervals();
        if (! DOMAIN_LIST_LITERAL.isCompatible(left)) throwNewCalculusException ("Expected a list of literals as left side function while was found "+left);
        final byte sL = (byte) left.size();           

        for (int i=0; i<sL; ++i) {
            String varname;
            varname = left.get(i).getName();
            if (varname == null) throwNewCalculusException("It was expected a literal and not the value "+left.get(i)+" as a function definition variable.");
            varNamesLeft.add(varname);
        }

        LinkedList<String> varNamesRight = new LinkedList<String> ();

        ListIntervals right = input.get(2).getListIntervals();
        if (! DOMAIN_LIST_LITERAL.isCompatible(right)) throwNewCalculusException ("Expected a list of literals as right side function instead it was found "+right);        
        final byte sR = (byte) right.size();           

        for (int i=0; i<sR; ++i) {
            String varname;
            varname = right.get(i).getName();
            if (varname == null) throwNewCalculusException("It was expected a literal and not the value "+right.get(i)+" as a function definition variable.");
            varNamesRight.add(varname);
        }            

        LinkedList<String> allVarNames = new LinkedList<String> (varNamesLeft);
        allVarNames.addAll(varNamesRight);
        Expression e;        
        e = new Expression(input.get(3).getName(), allVarNames);

        StringBuilder help = new StringBuilder();
        help.append (" ({");
        for (String v : varNamesLeft)
            help.append(v).append(',');        
        help.setCharAt(help.length()-1, '}');
        help.append (") ");
        help.append(fname);
        help.append (" ({");
        for (String v : varNamesRight)
            help.append(v).append(',');
        help.setCharAt(help.length()-1, '}');
        help.append (") = ");
        help.append(e.toString());

        LinkedList<String> dup = e.listOfDuplicatedVariables();
        if (dup.size() != 0) {
            Calculator.addWarning("\nWarning: there are duplicated variables in some input function sub expression, which may lead to overestimation of the result. Try to rewrite the input sub expressions avoiding the following duplications for a smaller result: ".concat(dup.toString()));
        }

        UDeFunctionInfixByExpression f = new UDeFunctionInfixByExpression (fname, sL, sR, true, help.toString(), e, varNamesLeft, varNamesRight);
        try {
            if (! FunctionInfix.addFunction(f, input.get(4).getName())) throwNewCalculusException ("There is a function already defined with the name "+f.getSymbol()+" which is locked.");
        } catch (RuntimeException ex) {
            throwNewCalculusException ("The priority name given "+input.get(4).getName()+" was not recognized as a valide one.");
        }

        return new ListIntervals (new IntervalLiteral (f.getSymbol()));
    }
}
