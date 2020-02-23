package by.training.parser.service.parser;

import by.training.parser.entity.Component;
import by.training.parser.entity.ComponentType;
import by.training.parser.entity.Composite;
import by.training.parser.entity.Leaf;
import by.training.parser.exception.ServiceException;

public class LexemeParser implements Parser {

	private static final int ZERO_LENGTH = 0;
	private ComponentType componentType;
	private Parser nextParser;

	public LexemeParser() {
		componentType = ComponentType.LEXEME;
		nextParser = new SymbolParser();
	}

	public LexemeParser(Parser nextParser) throws ServiceException {
		if (nextParser == null) {
			throw new ServiceException("Cannot init parser with null nextParser");
		}
		this.componentType = ComponentType.LEXEME;
		this.nextParser = nextParser;
	}

	@Override
	public Component parseString(String lexeme) throws ServiceException {
		if (lexeme == null) {
			throw new ServiceException("Cannot parse null string");
		}
		Component component = new Composite(componentType);
		StringBuilder tempWord = new StringBuilder();
		for (int i = 0; i < lexeme.length(); i++) {
			handleSymbol(lexeme, component, tempWord, i);
		}
		return component;
	}

	private void handleSymbol(String lexeme, Component component, StringBuilder tempWord, int index)
			throws ServiceException {
		if (Character.isLetter(lexeme.charAt(index))) {
			tempWord.append(lexeme.charAt(index));
			if ((index + 1) < lexeme.length() && !Character.isLetter(lexeme.charAt(index + 1))) {
				component.addComponent(nextParser.parseString(tempWord.toString()));
				tempWord.setLength(ZERO_LENGTH);
			}
		} else {
			component.addComponent(new Leaf(lexeme.charAt(index)));
		}
	}

}
