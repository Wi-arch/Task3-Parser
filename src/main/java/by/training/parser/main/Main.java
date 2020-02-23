package by.training.parser.main;

import org.apache.log4j.Logger;

import by.training.parser.entity.Component;
import by.training.parser.entity.ComponentType;
import by.training.parser.exception.ServiceException;
import by.training.parser.service.ComponentSorter;
import by.training.parser.service.parser.CommonParser;
import by.training.parser.service.parser.ExpressionParser;
import by.training.parser.service.parser.LexemeParser;
import by.training.parser.service.parser.Parser;
import by.training.parser.util.TextFileReader;

public class Main {

	private static final String SOURCE_FILE = "text.rtf";
	private static final String LINE_SEPARATOR = System.lineSeparator();
	private static final ComponentSorter SORTER = ComponentSorter.getInstance();
	private static final TextFileReader READER = TextFileReader.getInstance();
	private static final Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		String text = READER.getTextFromFile(SOURCE_FILE);
		System.out.println("Source text:" + LINE_SEPARATOR + text + LINE_SEPARATOR);

		Component component = null;
		try {
			Parser parser = initChainOfParsers();
			component = parser.parseString(text);
		} catch (ServiceException e) {
			LOGGER.error("Cannot parse string", e);
			throw new RuntimeException("Cannot parse string");
		}
		System.out.println("Components after replacing valid expressions:");
		component.printComponent();

		System.out.println(LINE_SEPARATOR + "Components after sorting paragraphs by sentence count:");
		try {
			SORTER.sortParagraphBySentenceCount(component);
		} catch (ServiceException e) {
			LOGGER.warn("Cannot sort text");
		}
		component.printComponent();
	}

	private static Parser initChainOfParsers() throws ServiceException {
		Parser wordParser = new CommonParser(ComponentType.WORD);
		Parser lexemParser = new LexemeParser(wordParser);
		Parser sentenceParser = new CommonParser(ComponentType.SENTENCE, lexemParser);
		Parser paragraphParser = new CommonParser(ComponentType.PARAGRAPH, sentenceParser);
		Parser textParser = new CommonParser(ComponentType.TEXT, paragraphParser);
		return new ExpressionParser(textParser);
	}
}