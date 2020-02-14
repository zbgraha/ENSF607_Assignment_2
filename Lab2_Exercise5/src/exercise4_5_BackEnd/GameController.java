package exercise4_5_BackEnd;

import exercise4_5_Shared.*;

import java.io.IOException;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author zacha
 *
 */
public class GameController implements Runnable, Constants{

	private ObjectOutputStream socketOutPlayerA;
	private ObjectInputStream socketInPlayerA;
	private char playerAMark;
	
	private ObjectOutputStream socketOutPlayerB;
	private ObjectInputStream socketInPlayerB;
	
	private Board backEndBoard;
	
	private GameCommand currentCommand;
	
//	private GameCommand command;
	
	
	/**Accepts the object input and output stream of both players involved in a game.
	 * Creates a new instance of class Board that contains functionality for analyzing the board
	 * to see if the game is over.
	 * @param aOut
	 * @param aIn
	 * @param bOut
	 * @param bIn
	 */
	public GameController(ObjectOutputStream aOut,ObjectInputStream aIn,ObjectOutputStream bOut, ObjectInputStream bIn) {
		socketOutPlayerA = aOut;
		socketInPlayerA = aIn;
		socketOutPlayerB = bOut;
		socketInPlayerB = bIn;
		
		backEndBoard = new Board();		
	}
	
	/**First obtains player information, and then starts the tic tac toe game by calling the playGame() function.
	 *
	 */
	@Override
	public void run() {
		System.out.println("Game controller started");
		initializeGame(); // gets the name from each user, sets their mark
		playGame();
		
	}
	
	///////////////////// 
	////// Helper Methods
	
	/**Sets all of the appropriate values to begin the game. Obtains player names, 
	 * sets their character values, and updates the opponents names. Sends the information
	 * to both clients
	 * 
	 */
	private void initializeGame() {
		GameCommand startupCommandX = new GameCommand(); // default command is 0
		startupCommandX.setCurrentPlayer(LETTER_X );
		GameCommand startupCommandO = new GameCommand(); // default command is 0
		startupCommandO.setCurrentPlayer(LETTER_O );

		try {
			// Send get name command to each client
			socketOutPlayerA.writeObject(startupCommandX);
			playerAMark = LETTER_X;
			socketOutPlayerB.writeObject(startupCommandO);
			// wait for response from each client
//			while(startupCommandX.getxPlayer() == null) {
				startupCommandX = (GameCommand) socketInPlayerA.readObject();
//			}
				System.out.println("TestPointE");
//			while(startupCommandO.getoPlayer() == null) {
				startupCommandO = (GameCommand) socketInPlayerB.readObject();
//			}
				System.out.println("TestPointF");
			// sets the current player to X, gets opponent info
			currentCommand = startupCommandX;
			currentCommand.setoPlayer(startupCommandO.getoPlayer());
			currentCommand.setCurrentPlayer(LETTER_X); 
			// Should start with X playing first
			currentCommand.setGameOver(false);
		

			currentCommand.setCommand(2); // get opponent names

			msgBothPlayers();


			
		} catch (IOException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**Set the command value to indicate that the game is begun. Writes the object
	 * to both object outputstreams.
	 * 
	 */
	private void playGame() {
		
		currentCommand.setCommand(1);
		msgBothPlayers();

		while(!gameOver()) {
			playerMove();
		}

		
	}
	
	/**Reads from the object input stream and sends the object to the processCommand
	 * function to interpret the command integer received
	 * 
	 */
	private void playerMove() {

		getCurrentCommand();
		processCommand();
			
						
	}
	
	/**Reads the socket inputstream of the current player matching the character of
	 * the GameCommand object member variable
	 * 
	 */
	private void getCurrentCommand() {
		
			try {
				if (currentCommand.getCurrentPlayer() == LETTER_X) {
					currentCommand = (GameCommand) socketInPlayerA.readObject();
//					GameCommand emptyCommand = (GameCommand) socketInPlayerB.readObject();
				}
				else if (currentCommand.getCurrentPlayer() == LETTER_O) {
					currentCommand = (GameCommand) socketInPlayerB.readObject();
//					GameCommand emptyCommand = (GameCommand) socketInPlayerA.readObject();
				}
				else {
					System.err.println("ERROR: current player not set");
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	/**Interprets the command integer of the incoming object from the object inputstream
	 * and executes back-end functionality depending on its value
	 * 
	 */
	private void processCommand() {
		updateBoard();
//		System.out.println("Current game command: "+ currentCommand.getCommand());
		if(!gameOver()) {
			
			switch(currentCommand.getCommand()) {
			
			case 1:// keep playing
				
				switchPlayer();
				msgBothPlayers();
				break;
			case 6:// restart requested
				currentCommand.setCommand(6);
				switchPlayer();
				msgCurrentPlayer();
				break;
			case 7: // restart confirmed by other player, restart game
				switchPlayer();
				msgBothPlayers();
				break;
			
			case 8: // restart declined
				switchPlayer();
				msgCurrentPlayer();
				break;
				
			default:
				System.err.println("ERROR: UnknownCommand From Client");
			}
			
			
		}
	}
	
	/**Updates the current player char to the opposite of what it currently is
	 * 
	 */
	private void switchPlayer() {
		if (currentCommand.getCurrentPlayer() == LETTER_X) {
			currentCommand.setCurrentPlayer(LETTER_O);
		}
		else if (currentCommand.getCurrentPlayer() == LETTER_O) {
			currentCommand.setCurrentPlayer(LETTER_X);
		}
		else {
			System.err.println("ERROR: current player not set");
		}
	}
	
	/**Writes the game information object to both object output streams
	 * so that both players receive its contents
	 * 
	 */
	private void msgBothPlayers() {
		
		try {
			socketOutPlayerA.writeObject(currentCommand);
			socketOutPlayerB.writeObject(currentCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**Writes the current command to the object output stream pertaining to the current player
	 * 
	 */
	private void msgCurrentPlayer() {
		if (currentCommand.getCurrentPlayer() == playerAMark) {
			try {
				socketOutPlayerA.writeObject(currentCommand);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				socketOutPlayerB.writeObject(currentCommand);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**Uses the contents of the object received in the object input stream
	 * to updates the board used for game over calculation
	 * 
	 */
	private void updateBoard() {
		backEndBoard.setBoard(currentCommand.getBoard());

		
		// For testing
		backEndBoard.display();
		
	}
	
	/**Checks the board received in the object input stream to see if any player
	 * has won. Updates the command integer to reflect the outcome of the game if it is over.
	 * If the game is over, writes the outcome to the object output stream for both players.
	 * @return true if the game is over, false if the game is not over
	 */
	private boolean gameOver() {
		
		if(backEndBoard.xWins())
			currentCommand.setCommand(3);
		
		else if(backEndBoard.oWins())
			currentCommand.setCommand(4);
		
		else if (backEndBoard.isFull())
			currentCommand.setCommand(5);
		
		else 
			return false;
		
		msgBothPlayers();
		return true;
		

	}
	

}
