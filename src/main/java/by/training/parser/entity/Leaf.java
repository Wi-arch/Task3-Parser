package by.training.parser.entity;

import java.util.List;

public class Leaf implements Component {

	private static final ComponentType COMPONENT_TYPE = ComponentType.SYMBOL;
	private final Character character;

	public Leaf(Character character) {
		this.character = character;
	}

	@Override
	public void addComponent(Component component) {
		throw new UnsupportedOperationException("Cannot add component to leaf");
	}

	@Override
	public Component getChildComponent(int index) {
		throw new UnsupportedOperationException("Leaf does not contain components");
	}

	@Override
	public boolean removeComponent(Component component) {
		throw new UnsupportedOperationException("Cannot remove component from leaf");
	}

	@Override
	public ComponentType getComponentType() {
		return COMPONENT_TYPE;
	}

	@Override
	public void printComponent() {
		System.out.print(character);
	}

	@Override
	public String toString() {
		return character.toString();
	}

	@Override
	public int getNumberOfComponents() {
		throw new UnsupportedOperationException("Leaf does not contain components");
	}

	@Override
	public List<? extends Component> getComponentList() {
		throw new UnsupportedOperationException("Leaf does not contain components");
	}
}
