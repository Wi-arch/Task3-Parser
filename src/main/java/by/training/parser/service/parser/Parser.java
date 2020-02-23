package by.training.parser.service.parser;

import by.training.parser.entity.Component;
import by.training.parser.exception.ServiceException;

public interface Parser {

	Component parseString(String string) throws ServiceException;
}
