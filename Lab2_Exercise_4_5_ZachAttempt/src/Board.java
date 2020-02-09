


/**Class with a constructor for generating a tic-tac-toe board. Contains methods for populating
 * the board with characters, checking the board for a winner, and displaying the board
 * to the console window.
 * @author Dr. Moshirpour
 *
 */
public class Board implements Constants {
	private char theBoard[][];
	private int markCount;

	/**
	 * Constructor of the board object.
	 * Creates an empty tic-tac-toe board.
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}
	
	public char [][] getTheBoard() {
		char [][] boardCopy = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boardCopy[i][j] = theBoard[i][j];
			}
		}
		return boardCopy;
	}
	
	public void copyBoard(Board temp) {
		temp.theBoard = getTheBoard();
	}

	/**Finds the character at the given row and column on the board and returns it.
	 * @param row
	 * the row on the tic-tac-toe board
	 * @param col
	 * the column on the tic-tac-toe board
	 * @return character at the specified location
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**If there are nine marks on the board, this function returns true. Otherwise, 
	 * it returns false
	 * @return true or false depending on if every slot on the board is full
	 */
	public boolean isFull() {
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

	/**Checks the board to see if the X Player has won the game
	 * @return true if the x player has won
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**Checks the board to see if the O Player has won the game
	 * @return true if the o player has won
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Prints the tic-tac-toe board onto the console window in a human readable way.
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

	/**Adds a mark of the type character in the argument to the location specified on the board
	 * @param row
	 * the row that the mark should be placed on the tic-tac-toe board
	 * @param col
	 * the column that the mark should be placed on the tic-tac-toe board
	 * @param mark
	 * the mark that should be placed in the specified location of the board; either X or O
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	/**Replaces all characters on the board with a 'space' character (' ').
	 * This simulates an empty tic-tac-toe board.
	 * 
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**A helper function used by the function xWins() and oWins(). This helper function
	 * accepts a character and returns true if that character exists in a winning combination
	 * on the tic-tac-toe board.
	 * @param mark 
	 * the character that is checked for a winning combination on the tic-tac-toe board; 
	 * either X or O
	 * @return true if the character exists in a winning combination
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
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

	/**Helper function called by the display() function. Prints the tic-tac-toe column
	 * headers to the screen to assist in human reading of the board.
	 */
	void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}

	/**Helper function called by the display() function. Prints the horizontal lines of the 
	 * tic-tac-toe board to assist in human reading of the visual representation of the board.
	 */
	void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}

	/**Helper function called by the display() function. Prints the vertical lines 
	 * and the spaces between them on the tic-tac-toe board to assist in 
	 * human reading of the visual representation of the board.
	 */
	void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
	
	/**Takes a character array and sets the board's character array to equal it.
	 * @param theBoard
	 * A 3x3 character array containing spaces, X's, O's
	 */
	public void setBoard(char[][] theBoard) {
		this.theBoard = theBoard;
	}
}
