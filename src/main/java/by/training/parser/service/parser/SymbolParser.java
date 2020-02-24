package by.training.parser.service.parser;

import by.training.parser.entity.Component;
import by.training.parser.entity.Leaf;
import by.training.parser.exception.ServiceException;

public class SymbolParser implements Parser {

	private static final int INDEX_OF_FIRST_SYMBOL = 0;
	private static final int VALID_STRING_LENGTH = 1;

	@Override
	public Component parseString(String string) throws ServiceException {
		if (string == null || string.length() != VALID_STRING_LENGTH) {
			throw new ServiceException("Cannot add symbol");
		}
		return new Leaf(string.charAt(INDEX_OF_FIRST_SYMBOL));
	}
}
