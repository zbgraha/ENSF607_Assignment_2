import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket aSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private GameInformation newGame;
	
	private TicTacToeController controller;
	
	public Client (String serverName, int portNumber, TicTacToeController game) {
		controller = game;
		controller.waitingForConnect();
		try {
			aSocket = new Socket (serverName, portNumber);
			//keyboard input stream
			//socket input stream
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
				newGame = (GameInformation) socketIn.readObject();
				if (newGame.getDisplayInstruction()==10) {
					controller.playerDisconnect();
					break;
				}
					
				controller.updateDisplay(newGame);
				
				processInstruction(newGame);
				if (gameOver())
					break;
			}catch (IOException e) {
//				e.getStackTrace();
				
				System.err.println("Connection lost");
				break;
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
	private boolean gameOver() {
		if(newGame.getDisplayInstruction() == 3 || newGame.getDisplayInstruction() == 4 || newGame.getDisplayInstruction() == 5)
			return true;
		return false;
	}
	private void processInstruction(GameInformation game) {
		controller.processInstruction(game);
	}

	public ObjectOutputStream getSocketOut() {
		return socketOut;
	}
	public ObjectInputStream getSocketIn() {
		return socketIn;
	}
}
