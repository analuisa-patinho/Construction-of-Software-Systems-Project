package client;

import javax.ejb.EJB;

import facade.remote.ICreateActivityServiceRemote;
import facade.remote.ISetScheduleServiceRemote;


public class Main {
	
	// 1. confirm that in saudeges project directory, you have saudege-ear/target/saudeges-ear-1.0.ear
	// you can get a copy from from PATH-TO-WILDFLY/standalone/deployments or 
	// make Eclipse produce it by using export from the context menu, over saude-ges-ear project
	// 2. run with the following command inside the saudeges project directory:
	// PATH-TO-WILDFLY/bin/appclient.sh saudeges-ear/target/saudeges-ear-1.0.ear#saudeges-gui-client.jar
	// Example in case the wildfly server is in your homedir in dir wildfly-10.1.0.Final
	// ~/wildfly-10.1.0.Final/bin/appclient.sh saudeges-ear/target/saudeges-ear-1.0.ear#saudeges-gui-client.jar
	
    @EJB
    private static ICreateActivityServiceRemote createActivityService;
    
    @EJB
    private static ISetScheduleServiceRemote setNewScheduleService;

	/**
	 * An utility class should not have public constructors
	 */
	private Main() {
	}

    /**
     * A simple interaction with the application services
     *
     * @param args Command line parameters
     */
    public static void main(String[] args) {
    	presentation.fx.Startup.startGUI(createActivityService, setNewScheduleService);
    }
}