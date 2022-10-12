package presentation.fx.inputcontroller;

import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.remote.IActivityServiceRemote;
import facade.remote.ISetScheduleServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import presentation.fx.model.RegularModel;
import presentation.fx.model.SetScheduleModel;

public class SelectActivityController extends BaseController {
	
	private SetScheduleModel setScheduleModel;
	
	private ISetScheduleServiceRemote setSchedulerService;
	//private IActivityServiceRemote activityService;
	
	private Scene mainMenuScene;
	
	private Scene setSessionsScene;

	@FXML
	private Button mainMenuButton;
	
	@FXML
	private Button nextButton1;
	
	@FXML
	private ComboBox<RegularModel> regularActivityComboBox;
	
	public void setSetScheduleService(ISetScheduleServiceRemote setSchedulerService) {
		this.setSchedulerService = setSchedulerService;
	}
	
	/**public void setModel(SetScheduleModel setScheduleModel) {
		this.setScheduleModel = setScheduleModel;
		regularActivityComboBox.setItems(setScheduleModel.getActivities());
		regularActivityComboBox.setValue(setScheduleModel.getCurrentActivity());
		//regularActivityComboBox.getSelectionModel().selectedItemProperty()
			//.addListener((obs, oldSelection, newSelection) -> setScheduleModel.setCurrentActivity(newSelection));
	}**/
	
	public void setModel(SetScheduleModel setScheduleModel) {
		this.setScheduleModel = setScheduleModel;
		regularActivityComboBox.setItems(setScheduleModel.getActivities());
		regularActivityComboBox.setValue(setScheduleModel.getCurrentActivity());
		//regularActivityComboBox.getSelectionModel().selectedItemProperty()
			//.addListener((obs, oldSelection, newSelection) -> setScheduleModel.setCurrentActivity(newSelection));
	}
	
	
	@FXML
	void activitySelected(ActionEvent event) {
		setScheduleModel.setCurrentActivity(regularActivityComboBox.getValue());
	}
	/**@FXML
	void activitySelected(ActionEvent event) {
		setScheduleModel.setCurrentActivity(regularActivityComboBox.getValue());
	}**/
	

	public void setNext1 (Scene setSessionsScene) {
		this.setSessionsScene = setSessionsScene;
	}
	
	@FXML
	public void navigateToSessions (ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				setSchedulerService.setActivity(setScheduleModel.getCurrentActivity().getName());
				//setScheduleModel.clearProperties1();
				//regularActivityComboBox.getSelectionModel().clearSelection();
				showInfo(i18nBundle.getString("selecting.activity.success"));
			} catch (ActivityNotFoundException e) {
				showError(i18nBundle.getString("set.schedule.error.activityInvalid") + ": " + e.getMessage());
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("set.schedule.error.setting") + ": " + e.getMessage());
			}
		} else {
			showError(i18nBundle.getString("set.schedule.error.validating") + ":\n" + errorMessages);
		}
		Stage setSessionsStage = (Stage) nextButton1.getScene().getWindow();
		setSessionsStage.setScene(setSessionsScene);
	}
	
	private String validateInput() {
		StringBuilder sb = new StringBuilder();

		if (setScheduleModel.getCurrentActivity() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("set.schedule.invalid.activity"));
		}

		return sb.toString();
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
