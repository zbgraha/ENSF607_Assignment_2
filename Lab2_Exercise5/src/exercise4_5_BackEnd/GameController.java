package exercise4_5_BackEnd;

import exercise4_5_Shared.*;

import java.io.IOException;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameController implements Runnable, Constants{

	private ObjectOutputStream socketOutPlayerA;
	private ObjectInputStream socketInPlayerA;
	private char playerAMark;
	
	private ObjectOutputStream socketOutPlayerB;
	private ObjectInputStream socketInPlayerB;
	
	private Board backEndBoard;
	
	private GameCommand currentCommand;
	
//	private GameCommand command;
	
	
	public GameController(ObjectOutputStream aOut,ObjectInputStream aIn,ObjectOutputStream bOut, ObjectInputStream bIn) {
		socketOutPlayerA = aOut;
		socketInPlayerA = aIn;
		socketOutPlayerB = bOut;
		socketInPlayerB = bIn;
		
		backEndBoard = new Board();		
	}
	
	@Override
	public void run() {
		System.out.println("Game controller started");
		initializeGame(); // gets the name from each user, sets their mark
		playGame();
		
	}
	
	///////////////////// 
	////// Helper Methods
	
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
	
	private void playGame() {
		
		currentCommand.setCommand(1);
		msgBothPlayers();

		while(!gameOver()) {
			playerMove();
		}

		
	}
	
	private void playerMove() {

		getCurrentCommand();
		processCommand();
			
						
	}
	
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
	
	private void msgBothPlayers() {
		
		try {
			socketOutPlayerA.writeObject(currentCommand);
			socketOutPlayerB.writeObject(currentCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	private void updateBoard() {
		backEndBoard.setBoard(currentCommand.getBoard());

		
		// For testing
		backEndBoard.display();
		
	}
	
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
