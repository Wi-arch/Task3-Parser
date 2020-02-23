package by.training.parser.entity;

import java.util.List;

public interface Component {

	void addComponent(Component component);

	Component getChildComponent(int index);

	boolean removeComponent(Component component);

	void printComponent();

	ComponentType getComponentType();

	int getNumberOfComponents();

	List<? extends Component> getComponentList();
}
