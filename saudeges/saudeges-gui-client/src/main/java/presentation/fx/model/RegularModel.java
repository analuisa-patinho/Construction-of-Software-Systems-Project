package presentation.fx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegularModel {
	
	private final StringProperty name = new SimpleStringProperty();
	//private final int id;
	private final IntegerProperty numSessions = new SimpleIntegerProperty();
	private final IntegerProperty maxParticipants = new SimpleIntegerProperty();

	public RegularModel(String name, int numSessions, int maxParticipants) {
		//this.id = 0;
		setName(name);
		setNumSessions(numSessions);
		setMaxParticipants(maxParticipants);
	}

	public final StringProperty nameProperty() {
		return name;
	}
	
	public final IntegerProperty numSessionsProperty() {
		return numSessions;
	}
	
	public final IntegerProperty maxParticipantsProperty() {
		return maxParticipants;
	}

	public final String getName() {
		return this.nameProperty().get();
	}
	
	public final int getNumSessions() {
		return this.numSessionsProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}
	
	public final void setNumSessions(final int numSessions) {
		this.numSessionsProperty().set(numSessions);
	}
	
	public final void setMaxParticipants(final int maxParticipants) {
		this.maxParticipantsProperty().set(maxParticipants);
	}

	@Override
	public String toString() {
		return getName();
	}


}
