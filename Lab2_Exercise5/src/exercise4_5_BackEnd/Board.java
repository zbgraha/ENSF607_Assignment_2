package exercise4_5_BackEnd;

import java.io.Serializable;
import exercise4_5_Shared.Constants;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 
/**
 * Creates and tracks the status of a back end game board
 * 
 * @author Unknown, last modified by Riley Berry
 * @version 2.0
 * @Since 2019-11-06
 * @modified 2020-02-10
 *
 */

// changed the isFull method, added a character array copy, made a serializable object

public class Board implements Constants, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2571387713060597090L;
	/**
	 * create a 2D array of chars
	 */
	private char theBoard[][];
	/**
	 * An integer to count the number of marks on the board
	 */
//	private int markCount;

	/**
	 * Default constructor
	 * Sets all the board to be a 3x3 2D char array
	 * Assigns each element to be the space char from the constants file
	 */
	public Board() {
//		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * Accepts the row and column as inputs, 
	 * return the char at the corresponding element from theBoard
	 * @param row row in the board
	 * @param col column in the board
	 * @return the character in the specified element
	 */
	public char getMark(int row, int col) {
		//Potentially add some error checking on the input value
		return theBoard[row][col];
	}

	/**
	 * Checks the total number of marks on the 3x3 board
	 * @return true if the board is full
	 */
	public boolean isFull() {
//		return markCount == 9;
		int marks = 0;
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 3; j++) {
				if (theBoard[i][j] != SPACE_CHAR) {
					marks += 1;
				}
			}
		}
		if (marks == 9) {
			return true;
		}
		return false;
	}
	/**
	 * Takes a character array and copies it to the board if it is 3x3
	 * @param theBoard a 3x3 character array
	 */
	public void setBoard(char[][] theBoard) {
		// verifies that the board is 3x3
		if (theBoard.length == 3) {
			for (int i = 0; i<3; i++) {
				if(theBoard[i].length != 3) {
					return;
				}
			}
			this.theBoard = theBoard;
		}
		else
			return;
		
	}

	/**
	 * Determines if the x character wins
	 * @return true if x wins
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}
	/**
	 * Determines if the o character wins
	 * @return true if o wins
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}
	
	public boolean isOccupied(int row, int col) {
		if (theBoard[row][col]!= SPACE_CHAR && row != -1 && col !=-1) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * prints the board with headers and the current player marking
	 */
	public void display() {
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			System.out.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				System.out.print("|  " + getMark(row, col) + "  ");
			System.out.println("|");
			addSpaces();
			addHyphens();
		}
	}

	/**
	 * Adds a mark to the board
	 * @param row row to place the mark
	 * @param col column to place the mark
	 * @param mark character to mark the board with
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
//		markCount++;
	}
	
	
	public void removeMark(int row, int col) {

		theBoard[row][col] = SPACE_CHAR;
//		markCount--;
	}

	/**
	 * assigns all elements of the board to the space char
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
//		markCount = 0;
	}
	/**
	 * Loops through all rows, columns and diagonals to search for three of the same mark
	 * @param mark The character to check for three in a row
	 * @return Returns 1 if three in a row are found with the supplied mark
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;
		
		//Loops through each row
		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			// Loops through each column of the row
			for (col = 0; row_result == 1 && col < 3; col++)
				//If the element is not equal to the mark, break out of the loop
				if (theBoard[row][col] != mark)
					row_result = 0;
			// If the same element shows up in all three spaces, result is 1
			if (row_result != 0)
				result = 1;
		}

		//Loops through each column, if no group of 3 is found above
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			//loops through each row of the column
			for (row = 0; col_result != 0 && row < 3; row++)
				// As soon as the element is not in the row, break out of the loop
				if (theBoard[row][col] != mark)
					col_result = 0;
			// If the same element is in all three spaces result is 1
			if (col_result != 0)
				result = 1;
		}
		// Check the diagonal from upper left to lower right
		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		//Check the diagonal from upper right to lower left
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/**
	 * Displays the columns header of the board
	 */
	private void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}
	/** 
	 * Adds hyphens to the board print out
	 */
	private void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}

	
	/** 
	 * Adds blank space to the board print out
	 */
	private void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
	public char[][] getTheBoard(){
		return this.theBoard;
	}
}
