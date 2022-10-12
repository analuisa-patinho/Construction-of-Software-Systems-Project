package presentation.fx.inputcontroller;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDateException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidInstanceException;
import facade.remote.IActivityServiceRemote;
import facade.remote.ISetScheduleServiceRemote;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.SetScheduleModel;

public class SetSessionsController extends BaseController {

	private SetScheduleModel setScheduleModel;
	
	private ISetScheduleServiceRemote setSchedulerService;
	//private IActivityServiceRemote activityService;
	
	private Scene mainMenuScene;
	private Scene selectActivityScene;
	private Scene setInstructorScene;
	
	@FXML
	private Button mainMenuButton;
	@FXML
	private Button nextButton2;
	@FXML
	private Button previousButton1;

	@FXML
	private CheckBox sunday;
	@FXML
	private CheckBox monday;
	@FXML
	private CheckBox tuesday;
	@FXML
	private CheckBox wednesday;
	@FXML
	private CheckBox thursday;
	@FXML
	private CheckBox friday;
	@FXML
	private CheckBox saturday;

	@FXML
	private TextField session1Hour;
	@FXML
	private TextField session2Hour;
	@FXML
	private TextField session3Hour;
	@FXML
	private TextField session4Hour;
	@FXML
	private TextField session5Hour;
	
	@FXML
	private TextField session1Minutes;
	@FXML
	private TextField session2Minutes;
	@FXML
	private TextField session3Minutes;
	@FXML
	private TextField session4Minutes;
	@FXML
	private TextField session5Minutes;
	
	@FXML
	private DatePicker startDatePicker;
	
	@FXML
	private TextField monthDuration;
	
	private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
    private ObservableList<String> times = FXCollections.observableArrayList();

    private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

    private int maxNumSelected; 
	
	public void setSetScheduleService(ISetScheduleServiceRemote setSchedulerService) {
		this.setSchedulerService = setSchedulerService;
	}
	
	public void setModel(SetScheduleModel setScheduleModel) {
		this.setScheduleModel = setScheduleModel;
		//setScheduleModel.currentActivityProperty().addListener((obs, oldSelection, newSelection) -> initialize());
		startDatePicker.valueProperty().bindBidirectional(setScheduleModel.getStartDateProperty());	
		monthDuration.textProperty().bindBidirectional(setScheduleModel.durationProperty(),
				new NumberStringConverter());
		maxNumSelected = setScheduleModel.getNumSessions();
	}
	

	@FXML
	private void initialize() {
		configureCheckBox(sunday);
		configureCheckBox(monday);
		configureCheckBox(tuesday);
		configureCheckBox(wednesday);
		configureCheckBox(thursday);
		configureCheckBox(friday);
		configureCheckBox(saturday);
		
		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) {
				return change;
			}
			return null;
		};
		
		//Bloqueia checkbox restantes qnd o nr de checkboxes selected=nrSessoes
		numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
			if (newSelectedCount.intValue() >= maxNumSelected) {
		        unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
		    } else {
		    	unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
		    }
		});
		
		startDatePicker.getEditor().setDisable(true);
		monthDuration.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
		session1Hour.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		session1Minutes.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		session2Hour.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		session2Minutes.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		session3Hour.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		session3Minutes.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		session4Hour.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		session4Minutes.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		session5Hour.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		session5Minutes.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 00, integerFilter));
		
		configureTimes();
		
		passToModelWeekdaysAndTime();
	}


	private void configureCheckBox(CheckBox checkBox) {
		if (checkBox.isSelected()) {
            selectedCheckBoxes.add(checkBox);
        } else {
            unselectedCheckBoxes.add(checkBox);
        }

		checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                unselectedCheckBoxes.remove(checkBox);
                selectedCheckBoxes.add(checkBox);
            } else {
                selectedCheckBoxes.remove(checkBox);
                unselectedCheckBoxes.add(checkBox);
            }

        });
	}
	
	
	private void configureTimes() {
		
		if(maxNumSelected == 1) {
			session1Hour.setDisable(false);
			session2Hour.setDisable(true);
			session3Hour.setDisable(true);
			session4Hour.setDisable(true);
			session5Hour.setDisable(true);
			
			session1Minutes.setDisable(false);
			session2Minutes.setDisable(true);
			session3Minutes.setDisable(true);
			session4Minutes.setDisable(true);
			session5Minutes.setDisable(true);
			
		}else if(maxNumSelected == 2) {
			session1Hour.setDisable(false);
			session2Hour.setDisable(false);
			session3Hour.setDisable(true);
			session4Hour.setDisable(true);
			session5Hour.setDisable(true);
			
			session1Minutes.setDisable(false);
			session2Minutes.setDisable(false);
			session3Minutes.setDisable(true);
			session4Minutes.setDisable(true);
			session5Minutes.setDisable(true);
			
		}else if(maxNumSelected == 3) {
			session1Hour.setDisable(false);
			session2Hour.setDisable(false);
			session3Hour.setDisable(false);
			session4Hour.setDisable(true);
			session5Hour.setDisable(true);
			
			session1Minutes.setDisable(false);
			session2Minutes.setDisable(false);
			session3Minutes.setDisable(false);
			session4Minutes.setDisable(true);
			session5Minutes.setDisable(true);
			
		
		}else if(maxNumSelected == 4) {
			session1Hour.setDisable(false);
			session2Hour.setDisable(false);
			session3Hour.setDisable(false);
			session4Hour.setDisable(false);
			session5Hour.setDisable(true);
			
			session1Minutes.setDisable(false);
			session2Minutes.setDisable(false);
			session3Minutes.setDisable(false);
			session4Minutes.setDisable(false);
			session5Minutes.setDisable(true);
			
		}else {
			session1Hour.setDisable(false);
			session2Hour.setDisable(false);
			session3Hour.setDisable(false);
			session4Hour.setDisable(false);
			session5Hour.setDisable(false);
			
			session1Minutes.setDisable(false);
			session2Minutes.setDisable(false);
			session3Minutes.setDisable(false);
			session4Minutes.setDisable(false);
			session5Minutes.setDisable(false);
		}
	}
	
	
	private void passToModelWeekdaysAndTime() {
		for(CheckBox b : selectedCheckBoxes) {
        	setScheduleModel.getSelectedDaysOfWeek().add(b.getId());
        }
		
		if(maxNumSelected == 1) {
			setScheduleModel.getTimes().add(session1Hour.getText()+":"+session1Minutes.getText());
		}else if(maxNumSelected == 2) {
			setScheduleModel.getTimes().addAll(session1Hour.getText()+":"+session1Minutes.getText(),
					session2Hour.getText()+":"+session2Minutes.getText());
		}else if(maxNumSelected == 3) {
			setScheduleModel.getTimes().addAll(session1Hour.getText()+":"+session1Minutes.getText(),
					session2Hour.getText()+":"+session2Minutes.getText(),
					session3Hour.getText()+":"+session3Minutes.getText());
		}else if(maxNumSelected == 4) {
			setScheduleModel.getTimes().addAll(session1Hour.getText()+":"+session1Minutes.getText(),
					session2Hour.getText()+":"+session2Minutes.getText(),
					session3Hour.getText()+":"+session3Minutes.getText(),
					session4Hour.getText()+":"+session4Minutes.getText());
		}else {
			setScheduleModel.getTimes().addAll(session1Hour.getText()+":"+session1Minutes.getText(),
					session2Hour.getText()+":"+session2Minutes.getText(),
					session3Hour.getText()+":"+session3Minutes.getText(),
					session4Hour.getText()+":"+session4Minutes.getText(),
					session5Hour.getText()+":"+session5Minutes.getText());
		}
	}
	
	
	public void setScene2(Scene setInstructorScene) {
		this.setInstructorScene = setInstructorScene;
	}
	
	@FXML
	public void navigateToSetInstructor(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				setSchedulerService.setSchedule(setScheduleModel.getSelectedDaysOfWeek().stream().collect(Collectors.toList())
						.toArray(new String[setScheduleModel.getSelectedDaysOfWeek().size()]),
						setScheduleModel.getTimes().stream().collect(Collectors.toList())
						.toArray(new String[setScheduleModel.getSelectedDaysOfWeek().size()]),
						setScheduleModel.getStartDate(), setScheduleModel.getDuration());
				/*setScheduleModel.clearProperties2();
				sunday.setSelected(false);
				monday.setSelected(false);
				tuesday.setSelected(false);
				wednesday.setSelected(false);
				thursday.setSelected(false);
				friday.setSelected(false);
				saturday.setSelected(false);*/
				showInfo(i18nBundle.getString("Instances.Dates.success"));

			} catch (InvalidDateException e) {
				showError(i18nBundle.getString("set.schedule.error.dateInvalid") + ": " + e.getMessage());
			} catch (InvalidDurationException e) {
				showError(i18nBundle.getString("set.schedule.error.duration") + ": " + e.getMessage());
			} catch (InvalidInstanceException e) {
				showError(i18nBundle.getString("set.schedule.error.instanceInvalid") + ": " + e.getMessage());
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("set.schedule.error.setting") + ": " + e.getMessage());
			}
		} else {
			showError(i18nBundle.getString("set.schedule.error.validating") + ":\n" + errorMessages);
		}
		
		Stage selectInstructorStage = (Stage) nextButton2.getScene().getWindow();
		selectInstructorStage.setScene(setInstructorScene );
	}
	
	private String validateInput() {
		StringBuilder sb = new StringBuilder();

		//VER COMO VERIFICAR SE HORAS VALIDAS

		if (setScheduleModel.getDuration() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("set.schedule.invalid.duration"));
		}

		if (setScheduleModel.getSelectedDaysOfWeek() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("set.schedule.invalid.daysOfWeek"));
		}

		return sb.toString();
	}
	
	
	public void setScene1(Scene selectActivityScene) {
		this.selectActivityScene = selectActivityScene;
	}
	
	@FXML
	public void navigateToScene1(ActionEvent event) {
		Stage selectActivityStage = (Stage) previousButton1.getScene().getWindow();
		selectActivityStage.setScene(selectActivityScene);
	}
	
	
	public void setMenuScene(Scene mainMenuScene) {
		this.mainMenuScene = mainMenuScene;
	}
	
	@FXML
	public void navigateToMainMenu(ActionEvent event) {
		Stage mainMenuStage = (Stage) mainMenuButton.getScene().getWindow();
		mainMenuStage.setScene(mainMenuScene);
	}
}
