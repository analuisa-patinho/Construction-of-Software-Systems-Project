package presentation.fx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SpecialtyModel {
	private final StringProperty name = new SimpleStringProperty();
	private final int id;
	
	public SpecialtyModel(int id, String name) {
		this.id = id;
		setName(name);
	}

	public final StringProperty nameProperty() {
		return name;
	}

	public final int getId() {
		return id;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	@Override
	public String toString() {
		return getName();
	}

}
