package by.training.parser.entity;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {

	private final ComponentType componentType;
	private final List<Component> componentList;

	public Composite(ComponentType componentType) {
		this.componentList = new ArrayList<>();
		this.componentType = componentType;
	}

	@Override
	public void addComponent(Component component) {
		componentList.add(component);
	}

	@Override
	public Component getChildComponent(int index) {
		return componentList.get(index);
	}

	@Override
	public boolean removeComponent(Component component) {
		return componentList.remove(component);
	}

	@Override
	public ComponentType getComponentType() {
		return componentType;
	}

	@Override
	public void printComponent() {
		for (Component component : componentList) {
			component.printComponent();
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Component component : componentList) {
			result.append(component.toString());
		}
		return result.toString();
	}

	@Override
	public int getNumberOfComponents() {
		return componentList.size();
	}

	@Override
	public List<? extends Component> getComponentList() {
		return componentList;
	}
}
