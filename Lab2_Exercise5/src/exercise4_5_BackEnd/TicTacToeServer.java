package exercise4_5_BackEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**Class with a server socket for tic tac toe. Accepts connection from 2 users
 * and then executes a thread of type GameController which handles the interactions
 * between the players.
 * @author Riley Berry and Zachary Graham
 *
 */
public class TicTacToeServer {
	
	private ServerSocket serverSocket;
	
	private ExecutorService threadPool;
	
	
	/**Constructor for the server. Intantiates the serverSocket and creates
	 * a thread pool of size 2 so that two games can be played concurrently.
	 * @param portNum
	 */
	public TicTacToeServer(int portNum) {
		try {
			serverSocket = new ServerSocket(portNum);
			threadPool = Executors.newFixedThreadPool(2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**Waits for two players to connect, establishes an input and output stream with each one, 
	 * then creates and executes a new GameController object using one of the server's threads
	 * 
	 */
	public void runServer() {
		System.out.println("Server started");
		try {
			Socket playerA = serverSocket.accept();
			ObjectOutputStream aPlayerOutput = new ObjectOutputStream(playerA.getOutputStream());
			ObjectInputStream aPlayerInput = new ObjectInputStream(playerA.getInputStream());
			
			System.out.println("Connection accepted from first player");
			
			Socket playerB = serverSocket.accept();
			ObjectOutputStream bPlayerOutput = new ObjectOutputStream(playerB.getOutputStream());
			ObjectInputStream bPlayerInput = new ObjectInputStream(playerB.getInputStream());
			System.out.println("Connection accepted from second player");
			
			
			GameController game = new GameController(aPlayerOutput, aPlayerInput, bPlayerOutput, bPlayerInput );
			threadPool.execute(game);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	/**Main function for starting the server.
	 * @param args
	 */
	public static void main(String[] args) {
		
		TicTacToeServer testServer = new TicTacToeServer(9899);
		testServer.runServer();
		
	}
}
