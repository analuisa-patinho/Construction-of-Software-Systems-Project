package presentation.fx.model;

import java.util.Collection;
import java.util.List;

import facade.exceptions.ApplicationException;
import facade.remote.IActivityServiceRemote;
import facade.remote.ICreateActivityServiceRemote;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateActivityModel {
	
	private final ObservableList<SpecialtyModel> specialties;
	private final ObjectProperty<SpecialtyModel> selectedSpecialty;

	private final StringProperty activityName;

	//private final ObservableList<String> selectedIsRegular;

	private final ObjectProperty<Boolean> selectedIsRegular;
	private final IntegerProperty duration;
	private final IntegerProperty price;
	private final IntegerProperty maxParticipants;
	private final IntegerProperty sessionsNumber;
	
	public CreateActivityModel(ICreateActivityServiceRemote activityService) {
		specialties = FXCollections.observableArrayList();
		activityName = new SimpleStringProperty();
		//selectedIsRegular = FXCollections.observableArrayList();
		duration = new SimpleIntegerProperty();
		price = new SimpleIntegerProperty();
		maxParticipants = new SimpleIntegerProperty();
		sessionsNumber = new SimpleIntegerProperty();
		
		try {
			activityService.getSpecialities()
					.forEach(sm -> specialties.add(new SpecialtyModel(sm.getId(), sm.getDesignation())));
		} catch (ApplicationException e) {

		}
		selectedIsRegular = new SimpleObjectProperty<>();
		selectedSpecialty = new SimpleObjectProperty<>(null);
	}

	public ObjectProperty<SpecialtyModel> selectedSpecialtyProperty() {
		return selectedSpecialty;
	}

	public final SpecialtyModel getSelectedSpecialty() {
		return selectedSpecialty.get();
	}
	
	public final void setSelectedSpecialty(SpecialtyModel specialty) {
		selectedSpecialty.set(specialty);	
	}
	
	public ObservableList<SpecialtyModel> getSpecialties() {
		return specialties;
	}

	public String getActivityName() {
		return activityName.get();
	}
	
	public StringProperty activityNameProperty() {
		return activityName;
	}
	/**
	public ObservableList<String> getSelectedIsRegular() {
		return selectedIsRegular;
	}**/
	
	public int getDuration() {
		return duration.get();
	}

	public IntegerProperty durationProperty() {
		return duration;
	}
	
	public int getPrice() {
		return price.get();
	}

	public IntegerProperty priceProperty() {
		return price;
	}
	
	public boolean getSelectedIsRegular() {
		return selectedIsRegular.get();
	}
	
	//public BooleanProperty isRegularProperty() {
		//return selectedIsRegular;
	//}
	
	public final void setIsRegular(Boolean is) {
		selectedIsRegular.set(is);	
	}
	
	public int getMaxParticipants() {
		return maxParticipants.get();
	}
	
	public IntegerProperty maxParticipantsProperty() {
		return maxParticipants;
	}
	
	public int getSessionsNumber() {
		return sessionsNumber.get();
	}

	public IntegerProperty sessionsNumberProperty() {
		return sessionsNumber;
	}
	
	public void clearProperties() {
		activityName.set("");
		duration.set(0);
		price.set(0);
		maxParticipants.set(1);
		sessionsNumber.set(0);
		selectedSpecialty.set(null);
	}

}
