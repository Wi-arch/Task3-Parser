package by.training.parser.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import by.training.parser.exception.InvalidExpressionException;

public class ExpressionCalculatorTest {

	private static final String VALID_EXPRESSION = "~4 % 2 * (3>>>1-5)/10 & 20 | 13 + 11 << 2 ^ 55 >> 5";
	private static final String INVALID_EXPRESSION = "(3+4)-((2)& 13 | 2";

	@Test
	public void testCalculateExpressionPositive() throws InvalidExpressionException {
		int intResult = ~4 % 2 * (3>>>1-5)/10 & 20 | 13 + 11 << 2 ^ 55 >> 5;
		BigDecimal expected = new BigDecimal(intResult);
		BigDecimal actual = ExpressionCalculator.calculateExpression(VALID_EXPRESSION);
		assertEquals(expected, actual);
	}

	@Test(expected = InvalidExpressionException.class)
	public void testCalculateExpressionNegativeWithException() throws InvalidExpressionException {
		BigDecimal actual = ExpressionCalculator.calculateExpression(INVALID_EXPRESSION);
		assertNull(actual);
	}
}
