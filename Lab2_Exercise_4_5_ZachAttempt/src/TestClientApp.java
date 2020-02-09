


/**Class with a main function to test the functionality of the tic tac toe game.
 * @author Zachary Graham
 *
 */
public class TestClientApp {

	public static void main(String[] args) {
		TicTacToeViewer view = new TicTacToeViewer();
		
		TicTacToeController game = new TicTacToeController(view);
		Client aClient = new Client ("localhost", 9898, game);
		game.setClient(aClient);
		aClient.communicate();
	}
}
