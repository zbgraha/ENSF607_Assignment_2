package exercise4_5_FrontEnd;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import exercise4_5_Shared.*;

/**Controller for client side of the tic tac toe application
 * @author Riley Berry and Zachary Graham
 *
 */
public class TicTacToeController implements Constants{

	private TicTacToeViewer myView;
	private GameCommand myCommand;
	private TicTacToeClient myClient;
	private char myMark;
	
	private int defaultPort = 9898;
	private String defaultHost = "localhost";
	
	private boolean gameActive = false;
	private boolean myTurn = false;
	private boolean isConnected = false; // duplicate to check while client is in separate loop/thread
	
	private ExecutorService threadPool;
	
	
	/**Constructor for the Controller. Creates the client and provides itself as the
	 * input argument for the client. Also sets the board listeners.
	 * @param theView
	 */
	public TicTacToeController(TicTacToeViewer theView ) {
		myView = theView;
		
		myClient = new TicTacToeClient(this);
		threadPool = Executors.newFixedThreadPool(2);
		setListeners();

		
		
	}
	
	/**Constructor for the Controller. Creates the client and provides itself as the
	 * input argument for the client. Also sets the board listeners.
	 */
	public TicTacToeController( ) {
		myView = new TicTacToeViewer();
		myClient = new TicTacToeClient(this);

		threadPool = Executors.newFixedThreadPool(2);
		setListeners();

		
	}
	
	

	/**Updates the UI display board with the contents of the gameCommand object board
	 * 
	 */
	public void updateBoard() {
		myView.updateBoard(myCommand.getBoard());
	}

	public GameCommand getMyCommand() {
		return myCommand;
	}
	
	public void setMyCommand(GameCommand command) {
		myCommand = command;
	}
	
	
	//////////////////////////////////////////
	//////////// Helper methods
	
	/**Attempts to user the port and server address on the UI to connect to the server.
	 * If the connection is successful, the client is run
	 * 
	 */
	private void connectToServer() {
		if(myClient.isConnected() == false) {
			
			if (connectToUserServer(myView.getServer(), myView.getPort())) {
				myView.addMessage("Connected To Server");
				
			}
			else if(connectToDefault()) {
				myView.addMessage("Connected to default Server");
				
			}
			else {
				myView.addMessage("Unable to connect");
			}
			
			
			
			System.out.println(threadPool);
			System.out.println(myClient);
			
//			threadPool.execute(myClient);
			
			runClient();
		}
		else
			myView.addMessage("Already conected to server");

	}
	
	/**Executes the client runnable which begins trying to read the socket input
	 * 
	 */
	private void runClient() {
		if (myClient.isConnected()) {
			isConnected = true; // copies to local variable
//			System.out.println(myClient);
			threadPool.execute(myClient);
		}
	}
	
	/**Attempts to connect to the default server address
	 * @return true if connection successful
	 */
	private boolean connectToDefault() {
		return myClient.connect(defaultPort, defaultHost);
	}
	
	/**Attempts to connect to the user provided server host and port
	 * @param host UI entry for host
	 * @param port UI entry for port
	 * @return true if the connection is successful
	 */
	private boolean connectToUserServer(String host, int port) {
		return myClient.connect(port, host);
		
	}
	
	
	/**Interprets the command integer value of the gameCommand object received by the inputstream and 
	 * executes the appropriate behavior and instruction to the user.
	 * 
	 */
	public void executeCommand() {
		updateBoard();
//		System.out.println("MyMark: "+myMark +", Current Mark: " + myCommand.getCurrentPlayer() 
//		+ ", Current Command: " + myCommand.getCommand());
		switch(myCommand.getCommand()) {
		case 0: //get player names
			
			getPlayerName();
			gameActive = true;
			
			messageServer();
			
//			communicate();
			break;
		case 1:// make move

//			updateBoard();
			if (myCommand.getCurrentPlayer() == myMark) {
				
				myView.addMessage("It is your turn to play");
				myTurn = true;
			}
			else {
				myView.addMessage("Waiting for Opponent to play");
				myTurn = false;
//				listen();
			}
			
			break;
		case 2: // update opponent
			setOpponentName();
//			messageServer();
			break;
		case 3:// game over, x wins
			gameActive = false;
			xPlayerWinsMessage();
			
			break;
		case 4:// game over, o wins
			gameActive = false;
			oPlayerWinsMessage();
			break;
		case 5:// game over, draw
			gameActive = false;
			myView.addMessage("Game ended in a draw!");
			break;
		case 6:// restart request
			restartRequested();
//			myClient.setCommand(myCommand);
			messageServer();
//			communicate();
			break;
		case 7:// restart confirmed
			gameActive = true;
			myView.addMessage("Restart confirmed, game has been reset");
			myCommand.clearBoard();
			myView.updateBoard(myCommand.getBoard());
			myCommand.setCommand(1);
			messageServer();
//			executeCommand();
			break;
		case 8:// restart declined
			myView.addMessage("Restart declined");
			myCommand.setCommand(1);
			messageServer();
//			executeCommand();
			break;

		case 98:// not connected to server
			myView.addMessage("Unable to connect to server");
			break;
		case 99:// response not received correctly
			myView.addMessage("Unable to connect to read message");
			break;
			
		default:
			myView.addMessage("ERROR: unknown command from server");
			
		}
		
//		System.out.println("End of Execute Command");


	}
	
	/**Writes the myCommand object to the outputstream
	 * 
	 */
	private void messageServer() {
		try {
			myClient.getOutStream().writeObject(myCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**Takes the value of the opponents name from the object received
	 * by the input stream and updates the UI with the information
	 * 
	 */
	private void setOpponentName() {
		myView.addMessage("Test message 2");
		if (myMark == LETTER_X) 
			myView.setOpponentName(myCommand.getoPlayer());
		else
			myView.setOpponentName(myCommand.getxPlayer());
		
		myCommand.setCommand(1);
		myView.addMessage("Test message 3");
	}
	
	/**Displays to the user that the opponent has requested the the game be restart.
	 * sets the appropriate command instruction to the object being sent to the outputstream
	 * 
	 */
	private void restartRequested() {
		boolean restart = myView.getYesNoDialogBox("Opponent would like to restart the game. Do you want to restart?");
		if (restart)
			myCommand.setCommand(7);
		else
			myCommand.setCommand(8);
	}
	
	/**Displays a variable message that the X player has won
	 * depending on if the current player is the X player or not
	 * 
	 */
	private void xPlayerWinsMessage() {
		if(myMark == LETTER_X) {
			myView.addMessage("Congratulations! You are the winner!");
		}
		else if(myMark == LETTER_O) {
			myView.addMessage("Your opponent has won the game, better luck next round");
		}
		else {
			myView.addMessage("game over, but unable to read mark");
		}
		
	}
	
	/**Displays a variable message that the O player has won
	 * depending on if the current player is the O player or not
	 */
	private void oPlayerWinsMessage() {
		if(myMark == LETTER_O) {
			myView.addMessage("Congratulations! You are the winner!");
		}
		else if(myMark == LETTER_X) {
			myView.addMessage("Your opponent has won the game, better luck next round");
		}
		else {
			myView.addMessage("game over, but unable to read mark");
		}
		
	}
	
	/**Prompts the user to enter their name and writes the input to the
	 * object being sent to the object output stream.
	 * 
	 */
	private void getPlayerName() {
		

		String name = myView.getPopupMessage("Please enter your name");


		if (myCommand.getCurrentPlayer() == LETTER_X) {
			myCommand.setxPlayer(name);
			myMark = LETTER_X;	
			
		}
		else if (myCommand.getCurrentPlayer() == LETTER_O) {
			myCommand.setoPlayer(name);
			myMark = LETTER_O;
//			myView.setPlayerMark(myMark);
		}
		else {
			myView.addMessage("Error reading name");
			System.out.println("Error reading name");
		}
		
		myView.setPlayerName(name);
		myView.setPlayerMark(myMark);

	}
	

	/**Connects the action listeners to their UI buttons
	 * 
	 */
	private void setListeners() {
		
		myView.addConnectButtonListener((ActionEvent e) -> {connectToServer();});
		myView.addRestartButtonListener(new RestartButtonListener());
		for(int i = 0; i< myView.getTicButtonListLength(); i++ ) {
			myView.addTicButtonListeners(new TicButtonListener(), i);
		}
	}
	/////////////////////////////////////////////////
	//////////////// Action listener classes
	
	
	/**Sets the command value to 6 and writes the myCommand to the object outputstream
	 * which will display a message to the opponent that the user wants to restart
	 * @author Riley Berry and Zachary Graham
	 *
	 */
	public class RestartButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(isConnected) {
				myCommand.setCommand(6);
				myView.addMessage("Restart requested");
				messageServer();
			}
			else
				myView.addMessage("Not Connected");
			
		}
	}
	
	/**If it is the current players turn, updates the text of the UI to match
	 * their mark, updates the myCommand object board to match the UI, and writes
	 * the object to the socket
	 * @author Riley Berry and Zachary Graham
	 *
	 */
	public class TicButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(gameActive) {
				if (myTurn) {
					TicButton tempB = (TicButton) e.getSource();
					
					if (tempB.getMark()!= SPACE_CHAR) {
						myView.addMessage("Unable to make move");
					}
					else {
						tempB.setMark(myMark);
						myCommand.addMark(tempB.getRowNum(),tempB.getColNum());
						myCommand.setCommand(1);
						myView.updateBoard(myCommand.getBoard());
						messageServer();
					}
					
					
					
				}
				else {
					myView.addMessage("Waiting for opponent to play");
				}
			}
			else {
				myView.addMessage("Game is not active");
			}
			
			
		}
	}
	
	
	
	
	
}
