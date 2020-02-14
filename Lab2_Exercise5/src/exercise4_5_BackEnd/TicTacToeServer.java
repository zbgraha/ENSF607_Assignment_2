package exercise4_5_BackEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicTacToeServer {
	
	private ServerSocket serverSocket;
	
	private ExecutorService threadPool;
	
	
	public TicTacToeServer(int portNum) {
		try {
			serverSocket = new ServerSocket(portNum);
			threadPool = Executors.newFixedThreadPool(2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runServer() {
		
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

	
	
	public static void main(String[] args) {
		
		TicTacToeServer testServer = new TicTacToeServer(9899);
		testServer.runServer();
		
	}
}
