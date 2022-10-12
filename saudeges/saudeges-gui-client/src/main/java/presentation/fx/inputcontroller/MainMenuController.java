package presentation.fx.inputcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController extends BaseController {

	private Scene createActivityScene;

	private Scene scheduleRegularActivityScene;

	@FXML
	private Button createActivityButton;

	@FXML
	private Button scheduleRegularActivityButton;

	public void setCreateActivityScene(Scene createActivityScene) {
		this.createActivityScene = createActivityScene;
	}

	public void setScheduleRegularActivityScene(Scene scheduleRegularActivityScene) {
		this.scheduleRegularActivityScene = scheduleRegularActivityScene;
	}

	@FXML
	void navigateCreateActivity(ActionEvent event) {
		Stage mainMenuStage = (Stage) createActivityButton.getScene().getWindow();
		mainMenuStage.setScene(createActivityScene);
	}

	@FXML
	void navigateScheduleRegular(ActionEvent event) {
		Stage mainMenuStage = (Stage) scheduleRegularActivityButton.getScene().getWindow();
		mainMenuStage.setScene(scheduleRegularActivityScene);
	}

}
