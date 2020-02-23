package by.training.parser.service.parser;

import by.training.parser.entity.Component;
import by.training.parser.entity.Leaf;
import by.training.parser.exception.ServiceException;

public class SymbolParser implements Parser {

	private static final int INDEX_OF_FIRST_SYMBOL = 0;

	@Override
	public Component parseString(String string) throws ServiceException {
		if (string == null) {
			throw new ServiceException("Cannot add null symbol");
		}
		return new Leaf(string.charAt(INDEX_OF_FIRST_SYMBOL));
	}
}
