package serverSide;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;

public class ServerController {

	private Socket aSocket;
	private ServerSocket serverSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private ClientDataBaseManager database;
	
	private ExecutorService pool;

	public ServerController(ClientDataBaseManager database) {
		try {
			this.database = database;
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
				
				System.out.println("Server Active! \nWaiting for Connection...");
				aSocket = serverSocket.accept();
				System.out.println("Connection accepted by server!");
				socketOut = new ObjectOutputStream((aSocket.getOutputStream()));
				socketIn = new ObjectInputStream(aSocket.getInputStream());

				ThreadController communicator = new ThreadController (socketIn, socketOut, database);
				pool.execute(communicator);
			}
		} catch (Exception e) {
			System.out.println("There was an error");
		}
		
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void main(String[] args) throws IOException {

		ClientDataBaseManager database = new ClientDataBaseManager();
		database.createDB();
		database.connectToDB();
		database.createTable();
		database.fillTable();
		
		ServerController myServer = new ServerController(database);
		myServer.runServer();
	}

}
