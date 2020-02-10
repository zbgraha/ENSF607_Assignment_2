package clientSide;



/**Class with a main function to test the functionality of the tic tac toe game.
 * @author Zachary Graham
 *
 */
public class TestClientManagementApp {

	public static void main(String[] args) {
		ClientManagementScreen view = new ClientManagementScreen();
		ConnectionWaitingScreen wait = new ConnectionWaitingScreen();
		
		ClientController controller = new ClientController(view, wait);
		Client aClient = new Client ("localhost", 9898, controller);
		controller.setClient(aClient);
		controller.addCloseWindowListener();
		
		aClient.communicate();
	}
}
