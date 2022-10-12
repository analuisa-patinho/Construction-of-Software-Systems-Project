package presentation.fx.model;

import java.time.DayOfWeek;
import java.time.LocalDate;

//import business.activity.Activity;
//import business.activity.RegularActivity;
import facade.exceptions.ApplicationException;
import facade.remote.IActivityServiceRemote;
import facade.remote.ISetScheduleServiceRemote;
import presentation.fx.model.*;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SetScheduleModel {
	
	//ISetScheduleServiceRemote setNewScheduleService
	
	//SCENE 1
	private final ObservableList<RegularModel> activities;
	private final ObjectProperty<RegularModel> currentActivity;
	private final ObjectProperty<LocalDate> startDate;
	
	//SCENE 2
	private final IntegerProperty duration;
	private final ObservableList<String> selectedDaysOfWeek;
	private final ObservableList<String> times;
	
	//VER VER --SÓ PARA O CLEAR
	private final IntegerProperty session1Hour;
	private final IntegerProperty session2Hour;
	private final IntegerProperty session3Hour;
	private final IntegerProperty session4Hour;
	private final IntegerProperty session5Hour;
	private final IntegerProperty session1Minutes;
	private final IntegerProperty session2Minutes;
	private final IntegerProperty session3Minutes;
	private final IntegerProperty session4Minutes;
	private final IntegerProperty session5Minutes;
	
	//SCENE 3
	private final ObservableList<InstructorModel> instructors;
	private final ObjectProperty<InstructorModel> selectedInstructor;
	private final IntegerProperty instructorTime;

	public SetScheduleModel(ISetScheduleServiceRemote setNewScheduleService) {
		activities = FXCollections.observableArrayList();
		instructors = FXCollections.observableArrayList();
		try {
			setNewScheduleService.getRegularActivities().forEach(a -> activities.add(new RegularModel(a.getName(), a.getNumSessions(), a.getMaxParticipants())));
			setNewScheduleService.chooseTrainer().forEach(i -> instructors.add(new InstructorModel(i.getId(), i.getName())));
		} catch (ApplicationException e) {
			// no discounts added
		}
		currentActivity = new SimpleObjectProperty<>(null);
		selectedInstructor = new SimpleObjectProperty<>(null);
		
		//SCENE 2
		selectedDaysOfWeek = FXCollections.observableArrayList();
		times = FXCollections.observableArrayList();
		startDate = new SimpleObjectProperty(null);
		duration = new SimpleIntegerProperty();
		instructorTime = new SimpleIntegerProperty();
		
		//VER VER --SÓ PARA O CLEAR
		session1Hour = new SimpleIntegerProperty();
		session2Hour = new SimpleIntegerProperty();
		session3Hour = new SimpleIntegerProperty();
		session4Hour = new SimpleIntegerProperty();
		session5Hour = new SimpleIntegerProperty();
		session1Minutes = new SimpleIntegerProperty();
		session2Minutes = new SimpleIntegerProperty();
		session3Minutes = new SimpleIntegerProperty();
		session4Minutes = new SimpleIntegerProperty();
		session5Minutes = new SimpleIntegerProperty();
	}

	//SCENE 1
	public ObservableList<RegularModel> getActivities() {
		return activities;
	}
	
	public ObjectProperty<RegularModel> currentActivityProperty() {
		return currentActivity;
	}
	
	public final void setCurrentActivity(RegularModel a) {
		currentActivityProperty().set(a);
	}
	
	public void clearProperties1() {
		currentActivity.set(null);
	}
	
	public final RegularModel getCurrentActivity() {
		return currentActivityProperty().get();
	}
	
	//SCENE 2
	public ObservableList<String> getSelectedDaysOfWeek() {
		return selectedDaysOfWeek;
	}
	
	public ObservableList<String> getTimes() {
		return times;
	}
	

	public ObjectProperty<LocalDate> getStartDateProperty() {
		return startDate;
	}
	
	public LocalDate getStartDate() {
		return startDate.get();
	}
	
	public int getNumSessions() {
		return getCurrentActivity().getNumSessions();
	}
	
	public int getDuration() {
		return duration.get();
	}

	public IntegerProperty durationProperty() {
		return duration;
	}
	
	public void clearProperties2() {
		startDate.set(null);
		duration.set(0);
		//ver
		session1Hour.set(0);
		session2Hour.set(0);
		session3Hour.set(0);
		session4Hour.set(0);
		session5Hour.set(0);
		session1Minutes.set(0);
		session2Minutes.set(0);
		session3Minutes.set(0);
		session4Minutes.set(0);
		session5Minutes.set(0);
	}
	
	//SCENE 3
	
	public ObservableList<InstructorModel> getInstructors() {
		return instructors;
	}
	
	public ObjectProperty<InstructorModel> selectedInstructorProperty() {
		return selectedInstructor;
	}
	
	public final void setSelectedInstructor(InstructorModel i) {
		selectedInstructorProperty().set(i);
	}
	
	public final InstructorModel getSelectedInstructor() {
		return selectedInstructor.get();
	}
	
	public int getInstructorTime() {
		return instructorTime.get();
	}

	public IntegerProperty instructorTimeProperty() {
		return instructorTime;
	}
	
	public void clearProperties3() {
		selectedInstructor.set(null);
		instructorTime.set(0);
		
	}
	
	
}
