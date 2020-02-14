package clientSide;



/**Class with a main function to test the functionality of the tic tac toe game.
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
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
