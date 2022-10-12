package presentation.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.remote.ICreateActivityServiceRemote;
import facade.remote.ISetScheduleServiceRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.CreateActivityController;
import presentation.fx.inputcontroller.MainMenuController;
import presentation.fx.inputcontroller.SelectActivityController;
import presentation.fx.inputcontroller.SetInstructorController;
import presentation.fx.inputcontroller.SetSessionsController;
import presentation.fx.model.CreateActivityModel;
import presentation.fx.model.SetScheduleModel;

public class Startup extends Application {
    
	 private static ICreateActivityServiceRemote createActivityService;
	 private static ISetScheduleServiceRemote setNewScheduleService;

	@Override 
    public void start(Stage stage) throws IOException {
    
		// This line to resolve keys against Bundle.properties
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
        //ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), i18nBundle);
        Parent root = mainLoader.load();
        Scene menuScene = new Scene(root, 736, 342);
        MainMenuController mainMenuController = mainLoader.getController();
		mainMenuController.setI18NBundle(i18nBundle);

		
        FXMLLoader createActivityLoader = new FXMLLoader(getClass().getResource("/fxml/createActivity.fxml"), i18nBundle);
    	Parent createActivityPane = createActivityLoader.load();
    	Scene createActivityScene = new Scene(createActivityPane, 736, 342);
    	CreateActivityController newActivityController = createActivityLoader.getController();        
    	CreateActivityModel newActivityModel = new CreateActivityModel(createActivityService);
    	newActivityController.setCreateActivityService(createActivityService);
    	newActivityController.setI18NBundle(i18nBundle);
    	newActivityController.setModel(newActivityModel);
    	
    	FXMLLoader setNewScheduleLoader = new FXMLLoader(getClass().getResource("/fxml/selectRegularActivity.fxml"), i18nBundle);    	
    	Parent setSchedulePane = setNewScheduleLoader.load();
    	Scene selectActivityScene = new Scene(setSchedulePane, 736, 342);
    	SelectActivityController selectActivityController = setNewScheduleLoader.getController();
    	SetScheduleModel setScheduleModel = new SetScheduleModel(setNewScheduleService);
    	selectActivityController.setModel(setScheduleModel);
    	selectActivityController.setSetScheduleService(setNewScheduleService);
    	selectActivityController.setI18NBundle(i18nBundle);
    	
    	/**Scene setSessionsScene = new Scene(setSchedulePane, 736, 342);
    	SetSessionsController setSessionsController = setNewScheduleLoader.getController();
    	setSessionsController.setModel(setScheduleModel);
    	setSessionsController.setSetScheduleService(setNewScheduleService);
    	setSessionsController.setI18NBundle(i18nBundle);
    	
    	Scene setInstructorScene = new Scene(setSchedulePane, 736, 342);
    	SetInstructorController setInstructorController = setNewScheduleLoader.getController();
    	setInstructorController.setModel(setScheduleModel);
    	setInstructorController.setSetScheduleService(setNewScheduleService);
    	setInstructorController.setI18NBundle(i18nBundle);**/
    	
    	
    	mainMenuController.setCreateActivityScene(createActivityScene);
		mainMenuController.setScheduleRegularActivityScene(selectActivityScene);
		newActivityController.setMenuScene(menuScene);
		selectActivityController.setMenuScene(menuScene);
		//selectActivityController.setNext1(setSessionsScene);
		//setSessionsController.setMenuScene(menuScene);
		//setSessionsController.setScene1(selectActivityScene);
		//setSessionsController.setScene2(setInstructorScene);
		//setInstructorController.setMenuScene(menuScene);
		//setInstructorController.setPreviousScene(setSessionsScene);
              
        stage.setTitle(i18nBundle.getString("application.title"));
        stage.setScene(menuScene);
        stage.show();
    }
	
	public static void startGUI(ICreateActivityServiceRemote createActivityService, ISetScheduleServiceRemote setNewScheduleService) {
		Startup.createActivityService = createActivityService;
		Startup.setNewScheduleService = setNewScheduleService;
        launch();
	}
}
