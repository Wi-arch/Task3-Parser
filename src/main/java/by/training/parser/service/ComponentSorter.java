package by.training.parser.service;

import java.util.Comparator;

import by.training.parser.entity.Component;
import by.training.parser.entity.ComponentType;
import by.training.parser.exception.ServiceException;

public class ComponentSorter {

	private ComponentSorter() {
	}

	private static class ParagraphSorterBySentenceCountInstance {
		private static final ComponentSorter INSTANCE = new ComponentSorter();
	}

	public static ComponentSorter getInstance() {
		return ParagraphSorterBySentenceCountInstance.INSTANCE;
	}

	public void sortParagraphBySentenceCount(Component component) throws ServiceException {
		if (component == null || component.getComponentType() != ComponentType.TEXT) {
			throw new ServiceException("Invalid component");
		}
		component.getComponentList().sort(Comparator.comparing(Component::getNumberOfComponents));
	}
}
