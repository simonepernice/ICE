\n"+
"﻿Eng Calculator Changes \n"+
"\n"+
"\n"+
"\n"+
"1. + Add to literal the option to behave as interval as well: variable&function will be literal and interval at the same time making in simpler adding function: \n"+
"1. interval pure getString = null \n"+
"2. literal extends Inteval: getString = the literal, the interval may be \n"+
"1. An actual interval (for variable) \n"+
"2. A NAN for messages and function \n"+
"\n"+
"3. Add variables with introduction (explaining how to use help) and about \n"+
"4. Add \"about\" string visualization at start up (showing to see introduction) \n"+
"5. Improve function and variables help using common an explanation and an example \n"+
"6. Can make sense to add some statistical information on list (average, variance, number of elements, ...)? \n"+
"7. Use a nice icon for the program and for the about box \n"+
"8. Add some help in the console for the keys short cut \n"+
"\n"+
"\n"+
"\n"+
"\n"+
"1. + Create NamedInterval which behaves like Interval and Literal \n"+
"2. + Create NamedIntervalList which behaves like IntervalList plus a name \n"+
"\n"+
"3. Add variables with introduction (explaining how to use help) and about \n"+
"4. Add \"about\" string visualization at start up (showing to see introduction) \n"+
"5. Improve function and variables help using common an explanation and an example \n"+
"6. Can make sense to add some statistical information on list (average, variance, number of elements, ...)? \n"+
"7. Can make sense to add function for getting elements of a list. \n"+
"\n"+
"8. Use a nice icon for the program and for the about box \n"+
"9. Add some help in the console for the keys short cut \n"+
"\n"+
"\n"+
"\n"+
"\n"+
"1. + Use late binding function instead of literal to solve expression like: setvar(a,2),a+a \n"+
"2. + Add terminal symbol ';' to avoid output \n"+
"3. + For Infix functions with 1 or more argument suggest '(' as well \n"+
"4. + Add on console when push enter the fix of brackets number then get the expression \n"+
"5. + Add ESC to clear input field \n"+
"6. Add abs, sign, module, partition, dB power and amplitude function \n"+
"7. Add statistical functions for list (average, variance, number of elements, max, min) \n"+
"8. Add function for set/get an element of a list. \n"+
"9. Add comparison, iteration and conditional operators \n"+
"10. Add numerical integration and differentiation \n"+
"11. Add conversion constants: (inches, feed, yard to meter; pounds, once to Kg; year, day, minute to second; knot, mi/h, km/h, ft/h to m/s; ) \n"+
"12. Add most used physical constants: http://physics.nist.gov/cgi-bin/cuu/Category?view=html&Frequently+used+constants.x=81&Frequently+used+constants.y=28 \n"+
"13. Add variables with introduction (explaining how to use help) and about (on the author an license) \n"+
"14. Add \"about\" string visualization at start up (showing to see introduction) \n"+
"15. Improve function and variables help using common an explanation and an example \n"+
"16. Use a nice icon for the program and for the about box \n"+
"17. Add some help in the console for the keys short cuts \n"+
"18. Add draw graphics option \n"+
"19. Add option to save and load the functions from and to a text file \n"+
"20. May have sense to add right associativity for function like '^'??? \n"+
"\n"+
"\n"+
"\n"+
"\n"+
"1. + Fix text completion: '(x+2 is completed like '(x+2') \n"+
"2. + Fix Increase the room of input text because with scrolling bars the text is not visible \n"+
"3. + Fix TAB does not work in the middle of an expression \n"+
"4. - Add separator between output and input (preferred to keep this way out) \n"+
"6. Add more clever error report which usually does not have the information on where the error is found \n"+
"7. + Change set the priority on addFunction instead of doing it in the function in order to simplify the addition of low priority function (which now requires to change the priority of all other functions at higher priority) \n"+
"8. Fix the use of '+' to add Strings, use StringBuffer.append to avoid memory waste \n"+
"\n"+
"9. + Fix arccos(2) which does give exception \n"+
"\n"+
"10. - Fix implement terminals ',' as infix function instead of adding appropriate parser rules (would give issues because automatically the single interval is expanded to list when applied to another component for ',') \n"+
"\n"+
"11. Fix implement terminals ';' as infix function instead of adding appropriate parser rules (check if ';' would work out because it starts from list when try to compute a new piece of exception) \n"+
"\n"+
"12. + Move the Tokenize code in Token as static and the parser code in Parser static \n"+
"\n"+
"13. Add abs, sign, module, dB power and amplitude function \n"+
"14. Add statistical functions for list (average, variance, number of elements, max, min) \n"+
"15. Add function for set/get an element of a list. \n"+
"16. Add comparison, iteration and conditional operators \n"+
"17. Add numerical integration and differentiation \n"+
"18. Add conversion constants: (inches, feed, yard to meter; pounds, once to Kg; year, day, minute to second; knot, mi/h, km/h, ft/h to m/s; ) \n"+
"19. Add most used physical constants: http://physics.nist.gov/cgi-bin/cuu/Category?view=html&Frequently+used+constants.x=81&Frequently+used+constants.y=28 \n"+
"20. Add variables with introduction (explaining how to use help) and about (on the author an license) \n"+
"21. Add \"about\" string visualization at start up (showing to see introduction) \n"+
"22. Improve function and variables help using common an explanation and an example \n"+
"23. Use a nice icon for the program and for the about box \n"+
"24. Add some help in the console for the keys short cuts \n"+
"25. Add draw graphics option \n"+
"26. Add option to save and load the functions from and to a text file \n"+
"27. May have sense to add right associativity for function like '^'??? \n"+
"\n"+
"\n"+
"\n"+
"1. +Fix when a variable contains a list it is not possible to use it without ' to set again the variable while it may be possible checking for the name of the intervalList \n"+
"2. +When a new function is defined it should only accept already defined variables plus its parameters instead it accepts also literal not yet defined. Moreover can make sense to compile the parameters. ParameterTkn. \n"+
"\n"+
"\n"+
"3. +Add more clever error report which usually does not have the information on where the error is found \n"+
"4. Fix the use of '+' to add Strings, use StringBuffer.append to avoid memory waste \n"+
"\n"+
"\n"+
"5. -Fix implement terminals ';' as infix function instead of adding appropriate parser rules (check if ';' would work out because it starts from list when try to compute a new piece of exception). Not possible because it has different working principle \n"+
"\n"+
"\n"+
"6. +Add settolerance, setvalue, abs, sign, module, dB power and amplitude function \n"+
"7. +Add statistical functions for list (average, variance, number of elements, max, min) \n"+
"8. +Add function for set/get an element of a list. \n"+
"9. +Add comparison operators \n"+
"10. Add iteration and conditional operators \n"+
"11. +Add numerical integration and differentiation \n"+
"12. Add conversion constants: (inches, feed, yard to meter; pounds, once to Kg; year, day, minute to second; knot, mi/h, km/h, ft/h to m/s; ) \n"+
"13. Add most used physical constants: http://physics.nist.gov/cgi-bin/cuu/Category?view=html&Frequently+used+constants.x=81&Frequently+used+constants.y=28 \n"+
"14. Add variables with introduction (explaining how to use help) and about (on the author an license) \n"+
"15. Add \"about\" string visualization at start up (showing to see introduction) \n"+
"16. Improve function and variables help using common an explanation and an example \n"+
"17. Use a nice icon for the program and for the about box \n"+
"18. Add some help in the console for the keys short cuts \n"+
"19. Add draw graphics option \n"+
"20. Add option to save and load the functions from and to a text file \n"+
"21. +Add: may have sense to add right associativity for function like '^'? \n"+
"22. Add: may have sense to extends list to matrix and add the matrix operations? \n"+
"\n"+
"\n"+
"\n"+
"1. Fix the use of '+' to add Strings, use StringBuffer.append to avoid memory waste \n"+
"2. +Fix when a function is defined all its sub functions (variables and function) should be linked statically \n"+
"3. Add the option to compute the variables as dependent \n"+
"\n"+
"4. Add iteration and conditional operators \n"+
"5. +Add conversion constants: (inches, feed, yard to meter; pounds, once to Kg; year, day, minute to second; knot, mi/h, km/h, ft/h to m/s; ) \n"+
"6. +Add most used physical constants: http://physics.nist.gov/cgi-bin/cuu/Category?view=html&Frequently+used+constants.x=81&Frequently+used+constants.y=28 \n"+
"7. Add variables with introduction (explaining how to use help) and about (on the author an license) \n"+
"8. Add \"about\" string visualization at start up (showing to see introduction) \n"+
"9. Improve function and variables help using common an explanation and an example \n"+
"10. Use a nice icon for the program and for the about box \n"+
"11. Add some help in the console for the keys short cuts \n"+
"12. Add draw graphics option \n"+
"13. Add option to save and load the functions from and to a text file \n"+
"14. Add: may have sense to extends list to matrix and add the matrix operations? \n"+
"\n"+
"\n"+
"\n"+
"1. Add the option to compute the variables as dependent \n"+
"2. +Fix the logspace does not take the extremes but 10^extremes \n"+
"\n"+
"3. +Add self check using examples \n"+
"\n"+
"4. Add iteration and conditional operators \n"+
"5. +Add variables with introduction (explaining how to use help) and about (on the author an license) \n"+
"6. +Add the option to insert a multi-string input. Solved retokenizeing input after ; \n"+
"\n"+
"7. +Add \"about\" string visualization at startup (showing to see introduction) \n"+
"8. +Improve function and variables help using common an explanation and an example \n"+
"9. +Use a nice icon for the program and for the about box \n"+
"10. Add some help in the console for the keys short cuts \n"+
"11. Add draw graphics option \n"+
"12. Add option to save and load the functions from and to a text file \n"+
"13. Add: may have sense to extends list to matrix and add the matrix operations? \n"+
"14. +Add directed interval for basic operations \n"+
"\n"+
"\n"+
"17/11/2009 version 0.91 \n"+
"\n"+
"\n"+
"1. -Add the option to compute the variables as dependent (that does not make sense thinking to the worst case calculation) \n"+
"\n"+
"2. Add iteration and conditional operators \n"+
"3. +Add variables with version \n"+
"4. Attach documents with explanation on intervals and program history \n"+
"\n"+
"5. Add some help in the console for the keys short cuts \n"+
"6. +Add draw graphics option \n"+
"7. Add option to save and load the functions from and to a text file \n"+
"8. +Add matrix multiplication and linear simultaneous equation solving \n"+
"9. +Add function to append matrix to each other (may be ','???) \n"+
"\n"+
"10. +Fix command without arguments do not accept empty brackets \n"+
"11. Change '-' should have lower priority then ^ \n"+
"12. +Add function to concatenate and translate matrix \n"+
"13. Fix sin does not work at 2 pi \n"+
"\n"+
"\n"+
"30/11/2009 version 0.92 \n"+
"\n"+
"\n"+
"1. Add iteration and conditional operators \n"+
"2. Attach documents with explanation on intervals and program history \n"+
"\n"+
"3. Add some help in the console for the keys short cuts \n"+
"4. Add option to save and load the functions from and to a text file \n"+
"5. Change '-' should have lower priority then ^ \n"+
"6. Fix sin does not work at 2 pi \n"+
"7. +Add automatically update a drawing on X, Y, Joined and Visible change, label focus lost partially done \n"+
"\n"+
"8. +Change update button in clean and new \n"+
"9. +Add selecting Y will automatically set the label with its name and visible \n"+
"\n"+
"10. +Change tick to pass through integer number \n"+
"11. +Change Fit Scale to write a small number of decimal \n"+
"12. +Change the drawing variable should be taken fresh from the calculator to be updated \n"+
"13. +Fix sometime the pen is thick, try with minimum instead of average of x and y ratios \n"+
"\n"+
"4/12/2009 version 0.91 \n"+
"\n"+
"\n"+
"1. Add iteration and conditional operators \n"+
"2. Attach documents with explanation on intervals and program history \n"+
"\n"+
"3. Add some help in the console for the keys short cuts \n"+
"4. Add option to save and load the functions from and to a text file \n"+
"5. Change '-' should have lower priority then ^ \n"+
"6. +Fix sin does not work at 2 pi (that is due to computational error on the displacement) \n"+
"\n"+
"7. +Add function for constant space (not required it is enough using linspace on a scalar) and for building diagonal matrix \n"+
"\n"+
"\n"+
"8/12/2009 version 0.92 \n"+
"\n"+
"\n"+
"1. +Add iteration and conditional operators \n"+
"2. Attach documents with explanation on intervals and program history \n"+
"\n"+
"3. Add some help in the console for the keys short cuts \n"+
"4. Add option to save and load the functions from and to a text file \n"+
"5. Change '-' should have lower priority then ^ \n"+
"6. +Check trig function there are two parameters not used \n"+
"7. -Change incspace should use an interval as extremes, no way \n"+
"\n"+
"8. Add the option to use a third variable to set the color \n"+
"9. -Change probably constructor Matrix of Matrix is not required, was required \n"+
"\n"+
"10. +Add get (int, int) to intervalList to avoid casting \n"+
"11. +Add shortcut for drawing and re size buttons which are not visible \n"+
"12. +Used thread to execute calculation in order to avoid stopping the console \n"+
"13. Add function to set auto increment of interval to ensure inclusion and to set the width of number for matrix and list \n"+
"14. +Change gives error for variable and function not initialized (for literal, they are not stored in answer) \n"+
"\n"+
"\n"+
"14/12/2009 version 0.92 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Add some help in the console for the keys short cuts \n"+
"3. Add option to save and load the functions from and to a text file \n"+
"4. Change '-' should have lower priority then ^ \n"+
"5. Add the option to use a third variable to set the color \n"+
"6. Add function to set auto increment of interval to ensure inclusion and to set the width of number for matrix and list (for the second it can be printmode changed in order to accept a shorter list of parameters using only the last one) \n"+
"7. -Fix repeat next while does not provide an answer, the behaviour is correct \n"+
"\n"+
"8. +Fix the X text file is smaller than others and buttons alignment \n"+
"\n"+
"9. +Change the strategy to increase the expression: not increase only if: the list is composed by 1 element which is named or the list is named. \n"+
"\n"+
"10. -Change Expression in Question. Expression makes more sense \n"+
"\n"+
"11. -Split the numbering for question and answers do not like the idea it seems awkward \n"+
"\n"+
"12. +Add to the help of repeat functions they work only if used as last statement \n"+
"\n"+
"\n"+
"15/12/2009 version 0.92 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Add some help in the console for the keys short cuts \n"+
"3. Add option to save and load the functions from and to a text file \n"+
"4. Change '-' should have lower priority then ^ \n"+
"5. Add the option to use a third variable to set the color \n"+
"6. Add function to set auto increment of interval to ensure inclusion and to set the width of number for matrix and list (for the second it can be printmode changed in order to accept a shorter list of parameters using only the last one) \n"+
"7. +Fix stop function which reports an error erroneously \n"+
"8. +Change the way to stop the thread since \"stop()\" is deprecated \n"+
"9. Add function to \"read\" and \"write\" color code \n"+
"\n"+
"30/12/2009 version 0.93 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Add some help in the console for the keys short cuts \n"+
"3. Add option to save and load the functions from and to a text file \n"+
"4. Change '-' should have lower priority then ^ \n"+
"5. Add the option to use a third variable to set the color \n"+
"6. +Add function to set auto increment of interval to ensure inclusion and to set the width of number for matrix and list (for the second it can be printmode changed in order to accept a shorter list of parameters using only the last one) \n"+
"7. +Add function to \"read\" and \"write\" color code \n"+
"8. +Change trig to work on directed intervals \n"+
"\n"+
"\n"+
"1/1/2010 version 0.94 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Add some help in the console for the keys short cuts \n"+
"3. Add option to save and load the functions from and to a text file \n"+
"4. Change '-' should have lower priority then ^ \n"+
"5. +Add the option to use a third variable to set the color \n"+
"6. +Add function ## to set the number of rows for a matrix \n"+
"7. +Change Use column matrix to draw to drastically simplify the drawing window \n"+
"8. +Change move the zoom option of the drawing windows in a new window \n"+
"\n"+
"\n"+
"3/1/2010 version 0.94 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Add some help in the console for the keys short cuts \n"+
"3. Add option to save and load the functions from and to a text file \n"+
"4. Change '-' should have lower priority then ^ \n"+
"5. +Fix function definition gives error \n"+
"6. +Fix an expression starting by ; gives error \n"+
"7. Add surface plot \n"+
"8. +Change surfaceplot, standardplot, parametricplot should skip wrong values OR let function work on list providing NAN when the operation is not valid? \n"+
"9. +Add buttons to scroll up and down between visible functions \n"+
"10. +Change cancel button is not visible \n"+
"11. Add sizerow and sizecolumn function \n"+
"12. Simultaneous equation solver should return the vector of solution \n"+
"13. Change \":\" logic use interval to describe row/columns instead of lists \n"+
"14. Add :: to get rows or columns from a matrix \n"+
"15. Change use \"#\" to set row or columns of a matrix without extra function ##, which may require a lowering in priority of # respect to _, remove ## \n"+
"\n"+
"16. Let function definition to return a literal with the function name in order to define function inline for findroot, plot, etc. \n"+
"17. Fix when option window is showed it does not update the controls (like axes and ticks) \n"+
"18. Add explanation on engineer notation for number formatting (multipliers) \n"+
"\n"+
"\n"+
"\n"+
"5/1/2010 version 0.94 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Add some help in the console for the keys short cuts \n"+
"3. Add option to save and load the functions from and to a text file \n"+
"4. Change '-' should have lower priority then '^' \n"+
"5. +Add surface plot \n"+
"6. +Add sizerow and sizecolumn function \n"+
"7. Simultaneous equation solver should return the vector of solution \n"+
"8. +Change \":\" logic use interval to describe row/columns instead of lists \n"+
"9. +Add :: to get rows or columns from a matrix \n"+
"10. +Change use \"#\" to set row or columns of a matrix without extra function ##, which may require a lowering in priority of # respect to _, remove ## \n"+
"\n"+
"11. +Change function definition to return a literal with the function name in order to define function inline for findroot, plot, etc. \n"+
"12. +Fix when option window is showed it does not update the controls (like axes and ticks) \n"+
"13. Add explanation on engineer notation for number formatting (multipliers) \n"+
"14. +Fix version is misspelled \n"+
"15. +Change simultaneous equation solving method should be used for intervals \n"+
"16. +Add multiple lines input management supported only by console \n"+
"17. Change the examples that are self meaning \n"+
"18. Fix gauss elimination if possible \n"+
"19. +Add option to set color by value or by tolerance \n"+
"\n"+
"\n"+
"6/1/2010 version 0.94 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. +Add some help in the console for the keys short cuts: added menu \n"+
"3. +Add option to save and load the functions from and to a text file \n"+
"4. Change '-' should have lower priority then '^' \n"+
"5. +Simultaneous equation solver should return the vector of solution \n"+
"6. Add explanation on engineer notation for number formatting (multipliers) \n"+
"7. Change the examples that are self meaning \n"+
"8. Change gauss elimination for interval if possible \n"+
"9. +Fix q=5;standardplot('sin',q,0_2*pi) does not work: it was retokenize how changed 'sin' in the actual sin \n"+
"\n"+
"10. +Fix delete (pi) gives internal error \n"+
"11. Extends ==, » and « to literals? \n"+
"12. +Send the commands to the interpeter separately instead of all in once \n"+
"13. Add packages to collect functions on smaller groups \n"+
"14. +Fix = do not work on list with literals \n"+
"15. +Fix variable can store literal to be extracted as variable as well \n"+
"\n"+
"\n"+
"9/1/2010 ver 0.94 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Change '-' should have lower priority then '^' \n"+
"3. Add explanation on engineer notation for number formatting (multipliers) \n"+
"4. Change the examples that are self meaning \n"+
"5. Change gauss elimination for interval if possible \n"+
"6. +Extends ==, » and « to literals? \n"+
"7. +Add packages to collect functions on smaller groups \n"+
"8. +Fix (sinc, x ) = sin x/x, (sinc3d, x, y) = sinc x + sinc y does not work \n"+
"9. +Change use a function returning two values for a parametric plot \n"+
"10. +Add a bar showing the color change due to its hue in drawing window \n"+
"11. +Add a time function to report the current time in ms for calculus time measure \n"+
"12. +Change the tolerance should follow the print rules as per the value for the multipliers \n"+
"\n"+
"13. Add compiled expression for function \n"+
"\n"+
"14. Add code to detect deleted variables from drawings \n"+
"15. Add square bracket to insert matrix? \n"+
"\n"+
"13/1/2010 ver 0.94 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Change '-' should have lower priority then '^' \n"+
"3. Add explanation on engineer notation for number formatting (multipliers) \n"+
"4. Change the examples that are self meaning \n"+
"5. Change gauss elimination for interval if possible \n"+
"6. Add compiled expression for function \n"+
"\n"+
"7. +Add code to detect deleted variables from drawings \n"+
"8. +Fix if change anything in drawing window without a drawing \n"+
"\n"+
"9. +Add square bracket to insert matrix rows to be joined by comma? \n"+
"10. +Fix extremes(partition(50K_180K,10K)) » 1 \n"+
"11. +Change power to work with negative exponent \n"+
"12. +Add a warning if the same interval or variable appear more the once in the expression (only for variable which is easier at the moment) \n"+
"\n"+
"13. Change the parser may work out everything (also ;) without the use of sub Expression \n"+
"14. Change the parser may workout parenthesis without the use of parenthesis \n"+
"15. Add a function for better case examination \n"+
"\n"+
"\n"+
"31/1/2010 version 0.95 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Change '-' should have lower priority then '^' (that requires a big change in the parser, hard to implement) \n"+
"\n"+
"3. Add explanation on engineer notation for number formatting (multipliers) \n"+
"4. Change the examples that are self meaning \n"+
"5. Change Gauss elimination for interval if possible \n"+
"6. Add compiled expression for function (that requires a change in the parser to avoid terminals symbols) \n"+
"\n"+
"1. -Change the parser may work out everything (also ;) without the use of sub Expression (too work, requires the introduction of a new type of function and token) \n"+
"\n"+
"2. -Change the parser may workout parenthesis without the use of parenthesis (too work, requires the introduction of a new type of function and token) \n"+
"3. +Change create a common variable arguments abstract 'compute' at function level \n"+
"\n"+
"4. Add a function for better case examination \n"+
"5. Add complex intervals \n"+
"6. +Add extra newline on warning for the duplicated variables \n"+
"7. +Get change flow command in every place \n"+
"\n"+
"\n"+
"15/2/2010 version 0.95 \n"+
"\n"+
"\n"+
"1. Attach documents with explanation on intervals and program history \n"+
"\n"+
"2. Change '-' should have lower priority then '^' (that requires a big change in the parser, hard to implement) \n"+
"\n"+
"3. Add explanation on engineer notation for number formatting (multipliers) \n"+
"4. Change the function examples that are self meaning \n"+
"5. Change Gauss elimination for interval if possible \n"+
"6. Add compiled expression for function \n"+
"\n"+
"7. Add a function for better case examination \n"+
"8. Add complex intervals \n"+
"9. +Fix help does not work any longer (it makes a kind of recursive not ending call). \n"+
"\n"+
"\n"+
"16/3/2010 version 0.95 \n"+
"\n"+
"\n"+
"1. +Attach documents with explanation on intervals and program history \n"+
"2. +Add explanation on engineer notation for number formatting (multipliers) \n"+
"3. +Add more examples to show how the program works \n"+
"4. +Add '+-' operator for absolute tolerance \n"+
"5. +Fix self check does not work on compiled function \n"+
"\n"+
"6. Add domain check at function level \n"+
"\n"+
"7. Change the function examples that are self meaning \n"+
"8. +Add compiled expression for function \n"+
"\n"+
"9. Add complex intervals \n"+
"\n"+
"10. +Min_Max printout mode should be avoided for scalar value \n"+
"11. Add a function for better case examination \n"+
"\n"+
"12. Change Gauss elimination for interval if possible \n"+
"\n"+
"13. Change '-' should have lower priority then '^' (that requires a big change in the parser, hard to implement) \n"+
"\n"+
"21/3/2010 version 0.96 \n"+
"\n"+
"\n"+
"1. Add domain check at function level \n"+
"\n"+
"2. Add domain to functions \n"+
"\n"+
"3. Change the function examples that are self meaning \n"+
"4. Add complex intervals (expand standard intervals with getImaginaryLeft and getImaginaryRight that always return 0, then extend to complex interval which provide the complex part facility) \n"+
"\n"+
"5. Add a function for better case examination \n"+
"\n"+
"6. Change Gauss elimination for interval if possible \n"+
"\n"+
"7. Change '-' should have lower priority then '^' (that requires a big change in the parser, hard to implement) \n"+
"8. +Add deleteall to clear all the variables and functions \n"+
"9. Check findroot(('f','x')='x+2',-5_5) gives an actual interval as solution? \n"+
"10. +Add labels on ticks in the draw window \n"+
"11. +Add automatic extension to saved file name \n"+
"12. +Fix literal may become variable or function after retokenize but the opposite does not happen due to a deletion inside an expression before a retokenize \n"+
"13. +Change function labels should be overwritten \n"+
"14. +Change scale fit should be rounded to the same digit of interval \n"+
"\n"+
"15. +Change the story paragraph on about should move to history because it is too long \n"+
"16. +Fix why the compiled function ('ntcr',t)_='10k*e^(3900/(275+t)-3900/(275+25))' does not work \n"+
"\n"+
"\n"+
"21/3/2010 version 0.97 \n"+
"\n"+
"\n"+
"1. Add domain check at function level \n"+
"\n"+
"2. +Add domain to functions \n"+
"\n"+
"3. Change the function examples that are self meaning \n"+
"4. Add complex intervals (expand standard intervals with getImaginaryLeft and getImaginaryRight that always return 0, then extend to complex interval which provide the complex part facility) \n"+
"\n"+
"5. Add a function for better case examination \n"+
"\n"+
"6. Change Gauss elimination for interval if possible \n"+
"\n"+
"7. Change '-' should have lower priority then '^' (that requires a big change in the parser, hard to implement) \n"+
"8. Check findroot(('f','x')='x+2',-5_5) gives an actual interval as solution? \n"+
"9. +Fix disable the debug from compiled function \n"+
"10. +Add find root target so that instead of f(x)=0 can be solved f(x)=5 without redefining f(x) \n"+
"11. Add function interpolation definition \n"+
"12. +Fix functions applied to lists should not stop if an error is found, they should just skip it \n"+
"13. +Check strings spelling with Unix script and aspell \n"+
"\n"+
"\n"+
"\n"+
"9/5/2010 version 0.98 \n"+
"\n"+
"\n"+
"1. +Add domain check at function level \n"+
"\n"+
"2. +Change the function examples that are self meaning \n"+
"3. Add complex intervals (expand standard intervals with getImaginaryLeft and getImaginaryRight that always return 0, then extend to complex interval which provide the complex part facility) \n"+
"\n"+
"4. Add a function for better case examination \n"+
"\n"+
"5. Change Gauss elimination for interval if possible \n"+
"\n"+
"6. Change '-' should have lower priority then '^' (that requires a big change in the parser, hard to implement) \n"+
"7. +Check findroot(('f','x')='x+2',-5_5) gives an actual interval as solution? The behaviour is corret it just depends on the epsilon inside findroot \n"+
"\n"+
"8. +Add function interpolation definition \n"+
"9. +Change about screen to be more clear on console builder and engCalc builder \n"+
"\n"+
"\n"+
"\n"+
"18/5/2010 version 0.99 \n"+
"\n"+
"\n"+
"1. +Add complex intervals (expand standard intervals with getImaginaryLeft and getImaginaryRight that always return 0, then extend to complex interval which provide the complex part facility) \n"+
"\n"+
"2. Add a function for better case examination \n"+
"\n"+
"3. Change Gauss elimination for interval if possible \n"+
"\n"+
"4. Change '-' should have lower priority then '^' (that requires a big change in the parser, hard to implement) \n"+
"5. +Add global timeout and epsilon definition \n"+
"6. +Check the feature description (cumulative description command is wrong) \n"+
"7. +Add GPL license version \n"+
"\n"+
"\n"+
"28/5/2010 version 0.991 \n"+
"\n"+
"\n"+
"1. +Change basic arithmetic operations to work on complex interval \n"+
"2. Add auto loading functions class \n"+
"3. Add a function for better case examination \n"+
"\n"+
"4. Change Gauss elimination for interval if possible \n"+
"\n"+
"5. Change '-' should have lower priority then '^' (that requires a big change in the parser, hard to implement) \n"+
"\n"+
"13/6/2010 version 0.992 \n"+
"\n"+
"\n"+
"1. -Add auto loading functions class (I do not found a way to check for all the class available in the jar. It would be possible to create a little application to create a proper java file before compilation, however the effort for that task seems much higher then its benefit, therefore I discarded the idea) \n"+
"2. +Change brackets should be supported by virtual method so that also compiled function may use square brackets \n"+
"3. Check if it is possible to use function to create loop instead of standard instructions \n"+
"\n"+
"4. Add a function for better case examination \n"+
"\n"+
"5. Change Gauss elimination for interval if possible \n"+
"\n"+
"6. Change '-' should have lower priority then '^' (that requires a big change in the parser, hard to implement) \n"+
"7. +Fix edit -» suggest \n"+
"8. +Change \"fit scale\" in option \n"+
"\n"+
"9. +Add feature and other details in the help window \n"+
"10. +Add an internal function to be recalled instead of [] in order to support square also in compiled function \n"+
"11. +Add real and imaginary functions \n"+
"12. +Change extends Abs to complex number and avoid sqrt and sqr when the input number is a pure imaginary \n"+
"13. +Change use StringBuilder instead of StringBuffer which is more efficient in case of single thread \n"+
"\n"+
"14. +Change use negative argument size to indicate the minimum amount of required values: 3 means exactly 3 arguments, 0 means no argument, -5 means at least 5 argument or more \n"+
"\n"+
"\n"+
"\n"+
"27/6/2010 version 0.993 \n"+
"\n"+
"\n"+
"1. +Check if it is possible to use function to create loop instead of standard instructions \n"+
"\n"+
"2. Add a function for better case examination \n"+
"\n"+
"3. -Change Gauss elimination for interval if possible (in my opinion it is NOT possible due to a*(b+c) is a sub set of ab+ ac. \n"+
"\n"+
"4. Change '-' should have lower priority then '^' (that requires a big change in the parser because all infix functions has intrisecally higher priority than infix ones, hard to implement) \n"+
"5. +Add if function (but it is no suitable for recursion because it evaluate all its arguments) \n"+
"6. +Add returnthisif function which would not evaluate the false argument, it can be used in recursion \n"+
"\n"+
"7. +Change the name to make Engineer Calculator more recognizable: suggestion ICE (Interval Calculator for Engineers) \n"+
"8. +Add licence information \n"+
"\n"+
"9. +Add zoom dragging left mouse button on the finction panel and center into the middle of diagonal drawed \n"+
"\n"+
"10. +Add move dragging center mouse button on the finction panel \n"+
"11. +Add unzoom dragging right mouse button on the finction panel and centering and center into the middle of diagonal drawed \n"+
"\n"+
"12. +Add zoom on a specific zone with CTRL button pushed \n"+
"\n"+
"13. +Add FFT direct and inverse \n"+
"14. +Add function to plot simple list of data without the X data to play with fft results \n"+
"\n"+
"15. +Add support for recursion in function declaration (which understand its name before end of declaration) \n"+
"\n"+
"16. +Add multi variable optimization algorithm \n"+
"17. +Check parametric plot \n"+
"18. +Add complex phase function can be useful with complex result of a FFT \n"+
"\n"+
"19. +Check delete does not work \n"+
"20. +Change helpexact into exacthelp (more grammatically correct) \n"+
"\n"+
"21. +Check '::' the syntax does not seem good to get the first column and check if seno=dataplot(abs(fft(standardplot(('f',x) =' 5 *cos (2*x) + 5 *sin (35*x)', 128,0_2*pi)::1_-1))); would work with new version \n"+
"\n"+
"22. +Change improve self check to control also literal result \n"+
"23. +Add recursive function (which internally would pass itself as a parameter to avid error during the parse step) \n"+
"24. +Check if function 'not' can be updated to '!!', done and added xor as !! \n"+
"\n"+
"25. +Check compiled function ('f',x)_='2*abs x+1*abs(5*x)' gives f(1) = 11 instead of 7; try also ('f',x)_='20/abs x+10/abs(5*x)' where the operations are not symmetrical (/ vs. *) \n"+
"26. +Fix when a function is defined it is taken the global variable value instead of the parameter value. i.e.: x=2; ('f','x')='2*x'; would always produce 4 as answer \n"+
"27. +Add license to be readable by the user \n"+
"28. Fix improve istruction to be usable by people it needs more example \n"+
"29. +Change arithmetic functions on point interval may retur directly the valuse, that would increase a lot the execution speed: (done only where a significant execution speed increase is foreseen) \n"+
"\n"+
"27/6/2010 version 0.994 \n"+
"\n"+
"\n"+
"1. Add a function for better case examination \n"+
"\n"+
"2. Change '-' should have lower priority then '^' (that requires a big change in the parser because all infix functions has intrisecally higher priority than infix ones, hard to implement) \n"+
"3. Fix improve 'istruction' explanation to be usable by people it needs more examples \n"+
"4. Change update history file \n"+
"5. +Add a funtion to show the type of an expression and error exception to check the domain in the user function \n"+
"6. +Add an andup, orup functions \n"+
"7. +Add postcard or donation to about text \n"+
"8. +Change the icon in about screen, use a nice piece of ICE (isn't it cool???) \n"+
"\n"+
"9. +Change fft to work also on matrix formatted for plotting adding a nicer example \n"+
"\n"+
"10. +Fix the matrix column joiner is not useful it is mostly covered by the list builder \n"+
"11. -Check interval functions there are two instruction which behave closely (abs and module), nothing to do they make different stuff \n"+
"\n"+
"12. +Add at boot console should load and execute a configuration file (like startup.ice) if found \n"+
"13. +Add on domain error what is the type which was found \n"+
"14. +Fix help without argument does not show all the help, it may be used help ('') \n"+
"15. +Fix change file extension to ICE \n"+
"16. +Check if a variable is defined and a function with the same name is defined later the variable is not deleted, it makes a mess \n"+
"17. +Fix if a file is already present ICE should ask before overwrite it \n"+
"18. +Change let to # the option to delete the matrix format \n"+
"19. -Fix if fft input is not a 2 column matrix it should be converted like it was a list (it is more coherent to work with list) \n"+
"\n"+
"20. +Add in help the list of commands you can get from help('') \n"+
"21. +Add option to overwrite system functions renaming them with old \n"+
"22. +Add a function to load and save variables from file \n"+
"\n"+
"4/7/2010 version 0.995 \n"+
"\n"+
"\n"+
"1. +Add a function for better case examination (random and linear function scan) \n"+
"\n"+
"2. Change '-' should have lower priority then '^' (that requires a big change in the parser because all infix functions has intrisecally higher priority than infix ones, hard to implement) \n"+
"3. Fix improve 'istruction' explanation to be usable by people it needs more examples \n"+
"4. Change update history file \n"+
"5. +Change review about text there are too many repetitions \n"+
"6. +Change update the features file \n"+
"7. +Change review interpeter workflow to simplify it \n"+
"8. +Add a help feature to show all the functions in aplhabetical order \n"+
"9. +Add a cast function (not required it is possible to get left, right, real and immaginary parts) \n"+
"10. +Fix fromcolor as supported domain gives \")\" \n"+
"11. Change the boolean operations should be three (or four) values logic? \n"+
"12. +Use ICE.jar as compiled name \n"+
"\n"+
"\n"+
"9/8/2010 version 0.996 \n"+
"\n"+
"\n"+
"1. Change '-' should have lower priority then '^' (that requires a big change in the parser because all infix functions has intrisecally higher priority than infix ones, hard to implement) \n"+
"2. Fix improve 'istruction' explanation to be usable by people it needs more examples \n"+
"3. Change update history file \n"+
"4. +Change the boolean operations should be three values logic? \n"+
"5. +Add montecarlo tool \n"+
"6. +Add search tool for console instructions \n"+
"7. +Check what goes wrong on montecarlo and sinc \n"+
"8. +Delete beansbinding from library list, it is not required \n"+
"9. +Added extra functions for matrix by JAMA (only for point interval): invert, eigenvalue, eigenvector, determinant, power, division \n"+
"\n"+
"12/8/2010 version 1.0 \n"+
"\n"+
"\n"+
"1. Change '-' should have lower priority then '^' (that requires a big change in the parser because all infix functions has intrisecally higher priority than infix ones, hard to implement) \n"+
"2. +Fix improve 'istruction' explanation to be usable by people it needs more examples \n"+
"3. +Change update history file \n"