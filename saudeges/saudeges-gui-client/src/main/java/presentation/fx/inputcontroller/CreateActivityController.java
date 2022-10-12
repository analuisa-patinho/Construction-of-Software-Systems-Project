package presentation.fx.inputcontroller;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import facade.exceptions.ActivityAlreadyExistsException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidNumParticipantsException;
import facade.exceptions.InvalidNumSessionsException;
import facade.remote.IActivityServiceRemote;
import facade.remote.ICreateActivityServiceRemote;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.CreateActivityModel;
import presentation.fx.model.SpecialtyModel;

public class CreateActivityController extends BaseController {

	private CreateActivityModel createActivityModel;

	private ICreateActivityServiceRemote createActivityService;
	//private IActivityServiceRemote activityService;

	private Scene mainMenuScene;

	@FXML
	private ComboBox<SpecialtyModel> specialtyComboBox;

	@FXML
	private TextField activityNameTextField;

	@FXML
	private TextField durationTextField;
	
	@FXML
	private TextField priceTextField;
	
	@FXML
	private TextField maxParticipantsTextField;
	
	@FXML
	private TextField sessionsNumberTextField;
	
	@FXML
	private CheckBox isRegularCheckBox;
	

	@FXML
	private Button mainMenuButton;

	public void setCreateActivityService(ICreateActivityServiceRemote createActivityService) {
		this.createActivityService = createActivityService;
	}

	public void setModel(CreateActivityModel createActivityModel) {
		this.createActivityModel = createActivityModel;
		specialtyComboBox.setItems(createActivityModel.getSpecialties());
		specialtyComboBox.setValue(createActivityModel.getSelectedSpecialty());
		activityNameTextField.textProperty().bindBidirectional(createActivityModel.activityNameProperty());
		durationTextField.textProperty().bindBidirectional(createActivityModel.durationProperty(),
				new NumberStringConverter());
		priceTextField.textProperty().bindBidirectional(createActivityModel.priceProperty(),
				new NumberStringConverter());
		//ENABLE TEXTFIELD IF CHECKBOX IS SELETED
		maxParticipantsTextField.disableProperty().bind(isRegularCheckBox.selectedProperty().not());
		maxParticipantsTextField.textProperty().bindBidirectional(createActivityModel.maxParticipantsProperty(),
				new NumberStringConverter());
		sessionsNumberTextField.textProperty().bindBidirectional(createActivityModel.sessionsNumberProperty(),
				new NumberStringConverter());
		
		
	}

	@FXML
	private void initialize() {
		configureBox(isRegularCheckBox);
		
		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) {
				return change;
			}
			return null;
		};

		priceTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
		durationTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
		//maxParticipantsTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 1, integerFilter));
		sessionsNumberTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
	}

	private void configureBox(CheckBox isRegularCheckBox2) {
		//if(isRegularCheckBox.isSelected())
			//isRegularCheckBox.setSelected(true);
		
		isRegularCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
				if(isNowSelected) {
					createActivityModel.setIsRegular(true);
				} else {
					createActivityModel.setIsRegular(false);
				}		
		});
		
	}

	@FXML
	void createActivityAction(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				if(createActivityModel.getSelectedIsRegular()) {
					createActivityService.addActivity(createActivityModel.getActivityName(),
							createActivityModel.getSelectedSpecialty().getName(),
							createActivityModel.getSelectedIsRegular(),
							createActivityModel.getSessionsNumber(),
							createActivityModel.getDuration(), new Long (createActivityModel.getPrice()),
							createActivityModel.getMaxParticipants());
				} else {
					createActivityService.addActivity(createActivityModel.getActivityName(),
							createActivityModel.getSelectedSpecialty().getName(),
							createActivityModel.getSelectedIsRegular(),
							createActivityModel.getSessionsNumber(),
							createActivityModel.getDuration(), new Long (createActivityModel.getPrice()));
				}
				createActivityModel.clearProperties();
				specialtyComboBox.getSelectionModel().clearSelection();
				isRegularCheckBox.setSelected(false); //VER SE NECESSÁRIO COM O clearProperties();
			
			showInfo(i18nBundle.getString("new.activity.success"));

			} catch (ActivityAlreadyExistsException e) {
				showError(i18nBundle.getString("new.activity.error.activityInvalid") + ": " + e.getMessage());
			} catch (InvalidDurationException e) {
				showError(i18nBundle.getString("new.activity.error.duration") + ": " + e.getMessage());
			} catch(InvalidNumSessionsException e) {
				showError(i18nBundle.getString("new.activity.error.sessionsNumber") + ": " + e.getMessage());
			} catch (InvalidNumParticipantsException e) {
				showError(i18nBundle.getString("new.activity.error.numParticipants") + ": " + e.getMessage());
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("new.activity.error.adding") + ": " + e.getMessage());
			}
		} else {
			showError(i18nBundle.getString("new.activity.error.validating") + ":\n" + errorMessages);
		}
	}

	private String validateInput() {
		StringBuilder sb = new StringBuilder();
		String activityName = createActivityModel.getActivityName();
		if (activityName == null || activityName.length() == 0)
			sb.append(i18nBundle.getString("new.activity.invalid.designation"));

		//CHECK CHECK
		if (createActivityModel.getPrice() == 0 ) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.lesson.invalid.price"));
		}

		if (createActivityModel.getDuration() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.activity.invalid.duration"));
		}

		if (createActivityModel.getSelectedSpecialty() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.activity.invalid.specialty"));
		}

		// CHECK CHECK
		if (createActivityModel.getSelectedIsRegular() && createActivityModel.getSessionsNumber() > 5 && createActivityModel.getSessionsNumber() < 1) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.activity.invalidSessionsNumber.daysOfWeek"));
		}
		
		if (!createActivityModel.getSelectedIsRegular() && createActivityModel.getSessionsNumber() > 20 && createActivityModel.getSessionsNumber() < 1) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.activity.invalidSessionsNumber.daysOfWeek"));
		}
		
		if(createActivityModel.getSessionsNumber() > 20 && createActivityModel.getSessionsNumber() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.activity.invalidSessionsNumber.daysOfWeek"));
		}

		return sb.toString();
	}

	@FXML
	void specialtySelected(ActionEvent event) {
		createActivityModel.setSelectedSpecialty(specialtyComboBox.getValue());
	}

	public void setMenuScene(Scene mainMenuScene) {
		this.mainMenuScene = mainMenuScene;
	}

	@FXML
	void navigateToMainMenu(ActionEvent event) {
		Stage mainMenuStage = (Stage) mainMenuButton.getScene().getWindow();
		mainMenuStage.setScene(mainMenuScene);
	}

}
