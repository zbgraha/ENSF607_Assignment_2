import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;

public class TicTacToeServer {

	private Socket aSocketPlayerX, aSocketPlayerO;
	private ServerSocket serverSocket;
	private ObjectOutputStream socketOutPlayerX, socketOutPlayerO;
	private ObjectInputStream socketInPlayerX, socketInPlayerO;
	
	private ExecutorService pool;

	public TicTacToeServer() {
		try {
			serverSocket = new ServerSocket(9898);
			pool = Executors.newFixedThreadPool(3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runServer () {
		try {
			while (true) {
				
				System.out.println("Server Active! \nWaiting for Players...");
				aSocketPlayerX = serverSocket.accept();
				System.out.println("Connection accepted by server!\n"
						+ "Waiting for another player...");
				socketOutPlayerX = new ObjectOutputStream((aSocketPlayerX.getOutputStream()));
				socketInPlayerX = new ObjectInputStream(aSocketPlayerX.getInputStream());
				
				aSocketPlayerO = serverSocket.accept();
				System.out.println("Connection accepted by server. "
						+ "\nWaiting for Name Entry");
				socketOutPlayerO = new ObjectOutputStream((aSocketPlayerO.getOutputStream()));
				socketInPlayerO = new ObjectInputStream(aSocketPlayerO.getInputStream());
				Game game = new Game (socketInPlayerX, socketOutPlayerX, socketInPlayerO, socketOutPlayerO);
				System.out.println("Game is beginning!");
				pool.execute(game);
			}
		} catch (Exception e) {
			System.out.println("There was an error");
		}
		
		try {
			socketInPlayerX.close();
			socketOutPlayerO.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void main(String[] args) throws IOException {

		TicTacToeServer myServer = new TicTacToeServer();
		myServer.runServer();
	}

}
