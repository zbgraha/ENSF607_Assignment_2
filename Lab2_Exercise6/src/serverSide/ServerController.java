package serverSide;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;
/**
 * An object to manage the server that a client can connect to in order to interact with the customer database
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
 *
 */
public class ServerController {

	/**
	 * socket that connects to the server
	 */
	private Socket aSocket;
	/**
	 * server socket used to accept incoming connections
	 */
	private ServerSocket serverSocket;
	/**
	 * object output stream for the connected socket
	 */
	private ObjectOutputStream socketOut;
	/**
	 * object input stream for the connected socket
	 */
	private ObjectInputStream socketIn;
	/**
	 * The database manager that is used to create and manage the customer database
	 */
	private ClientDataBaseManager database;
	
	/**
	 * thread pool to manage mutilple client connections
	 */
	private ExecutorService pool;

	/**
	 * Constructor for the server class. Uses port 9898 with up to three connections at a time
	 * @param database the database that the server is managing
	 */
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

	/**
	 * method to start the  server and begin accepting connections
	 */
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
