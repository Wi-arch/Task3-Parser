package by.training.parser.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExpressionValidatorTest {

	private static final String VALID_EXPRESSION = "3+4 % 2 * (3<<1-5)/10 & 20 | 13- 11";
	private static final String INVALID_EXPRESSION = "(3+4)-((2) & 13 | 2";

	@Test
	public void testIsValidExpressionPositive() {
		boolean actual = ExpressionValidator.isValidExpression(VALID_EXPRESSION);
		assertTrue(actual);
	}

	@Test
	public void testIsValidExpressionNegative() {
		boolean actual = ExpressionValidator.isValidExpression(INVALID_EXPRESSION);
		assertFalse(actual);
	}
}
