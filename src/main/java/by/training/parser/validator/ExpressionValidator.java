package by.training.parser.validator;

public class ExpressionValidator {

	private static final String LEFT_BRACKETS_REGEX = "[^(]";
	private static final String RIGHT_BRACKETS_REGEX = "[^)]";
	private static final String SPACE_REGEX = "\\s+";
	private static final String MATH_EXPRESSION_REGEX = "^[(]?(~|-)?\\d+[)]?(((\\+|-|\\*|/|%|<<|>>|>>>|&|\\^|\\|)[(]?\\d+)?([.]\\d+)?[)]?)*?$";

	public static boolean isValidExpression(String expression) {
		return expression != null && checkLeftAndRigthBrackets(expression) && checkExpression(expression);
	}

	private static boolean checkLeftAndRigthBrackets(String expression) {
		String leftBrackets = expression.replaceAll(LEFT_BRACKETS_REGEX, "");
		String rightBrackets = expression.replaceAll(RIGHT_BRACKETS_REGEX, "");
		return leftBrackets.length() == rightBrackets.length();
	}

	private static boolean checkExpression(String expression) {
		return expression.replaceAll(SPACE_REGEX, "").matches(MATH_EXPRESSION_REGEX);
	}
}
