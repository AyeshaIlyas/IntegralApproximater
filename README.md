# IntegralApproximater
Terminal app that approximates an integral, assumed to be continuous over the interval of integration, using the Left Endpoint Rule, Right Endpoint Rule, Midpoint Rule, Trapezoidal Rule, and/or Simpson’s Rule.

## Classes in Project
Class `Function` - Represent a function. The constructor takes a `String` representation of a function (ex: x\*sin(x)) and evaluates the `String` to determine whether it is a valid function. It throws an `IllegalArgumentException` if the syntax is not correct. Otherwise, it will tokenize the function. The most important public service that this class provides is an evaluateAt method, which allows a client class to find f(x) where x is some number in the domain of f.
 
Class `ApproximateIntegral` - This class contains static methods leftEndpointRule, rightEndpointRule, midpointRule, trapezoidalRule, and simpsonsRule implemented both recusively and iteratively. The set of iterative methods have a boolean paramater which specifies whether text specifying the steps shoul be be printed to the console. Each method approximates a definite integral given a `Function` object, doubles a and b, and an integer n.

Class `ApproximateIntegralsApp` - The main class of the application gets the function, a, b, and n values from the user and approximate the integral based on which option the user chooses from a menu. 

Class `Test` - This class was created purely to test the capabilities of Class `Function`. Class `ApproximateIntegralsApp` contains the main program.

## Java Programming Topics Used 
- Javadoc
- Exception Handling
- Strings and Regular Expressions
- Generic Collections and Collection Methods
- Recursion

