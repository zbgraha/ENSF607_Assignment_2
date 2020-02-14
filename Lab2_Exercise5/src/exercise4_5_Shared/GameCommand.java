package exercise4_5_Shared;

import java.io.Serializable;

/**A class containing a serializable object containing information passed between a client and server of
 * a tic tac toe game.
 * @author Riley Berry and Zachary Graham
 *
 */
public class GameCommand implements Serializable, Constants{
	
	private static final long serialVersionUID = 6601503566574509546L;
	private char currentPlayer;
	private String xPlayer;
	private String oPlayer;
	private char[][] board = new char[3][3];
	private int command;
	private boolean gameOver;
	// 0 get player names
	// 1 make move
	// 2 update opponent
	// 3 game over x wins
	// 4 game over o wins
	// 5 game over tie
	// 6 restart request
	// 7 restart confirmed
	
	// 98 not Connected
	// 99 response not received

	/**Creates an 'empty' tic tac toe board and sets the command to zero which
	 * prompts users to provide their name
	 * 
	 */
	public GameCommand() {
		initializeBoard();
		setCommand(0);
	}
	
	/**Adds a char character matching that of the current players to
	 * the row and column matching the input arguments
	 * @param row of the tic tac toe board that the mark will be placed
	 * @param col of the tic tac toe board that the mark will be placed
	 */
	public void addMark(int row, int col) {
		board[row][col] = currentPlayer;
	}
	
	/**Re-instantiates an empty tic tac toe board
	 * 
	 */
	public void clearBoard() {
		initializeBoard();
	}
	
	////////////////////////////
	////// Helper Methods
	/**Creates a 3x3 char array and places a space in each spot.
	 * A space represents an empty place in this model of a tic tac toe board.
	 * 
	 */
	private void initializeBoard() {
		for (int i = 0; i<3; i++){
			for(int j = 0; j<3; j++ ) {
				board[i][j] = SPACE_CHAR;
			}
		}
	}
	
	
	//////////////////////////////
	///// Getters and setters

	public String getxPlayer() {
		return xPlayer;
	}

	public void setxPlayer(String xPlayer) {
		this.xPlayer = xPlayer;
	}

	public String getoPlayer() {
		return oPlayer;
	}

	public void setoPlayer(String oPlayer) {
		this.oPlayer = oPlayer;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public char getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(char currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}


	public boolean isGameOver() {
		return gameOver;
	}


	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
