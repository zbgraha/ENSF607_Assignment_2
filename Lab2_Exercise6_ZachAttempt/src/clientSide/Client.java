package clientSide;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import customerAndActions.CustomerAndAction;

public class Client {
	
	private Socket aSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	
	private ClientController controller;
	
	public Client (String serverName, int portNumber, ClientController controller) {
		this.controller = controller;
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

	public void communicate () {
		
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
