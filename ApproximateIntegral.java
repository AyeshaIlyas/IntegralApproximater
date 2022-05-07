// ApproximateIntegral.java

package integrals;

/**
 * ApproximateIntegral provides static methods to estimate integrals using 
 * the Left Endpoint Rule, Right Endpoint Rule, Midpoint Rule, Trapezoidal Rule, and/or Simpson’s Rule.
 * A set of recursive and iterative methods are provided. The interval of integration is assumed to
 * be continuous.
 * 
 * @author Ayesha Ilyas 
 * @since 3/24/22
 * <p>Updated 4/3/22</p>
 * <p>Updated 4/11/22</p>
 * @see integrals.Function
 */

public class ApproximateIntegral {

	private static final String note = 
			"NOTE: Numbers are rounded to 4 decimal places for cleaner output," +
					System.lineSeparator() + 
					"      but the underlying calculation remains precise.";


	// * * * * * * * * * * * Iterative functions * * * * * * * * * * // 
	/**
	 * Iteratively approximates an integral using the Left Endpoint Rule. 
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @param showSteps a <code>boolean</code> that determines whether the steps used to compute
	 * the integral should be shown. If <code>showSteps</code> if true, the steps will be shown, otherwise
	 * the steps will not be shown.
	 * @return a <code>double</code> approximation of the integral using the Left Endpoint Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if an endpoint is not in the domain of the function
	 */
	public static double leftEndpointRule(Function function, double a, double b, int n, boolean showSteps) {
		// throw exception if a >= b and n is negative
		if (!(a < b && n > 0)) 
			throw new IllegalArgumentException("Left Endpoint Rule: a must be less than b and n must be a positive integer not equal to zero.");

		// calculate delta x
		double deltaX = (b - a) / n;

		// calculate endpoints
		double[] values = new double[n]; 
		for (int i = 0; i < n; i++) {
			values[i] = a;
			a += deltaX;
		}

		// output function, deltaX, and endpoints
		if (showSteps)
			System.out.printf("- - - Left Endpoint Rule - - -%nf(x) = %s%nΔx = %.4f%nEndpoints: %s%n", 
					function, deltaX, getString(values, ", "));
		// find function values at endpoints and add to result
		double result = 0;
		for (int i = 0; i < values.length; i++) {
			values[i] = function.evaluateAt(values[i], showSteps); // may throw IllegalArgumentException if function is not defines at values[i]
			result += values[i];
		}
		result = deltaX * result; // calculate final result
		// output string showing summation of f(endpoint) all times deltaX and result
		if (showSteps) 
			System.out.printf("%n%.4f*%s = %.4f%n%n%s%n%n", deltaX, getString(values, " + "), result, note);
		return result;
	}

	/**
	 * Iteratively approximates an integral using the Right Endpoint Rule. 
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @param showSteps a <code>boolean</code> that determines whether the steps used to compute
	 * the integral should be shown. If <code>showSteps</code> if true, the steps will be shown, otherwise
	 * the steps will not be shown.
	 * @return a <code>double</code> approximation of the integral using the Right Endpoint Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if an endpoint is not in the domain of the function
	 * 
	 */
	public static double rightEndpointRule(Function function, double a, double b, int n, boolean showSteps) {
		// throw exception if a >= b and n is negative
		if (!(a < b && n > 0)) {
			throw new IllegalArgumentException("Right Endpoint Rule: a must be less than b and n must be a positive integer not equal to zero.");
		}

		// calculate delta x
		double deltaX = (b - a) / n;

		// calculate endpoints
		double[] values = new double[n]; 
		for (int i = 0; i < n; i++) {
			a += deltaX;
			values[i] = a;
		}

		// output function, deltaX, and endpoints
		if (showSteps)
			System.out.printf("- - - Right Endpoint Rule - - -%nf(x) = %s%nΔx = %.4f%nEndpoints: %s%n", 
					function, deltaX, getString(values, ", "));

		// find function values at endpoints and add to result
		double result = 0;
		for (int i = 0; i < values.length; i++) {
			values[i] = function.evaluateAt(values[i], showSteps); // may throw IllegalArgumentException if function is not defines at endpoints[i]
			result += values[i];
		}
		result = deltaX * result; // calculate final result
		// output string showing summation of f(endpoint) all times deltaX and result
		if (showSteps) 
			System.out.printf("%n%.4f*%s = %.4f%n%n%s%n%n", deltaX, getString(values, " + "), result, note);
		return result;
	}

	/**
	 * Iteratively approximates an integral using the Midpoint Rule. 
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @param showSteps a <code>boolean</code> that determines whether the steps used to compute
	 * the integral should be shown. If <code>showSteps</code> if true, the steps will be shown, otherwise
	 * the steps will not be shown.
	 * @return a <code>double</code> approximation of the integral using the Left Endpoint Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if a midpoint is not in the domain of the function
	 * 
	 */
	public static double midpointRule(Function function, double a, double b, int n, boolean showSteps) {
		// throw exception if a >= b and n is negative
		if (!(a < b && n > 0)) 
			throw new IllegalArgumentException("Midpoint Rule: a must be less than b and n must be a positive integer not equal to zero.");

		// calculate delta x
		double deltaX = (b - a) / n;

		// calculate midpoints
		double[] values = new double[n]; 
		double left = a;
		double right = a + deltaX;
		for (int i = 0; i < n; i++) {
			// calculate midpoint 
			values[i] = (left + right) / 2;
			left += deltaX;
			right += deltaX;
		}

		// output function, deltaX, and midpoints
		if (showSteps)
			System.out.printf("- - - Midpoint Rule - - -%nf(x) = %s%nΔx = %.4f%nMidpoints: %s%n", 
					function, deltaX, getString(values, ", "));

		// find function values at midpoints and add to result
		double result = 0;
		for (int i = 0; i < values.length; i++) {
			values[i] = function.evaluateAt(values[i], showSteps); // may throw IllegalArgumentException if function is not defines at midpoints[i]
			result += values[i];
		}
		result = deltaX * result; // calculate final result
		// output string showing summation of f(midpoint) all times deltaX and result
		if (showSteps) 
			System.out.printf("%n%.4f*%s = %.4f%n%n%s%n%n", deltaX, getString(values, " + "), result, note);
		return result;
	}

	/**
	 * Iteratively approximates an integral using the Trapezoidal Rule. 
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @param showSteps a <code>boolean</code> that determines whether the steps used to compute
	 * the integral should be shown. If <code>showSteps</code> if true, the steps will be shown, otherwise
	 * the steps will not be shown.
	 * @return a <code>double</code> approximation of the integral using the Trapezoidal Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if an endpoint is not in the domain of the function
	 * 
	 */
	public static double trapezoidalRule(Function function, double a, double b, int n, boolean showSteps) {
		// throw exception if a >= b and n is negative
		if (!(a < b && n > 0)) 
			throw new IllegalArgumentException("Trapezoidal Rule: a must be less than b and n must be a positive integer not equal to zero.");

		// calculate delta x
		double deltaX = (b - a) / n;

		// calculate endpoints
		double[] endpoints = new double[n+1]; 
		for (int i = 0; i < endpoints.length; i++) {
			endpoints[i] = a;
			a += deltaX;
		}

		// output function, deltaX, and endpoints
		if (showSteps)
			System.out.printf("- - - Trapezoidal Rule - - -%nf(x) = %s%nΔx = %.4f%nEndpoints: %s%n", 
					function, deltaX, getString(endpoints, ", "));

		// find function values at endpoints and add to result
		double result = 0;
		StringBuilder valuesString = new StringBuilder(); // used to build string of steps if showSteps is true
		// calculate f(x) at x = a
		result += function.evaluateAt(endpoints[0], showSteps); 
		if (showSteps) valuesString.append(String.format("%.4f", result));
		// calculate 2 * f(x) for endpoints not equal to the first and last endpoints
		for (int i = 1; i < endpoints.length - 1; i++) {
			double value =  function.evaluateAt(endpoints[i], showSteps); // may throw IllegalArgumentException if function is not defines at endpoints[i]
			if (showSteps) {
				valuesString.append(String.format(" + 2*%s%.4f%s", value < 0 ? "(" : "", value,  value < 0 ? ")" : ""));
				if ((i + 1) % 10 == 0)
					valuesString.append(System.lineSeparator());
			}
			result += (2 * value);
		}
		// calculate f(x) at x = b
		double lastValue = function.evaluateAt(endpoints[endpoints.length - 1], showSteps);
		result += lastValue;
		if (showSteps) valuesString.append(String.format(" + %.4f", lastValue));
		result = (deltaX / 2) * result; // calculate final result
		// output string showing summation of f(endpoint) all times deltaX and result
		if (showSteps) 
			System.out.printf("%n(%.4f/2)*[%s] = %.4f%n%n%s%n%n", deltaX, valuesString, result, note);
		return result;
	}

	/**
	 * Iteratively approximates an integral using Simpson's Rule. 
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @param showSteps a <code>boolean</code> that determines whether the steps used to compute
	 * the integral should be shown. If <code>showSteps</code> if true, the steps will be shown, otherwise
	 * the steps will not be shown.
	 * @return a <code>double</code> approximation of the integral using Simpson's Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if an endpoint is not in the domain of the function
	 * 
	 */
	public static double simpsonsRule(Function function, double a, double b, int n, boolean showSteps) {
		// throw exception if a >= b and n is negative and/or odd
		if (!(a < b && n > 0 && n % 2 == 0)) 
			throw new IllegalArgumentException("Simpson's Rule: a must be less than b and n must be a positive even integer not equal to zero.");

		// calculate delta x
		double deltaX = (b - a) / n;

		// calculate endpoints
		double[] endpoints = new double[n+1]; 
		for (int i = 0; i < endpoints.length; i++) {
			// calculate midpoint 
			endpoints[i] = a;
			a += deltaX;
		}

		// output function, deltaX, and endpoints
		if (showSteps)
			System.out.printf("- - - Simpson's Rule - - -%nf(x) = %s%nΔx = %.4f%nEndpoints: %s%n", 
					function, deltaX, getString(endpoints, ", "));

		// find function values at endpoints and add to result
		double result = 0;
		StringBuilder valuesString = new StringBuilder(); // used to build string of steps if showSteps is true
		// calculate f(x) at x = a
		result += function.evaluateAt(endpoints[0], showSteps); 
		if (showSteps) valuesString.append(String.format("%.4f", result));

		// calculate 2 * f(x) or 4 * f(x) for endpoints not equal to the first and last endpoints
		for (int i = 1; i < endpoints.length - 1; i++) {
			double value =  function.evaluateAt(endpoints[i], showSteps); // may throw IllegalArgumentException if function is not defines at endpoints[i]
			int multiplier = i % 2 != 0 ? 4 : 2; // choose a coefficient for the term (4 or 2)
			if (showSteps) {
				valuesString.append(String.format(" + %d*%s%.4f%s", multiplier, value < 0 ? "(" : "", value,  value < 0 ? ")" : ""));
				if ((i + 1) % 10 == 0)
					valuesString.append(System.lineSeparator());
			}
			result += (multiplier * value);
		}
		// calculate f(x) at x = b
		double lastValue = function.evaluateAt(endpoints[endpoints.length - 1], showSteps);
		result += lastValue;
		if (showSteps) valuesString.append(String.format(" + %.4f", lastValue));
		result = (deltaX / 3.0) * result; // calculate final result
		// output string showing summation of f(endpoint) all times deltaX and result
		if (showSteps) 
			System.out.printf("%n(%.4f/3)*[%s] = %.4f%n%n%s%n%n", deltaX, valuesString, result, note);
		return result;
	}


	// * * * * * * * * * * * Recursive functions * * * * * * * * * * // 

	/**
	 * Recursively approximates a definite integral using the Left Endpoint Rule.
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @return a <code>double</code> approximation of the integral using the Left Endpoint Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if an endpoint is not in the domain of the function
	 * 
	 */
	public static double leftEndpointRule(Function function, double a, double b, int n) {
		// throw exception if a >= b and n is negative
		if (!(a < b && n > 0)) 
			throw new IllegalArgumentException("Left Endpoint Rule: a must be less than b and n must be a positive integer not equal to zero.");

		// calculate delta x
		double deltaX = (b - a) / n;
		// base case
		if (n == 1) 
			return function.evaluateAt(a) * deltaX;
		return function.evaluateAt(a) * deltaX + leftEndpointRule(function, a + deltaX, b, n - 1);
	}

	/**
	 * Recursively approximates a definite integral using the Right Endpoint Rule.
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @return a <code>double</code> approximation of the integral using the Right Endpoint Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if an endpoint is not in the domain of the function
	 * 
	 */
	public static double rightEndpointRule(Function function, double a, double b, int n) {
		// throw exception if a >= b and n is negative
		if (!(a < b && n > 0)) 
			throw new IllegalArgumentException("Right Endpoint Rule: a must be less than b and n must be a positive integer not equal to zero.");

		// calculate delta x
		double deltaX = (b - a) / n;
		// base case
		if (n == 1) 
			return function.evaluateAt(a + deltaX) * deltaX;
		return function.evaluateAt(a + deltaX) * deltaX + rightEndpointRule(function, a + deltaX, b, n - 1);
	}

	/**
	 * Recursively approximates a definite integral using the Midpoint Rule.
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @return a <code>double</code> approximation of the integral using the Midpoint Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if a midpoint is not in the domain of the function
	 * 
	 */
	public static double midpointRule(Function function, double a, double b, int n) {
		// throw exception if a >= b and n is negative
		if (!(a < b && n > 0)) 
			throw new IllegalArgumentException("Midpoint Rule: a must be less than b and n must be a positive integer not equal to zero.");

		// calculate delta x
		double deltaX = (b - a) / n;
		// calculate midpoint 
		double midpoint = (a + (a + deltaX)) / 2;

		// base case
		if (n == 1) 
			return function.evaluateAt(midpoint) * deltaX;

		return function.evaluateAt(midpoint) * deltaX + midpointRule(function, a + deltaX, b, n - 1);
	}

	/**
	 * Recursively approximates a definite integral using the Trapezoidal Rule.
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @return a <code>double</code> approximation of the integral using the Trapezoidal Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if an endpoint is not in the domain of the function
	 * 
	 */
	public static double trapezoidalRule(Function function, double a, double b, int n) {
		// throw exception if a >= b and n is negative
		if (!(a < b && n > 0)) 
			throw new IllegalArgumentException("Trapezoidal Rule: a must be less than b and n must be a positive integer not equal to zero.");

		// calculate delta x
		double deltaX = (b - a) / n;

		// base case
		if (n == 1) 
			// calculate the area of one trapezoid
			return 0.5 * deltaX * (function.evaluateAt(a) + function.evaluateAt(a + deltaX));
		return 0.5 * deltaX * (function.evaluateAt(a) + function.evaluateAt(a + deltaX)) + 
				trapezoidalRule(function, a + deltaX, b, n - 1);
	}

	/**
	 * Recursively approximates a definite integral using Simpson's Rule.
	 * 
	 * @param function a <code>Function</code> object
	 * @param a a <code>double</code> representing the lower bound of the interval of integration. a should 
	 * be strictly less than b.
	 * @param b a <code>double</code> representing the upper bound of the interval of integration. b should 
	 * be strictly greater than a.
	 * @param n an <code>int</code> specifying how many subintervals to use in the approximation. n should be 
	 * a positive integer greater than 0.
	 * @return a <code>double</code> approximation of the integral using Simpson's Rule
	 * @throws IllegalArgumentException if a is not less then b and/or n is not greater than 0
	 * @throws ArithmeticException if an endpoint is not in the domain of the function
	 * 
	 */
	public static double simpsonsRule(Function function, double a, double b, double n) {
		// throw exception if a >= b and n is negative and/or odd
		if (!(a < b && n > 0 && n % 2 == 0)) 
			throw new IllegalArgumentException("Simpson's Rule: a must be less than b and n must be a positive even integer not equal to zero.");

		// calculate delta x
		double deltaX = (b - a) / n;

		// base case
		if (n == 2) 
			// calculate area of two regions bounded by a quadratic function
			return (deltaX / 3.0) * (function.evaluateAt(a) + 4 * function.evaluateAt(a + deltaX) + function.evaluateAt(a + deltaX + deltaX));
		return (deltaX / 3.0) * (function.evaluateAt(a) + 4 * function.evaluateAt(a + deltaX) + function.evaluateAt(a + deltaX + deltaX)) +
				simpsonsRule(function, a + deltaX + deltaX, b, n - 2);
	}


	// * * * * * * * * * * * Helper functions * * * * * * * * * * // 
	// Creates a String from an array of doubles using the specified separator.
	private static String getString(double[] doubles, String seperator) {
		StringBuilder formattedString = new StringBuilder("[");
		for (int i = 0; i < doubles.length - 1; i++) {
			// put parens about negative values
			formattedString.append(String.format("%s%.4f%s%s",  doubles[i] < 0 ? "(" : "", 
					doubles[i], doubles[i] < 0 ? ")" : "", seperator));
			if ((i + 1) % 10 == 0) 
				formattedString.append(System.lineSeparator());
		}
		formattedString.append(String.format("%.4f]",  doubles[doubles.length - 1]));
		return formattedString.toString();
	}

}
