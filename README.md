#ICE and interval instructions
By Simone Pernice

##Introduction

ICE is a simple Interval Calculator targeted for Engineers. Intervals are a way to express that a parameter is somewhere in a range whose only its extremes are defined, the exact value is not known. The missing of exact knowledge may be due to production tolerance, measurement accuracy, physical variations of an independent variable, etc. Real numbers can be considered as intervals whose extremes coincide, therefore ICE works on floating point values very well. 
 
Given an equation where some of the input values can change in a range, ICE will provide an interval as result showing all the possible solutions. That is very useful in engineering design where the projects must satisfy the requirements over the tolerance of the used components. There are tools which use a statistical approach (usually Monte Carlo analysis) to solve that kind of problem. However with interval analysis the result will contain for sure all the possible answers of the problem, although some of them may be unlikely to happen. 
 
ICE source code is written to be fast, multi platform and simple to learn and use. In order to be fast, ICE internal processes and its source code are designed to minimize the execution time using more memory when speed can be improved. The Java language was chosen to run ICE on most of the available platforms. ICE is simple to learn and use because it basically does not have a grammar: almost everything is a function (prefix or infix) which computes some list of intervals (respectively one or two) and returns one list of intervals. An ICE list contains an ordered set of intervals (also zero). In ICE the lists are flat and can represent matrix as well. 
 
This manual is quite theoretical, I tried to add as more examples as possible but I am much more keen on writing code than documentation. If you want to see all the commands available write "functionlist". To have an idea on the features available use the command "features". 
Note: in this manual all ICE functions are written between " " because it is not an ICE special character. Usually the examples are reported as "...example... == ...result...". To try that example just type "...example..." and push ENTER key. Note most of the infix operands are the same of C language (i.e.: "=" is to set a variable or function, while "==" is to check for equality). The keyboard key to press are written in upper case.
ICE internal structure 

ICE is composed by two separate pieces well integrated between them: the kernel and the console. 

The **kernel** is the piece of code which understands the strings provided by the user (called expressions), computes them and returns the answers. Note every kernel answer (unless it is the result of an assignment, i.e.: "a=3") is automatically stored in the variable called "answerxx", where "xx" is a progressive number. It is possible to reuse previous answer just typing "answerxx". Moreover in the variable "lastanswer" is always stored the last computation result. You may run the kernel alone from your command prompt, which leaves all the functionalities available, although it is inconvenient for a human being compared to the ICE console. However that feature may be useful if ICE is called by another program to be used as an external library.
Console
 
The **console** is the graphical user interface. It collects the user expressions and send them to the kernel (as strings), then it waits for the kernel answers (again as strings) and display them to the user. Moreover it provides a lot of help to the user in terms of commodities, file management, function graph, multi line statements. Here I will summarize the console most useful features: 
 
    1. The history of the executed commands is stored in the console. It is possible to go through them with CTRL + UP or DOWN arrows. The history can be saved and opened (and run again) later. The history can be showed and it is possible to delete some entry.
    2. Pushing the TAB key, the console tries to complete the current function or variable name. If more than a completion is possible, it suggests all the functions that would complete it. For example push TAB without entering any character to see all the variables and functions available.
    3. With CRTL + ENTER it is possible to enter a multi line statement. Every line will be sent separately to the kernel, which understands just a line at time. For example that is useful to execute a saved history file.
    4. Pushing ENTER the user expressions are automatically completed adding " ' " and ")" at the end of the expression if they do not match. The next will compute the expression. That is not always the correct thing to do, however it fixes the expression in most of the cases. Hint: push ENTER to close all the open brackets and then continue writing the expression.
    5. ICE console can draw diagrams. Basically the plot feature shows two and three column matrices on a Cartesian plane. For two column matrix the first one is considered the X data and the second the Y data. Three column matrix is used for surface plot, the third column represent the value of the function at the given X/Y rectangle. The plot drawing window will show all the variables which can be plot (which are all the two ones containing two and three columns matrices). It is possible to zoom in/out dragging the mouse while pushing LEFT/RIGHT button, move the Cartesian plane dragging with central button down, zoom in a rectangle dragging with CTRL+LEFT button through its diagonal. There are also more graphical options to show: a grid (optionally labeled), axes, variable names, fit the diagram in the cartesian plane, ...
    6. It is possible to stop the ICE kernel if an user defined iteration is taking too long to be completed. 
    7. If a file called "startup.ice" is found in the ICE directory it is automatically executed at start up. That feature is useful to customize ICE with the most commonly used functions, variables and settings.


###Engineer Notation

Usually scientific calculations require to manage values differing for several order of magnitudes. In engineer field it is commonly used a suffix indicating the magnitude of the value with step of 1000. The list follows with name, symbol to use in ICE, and value:
  
1. yotta        Y     1000^8             10^24     
2. zetta        Z     1000^7             10^21     
3. exa        E     1000^6             10^18     
4. peta     P     1000^5             10^15     
5. tera        T     1000^4             10^12     
6. giga        G     1000^3             10^9     
7. mega        M     1000^2             10^6     
8. kilo        k     1000^1             10^3     
9. milli        m     1000^-1     10^-3     
10. micro        u     1000^-2     10^-6     
11. nano        n     1000^-3     10^-9     
12. pico        p     1000^-4     10^-12     
13. femto        f     1000^-5     10^-15     
14. atto        a     1000^-6     10^-18     
15. zepto        z     1000^-7     10^-21     
16. yocto        y     1000^-8     10^-24
 
 
For example "12k == 12000, 12m == 0.012". Those suffices must follow the number without any space otherwise they are considered variables or functions. If you prefer, ICE understands  exponential notation as well (e.g. "12e3 == 12000, 12e-3 == 0.012"; note that it is not possible to use E which is EXA == 10^18, e.g. "12E3" gives error because after 12E, it is expected a function). By default ICE prints intervals in engineer notation and relative tolerance, however with "printmode" function it is possible to print the values in exponential notation and showing their extremes or tolerance.


###Interval

In ICE, intervals can be written as "left value _ right value", please note I do not use the words minimum and maximum for the interval extremes, the reason will be clear on Kaucher section. The actual value is something between left and right. Therefore we can define the air temperature during a summer day in Italy as "22_35" �C. The voltage at wall outlet in Europe is not exactly 230V, usually there is a 5% of tolerance, which means from 230-230/100*5 to 230+230/100*5. That interval can be written in ICE as "230%5". Sometime the tolerance is expressed in absolute terms, for instance an iron bar may have a length of 1 meter plus or minus 5 millimeter, which in ICE is written "1+-5m" meter. Note in ICE the tolerance is always symmetric around the interval average value. (Internally the intervals are stored by their extremes. Therefore to print an interval the average value and then the tolerance are computed.) Although the mean value is reported it is not more luckily to happen than other values belonging to the interval. There is not any statistical assumption in the interval theory, althought some statistical tool is provided with ICE (see "randomfunctionscan" and "montecarlo"). By default ICE outputs result in relative tolerance, however with the function "printmode" it is possible to change that as preferred.
 
There are five different domains recognized by ICE for intervals:

1. **Literal**: it is just a string without any value between a couple of quotation marks ('). If a symbol was never defined before it is considered to be a literal. There are very few functions working on literals because ICE is not designed for string manipulation. Usually literals are used to pass functions as parameters to other functions: in that case the argument will be the function name between quotation marks ('). For example in "integrate('sin',100,0_2*pi)==2" the function "sin" is passed as argument to the function "integrate" which computes its integrate on 100 points in the range between 0 and "2*pi".
2. **Point Interval**: the interval minimum and maximum value are the same (e.g. "5_5" is just the real number 5 which follows standard rules for real numbers)
3. **Real Interval**: is composed by two point intervals because the minimum and maximum values are different (e.g. "3_4, 4_3")
4. **Point Complex Interval**: is composed by two point intervals, the real and imaginary part (e.g. "3+I*5"). The upper case i "I" is the imaginary unit, so that "I*I==-1".
5. **Complex Interval**: is composed by two real intervals, the real and the imaginary part are composed by a real or point interval (e.g. "5_6+I*2_3, 2_3+I*5, 2+I*5_6"). The upper case i "I" is the imaginary unit, so that "I*I==-1".
 
The rest of this paragraph is quite technical. It is useful understand it to run ICE at the maximum performance and to write better user functions. The average user can skip it. 
 
The previous domains are stored in ICE as follows:

1. Literal as (Java) String
2. Point Interval as 64-bit IEEE 754 floating point
3. Real Interval as two point intervals
4. Point Complex and Complex Interval as two Real Intervals
 
Obviously complex interval would cover all the possible domains, however to improve execution speed (and as side effect also to reduce memory occupation) it was decided to use the above data structures to store data in ICE. That is because most of the time the user will compute on point intervals, than real and eventually complex intervals. Every time a function (with just one type of arguments) is computed its input arguments are automatically extended to the higher domain of the inputs as well as its output. For example: a point interval "*" (times) a real interval returns a real interval. If a calculus between two real intervals returns a point interval it is stored in a real interval. To go back from complex to real interval use "real" and "imaginary" functions, and to go back from real to point interval use "left" and "right" functions (see also "extremes" to get the list of left and right extremes).
 
Computations involving only point intervals are faster than real intervals, which are faster than complex intervals. Therefore the computations involving "4_4.00000001" or "4" will give close results, but the ones involving "4_4.00000001" will be slower than the ones involving "4". Note ICE correctly recognizes, stores and computes "4_4" and "4" as point intervals (e.g. "type(4_4) == 'pointinterval'" and "type (4) == 'pointinterval'").
 
Every computation on proper interval should include all the possible results. Due to floating point rounding that may not be the case on the extremes of the intervals. An extreme of an interval may erroneously drop on the wrong side of a solution point excluding it. To avoid that, it is possible to enable the outer estimation ("roundings 1") which after every computation on a proper real interval (and NOT on point interval) adds an epsilon to the higher boundary and subtract it to the lower one to make sure every solution is contemplated. It does the opposite on improper real interval (see the section on improper interval to understand why). The epsilon is the smallest value (higher than zero) in the 64-bit IEEE 754 quantization (note epsilon changes number magnitude) with That feature is disabled by default because some definition like "a-dual(a)==0" would not be true.
 
ICE can only read point intervals. Therefore when a real or complex interval is entered, the interval is actual built at the moment: "5 +- 1" is read like point interval "5", the infix function "+-", point interval "1". The function build a new real interval whose extremes are 4, 6. From an efficiency point of view it is not the best choice because it is slow compared to a parser able to read directly real interval. However that design choice gives more freedom, for example +- may be applied to a couple of list of intervals, and it makes ICE internal code simpler (to write, debug and expand).
 
When a function is evaluated a check on the input domains is performed unless it is a user defined compiled function. All the ICE functions works on point intervals, most of them on proper intervals, a lot on improper intervals, while at the moment few supports complex interval (like the arithmetic ones). Almost all functions are automatically extended to lists by ICE interpreter. For example a user defined function on a variable will work fine on a list of elements: ICE will automatically recall it for each element of the list. 
Functions extended to intervals
It should be quite clear from the instruction up to this point what means to apply a function to intervals. However I want to formalize it, to make it clears and explain interval pros and cons.
Let us define y = f( x1, x2, ...) as a continuous function which takes several real numbers x1, x2, ... and returns a real number y. You can think to f(x1, x2, ...) like the sum, division or a more complex operation. 
 
Using intervals instead of real value we will have that inty = f (intx1, intx2, ...) where the prefix "int" stands for interval. Basically giving intervals as input to the function f, it returns an interval as output. Please note that extension is made automatically by ICE. 
The property of inty is that: for every real value x1' belonging to intx1, for every real value x2'  belonging to intx2, ... , exists a real value y' belonging to  inty so that y' = f(x1', x2', ...). 
 
For example "(2_3)^2=4_9". That means for every "x" in 2_3 exists a "y" in 4_9 so that "x^2==y". Note in that case we can change the last "exists" in "for every". Unfortunately usually that is not the case, interval calculus tends to provide output result bigger then necessary. If the answer was a super-set of the interval 4_9, it would be a correct solution. "c_d" is a super-set of "a_b" if and only if "c <= a" and "b <= d". For example 3_10 a super-set of 4_9. It is a correct answer because for every "x" in 2_3 exists a "y" in 3_10 so that "x^2==y". By the way note in the latter case the opposite is not true: there are "y" in 3_10 (for example 10) which does not find "x" in 2_3 so that "x^2==y". ICE is coded to provide the smallest interval as solution on the basic operations; however when different operations are mixed, that is no more the case, due to intervals missing of correlation. Every time the same interval appears in an expression it is considered a different value, which may extend the output interval.
 
That is very useful (pros) because "inty" contains all the possible results returned by "f" applied to every real value contained in the input intervals. However (cons) in "inty" there may be some extra value which is not returned by "f" applied to any of the real value contained on the input intervals. That is very important, I want to repeat it: not all the values belonging to the result interval may be solution of the given equation.
 
The latter is the main cons of interval analysis, for example the interval from minus infinity to plus infinity is a correct solution of every possible function and input value. However in interval analysis (and in ICE) all the effort is done to get the smallest interval possible as solution. Unfortunately there are some cases explained below where it is not possible to have the smallest solution interval. For that reason there are a couple of function scan tools in ICE to check if a smaller interval may exists (see "randomfunctionscan" and "linearfunctionscan"). Every operation may grow the result interval more than required, however beginning to compute with small interval and taking care of the structure of the equations (as explained in the next paragraphs) it is possible to have a small enough result interval.
 
The rule to build the result interval when the function "f" is applied to the real intervals (X, Y, ...) is to make the union operation of the function results for every possible point interval x belonging to X, y belonging to Y, and so on. The union (symbol  "|" in ICE) between two intervals is defined as (a_b | c_d) == min(a, c) _ max(b, d). Please note if a_b and c_d are disjointed, the union will add points not belonging to a_b, neither to c_d. That is correct because as explained before the result interval may contain some extra value.
Boolean function extended to intervals 
Functions are extended to intervals simply making the union of their output values. Boolean functions like comparison (less <, less ore equal <=, equal ==, more >, more or equal >=), set (subset <<, superset >>), logic (and &&, or ||, unary not or binary xor !!) provide true or false as answer. Their extension to interval creates a new state, which in ICE is called "truefalse". It happens when some assertion may be true or false depending on the real inputs chosen inside the interval inputs. For example: "1_3 < 2_5 == truefalse" because for every "x" in 1_3 and "y" in 2_5 the result may be true or false depending on the chosen "x" and "y". In ICE that three state logic is represented with intervals (respectively 1, 0_1, 0). The respective constants  ("true", "truefalse", "false")  are also defined in ICE.
Intervals limitations
Standard Intervals do not have opposite operations. If the variable "a" is not a point interval the following equalities do not stand: "a-a == 0" and "a/a == 1". That happens because every time an interval appears in an expression it is considered uncorrelated with each other. Therefore the expression "a/a", when "a" is not a point interval, is like "a/b" where by chance "a" is equal to "b". Therefore the result of that operation is not 1 because taking a real value from the first interval and dividing it for one value of the second, we do not always get 1. Without those properties it is not possible equations. However that limitation is overcome by Kaucher directed intervals as you can see in the next section.
 
The standard calculus operations on real numbers are extended to intervals. However note that intervals behave differently from real numbers on some operations. The main difference is "a*(b+c)" is a subset (NOT always equal) of "a*b+a*c". That happens again because the two "a" in the second expression are uncorrelated. Our purpose is to have the smallest interval containing the solutions of our problem. Therefore to obtain the smallest solution every variable should appear just once in the evaluated expression. If an expression where a variable appears more than once  is entered in ICE, it will warn you because you may get an interval bigger then necessary. For example if you write the following expression "a=5_6;a*a", ICE will warn because "a" appears more than once. If "a" was a point interval that would have not rose any warning, i.e.: "a=5_5;a*a". It is not always possible to avoid the variable repetition, however in the previous case it is quite simple: "a=5_6;a^2". ICE checks only variable for repetition, i.e.: 1%5*1%5 does not rise any exception. Eventually in the cases where it is not possible to avoid repetitions, ICE has some tools for function scan ("linearfunctionscan" and "randomfunctionscan"). They will check if a smaller solution interval exists. That is a very long computational algorithm, to be executed in a reasonable amount of time just few points of the function are tested. Therefore the answer will not be correct for sure, however it usually gives a good idea of the size of the result.
 
Being the intervals defined by their extremes, the solution of a problem on n dimensions will always be an hyper-rectangle containing the solution. However in most cases that shape becomes quite bigger to contain the actual solution if that has a representation not matching with a hyper-rectangle (like a sphere or a segment, ...). For example the equations "y == x, x == 1_2" will return as interval solution the square "x == 1_2, y == 1_2"; while the real solution is actually just one of the diagonal of that square.

###Kaucher Directed Intervals

ICE uses the Kaucher directed intervals, which includes intervals with negative direction. A negative directed interval has left extreme higher then right extreme. They are called improper intervals, while standard interval are said proper. Proper intervals work like positive numbers where it is not possible to solve a problem like 5+x == 3 because  x+5 must be equal or higher than 5 (if x>=0). The same happens with addition over intervals where the tolerance can only increase. Improper interval makes possible to solve more problems although a negative directed interval as final solution has an interesting meaning (see below).
 
In the basic interval theory there is not the inverse and the opposite of an interval so that "a*(1/a)==1" and "a+(-a)==0". That is a limit in the solution of equations for example if "x + 1%5 = 2%10" then x is NOT equal to "2%10 - 1%5 (==1%25)". However that limit is overcome by directed intervals thanks to the "dual" operator. "dual" exchanges the extremes of an interval "dual(1_2)==2_1", it transforms a proper interval in an improper and vice versa. Thanks to "dual" it is possible to define the opposite of the basic arithmetic operations. Therefore the opposite of "+" is "-dual", and of "*" is "/dual". Basically the arithmetic opposite operation is obtained just computing it on the dual interval: "a-dual(a)==0" and "a/dual(a)==1". Therefore solution of the previous equation will be "x == 2%10 - dual(1%5) == 1%15". By the way note "dual(1%5) == 1%-5". 
Functions applied to directed intervals
Using directed intervals as input instead of real value we will have that dinty = f (dintx1, dintx2, ...) where the prefix "dint" stands for directed interval and f is a continuous function. 
 
The property of dinty is that: 
for every/exists x1' belonging to proper/not proper dintx1/dual(dintx1), 
for every/exists x2' belonging to proper/not proper dintx2/dual(dintx2), 
... , 
exists/for every y' belonging to proper/not proper inty/dual(inty)  
so that y' = f(x1', x2', ...). 
 
For example "(3_2)^2=9_4". That means for every "y" in 4_9 exists "x" in 2_3 so that "x^2==y". However due to interval calculus we may obtain every super set of the interval 9_4 as solution, it depends on how the equation is computed and written. "c_d" is a super-set of "a_b" if and only if "c <= a" and "b <= d". For example 8_5 is a super-set of 9_4. It is a correct answer because for every "y" in 5_8 exists "x" in 2_3 so that "x^2==y". Note in the latter case the opposite is not true: there are "x" in 2_3 (for example 3) which does not have "y" in 5_8 so that "x^2==y".
 
Unfortunately (cons) inty may not contain all the possible results returned by "f" applied to every real value contained in the input intervals. However (pros) in inty there are only values returned by f applied to real number chosen from input interval. No extra value is present like happens with proper intervals.
 
Think to an equation whose result is the area inside a circumference with radius 1. The equation my be "x^2+y^2==0_1". A square circumscribing the circumference (for example x == -1_1 and y == -1_1) would be a correct proper solution. A square inscribed in the circumference (for example x == 0.7_-0.7, y == 0.7_-0.7) would be a correct improper solution.
 
When the function f is applied to the real intervals (X, Y, ...) the result interval is the union operation on the function results scanning every proper variable through their intervals and the intersection scanning every improper variable through their intervals. The intersection (symbol "&" in ICE) between two intervals is defined as (a_b & c_d) == max(a, c) _ min(b, d). Please note if a_b and c_d are disjointed, the intersection will be a improper interval.

###Lists

In ICE every (input and output) value is contained in a (flat) list. For example if you enter the expression "4+5", ICE answers with "9". You are actually asking to the infix function "+" to operate on the left input list "(4)" and on the right input list "(5)", therefore "+" returns the list composed by the single element "(9)", which for clarity is represented by ICE without brackets.
 
To write a list composed by more than one element, just separate them by commas and usually collect between round brackets. The brackets are not strictly required, they are usually used because the priority of "," is very low: "4+5,6,7==9,6,7" while "4+(5,6,7) == (9,10,11)". The ICE lists are flat, it is not possible to nest a list into a list, i.e.: "a=(1,2,3);b=(1,2,a,3)" the answer is "b = (1,2,1,2,3,3)". Most of the functions operation are automatically extended to list. For instance "4 + (5,6,7) == (9,10,11)" because ICE automatically extends "4" to "(4,4,4)", then it applies the function "+" element by element. Therefore loops should never be used to apply the same function to the elements of a list, that is done intrinsically, quickly e simpler by ICE.
 
A list may be named. That happens when the list is assigned to a variable: "a=(1,2)" returns the list "(1,2)" with the name "a". Note ICE do not store "(1,2)" in an answer variable because it has already its variable. That feature let the user define several times the same variable without need to use " ' ". For example "a=(1,2)" and "a=(3,4)" is correct, although in the second expression it is expected a literal on the left of "=". However a is the list "(1,2)" but also the literal "a".
 
There are several ways to create the most useful list. The most common are: "linlist (numberpoints, start_end)" to create a list of the given "numberpoints" from "start" to "end" (e.g. "linlist (9,1_5) == (1,1.5,2,2.5,3,3.5,4,4.5,5)"). Other functions do the same on integer: "start .. end" (optionally " .. step") to create an integer list between start and end (e.g. "2 .. 5 == (2, 3, 4, 5)","2 .. 5 .. 2 == (2,  4)").
 
It is possible to get elements from lists with ":" operator which requires the indexes to get from the list (the first element has index 0). For instance: "(1,2,3,4):0 == 1", "(1,2,3,4):(0,0) == (1,1), (1,2,3,4):(1..3) == (2,3,4)". "size" gives the number of elements of a list, which means "size - 1" is the last element. It is not possible to modify an element of a list, instead you have to build a new list with the required changes. Do not care of (memory used by) list wasting, the garbage collector will later free all the memory space of the data structures not used any longer.

###Matrices 

ICE supports matrices. Matrices are lists split in rows according to the given number of columns. The command to set the number of columns of a list is "#". For instance "(1,2,3,4)#2" is represented like:
"[1, 2]
[3, 4]"
To insert a matrix it is also possible to use the notation with square brackets [1, 2],[3, 4]. 
Use "#0" to transform a matrix back to a list.
Most of the functions do not care if the input is a matrix or a list, they behave in the same manner. Therefore "2*(1,2,3,4)" gives the same answer than "2*([1, 2],[3, 4])" which is the correct answer for a matrix. Moreover there are extra functions for matrix like: "rowsize" provides the number of rows, "columnsize" provides the number of columns, "**" row by column product, "eigenvalue" and "eigenvector" to compute the characteristic parameters of the matrix, "determinant" and other commodities to create identity and diagonal matrix, function to "transpose", etc... Unfortunately a lot of algorithms developed for matrices can only be applied to point intervals.

##Internal Functions

ICE is almost grammar less, it is basically a collection of functions applied to lists which give a list as answer. There are two kind of functions: 
 
1. prefix like "sin" and "cos" which have just an argument "(list)" at their right
2. infix like "*" and "/" which have left and right arguments "(list)"
 
The prefix functions have the maximum priority so they are evaluated as soon as possible. Then the infix functions are evaluated according to their priority and their associativity (toward right or left). Running the function "help" on an infix function provides the priority level and associativity direction. For example "+" has a lower priority than "*", which has lower priority than "^". Therefore "2+3*2^3 == 2+3*8 == 2+24 == 26". Being the prefix function with the highest priority we have that "-2^2 == (-2)^2 == 4" and not "-4". Most of the functions are left associative, one of the few exception is "^" which associates toward right (e.g. "20/2/5 == 10/5 == 2" and not "20/2/5==20/0.4==50" while "2^3^2 == 2^9 == 512" and not "8^2 == 64").
 
In ICE the functions do not require brackets to collect their argument. However since prefix functions have the highest priority round brackets are required when a function needs a list of arguments longer than 1 element. For instance: "cos 0, 0 == (cos 0), 0 == (1, 0)" while "cos (0, 0) == (cos 0, cos 0) == (1, 1)". In the previous example you see how ICE automatically extends a prefix function to the intervals of a list. That happens also to user defined function. One more example: "standardplot 'sin',10,0_2*pi" ("0_2*pi == (0_2)*pi == (0*pi)_(2*pi) == 0_(2*pi)") rises an error because "standardplot" wants a three argument list while just one is provided, to avoid the error join the elements in a list: "standardplot ('sin',10,0_2*pi )". Last example "timems" does not need arguments. It provides the current time in milliseconds. Usually it is used to evaluate the execution time of a command. Therefore "timems" is correct, however "timems - 5" rise an error because ICE tries to compute "timems" on the list "(-5)" to avoid that just write "timems () - 5".

How get help on ICE internal functions
The most useful functions to know how ICE works are: 

1. "features" provides a summary of the main features with relative functions available in ICE. 
2. "functionlist" gives the help of all the functions available in ICE. 
3. "help ('some string')" provides the help to all the functions beginning with the given chars.
4. "exacthelp" is like help but returns just the exact matching function if found.

##Internal Variables

Variables are just a special case of prefix function: they are prefix function with 0 arguments. They give back their own value. A lot of constants are stored in ICE: 

1. Mathematical: point interval like "e" or "pi"
2. Logic: point interval like "true" and "false" and real interval like "truefalse".
3. Physical: real interval starting with the word "constant" (e.g.: "constantprotonmass", "constantfaraday"), they take into account the accuracy with which those constants are known.
4. Conversion coefficients: point interval starting with the word "convert" (e.g: "convertounce2kilogram", "convertdegree2radiant") . For example to convert 5 ounces to kilogram just write "5 * convertounce2kilogram".
Hint: write "convert" or "constant" and push TAB key to see all the conversion coefficients and constants available.

##User defined variables and functions

Although ICE has several hundreds of internal functions something will miss for sure. That is why it was added the opportunity for the user to create its own variables and (prefix) functions. 

1. Name: function and variable names can contains only characters (from a to z and from A to Z) and numbers (from 0 to 9). They must begin by a character. Only infix functions and prefix functions defined by infix functions can contain special characters, however the user cannot define them.
2. Definition: to define a variable write its name "=" its value (e.g.: "a = 4, b = 7"). ICE will expect a literal on the left of the "=" sign. If the variable is already defined it is overwritten unless it is an internal ICE variable. To define a function the syntax is similar "(function name, parameter1, parameter2, ...) = 'function algorithm'". Note that if the "function algorithm" is a list the function will return a list (several values). For example the function " ('circle', t) = 'sin t, cos t' ", whose examples are " circle(0) == (0, 1) ", " circle(pi/2) == (1, 0) ". The "circle" function may be used in a parametric plot to display a circle. If in the "function algorithm" it is used some variable which is not defined in the list of parameters its value is got as constant. Therefore if it later changes, the parameter will keep the value at the definition time (e.g.: "a=2; (f,x)='2*a*x'; f(2),a=3,f(2) == 8,3,8"). If a function was already defined it is not possible to redefine it without writing its name between " ' ", because otherwise the ICE parser would try to execute the function. For complex algorithms there are several flow control instructions that can be used. The functions are split in tokens when the function is stored in ICE. At the execution of the function the interpreter will execute the tokens  (which is much faster being the tokenize step (the longest) already performed).
3. Recursion: the only exception where a parameter can be used before its definition is the self function name. That was done to implement recursion. For instance to implement factorial a possible (not efficient) code would be: "(factorial, x) = '1, returnthisif (x <= 1); x*factorial(x-1)'". To implement that, ICE stealthily pass the function itself as an argument to the function. Therefore looking at the help of an user defined function, among the parameters it is automatically added the function name. For example define a function "(f, x, y) = 'x + y' " and then look at its help " exacthelp('f') ", you will find: "...f(x,y,f)=x+y..." the function name "f" is automatically passed as an argument. Although "f" is not a recursive function, the function name is always added on the parameter list. Thanks to that it is possible to define recursive function.
4. Type control: sometime the functions have restriction on their arguments. To check the input use the function 'type' which returns a literal with the name of the interval which can be checked by the user with "==" function. 
5. Error: every time a wrong operation is performed an error is generated by ICE. The user functions can do the same thanks to the "error" function which returns the given literal as error message, for example: "(factorial, x)='skipnextif (x >= 1); error('The factorial argument must be > 0); 1,returnthisif(x<=1); x*factorial(x-1)' "
6. Function call: sometime a function gets another function as argument (in that case the function name is provided between " ' "). In that case take the function name as literal argument and use "callfunction" to recall it. For example: " ('d',f,x1,x2) = '( callfunction( f, x1) - callfunction(f, x2)) / (x2 - x1) ", please note deviate is already defined.
7. Argument number control: if a function is expected as argument it is possible to check its number of expected arguments with "argumentsize". It returns the number of expected argument, 0 for variable and other stuff.
8. Sometime a predefined function or variable may not work as required. In that case it is possible to redefine it with the function "overwriteembeddedfunction 1", which disable the internal function protection. When an internal function is overwritten it is saved with "old" prefix. For example: "overwriteembeddedfunction 1;delete('sin');('sin',x)=(oldsin(x)/x)"
 
There are also two kinds of user functions which may be useful:

1. Compiled Function: are defined exactly like standard function but with "_=" operator. They do NOT support flow control instructions, sub expression ";", and recursion. At its definition the compiled function is split in tokens, and at its first evaluation the interpreter flow is transformed in RPN (Reverse Polish Notation) code and optimized. The optimization code is very simply, it works better when the variables are at the end of the expression (i.e.: " '2*3+x" code is faster than "x+2*3"). Every next evaluation will use the RPN code which is much faster since does not require any internal recursion through the tokens. Moreover the domain of the function arguments and other checks are not performed, which means several internal errors may be generated. Compiled function runs at least 20% faster the standard functions. However there are a lot of draw backs as highlighted, therefore it is preferable to avoid their use when speed is not critical.
2. Interval Function: a two column matrix like the one used for plot functions is used to define the Y values that the function should provide for every X value. For example:" 'f' := ((1,2,3,4)#1,(4,8,4,0)#1)", which means that f(x) is 4 for 1 < x < 2, 8 for 2 < x < 3, 4 for 3 < x < 4, 0 for x > 4. For every x lower than 1 is provided 4 and higher than 4 return 0. Since all the ICE function returning a plottable matrix use that format (like plot functions, "fft", "montecarlo", ...), it is possible to transform their result in function. Those function can be later used for other purpose ("intergate", "derivate", ...).


##Grammar

ICE is basically grammar less. Almost everything is a function (prefix or infix) or a list of intervals (may be empty) with the following exceptions: brackets "()[]" and " ; ". Every input is called expression. An expression is a list (which may be empty) to be evaluated. A list is a flat ordered set of elements separated by ",". Sometime it is useful ask more questions in the same expression: that can be done writing the expressions (questions) between commas, ICE will return a list as answer. For example: "sin(pi/2), 5*4 == 1, 20". It is not possible to define and use a function or a variable in the same input list because at the parsing time every occurrence wold be considered a literal. For example: "a=2,a+2" is wrong because the second occurrence of "a" is a literal and not the variable "2". In that case it is possible to split an expression in a set of sub expressions using " ; ". It triggers a new parsing of the input expression looking for all the literals become variables or functions and viceversa. For example the previous example become correct using ";": "a=2;a+2". The list build up to " ; " is discarded therefore in that case usually the partial result is stored in a variable. I.e.: "a=2; 2+2*a == 6", without the first "a=2" the job performed up to the ";" would have been lost. That is also used if the output of a function is very long (like the result of a plot) and the user does not want to see it ("a=1..100;" it would have been possible to write just "1..100" but the output would have been very long) or to change the execution flow through the sub expressions (i.e.: "i=0; i=i+1, repeatthiswhile (i<10);i"). The execution flow can be changed only inside an expression changing the sub expression execution order. 
Please see below some example:
 
Expression: 1+2,3+4
Answer: 3,7
 
Expression: 1+2,3+4;5*5
Answer: 25
Note: 1+2,3+4 was computed but the result is lost
 
Expression: a = 1 .. 100;
Answer: No answer returned
Note: the variable "a" contains the result.
 
Expression: a
Answer: (1, 2, 3, ............, 100)
 
Expression: a = 0, a = a +1
Answer: Error...
The error is given because "+" gets the literal "a" as input because when the string was split in tokens "a" was not defined yet. However after every ";" the strings are re-examined for literals become functions and viceversa.
 
Expression: a = 0; a = a + 1
Answer: a = 1
 
ICE parser works on a couple of steps:

1. Reads all the input strings to split them in tokens, which means every number becomes a point interval (please note that only point interval are directly recognized by the parser), every function and variable becomes a pointer to the actual internal function and value. If a symbol was never found before it is considered to be a literal.
2. Executes the required instructions following the functions precedence, brackets and ";" which is the end of a sub expression. 
 
The main draw back of this design is that it is not possible to define (but also redefine, delete) a variable (or function) and use it in the same sub expression. That is because while the expression was split in tokens the variable (or function) was not yet defined, therefore every occurrence was considered a literal. However that solution was chosen to improve the speed of user defined function execution which runs just the step 2 (by the way step 1 is the most time consuming). That is very nice because the aim of ICE is to run function over long list of values. By the way the user can create compiled functions which drastically reduce the step 2 execution time.

1. Brackets:" ( ) " are used to prioritize expression (like collecting the elements of a list), for instance: "sin 0, pi/2 == (0, pi/2)" while "sin (0, pi/2) == (0, 1)". The square brackets " [ ] " are used to define a matrix row, the ',' will automatically join the rows to build the matrix.
2. End of sub expression " ; ". After every " ; " the input expression is again converted to token because some literal may become a variable or function (or may be defined or deleted) then the execution restarts from the next sub-expression if any. This procedure is much faster than the first tokenize because the expression is already split in token and only the one which may have changed are checked.
3. Flow control: usually the sub expressions inside an expression are executed from left to right. However with flow control functions it is possible to change the order in which the sub expressions composing the expression are executed (for example to make loops). The flow control instructions answer with special intervals that will be recognized by the interpret to understand how to go on with the expression execution. The interpreter removes those intervals from the answer. Loops should never be used to apply the same functions to different argument because that is done intrinsically and faster by ICE. For example: the expression "i=1,j=0; j=j+i, i=i+1, repeatthiswhile(i <= 10); j" gives "j=55", however it is much faster to ask "sumup (0 .. 10)". "10^(1,2,3,4)" gives "(10, 100, 1000, 10000)" it may be replace by a loop but that would be much slower and cumbersome to write and read.

##Examples 

###Current on a Resistor
If you want to know how much current a 3.3V generator sources across a 10kohm resistor just write "3.3/10k" (note the answer is in "u" (micro-Ampere 10^-6)). In the real world your generator would have a tolerance (3% for example) as well as your resistor (5%), the actual current will be "3.3%3/10k%5". If you need to use the current later just write "i=3.3%3/10k%5" or use "lastAnswer" to get the last value or "answer???" where instead of ??? write the answer number you need to reuse.
 
###Body Mass Index
The BMI is the body mass index. It is the weight divided by the square of the height. While it is simple to know its own height with accuracy the weight changes a lot during a day. For example in may case the height is 1.72 meters and the weight is in the range between 69 and 71 Kg. Therefore my BMI is "extremes (69_71/1.72^2) == (23.3, 23.9)". It means any BMI is between 23.3 and 23.9. By the way is quite good. How you can see it is quite forward to get the range of the calculation in just one step.
 
###Painting
Let us say you want to repaint the walls of your living room completely messed up by your children. You need to compute how many paint cans you need to buy. The equation is quite simple (note in the equation I deliberately avoided to add twice the room width and length to avoid the risk to increase the solution range):
"paintCans = 2 * (roomWidth + roomLength) * roomHeight / (paintLitersPerCan * paintEfficiency)"
where: 
"paintEfficiency" is how many square meters of surface can be painted with a liter of paint. The problem here is that usually we do not have along enough meter to measure the room width and length, it is much simpler to count the number of steps to go through it (1step is about 1m, let us say from 0.9 to 1.1m). More over the paint provider usually declares a paint efficiency range.
Let us put below the data:

"roomWidth" = 6 (from 5.4 m to 6.6m)

"stepsroomLength" = 4 (from 3.6 m to 4.4m)

"roomHeight" = 3 (it is assumed correct because its tolerance is much smaller than the other pieces)

"meterspaintEciency" = from 0.7 to 1.3 square meters per liter (1liter per square meter in average)

"paintLitersPerCan" = 40 (it is assumed correct because its tolerance is 
much smaller than the other pieces)

To compute the average result just put average values in the initial equation. We get: "paintCans = 2*(6+4)*3/(40*1) ==1.5" paint cans, which means two unless you are able to buy just half of a can.

Are you satisfied with that result? I am not. What if I have underestimated something? As every engineer would check what happens in the worst case, which means 1. 1m step and efficiency of just 0.8. Again just substituting those values in the initial equation, we get: "paintCans = 2 * (6.6+4.4)*3/(40*0.7) = 2.36". That is really interesting: in the worst case I would miss 0.36 cans, it makes sense to buy three cans to avoid to go back to the hardware shop to buy one more (in the worst case).
More happy with the result now? I am not completely satisfied, I am asking my self: what if in the best case I need just 1 can? In that case probably I need more accurate data because the result range would be too wide. Eventually we get: "paintCans = 2*(5.4+3.6)*3/(40*1.3) = 1.04".Which means two cans.
I am satisfied, I have to buy at least two cans, but I may need one more. 
Here how to make all those calculation in just a couple of steps on ICE: " 2 * (5.4_6.6+3.6_4.4) * 3/(40*0.7_1.3) == 1.7%39", "extremes(lastanswer) ==  (1.04, 2.36)".