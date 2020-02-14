package exercise4_5_Shared;

import java.io.Serializable;

public class GameCommand implements Serializable, Constants{
	
	/**
	 * 
	 */
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

	public GameCommand() {
		initializeBoard();
		setCommand(0);
	}
	
	public void addMark(int row, int col) {
		board[row][col] = currentPlayer;
	}
	
	public void clearBoard() {
		initializeBoard();
	}
	
	////////////////////////////
	////// Helper Methods
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
