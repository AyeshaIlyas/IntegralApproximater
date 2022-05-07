// Function.java

package integrals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Java class that models a function of x. The following functions are supported:
 * sin, cos, tan, csc, sec, cot, arcsin, arccos, arctan, ln, log, sinh, cosh, tanh, sqrt. 
 * Arguments are assumed to be in radians.
 * 
 * @author Ayesha Ilyas
 * @since 3/25/22
 * <p>Updated 4/11/22</p>
 * <p>Updated 4/25/22</p>
 * @version 1.3
 */

public class Function {
	// * * * * * * * * * * * Private Attributes * * * * * * * * * * //
	private List<String> originalTokens;
	private String function;
	private final String[] validTokens = {"x", "e", "pi", "sin", "cos", "tan", "csc", "sec", "cot", "arcsin", 
			"arccos", "arctan", "ln", "log", "sinh", "cosh", "tanh", "sqrt"};

	// * * * * * * * * * * * Public Services * * * * * * * * * * //
	/**
	 * Constructor creates a <code>Function</code> using the String passed as an argument.
	 * 
	 * @param function a <code>String</code> representing the <code>Function</code>. x is assumed to be the function's variable.
	 * @throws IllegalArgumentException if <code>function</code> if not a valid function
	 * 
	 */
	public Function(String function) {
		// assumes variable is x
		if (function.isEmpty()) 
			throw new IllegalArgumentException("Input must be at least one character.");
		originalTokens = tokenize(function); // may throw IllegalArgumentException
		this.function = function.replace(" ", "");
	}

	/**
	 * Changes the function that the <code>Function</code> object represents.
	 * @param function a <code>String</code> representing the new function
	 * @throws IllegalArgumentException if the <code>function</code> if not valid
	 */
	public void setFunction(String function) {
		// assumes variable is x
		if (function.isEmpty()) 
			throw new IllegalArgumentException("Input must be at least one character.");
		originalTokens = tokenize(function);
		this.function = function.replace(" ", "");
	}


	/**
	 * Returns a read-only list of tokens.
	 * @return an unmodifiable <code>List</code> of <code>String</code> objects 
	 * @see java.util.Collections#unmodifiableList
	 */
	public List<String> getTokens() {
		return Collections.unmodifiableList(originalTokens);
	}

	/**
	 * Gets the function <code>String</code>.
	 * @return a <code>String</code> representing the function.
	 */
	public String toString() {
		return function;
	}

	// * * * * * * * * * *  Public Parsing Methods * * * * * * * * * * //
	
	/**
	 * Evaluates the function at a specified value and returns the result.
	 * 
	 * @param value a <code>double</code> to evaluate the function at
	 * @return the value of the function at the specified number as a <code>double</code>
	 * @throws ArithmeticException if <code>value</code> if not in the domain 
	 * 
	 */
	public double evaluateAt(double value) {
		return evaluateAt(value, false);
	}

	// The overloaded evaluateAt methods with String arguments evaluate expressions that 
	//   don't contain variables.
	
	/**
	 * Evaluates the function at a specified value and returns the result.
	 * 
	 * <p>This method can be used to evaluate a function at a value that is itself an expression
	 * that does not contain variables such as pi, 3*pi/2, e/2.</p>
	 * 
	 * @param value a <code>String</code> to evaluate the function at
	 * @return the value of the function at the specified number as a <code>double</code>
	 * @throws ArithmeticException if <code>value</code> if not in the domain 
	 * 
	 */
	public double evaluateAt(String value) {
		return evaluateAt(value, false);
	}

	/**
	 * Evaluates the function at a specified value and returns the result.
	 * 
	 * <p>This method can be used to evaluate a function at a value that is itself an expression
	 * that does not contain variables such as pi, 3*pi/2, e/2.</p>
	 * 
	 * @param value a <code>String</code> to evaluate the function at
	 * @param verbose a <code>boolean</code> specifying whether to print out the steps involved in the evaluation.
	 * To show steps, use true.
	 * @return the value of the function at the specified number as a <code>double</code>
	 * @throws ArithmeticException if <code>value</code> if not in the domain 
	 * 
	 */
	public double evaluateAt(String value, boolean verbose) {
		return evaluateAt(Function.evaluate(value), verbose);
	}

	
	/**
	 * A static method that evaluates a <code>String</code> expression and returns a <code>double</code> result.
	 * This method can be used to get the double equivalent of expressions like pi, 3*pi/2, and e/2.
	 * 
	 * @param expression a <code>String</code> expression
	 * @return the <code>double</code> value of the expression
	 * @throws IllegalArgumentException if <code>expression</code> contains variables or expression is not more than one character in length
	 * 
	 */
	// for constant functions and expressions without variables
	public static double evaluate(String expression) {
		// if value contains the variable, x, throw an error
		for (int i = 0; i < expression.length(); i++) {
			if (expression.charAt(i) == 'x') 
				throw new IllegalArgumentException("Expression must not contain variables.");
		}
		return new Function(expression).evaluateAt(0);
	}

	/**
	 * Evaluates the function at a specified value and returns the result.
	 * 
	 * @param value a <code>double</code> to evaluate the function at
	 * @param verbose a <code>boolean</code> specifying whether to print out the steps involved in the evaluation.
	 * To show steps, use true.
	 * @return the value of the function at the specified number as a <code>double</code>
	 * @throws ArithmeticException if <code>value</code> if not in the domain 
	 * 
	 */
	public double evaluateAt(double value, boolean verbose) {
		// create copy of originalTokens
		List<String> tokens = new ArrayList<>();
		for (int i = 0; i < originalTokens.size(); i++) {
			tokens.add("0");
		}
		Collections.copy(tokens, originalTokens);

		// replace all x with value
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).equals("x")) 
				tokens.set(i, String.valueOf(value));
		}

		// print function with x subbed for value
		if (verbose) {
			StringBuilder output = new StringBuilder(String.format("%nf(%.4f) = ", value));
			output.append(getString(tokens));
			System.out.printf("%s%n", output);
		}

		while (tokens.size() > 1) {
			parse(getInnermostExpression(tokens));

			if (verbose) {
				StringBuilder output = new StringBuilder("          = ");
				output.append(getString(tokens));
				System.out.printf("%s%n", output);
			}
		}
		return parseNumber(tokens.get(0));
	}

	// * * * * * * * * * * * Private Parsing Methods * * * * * * * * * * * //

	private String getString(List<String> tokens) {
		StringBuilder formattedString = new StringBuilder();
		for (String token : tokens) {
			if (!token.matches("e|pi|x") && isNumber(token))
				formattedString.append(String.format("%.4f", Double.parseDouble(token)));
			else 
				formattedString.append(token);
		}
		return formattedString.toString();
	}

	private void parse(List<String> tokens) {
		
		// exponentiation
		resolveExponentiation(tokens);
		
		// transcendental functions
		resolveFunctions(tokens);

		// mult and div
		resolveMultAndDiv(tokens);

		// addition and subtraction
		resolveAddAndSub(tokens);

		// for values that fall through function calls, like e and pi
		tokens.set(0, String.valueOf(parseNumber(tokens.get(0))));
	}
	
	// throws NumberFormatException if not parsable into a double
	private double parseNumber(String number) {
		double operand;
		if (number.equals("e")) {
			operand = Math.E;
		} else if (number.equals("pi")) {
			operand = Math.PI;
		} else {
			operand = Double.parseDouble(number); // throws NumberFormatException
		}
		return operand;
	}

	private double enhancedSin(double argument) {
		// return 0 for all multiples of pi and sin(argument) otherwise
		return argument % Math.PI == 0 ? 0 : Math.sin(argument);
	}

	private double enhancedCos(double argument) {		
		// return 0 for all odd multiples of pi/2 and cos(argument) otherwise
		return ((argument % (Math.PI / 2) == 0) && !(argument / (Math.PI / 2) % 2 == 0)) ? 0 : Math.cos(argument);
	}

	// sin, cos, tan, csc, sec, cot, arcsin, arccos, arctan, ln, log, sinh, cosh, tanh, sqrt
	// arguments in radians
	// Assumes that the element after the function name is the argument to function and attempts to calculate
	// 		with that argument. If the argument is NaN, then a NumberFormatException is caught and the program continues executing.
	// throws ArithmeticException if number is not in domain of function or division by zero
	private void resolveFunctions(List<String> tokens) {
		ListIterator<String> iterator = tokens.listIterator();

		if (tokens.size() == 1)
			return;

		// calculate function values

		while (iterator.hasNext()) {
			try {
				// get current index
				int i = iterator.previousIndex() + 1;
				// return if last element is reached
				if (i  == tokens.size() - 1) 
					return;
				String token = tokens.get(i); // get token at current index
				// gets next token and converts to double if possible or throws a NumberFormatException 
				double number = parseNumber(tokens.get(i + 1)); 
				double result = 0; // value will be overwritten when needed

				// sin, cos, tan, csc, sec, cot
				if (token.equals("sin")) 
					result = enhancedSin(number);
				else if (token.equals("cos")) 
					result = enhancedCos(number);
				else if (token.equals("tan")) 
					result = enhancedSin(number) / enhancedCos(number);
				else if (token.equals("csc")) 
					result = 1 / enhancedSin(number); 
				else if (token.equals("sec")) 
					result = 1 / enhancedCos(number); 
				else if (token.equals("cot")) 
					result = enhancedCos(number) / enhancedSin(number);
				
				// arcsin, arccos, arctan
				else if (token.equals("arcsin"))
					result = Math.asin(number);
				else if (token.equals("arccos")) 
					result = Math.acos(number);
				else if (token.equals("arctan")) 
					result = Math.atan(number);
				
				// log, ln
				else if (token.equals("ln")) 
					result = Math.log(number);
				else if (token.equals("log")) 
					result = Math.log10(number);
				
				// sinh, cosh, tan
				else if (token.equals("sinh")) 
					result = Math.sinh(number);
				else if (token.equals("cosh")) 
					result = Math.cosh(number);
				else if (token.equals("tanh")) 
					result = Math.tanh(number);
		
				// sqrt
				else if (token.equals("sqrt")) 
					result = Math.sqrt(number);
				

				if (token.matches("sin|cos|tan|csc|sec|cot|arcsin|arccos|arctan|ln|log|sinh|cosh|tanh|sqrt")) {
					// if the number is not in the domain, then the result will either be NaN or +/- infinity 
					if (Double.isNaN(result) || Double.isInfinite(result))
						throw new ArithmeticException(String.format("%.4f not in domain", number));
					// set value to the left of operand to result
					tokens.set(i, String.valueOf(result));
					// delete current and next item
					iterator.next();
					iterator.next();
					iterator.remove();	
					iterator.previous();
				} 
				// advance iterator
				iterator.next();
			} catch (NumberFormatException e) {
				iterator.next();
			}
		} 
	}

	private void resolveExponentiation(List<String> tokens) {
		ListIterator<String> iterator = tokens.listIterator();

		// exponentiation
		while (iterator.hasNext()) {
			// get current index
			int i = iterator.previousIndex() + 1;
			if (tokens.get(i).equals("^")) {

				double result = Math.pow(parseNumber(tokens.get(i - 1)), parseNumber(tokens.get(i + 1)));
				// set value to the left of operand to result
				tokens.set(i - 1, String.valueOf(result));
				// delete current and next item
				iterator.next();
				iterator.remove();
				iterator.next();
				iterator.remove();	
				iterator.previous();
			}

			// advance iterator
			iterator.next();
		}
	}

	private void resolveMultAndDiv(List<String> tokens) {
		ListIterator<String> iterator = tokens.listIterator();

		// mult and div
		while (iterator.hasNext()) {
			// get current index
			int i = iterator.previousIndex() + 1;
			if (tokens.get(i).equals("*")) {
				double result = parseNumber(tokens.get(i - 1)) * parseNumber(tokens.get(i + 1));
				// set value to the left of operand to result
				tokens.set(i - 1, String.valueOf(result));
				// delete current and next item
				iterator.next();
				iterator.remove();
				iterator.next();
				iterator.remove();	
				iterator.previous();
			}

			else if (tokens.get(i).equals("/")) {
				double result = parseNumber(tokens.get(i - 1)) / parseNumber(tokens.get(i + 1));
				if (Double.isInfinite(result)) 
					throw new ArithmeticException(String.format("%s not in domain", tokens.get(i + 1)));
				// set value to the left of operand to result
				tokens.set(i - 1, String.valueOf(result));
				// delete current and next item
				iterator.next();
				iterator.remove();
				iterator.next();
				iterator.remove();	
				iterator.previous();
			}

			// advance iterator
			iterator.next();
		}

	}

	private void resolveAddAndSub(List<String> tokens) {

		ListIterator<String> iterator = tokens.listIterator();

		while (iterator.hasNext()) {
			// get current index
			int i = iterator.previousIndex() + 1;
			if (tokens.get(i).equals("+")) {
				double result = parseNumber(tokens.get(i - 1)) + parseNumber(tokens.get(i + 1));
				// set value to the left of operand to result
				tokens.set(i - 1, String.valueOf(result));
				// delete current and next item
				iterator.next();
				iterator.remove();
				iterator.next();
				iterator.remove();	
				iterator.previous();
			}

			else if (tokens.get(i).equals("-")) {
				double result = parseNumber(tokens.get(i - 1)) - parseNumber(tokens.get(i + 1));
				// set value to the left of operand to result
				tokens.set(i - 1, String.valueOf(result));
				// delete current and next item
				iterator.next();
				iterator.remove();
				iterator.next();
				iterator.remove();	
				iterator.previous();
			}

			// advance iterator
			iterator.next();
		}

	}

	// get the right innermost expression
	// to get the left innermost expression, you can use a recursive solution
	//  but it's much slower
	private List<String> getInnermostExpression(List<String> tokens) {
		int startParenIndex = tokens.lastIndexOf("(");
		if (startParenIndex == -1) return tokens;
		int endParenIndex = -1;
		for (int i = startParenIndex; i < tokens.size(); i++) {
			if (tokens.get(i).equals(")")) {
				endParenIndex = i;
				break;
			}
		}

		if (endParenIndex == -1) return tokens;

		// delete parens from list and return what was in the parens
		List<String> sublist = tokens.subList(startParenIndex, endParenIndex + 1);
		sublist.remove(0);	
		sublist.remove(sublist.size() - 1);
		return sublist;

	}

	
	// * * * * * * * * * *  Tokenizing Methods * * * * * * * * * * //

	private List<String> tokenize(String function) {
		// remove spaces
		function = function.replace(" ", "");

		// create empty list
		List<String> tokens = new ArrayList<>();
		function = checkPreconditions(function, tokens); // throws IllegalArgumentException

		// keeps track of series of characters in order to match tokens such as sin, cos, pi, etc...
		String token = "";
		for (int i = 0; i < function.length(); i++) {
			token += function.charAt(i);

			// handles the case that the current char is an operator
			if (isOperator(function.charAt(i))) {
				// see if token is a number and add to list if it is
				// take substring from beginning to second to last character, because the last character is an operator 
				String potentialNumber = token.substring(0, token.length() - 1); // if token has length 1, potentialNumber will be ""
				if (isNumber(potentialNumber)) {
					// if there was ), variable, pi, or e before the number, add a multiplication symbol before the number
					if (!tokens.isEmpty() && (tokens.get(tokens.size() - 1).equals(")") ||
							isNumber(tokens.get(tokens.size() - 1))))
						tokens.add("*");

					// after this step, token will contain just the operator 
					token = String.valueOf(token.charAt(token.length() - 1));
					tokens.add(potentialNumber);

				} else if (!potentialNumber.isEmpty())  {
					// if token is invalid throw an exception
					String numericPart = "";
					String nonNumericPart = "";
					for (int index = 0; index < token.length(); index++) {
						if (!(Character.isDigit(token.charAt(index)) || token.charAt(index) == '.')) {
							numericPart = token.substring(0, index);
							nonNumericPart = function.substring(i - (token.length() - 1 - index));
							break;
						}
					}

					if (isNumber(numericPart)) {
						tokens.add(numericPart);
						tokens.add("*");
						// move to a previous position in the string
						i = function.length() - nonNumericPart.length() - 1;
						// reset token
						token = "";
						continue;
					} else 
						throw new IllegalArgumentException(String.format("Unknown token \"%s\" in expression.", potentialNumber));
				}

				// if the current character is an operator or paren, add it to list
				if (i > 0) { // i needs to be 1 or more since the character at i-1 is accessed
					char current = function.charAt(i);
					char previous = function.charAt(i - 1);
					String lastToken = tokens.get(tokens.size() - 1);
					// insert * between expression being multiplied together like this (expression1)(expression2) or 
					// number(expression) 
					if (current == '(' && (previous == ')' || isNumber(lastToken))) 
						tokens.add("*");
					// insert * for expression of the form (expression)number
					else if (current == ')' && (i + 1) < function.length() && isNumber(String.valueOf(function.charAt(i + 1)))) { 
						tokens.add(")");
						tokens.add("*");
						token = "";
					}
						
					// replace minus signs by -1 *
					else if (current == '-' && previous == '(') {
						tokens.add("-1");
						tokens.add("*");
						token = "";
					} 
					// throw exception if there are operators other than ( after a function name
					else if (!tokens.isEmpty() && current != '(' && !isNumber(lastToken) && 
							isValidToken(lastToken))
						throw new IllegalArgumentException("Illegal character after function name.");
					// throw error if there are invalid consecutive operators  
					// for ex: ^), **, *^, (/
					else if (isOperator(previous) && !(previous == ')' || current == '(')) 
						throw new IllegalArgumentException("Consecutive operators not allowed.");
				}
				// add operator if it has not already been added
				if (!token.isEmpty())
					tokens.add(token);
				// reset token
				token = "";

			} 

			// add any valid token to list; see isValidToken
			// assumes that x is the function parameter
			else if (isValidToken(token)) {
				// if token is sin, cos, or tan check to see if next char is h -> hyperbolic
				// skip to next iteration in case of hyperbolic functions
				if (token.matches("sin|cos|tan") && (i + 1) < function.length() && function.charAt(i + 1) == 'h') 
					continue;
				// if there is ) before the token, add a multiplication symbol before the token
				// if there is an x, e, pi, or # before token, insert *
				if (!tokens.isEmpty() && (tokens.get(tokens.size() - 1).equals(")") || 
						isNumber(tokens.get(tokens.size() - 1))))
					tokens.add("*");

				tokens.add(token); 
				// reset token
				token = "";
			} 
		}

		// check to see if there is a trailing number stored in token
		if (isNumber(token)) {
			if (!tokens.isEmpty() && (tokens.get(tokens.size() - 1).equals(")") || 
					isNumber(tokens.get(tokens.size() - 1))))
				tokens.add("*");
			tokens.add(token);
		}
		else if (!token.isEmpty()) {
			String numericPart = "";
			String nonNumericPart = "";
			for (int index = 0; index < token.length(); index++) {
				if (!(Character.isDigit(token.charAt(index)) || token.charAt(index) == '.')) {
					numericPart = token.substring(0, index);
					nonNumericPart = token.substring(index);
					break;
				}
			}

			if (isNumber(numericPart)) {
				tokens.add(numericPart);
				tokens.add("*");
				// recursive tokenizing
				tokens.addAll(tokenize(nonNumericPart)); // throws IllegalArgumentException
			} else 
				throw new IllegalArgumentException(String.format("Unknown token \"%s\" in expression.", token));
		}

		// throw error if last token is not a ), a number, or x
		String lastToken = tokens.get(tokens.size() - 1);
		if (!(lastToken.equals(")") || isNumber(lastToken))) 
			throw new IllegalArgumentException("Invalid ending token.");

		return tokens;
	}

	private int frequency(String s, char c) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) 
				count++;
		}
		return count;
	}

	private String checkPreconditions(String function, List<String> tokens) {
		// check to see if parens match (i.e. for every opening paren, there is a closing paren
		if (frequency(function, ')') != frequency(function, '(')) 
			throw new IllegalArgumentException("Unmatching parenthesis.");

		// Make sure leading characters are valid before parsing
		if (function.length() == 1 && !isNumber(String.valueOf(function.charAt(0)))) {
			throw new IllegalArgumentException(String.format("Unknown token \"%s\" in expression.", function));
		}

		else if (function.length() > 1) {
			char first = function.charAt(0);
			char second = function.charAt(1);
			// replace leading negative sign followed by x, a number, or valid token (any function name)
			// with -1 * and remove the sign
			if (first == '-') {
				if (isNumber(String.valueOf(second)) || containsValidToken(function, 1)) { 
					tokens.add("-1");
					tokens.add("*");
					function = function.replaceFirst("-", "");
				}
			}

			// if there is a single leading plus sign followed by x or a digit, remove the sign
			else if (first == '+' && (second == 'x' || Character.isDigit(second)))  
				function = function.replaceFirst("\\+", ""); 

			// throw error if the first character is an operator not equal to + / - followed by x or digit 
			// Ex: throws when ++, --, **, *x
			else if (!(first == '(') && isOperator(first))
				throw new IllegalArgumentException("Invalid leading token.");
		}

		return function;
	}

	private boolean isOperator(char c) {
		char[] tokens = {'^', '*', '/', '+', '-', '(', ')'};
		for (char token : tokens) {
			if (c == token) 
				return true;
		}
		return false;
	}

	private boolean isValidToken(String s) {
		for (String token : validTokens) {
			if (s.equals(token)) 
				return true;
		}
		return false;
	}
	
	private boolean containsValidToken(String s, int startIndex) {
		for (String token: validTokens) {
			if (s.substring(startIndex).length() >= token.length() 
					&& s.substring(startIndex, startIndex + token.length()).equals(token)) {
				return true;
			}
		}
		return false;
	}

	private boolean isNumber(String s) {
		if (s.equals("e") || s.equals("pi") || s.equals("x"))
			return true;
		try {
			Double.parseDouble(s); // if parseDouble does not throw an error, it is a valid double
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}