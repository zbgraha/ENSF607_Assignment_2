package exercise4_5_BackEnd;
/**
 * Application to run the server on port 9898
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
 *
 */
public class TicTacToeServerApp {

	public static void main (String [] args) {
		TicTacToeServer testServer = new TicTacToeServer(9899);
		testServer.runServer();
	}
}
