

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**A Class that contains a UI for a tic tac toe board game.
 * @author Zachary Graham
 *
 */
public class TicTacToeViewer extends JFrame{

	private String xPlayer, oPlayer;
	private JLabel title = new JLabel ("Tic Tac Toe");
	private JLabel messageWindow = new JLabel("Message Window");
	private JTextArea message = new JTextArea(1,25);
	private JLabel playerName = new JLabel();
	private JLabel player = new JLabel("Player:");
	private JTextField character = new JTextField("X");
	private JButton[][] boardButtons = new JButton[3][3];
	
	/**Constructor that builds the tic tac toe game UI and displays it on the screen
	 * 
	 */
	public TicTacToeViewer() {

		JPanel board = createBoard();
		JPanel messages = new JPanel ();
		JPanel playerInfo = new JPanel();
		JPanel titleBox = new JPanel();
		JPanel currentPlayer = new JPanel();
		messages.setLayout(new BorderLayout());
	    currentPlayer.setLayout(new FlowLayout());
	    
		setTitle("Game");
		setSize(500,330);
		setLayout(new BorderLayout());
		
		currentPlayer.add(player);
		currentPlayer.add(getPlayerName());
		currentPlayer.add(character);
		messages.add("North", messageWindow);
		messages.add("Center", message);
		messages.add("South", currentPlayer);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.X_AXIS));
		centerPanel.add(board);
		centerPanel.add(new JLabel("   "));
		centerPanel.add(messages);
		
		titleBox.add(title);
		
		add("North",titleBox);
		add("Center", centerPanel);
		add("South", playerInfo);
		add("West", new JLabel("   "));
		add("East", new JLabel("   "));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

	}
	
	private JPanel createBoard() {
		JPanel board = new JPanel(new GridLayout(3,3,10,10));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boardButtons[i][j] = new JButton(" ");
				boardButtons[i][j].setPreferredSize(new Dimension(70,50));
				board.add(boardButtons[i][j]);
			}	
		}
		return board;
	}
	
	void addButtonListeners (int row, int col, ActionListener listen) {
		boardButtons[row][col].addActionListener(listen);
	}

	/**Sets the text in the main message window to tell the 'X' player that it is their turn
	 * 
	 */
	public void xTurnMessage() {
		message.setText(xPlayer + ", it is your turn!\n");
	}
	
	/**Sets the text in the main message window to tell the 'O' player that it is their turn
	 * 
	 */
	public void oTurnMessage() {
		message.setText(oPlayer + ", it is your turn!\n");
	}
	
	/**Shows a dialog box where the 'O' Player can enter their name
	 * 
	 */
	public void setPlayerMessage(String character) {
		if (character.equals("O"))
			oPlayer = JOptionPane.showInputDialog(this,"Please Enter your Name");	
		else
			xPlayer = JOptionPane.showInputDialog(this,"Please Enter your Name");
	}
	
	public String getXPlayer() {
		return xPlayer;	
	}

	public void setXPlayer(String name) {
		xPlayer = name;
	}
	
	public void setOPlayer(String name) {
		oPlayer = name;
	}

	public String getOPlayer() {
		return oPlayer;	
	}
	
	public JLabel getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String string) {
		this.playerName.setText(string);
	}
	
	public String getCharacter() {
		return character.getText();
	}

	public void setCharacter(String string) {
		character.setText(string);
		
	}

	/**Sets a message in the message window to text indicating that the 'X' Player has won the game
	 * 
	 */
	public void xWins() {
		message.setText(xPlayer + " Wins!\n\nThe Game is Over!\n\n");
		
	}

	/**Sets a message in the message window to text indicating that the 'O' Player has won the game
	 * 
	 */
	public void oWins() {
		message.setText(oPlayer + " Wins!\n\nThe Game is Over!\n\n");
		
	}

	/**Shows a dialog box indicating that the game is over. The UI closes once the user closes this box
	 * @return true
	 */
	public boolean gameOver() {
		int result = JOptionPane.showConfirmDialog(this, 
				   "The Game is Over\nThank you for playing",null, JOptionPane.DEFAULT_OPTION);
		if(result == JOptionPane.DEFAULT_OPTION) {
		    return true;
		} 
		else
			return true;
	}

	public String getButtonText(int i, int j) {
		return boardButtons[i][j].getText();
	}
	
	public void setButtonText(int i, int j, String text) {
		boardButtons[i][j].setText(text);
	}
	
	public void setButtonText(char[][] board) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boardButtons[i][j].setText(Character.toString(board[i][j]));
			}	
		}
	}

	public JButton getboardButtons(int i,int j) {
		return boardButtons[i][j];
	}

	public void freeze(String string) {
		message.setText("Wait while " + string + " moves");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boardButtons[i][j].setEnabled(false);
			}	
		}
	}
	
	public void freeze() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boardButtons[i][j].setEnabled(false);
			}	
		}
	}

	public void unfreeze(String player) {
		message.setText(player + ", it is your turn!");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boardButtons[i][j].setEnabled(true);
			}	
		}
	}

	public void waiting() {
		message.setText("Waiting for Connection...");
	}

	public void tie() {
		message.setText("The Game is a Tie!\n\nThe Game is Over!\n\n");
	}

	public void connected() {
		message.setText("Connection Established...\nWaiting for other player.");
	}

	public boolean gameOver(String player) {
		int result = JOptionPane.showConfirmDialog(this, 
				   "The Game is Over\n" + player + " is the Winner!",null, JOptionPane.DEFAULT_OPTION);
		if(result == JOptionPane.DEFAULT_OPTION) {
		    return true;
		} 
		else
			return true;
	}
	
	public boolean disconnect() {
		int result = JOptionPane.showConfirmDialog(this, 
				   "Your Opponent has Disconnected...",null, JOptionPane.DEFAULT_OPTION);
		if(result == JOptionPane.DEFAULT_OPTION) {
		    return true;
		} 
		else
			return true;
	}
}
