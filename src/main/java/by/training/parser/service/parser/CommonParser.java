package by.training.parser.service.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.training.parser.entity.Component;
import by.training.parser.entity.ComponentType;
import by.training.parser.entity.Composite;
import by.training.parser.exception.ServiceException;

public class CommonParser implements Parser {

	private ComponentType componentType;
	private Parser nextParser;

	public CommonParser(ComponentType componentType) throws ServiceException {
		if (componentType == null) {
			throw new ServiceException("Cannot init parser with null component type");
		}
		this.componentType = componentType;
		nextParser = new SymbolParser();
	}

	public CommonParser(ComponentType componentType, Parser nextParser) throws ServiceException {
		if (componentType == null || nextParser == null) {
			throw new ServiceException("Cannot init parser with null values");
		}
		this.componentType = componentType;
		this.nextParser = nextParser;
	}

	public Component parseString(String string) throws ServiceException {
		if (string == null) {
			throw new ServiceException("Cannot parse null string");
		}
		Component component = new Composite(componentType);
		Matcher matcher = Pattern.compile(componentType.getRegex()).matcher(string);
		while (matcher.find()) {
			component.addComponent(nextParser.parseString(matcher.group()));
		}
		return component;
	}
}
