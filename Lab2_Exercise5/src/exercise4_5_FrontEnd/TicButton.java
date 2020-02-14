package exercise4_5_FrontEnd;

import javax.swing.JButton;

/**
 * A button for a game of Tic-Tac-Toe
 * Extends a JButton to also store a row and column location
 * @author Riley Berry
 * @version 1.0
 * @since 2019-11-06
 *
 */
public class TicButton extends JButton{
	
	/**
	 * Row location in the GUI
	 */
	private int rowNum;
	/**
	 * Column location in the GUI
	 */
	private int colNum;
	
	/**
	 * Constructor using a label, row and column location
	 * @param mark Label for the JButton constructor
	 * @param rowNum Row location for the GUI
	 * @param colNum Column location for the GUI
	 */
	public TicButton(char mark, int rowNum, int colNum) {
		super( mark+ "");
		this.rowNum = rowNum;
		this.colNum = colNum;
	}

	public int getRowNum() {
		return rowNum;
	}


	public int getColNum() {
		return colNum;
	}
	
	public char getMark() {
		return super.getText().charAt(0);
	}
	
	public void setMark(char mark) {
		super.setText(mark + "");
	}
	
	

	
}
