// Test.java

package integrals;

import java.util.Scanner;

/**
 * Program that tests class <code>Function</code> by showing examples of
 * valid functions versus invalid functions.
 * 
 * @author Ayesha Ilyas
 * @since 4/24/22
 * <p>Updated 4/25/22</p>
 * @version 1.0
 * 
 */
public class Test {
	
	/**
	 * Tests Function class.
	 * @param args NA
	 */
	public static void main(String[] args) {
		test1();
		test2();
	}
	
	/**
	 * Test class Function by allowing the user to enter a function, displaying its tokenization,
	 * allowing the user to evaluate the function at a specified value, and displaying the result
	 * if the value is in the domain. Type QUIT to exit.
	 */
	public static void test1() {
		
		System.out.println("Type QUIT to exit");
		Scanner input = new Scanner(System.in);

		System.out.print("Enter a function: ");
		String f = input.nextLine();


		while (!f.equals("QUIT")) {
			try {
				Function function = new Function(f);
				System.out.println(function.getTokens());
				System.out.print("Eval at: ");
				System.out.println(function.evaluateAt(input.nextLine()));
				System.out.println("LEGAL");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				System.out.println("Could not tokenize");
				System.out.println("ILLEGAL");
			} 
			catch (ArithmeticException e) {
				e.printStackTrace();
				System.out.println("Domain error");
			}

			System.out.println("\n");
			System.out.print("Enter a function: ");
			f = input.nextLine();
		}

		input.close();
	}

	/**
	 * Prints to the console the result of creating <code>Function</code> objects and evaluating them at 5.
	 * NOTE: 5 may or may not be in the domain of the tested functions.
	 */
	public static void test2() {
		// valid functions
		String[] functions = {
				"(x^2)^2", 
				"(x^2)-(10/2)", 
				"x/5*8", 
				"x+5-1+5", 
				"(2 * (x + 5))",
				"((-10 * x + 10) / (x + 1)^3)", 
				"(-1 / (x ^ 2)) / (1 / 4)", 
				"(-(x^4) + 10 * x^4 - 24 * x^2)", 
				"e^(-2 * x + 14)",
				"e^(-x + 3)",
				"(sin(x + 1) - 1) / cos(x * 5)",
				"sin(sin(x)) / (x^3 + 4 * x^2 + x + 4)",
				"sin(pi)",
				"sin(e)",
				"((x^2) + (sec(x * pi))) / ((e^x) - x)",
				"(x+3)(x-2)",
				"(-x^4)", 
				"-x^4",
				"-x + (-9) - (-10)",
				"-x^3(-1)(-x)",
				"(-x)^4",
				"x / pi",
				"x / e",
				"1 / x",
				"(x^2-1)(x+1)",
				"(x^2-1)x",
				"x(x^2-1)",
				"pi(x-1)",
				"x^3sinx",
				"xsinx",
				"sinx(cosx)",
				"sinx(cosx)sinx",
				"sin(x)x",
				"x^2+2x",
				"pisinx",
				"3.25cosx",
				"(xpi)3.5",
				"10/(-5)+1",
		};

		// invalid functions
		String[] illegalFunctions = {
				"sin^-1(x)", // use arcsin
				"ln+x", // dont use leading plus signs
				"cos^4x", // use cos(x) ^ 4
				"sqrt(cos^2(x)+1)", // use cos(x) ^ 2
				"sinx + cos^4(x)", // use cos(x) ^ 4
				"1--x", // Consecutive operators, use 1-x
				"10/-5+1", // put parens around -5
				"10*-5",  // put parens around -5
				"x - (+x)", // get rid of leading plus sign
				"x++9" // remove extra plus sign
		};

		// these should work (unless the function cant be evaluated at 5 (ie 5 is not in the domain)
		for (String f: functions) {
			try {
				System.out.println("--------------------------------------------------");
				Function func = new Function(f);
				System.out.println(func.getTokens());
				System.out.println(func.evaluateAt(5));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("--------------------------------------------------");
			System.out.println();
		}

		// these should all throw exceptions
		int counter = 0;
		for (String f: illegalFunctions) {
			try {
				System.out.println("--------------------------------------------------");
				Function func = new Function(f);
				System.out.println(func.getTokens()); // should always throw an exception
				System.out.println(func.evaluateAt(5)); // should never run
			} catch (Exception e) {
				counter++;
				e.printStackTrace();
			}
			System.out.println("--------------------------------------------------");
			System.out.println();
		}

		if (counter == illegalFunctions.length) 
			System.out.println("SUCCESS");
		else 
			System.out.println("FAILED");
	}
}
