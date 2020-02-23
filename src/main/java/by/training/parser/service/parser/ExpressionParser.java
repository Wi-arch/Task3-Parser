package by.training.parser.service.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import by.training.parser.entity.Component;
import by.training.parser.exception.InvalidExpressionException;
import by.training.parser.exception.ServiceException;
import static by.training.parser.util.ExpressionCalculator.calculateExpression;;

public class ExpressionParser implements Parser {

	private static final Logger LOGGER = Logger.getLogger(ExpressionParser.class);
	private static final String MATH_EXPRESSION_REGEX = "\\s([(]?(~|-)?\\d+[)]?(((\\+|-|\\*|/|%|<<|>>|>>>|&|\\^|\\|)[(]?\\d+)?([.]\\d+)?[)]?)*?)\\s";
	private static final int EXPRESSION_GROUP_NUMBER = 1;
	private Parser nextParser;

	public ExpressionParser() {
		nextParser = new SymbolParser();
	}

	public ExpressionParser(Parser nextParser) throws ServiceException {
		if (nextParser == null) {
			throw new ServiceException("Cannot init parser with null nextParser");
		}
		this.nextParser = nextParser;
	}

	@Override
	public Component parseString(String string) throws ServiceException {
		if (string == null) {
			throw new ServiceException("Cannot parse null string");
		}
		StringBuilder result = new StringBuilder();
		Matcher matcher = Pattern.compile(MATH_EXPRESSION_REGEX).matcher(string);
		String expression;
		String expressionResult;
		while (matcher.find()) {
			expression = matcher.group(EXPRESSION_GROUP_NUMBER);
			try {
				expressionResult = " " + calculateExpression(expression) + " ";
				matcher.appendReplacement(result, expressionResult);
			} catch (InvalidExpressionException e) {
				LOGGER.warn("Cannot replace invalid expression", e);
			}
		}
		matcher.appendTail(result);
		return nextParser.parseString(result.toString());
	}
}
