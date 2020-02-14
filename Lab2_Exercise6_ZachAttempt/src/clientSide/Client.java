package clientSide;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import customerAndActions.CustomerAndAction;

/**
 * An object to manage each connection from a client with a new thread
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
 *
 */
public class Client {
	
	/**
	 * Socket used by the client for communication to the server
	 */
	private Socket aSocket;
	/**
	 * Object output stream of the client
	 */
	private ObjectOutputStream socketOut;
	/**
	 * Object Input stream of the client
	 */
	private ObjectInputStream socketIn;
	
	/**
	 * Front end controller class
	 * Used to implement the run method of this object, and to recieve updates
	 */
	private ClientController controller;
	
	/**
	 * Constructor for the Client object, requires the socket information and the controller object
	 * @param serverName server name to connect to
	 * @param portNumber port to connect on
	 * @param controller controller to send the updates to
	 */
	public Client (String serverName, int portNumber, ClientController controller) {
		this.controller = controller;
		this.controller.waitingForConnection();
		try {
			aSocket = new Socket (serverName, portNumber);
			socketOut = new ObjectOutputStream(aSocket.getOutputStream());
			socketIn = new ObjectInputStream (aSocket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Continually listens for messages from the server and  sends them to the client when received
	 */
	public void communicate () {
		while (true) {
			try {
				CustomerAndAction com = (CustomerAndAction) socketIn.readObject();
				if (com.getAction()==99) {
					controller.connected();
					break;
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while (true) {
			
			try {
				CustomerAndAction instruction = (CustomerAndAction) socketIn.readObject();
				if (instruction.getAction() == 404)
					break;
				controller.process(instruction);
			}catch (IOException e) {
				e.getStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		try {
			//stdIn.close();
			socketIn.close();
			socketOut.close();
		}catch (IOException e) {
			e.getStackTrace();
		}
		
	}

	public ObjectOutputStream getSocketOut() {
		return socketOut;
	}
	public ObjectInputStream getSocketIn() {
		return socketIn;
	}
}
