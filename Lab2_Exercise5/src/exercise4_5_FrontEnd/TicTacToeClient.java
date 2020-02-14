package exercise4_5_FrontEnd;

import exercise4_5_Shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**A runnable class with a runnable that reads the object input stream
 * @author Riley Berry and Zachary Graham
 *
 */
public class TicTacToeClient implements Runnable{

	private Socket mySocket;
	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;
	private TicTacToeController myController;
	
	private GameCommand myCommand;
	private boolean isConnected;
	
	
	/**Constructor for the client
	 * @param controller
	 */
	public TicTacToeClient(TicTacToeController controller) {
		setMyController(controller);
		isConnected = false;

	}
	

	public boolean isConnected() {
		return isConnected;
	}
	

	public ObjectOutputStream getOutStream() {
		return objectOut;
	}
	
	/**Attempts to connected to the server of the provided parameters.
	 * If successful, establishes the object input and output streams
	 * @param port of the server
	 * @param host of the server
	 * @return true if the connection is successful
	 */
	public boolean connect(int port, String host) {
		try {
			mySocket = new Socket(host, port);
			objectIn = new ObjectInputStream(mySocket.getInputStream());
			objectOut = new ObjectOutputStream(mySocket.getOutputStream());
			isConnected = true;
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isConnected =false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isConnected = false;
		}
		
		return isConnected;
	}

//	public void listen() {
//		if (isConnected) {
//			
//			try {
//				myCommand = (GameCommand) objectIn.readObject();
//				
//			} catch (ClassNotFoundException | IOException e) {
//				e.printStackTrace();
//				myCommand.setCommand(99);	
//			}		
//		}
//		else {
//			myCommand.setCommand(98);
//		}
////		return myCommand;
//	
//	}

	/**Reads the object inputstream and updates the value of the myCommand
	 * object in the server with the object. Also executes the function in the controller
	 * that reads the command integer of the object.
	 *
	 */
	@Override
	public void run() {
//		if (isConnected) {
			
			while (isConnected)
			try {
				
				myCommand = (GameCommand) objectIn.readObject();
				
				myController.setMyCommand(myCommand);
				myController.executeCommand();
				
//				objectOut.writeObject(myCommand);
							
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				myCommand.setCommand(99);	
			}		
//		}
//		else {
//			myCommand.setCommand(98);
//		}
		
//		return myCommand;
			
	
	}
	
	/**
	 * @param command
	 */
	public void setCommand(GameCommand command) {
		myCommand = command;
	}
	
	/**
	 * @return
	 */
	public GameCommand getCommand() {
		return myCommand;
	}
	
	/**
	 * 
	 */
	public void closeStreams() {
		try {
			objectIn.close();
			objectOut.close();
			isConnected = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public TicTacToeController getMyController() {
		return myController;
	}

	/**
	 * @param myController
	 */
	public void setMyController(TicTacToeController myController) {
		this.myController = myController;
	}


	
	
	
}


