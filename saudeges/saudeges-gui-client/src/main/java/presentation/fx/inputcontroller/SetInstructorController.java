package presentation.fx.inputcontroller;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import facade.exceptions.ApplicationException;
import facade.exceptions.InstructorNotFoundException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidInstructorException;
import facade.remote.IActivityServiceRemote;
import facade.remote.ISetScheduleServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.InstructorModel;
import presentation.fx.model.RegularModel;
import presentation.fx.model.SetScheduleModel;

public class SetInstructorController extends BaseController {

private SetScheduleModel setScheduleModel;
	
	private ISetScheduleServiceRemote setSchedulerService;
	//private IActivityServiceRemote activityService;
	
	private Scene mainMenuScene;
	private Scene setSessionsScene;
	
	@FXML
	private Button mainMenuButton;
	
	//@FXML
	//private Button finishButton;
	
	@FXML
	private Button previousButton;
	
	@FXML
	private ComboBox<InstructorModel> instructorComboBox;
	
	@FXML
	private TextField instructorTime;
	
	public void setSetScheduleService(ISetScheduleServiceRemote setSchedulerService) {
		this.setSchedulerService = setSchedulerService;
	}
	
	public void setModel(SetScheduleModel setScheduleModel) {
		this.setScheduleModel = setScheduleModel;
		instructorComboBox.setItems(setScheduleModel.getInstructors());
		instructorComboBox.setValue(setScheduleModel.getSelectedInstructor());
		instructorTime.textProperty().bindBidirectional(setScheduleModel.instructorTimeProperty(),
				new NumberStringConverter());
	}
	
	@FXML
	void instructorSelected(ActionEvent event) {
		setScheduleModel.setSelectedInstructor(instructorComboBox.getValue());
	}
	
	
	@FXML
	private void initialize() {
		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) {
				return change;
			}
			return null;
		};

		instructorTime.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));	
	}
	
	
	@FXML
	void setScheduleAction(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				setSchedulerService.setInstructor(setScheduleModel.getSelectedInstructor().getId(), setScheduleModel.getInstructorTime());
				setScheduleModel.clearProperties3();
				instructorComboBox.getSelectionModel().clearSelection();
				showInfo(i18nBundle.getString("set.schedule.success"));

			} catch (InvalidInstructorException | InstructorNotFoundException e) {
				showError(i18nBundle.getString("set.schedule.error.instructorInvalid") + ": " + e.getMessage());
			} catch (InvalidDurationException e) {
				showError(i18nBundle.getString("set.schedule.error.duration") + ": " + e.getMessage());
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("set.schedule.error.setting") + ": " + e.getMessage());
			}
		} else {
			showError(i18nBundle.getString("set.schedule.error.validating") + ":\n" + errorMessages);
		}
	}
	
	private String validateInput() {
		StringBuilder sb = new StringBuilder();

		if (setScheduleModel.getInstructorTime() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("set.schedule.invalid.instructor.time"));
		}

		if (setScheduleModel.getSelectedInstructor() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("set.schedule.invalid.instructor"));
		}

		return sb.toString();
	}
	
	
	public void setMenuScene(Scene mainMenuScene) {
		this.mainMenuScene = mainMenuScene;
	}

	@FXML
	void navigateToMainMenu(ActionEvent event) {
		Stage mainMenuStage = (Stage) mainMenuButton.getScene().getWindow();
		mainMenuStage.setScene(mainMenuScene);
	}
	
	public void setPreviousScene(Scene setSessionsScene) {
		this.setSessionsScene = setSessionsScene;
	}
	
	@FXML
	void navigateToPrevious(ActionEvent event) {
		Stage previousStage = (Stage) previousButton.getScene().getWindow();
		previousStage.setScene(setSessionsScene);
	}
	
}
