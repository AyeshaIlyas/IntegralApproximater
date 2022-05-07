// ApproximateIntegralsApp.java

package integrals;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Java program that approximates an integral, assumed to be continuous on [a, b], using Left Endpoint Rule, 
 * Right Endpoint Rule, Midpoint Rule, Trapezoidal Rule, and/or Simpson’s Rule.
 * 
 * @author Ayesha Ilyas
 * @since 4/3/22
 * <p>Updated 4/11/22</p>
 * @version 1.4
 * 
 */

public class ApproximateIntegralsApp {

	/**
	 * Main gets integral information from the user and estimates the value of the integral using
	 * the chosen method.
	 *  
	 * <p>Instructions detailing how to use the program are displayed to the user. Then they are
	 * prompted to choose a method to use to approximate an integral. Next, the user is prompted to 
	 * enter the function to integrate, a and b values that define the interval of integration,
	 * and n--the number of subdivisions to use in the estimation. The user can then choose whether they
	 * wish to see the steps the problem involves. Finally, the results are displayed to the console.</p>
	 * 
	 * @param args NA
	 */
	public static void main(String[] args) {

		final String directions = String.format("%s%n%s%n%s%n%n%s%n%s%n%s%n%s%n%s%n%n%s%n%s%n%s%n%s%n", 
				"This application approximates definite integrals on an closed interval [a, b] using n subdivisions and a specified estimation method ",
				"(Left Endpoint Rule, Right Endpoint Rule, Midpoint Rule, Trapezoidal Rule, and Simpson’s Rule).",
				"First choose an integration method, then enter a function, a, b, and n values. ",

				"Requirements:",
				"	• x is assumed to be the function's variable",
				"	• The function, f(x), must be continuous over [a, b]",
				"	• n must be greater than zero (and a positive even number for Simpson's Rule)",
				"	• a must be greater than b",

				"Tips:",
				"	• Type \"pi\" for π",
				"	• Put parenthesis around function arguments. ex: sin(2*x)",
				"	• Put parenthesis around negative values",
				"	• Be explicit with operator placement. For example, write 2*x instead of 2x"
				);

		final String menu = String.format("%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n", 
				"Choose a method:",
				"  1 - Left Endpoint Rule",
				"  2 - Right Endpoint Rule",
				"  3 - Midpoint Rule",
				"  4 - Trapezoidal Rule",
				"  5 - Simpson's Rule",
				"  6 - Use All Rules",
				"  7 - Quit"
				);

		// menu options
		enum Options {
			LEFT_ENDPOINT_RULE,
			RIGHT_ENDPOINT_RULE,
			MIDPOINT_RULE,
			TRAPEZOIDAL_RULE,
			SIMPSONS_RULE,
			ALL,
			QUIT
		}

		// range of valid values for n
		final int LOWERBOUND = 1;
		final int UPPERBOUND = 1_000;

		// store Options in an array
		final Options[] options =  Options.values();
		// create Scanner 
		Scanner scanner = new Scanner(System.in);
		// display directions String
		System.out.println(directions);

		while (true) {
			// - - - Get menu option - - - // 
			System.out.println(menu);			
			int userChoice = getInteger(scanner, "[#] ", 1, 7, "Please choose one of the above options.%n%n",  
					"%n[x] Please enter an integer representing one of the above options.%n%n");
			Options option = options[userChoice - 1]; 

			// - - - Quit - - - //
			if (option == Options.QUIT)  {
				System.out.println("Terminating program.");
				break;
			}

			// - - - Get integral information - - - //
			System.out.println("Enter definite integral information: ");
			printIntegral("a", "b", "f(x)");
			// get function
			Function function = getFunction(scanner);
			printIntegral("a", "b", function.toString());
			// get a
			double a = getBounds(scanner, "a: ");
			printIntegral(String.valueOf(a), "b", function.toString());
			// get b
			double b = getBounds(scanner, "b: ");
			printIntegral(String.valueOf(a), String.valueOf(b), function.toString());
			// get n
			int n = getInteger(scanner, "n: ", LOWERBOUND, UPPERBOUND, 
					String.format("Please enter a number between %,d and %,d.%n%n", LOWERBOUND, UPPERBOUND),
					"%nPlease enter an integer.%n%n");

			// - - - Ask if output should be verbose (steps should be shown) - - - //
			String input = null;
			do {
				System.out.print("Show steps (Y/N): ");
				input = scanner.nextLine().toLowerCase();
			} while (!input.matches("y|yes|n|no"));

			boolean showSteps = input.matches("y|yes") ? true : false;

			// - - - Approximate using method specified - - - //
			try {

				// FIND CLEANER WAY TO DO THIS
				System.out.printf("%nResults:%n");

				// variables to store the approximation of the integral
				double LER = 0, RER = 0, MR = 0, TR = 0, SR = 0;

				// NOTE: calculating the integrals and printing the results are done separately 
				// 	so that if the user chooses to show steps, the steps are displayed for each method,
				//  THEN a summary of the results of shown at the end, after all the steps.

				// approximate the integral and store in above variables
				// show steps 
				if (option == Options.LEFT_ENDPOINT_RULE || option == Options.ALL)
					LER = ApproximateIntegral.leftEndpointRule(function, a, b, n, showSteps);
				if (option == Options.RIGHT_ENDPOINT_RULE || option == Options.ALL)
					RER = ApproximateIntegral.rightEndpointRule(function, a, b, n, showSteps);
				if (option == Options.MIDPOINT_RULE || option == Options.ALL)
					MR = ApproximateIntegral.midpointRule(function, a, b, n, showSteps);
				if (option == Options.TRAPEZOIDAL_RULE || option == Options.ALL)
					TR = ApproximateIntegral.trapezoidalRule(function, a, b, n, showSteps);
				if (option == Options.SIMPSONS_RULE || option == Options.ALL)
					SR = ApproximateIntegral.simpsonsRule(function, a, b, n, showSteps);

				// print the results
				if (option == Options.LEFT_ENDPOINT_RULE || option == Options.ALL)
					System.out.printf("  [*] Left endpoint rule: %f%n", LER);
				if (option == Options.RIGHT_ENDPOINT_RULE || option == Options.ALL)
					System.out.printf("  [*] Right endpoint rule: %f%n", RER);
				if (option == Options.MIDPOINT_RULE || option == Options.ALL)
					System.out.printf("  [*] Midpoint rule: %f%n", MR);
				if (option == Options.TRAPEZOIDAL_RULE || option == Options.ALL)
					System.out.printf("  [*] Trapezoidal rule: %f%n", TR);
				if (option == Options.SIMPSONS_RULE || option == Options.ALL)
					System.out.printf("  [*] Simpson's rule: %f%n", SR);

			} catch (IllegalArgumentException | ArithmeticException e) {
				// occurs if option 6 (Options.ALL) is chosen, in which case the user could enter
				//  a non-even value for n and Simpson's Rule would throw an IllegalArgumentException
				System.out.printf("  [x] %s%n", e.getMessage());
			}

			System.out.println("\nIMPORTANT: The function must be continuous on [a, b] for the results to be correct.\n"
							+    "           If the function is discontinuous on the interval of integration,\n"
							+    "           the results will be inaccurate.");
		}	

		scanner.close();
	}

	// * * * * * * * * * * * Helper Methods * * * * * * * * * * * //

	// Prints the integral with the specified a, b, and f values.
	private static void printIntegral(String a, String b, String f) {
		System.out.printf("%n    ∫[%s,%s]%sdx%n%n", a, b, f);
	}

	// Gets a function as a String from the user and returns a Function object.
	private static Function getFunction(Scanner scanner) {
		Function function = null;
		while (function == null) {
			try {
				System.out.print("f(x): ");
				String f = scanner.nextLine();
				function = new Function(f); // may throw an IllegalArgumentException
			} catch (IllegalArgumentException e) {
				System.out.printf("%n[x] Please enter a valid function: %s%n%n", e.getMessage());
			}
		}
		return function;
	}

	// Gets a valid value for a and b.
	// a and be can be integers, doubles,other numeric values that are represented as String
	//   such as e, pi, or expression like pi/2 or 3*pi/2
	private static double getBounds(Scanner scanner, String prompt) {
		double result = 0; // will be overwritten
		boolean isValid = false;
		while (!isValid) {
			try {
				System.out.print(prompt);
				result = Function.evaluate(scanner.nextLine()); // may throw an IllegalArgumentException 
				isValid = true;
			} catch (IllegalArgumentException e) {
				System.out.printf("%n[x] %s Please enter a valid number.%n%n", e.getMessage());
			}
		}
		return result;
	}

	// Get a valid integer from the user with in the range specified by lowerBound and upperBound 
	//  (both of which are inclusive). A String for the prompt and for the error message must be 
	//  provided as arguments.
	// Returns an integer within a specified range.
	private static int getInteger(Scanner scanner, String prompt, int lowerBound, int upperBound, String outOfBoundsErrorMsg, String errorMsg) {
		boolean isInvalid = true;
		int integer = 0;
		while (isInvalid || integer < lowerBound || integer > upperBound) {
			try {
				System.out.print(prompt);
				integer = scanner.nextInt();
				scanner.nextLine(); // read in new line character
				System.out.println(); // move cursor to new line
				if (integer < lowerBound || integer > upperBound) 
					System.out.printf(outOfBoundsErrorMsg);
				else 
					isInvalid = false;
			} catch (InputMismatchException e) {
				scanner.nextLine();
				System.out.printf(errorMsg);
			}
		}

		return integer;

	}
}
