package exercise4_5_FrontEnd;

import exercise4_5_Shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TicTacToeClient implements Runnable{

	private Socket mySocket;
	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;
	private TicTacToeController myController;
	
	private GameCommand myCommand;
	private boolean isConnected;
	
	
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
	
	public void setCommand(GameCommand command) {
		myCommand = command;
	}
	
	public GameCommand getCommand() {
		return myCommand;
	}
	
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

	public TicTacToeController getMyController() {
		return myController;
	}

	public void setMyController(TicTacToeController myController) {
		this.myController = myController;
	}


	
	
	
}


