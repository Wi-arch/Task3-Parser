package by.training.parser.util;

import static by.training.parser.validator.ExpressionValidator.isValidExpression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

import by.training.parser.exception.InvalidExpressionException;

public class ExpressionCalculator {
	private static final Map<String, Integer> MATH_OPERATIONS = new LinkedHashMap<>();

	private static final int FIRST_PRIORITY = 1;
	private static final int SECOND_PRIORITY = 2;
	private static final int THIRD_PRIORITY = 3;
	private static final int FOURTH_PRIORITY = 4;
	private static final int FIFTH_PRIORITY = 5;
	private static final int SIXTH_PRIORITY = 6;
	private static final String LEFT_BRACKET = "(";
	private static final String RIGHT_BRACKET = ")";
	private static final String UNARY_BITWISE_COMPLEMENT = "~";
	private static final String MULTIPLICATION_OPERATOR = "*";
	private static final String DIVISION_OPERATOR = "/";
	private static final String REMAINDER_OPERATOR = "%";
	private static final String ADDITIVE_OPERATOR = "+";
	private static final String SUBTRACTION_OPERATOR = "-";
	private static final String SIGNED_RIGHT_SHIFT = ">>";
	private static final String UNSIGNED_RIGHT_SHIFT = ">>>";
	private static final String SIGNED_LEFT_SHIFT = "<<";
	private static final String BITWISE_AND = "&";
	private static final String BITWISE_EXCLUSIVE_OR = "^";
	private static final String BITWISE_INCLUSIVE_OR = "|";
	private static final int SCALE = 2;
	private static final String SPACES_REGEX = "\\s+";

	static {
		MATH_OPERATIONS.put(LEFT_BRACKET, FIRST_PRIORITY);
		MATH_OPERATIONS.put(RIGHT_BRACKET, FIRST_PRIORITY);
		MATH_OPERATIONS.put(UNARY_BITWISE_COMPLEMENT, FIRST_PRIORITY);
		MATH_OPERATIONS.put(MULTIPLICATION_OPERATOR, SECOND_PRIORITY);
		MATH_OPERATIONS.put(DIVISION_OPERATOR, SECOND_PRIORITY);
		MATH_OPERATIONS.put(REMAINDER_OPERATOR, SECOND_PRIORITY);
		MATH_OPERATIONS.put(ADDITIVE_OPERATOR, THIRD_PRIORITY);
		MATH_OPERATIONS.put(SUBTRACTION_OPERATOR, THIRD_PRIORITY);
		MATH_OPERATIONS.put(SIGNED_RIGHT_SHIFT, FOURTH_PRIORITY);
		MATH_OPERATIONS.put(SIGNED_LEFT_SHIFT, FOURTH_PRIORITY);
		MATH_OPERATIONS.put(UNSIGNED_RIGHT_SHIFT, FOURTH_PRIORITY);
		MATH_OPERATIONS.put(BITWISE_AND, FIFTH_PRIORITY);
		MATH_OPERATIONS.put(BITWISE_EXCLUSIVE_OR, SIXTH_PRIORITY);
		MATH_OPERATIONS.put(BITWISE_INCLUSIVE_OR, SIXTH_PRIORITY);
	}

	public static BigDecimal calculateExpression(String expression) throws InvalidExpressionException {
		if (!isValidExpression(expression)) {
			throw new InvalidExpressionException("Invalid expression");
		}
		Deque<String> reversePolishNotation = convertExpressionToReversePolishNotation(expression);
		return calculateReversePolishNotationExpression(reversePolishNotation);
	}

	private static BigDecimal calculateReversePolishNotationExpression(Deque<String> expression) {
		Deque<BigDecimal> tempStorage = new ArrayDeque<>();
		String currentElement = null;
		while (!expression.isEmpty()) {
			currentElement = expression.pollFirst();
			if (MATH_OPERATIONS.keySet().contains(currentElement)) {
				BigDecimal operand2 = tempStorage.pop();
				BigDecimal operand1 = tempStorage.isEmpty() ? BigDecimal.ZERO : tempStorage.pop();
				switch (currentElement) {
				case "~":
					tempStorage.push(new BigDecimal(~operand2.intValue()));
					break;
				case "%":
					tempStorage.push(operand1.remainder(operand2));
					break;
				case "*":
					tempStorage.push(operand1.multiply(operand2));
					break;
				case "/":
					tempStorage.push(operand1.divide(operand2, SCALE, RoundingMode.HALF_UP));
					break;
				case "+":
					tempStorage.push(operand1.add(operand2));
					break;
				case "-":
					tempStorage.push(operand1.subtract(operand2));
					break;
				case ">>":
					tempStorage.push(new BigDecimal(operand1.intValue() >> operand2.intValue()));
					break;
				case ">>>":
					tempStorage.push(new BigDecimal(operand1.intValue() >>> operand2.intValue()));
					break;
				case "<<":
					tempStorage.push(new BigDecimal(operand1.intValue() << operand2.intValue()));
					break;
				case "&":
					tempStorage.push(new BigDecimal(operand1.intValue() & operand2.intValue()));
					break;
				case "^":
					tempStorage.push(new BigDecimal(operand1.intValue() ^ operand2.intValue()));
					break;
				case "|":
					tempStorage.push(new BigDecimal(operand1.intValue() | operand2.intValue()));
					break;
				}
			} else {
				tempStorage.push(new BigDecimal(currentElement));
			}
		}
		return tempStorage.pop();
	}

	private static Deque<String> convertExpressionToReversePolishNotation(String expression) {
		expression = expression.replaceAll(SPACES_REGEX, "");
		Deque<String> result = new ArrayDeque<>();
		Deque<String> stack = new ArrayDeque<>();
		int index = 0;
		int nextOperationIndex = 0;
		String nextOperation = getNextOperation(expression, index);
		while (nextOperation != null) {
			nextOperationIndex = expression.indexOf(nextOperation, index);
			if (index != nextOperationIndex) {
				result.add(expression.substring(index, nextOperationIndex));
			}
			handleNextOperation(nextOperation, stack, result);
			index = nextOperationIndex + nextOperation.length();
			nextOperation = getNextOperation(expression, index);
		}
		if (index != expression.length()) {
			result.add(expression.substring(index));
		}
		stack.forEach(result::add);
		return result;
	}

	private static String getNextOperation(String expression, int index) {
		String nextOperation = null;
		int nextOperationIndex = expression.length();
		int i = 0;
		for (String operation : MATH_OPERATIONS.keySet()) {
			i = expression.indexOf(operation, index);
			if (i >= 0 && i <= nextOperationIndex) {
				nextOperation = operation;
				nextOperationIndex = i;
			}
		}
		return nextOperation;
	}

	private static void handleNextOperation(String nextOperation, Deque<String> stack, Deque<String> result) {
		if (nextOperation.equals(LEFT_BRACKET)) {
			stack.push(nextOperation);
		} else if (nextOperation.equals(RIGHT_BRACKET)) {
			while (!stack.peek().equals(LEFT_BRACKET)) {
				result.add(stack.pop());
			}
			stack.pop();
		} else {
			while (!stack.isEmpty() && !stack.peek().equals(LEFT_BRACKET)
					&& (MATH_OPERATIONS.get(nextOperation) >= MATH_OPERATIONS.get(stack.peek()))) {
				result.add(stack.pop());
			}
			stack.push(nextOperation);
		}
	}
}
