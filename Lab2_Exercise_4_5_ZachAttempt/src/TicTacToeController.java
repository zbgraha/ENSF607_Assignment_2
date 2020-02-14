

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

public class TicTacToeController {
	
	/**Class that ties the tic tac toe UI into backend functionality
	 * 
	 */
	private TicTacToeViewer game;
	private GameInformation gameInfo;
	private Client client;
	
	/**Accepts a tic tac toe board game UI and back-end functionality and implements event listeners between them
	 * to coordinate the activation of the back-end functionality
	 * @param game the tic tac toe viewer object
	 * @param board the panel that contains the tic tac toe buttons
	 * @param boardCalc the class that contains a back end model of the board and calculations for determining who wins
	 */
	public TicTacToeController(TicTacToeViewer game) {
		this.game = game;
		setClient(null);
		game.setVisible(true);
		setActionListeners();
	}
	
	public void addCloseWindowListener() {
		game.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        try {
					client.getSocketOut().writeObject(new GameInformation(10));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
	}
	
	/**Updates the board in the back end to match the text displayed in the UI
	 * 
	 */
	public void updateBoard() {
		char[][] boardUpdate = new char[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				boardUpdate[i][j] = game.getButtonText(i,j).charAt(0);
			}
		}
		gameInfo.setBoard(boardUpdate);
	}

	private void setActionListeners() {
		for (int i =0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				game.addButtonListeners(i,j, new ButtonListener(game.getboardButtons(i,j)));
			}
		}
	}

	class ButtonListener implements ActionListener{

		/**Accepts one of the board game buttons and updates the text in the UI after it is clicked.
		 * Checks to see if the game is over. Checks who won.
		 * 
		 */
		private JButton button;
		
		public ButtonListener(JButton jButton) {
			this.button = jButton;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!button.getText().equals(" ")) {
				System.out.println("NotEqual");
			}
			if (button.getText().equals(" ")) {
				if (game.getCharacter().equals("X")) {
					button.setText("X");
					gameInfo.setDisplayInstruction(2);
					updateGameInfo();
					try {
						client.getSocketOut().writeObject(gameInfo);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (game.getCharacter().equals("O")) {
					button.setText("O");
					gameInfo.setDisplayInstruction(1);
					updateGameInfo();
					try {
						client.getSocketOut().writeObject(gameInfo);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				updateBoard();
			}
		}
		
		
	}
	
	private void updateGameInfo() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				gameInfo.setBoard(i,j,game.getButtonText(i,j).charAt(0));
			}
		}	
	}

	public void processInstruction(GameInformation newGame) {
		//updateDisplay(newGame);
		if (newGame.getDisplayInstruction() == 0) {
			connected();
			namePlayer(newGame);
			try {
				client.getSocketOut().writeObject(newGame);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(game.getXPlayer() == null || game.getOPlayer() == null) {
				try {
					newGame = (GameInformation) client.getSocketIn().readObject();
					game.setXPlayer(newGame.getPlayerX());
					game.setOPlayer(newGame.getPlayerO());
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if (newGame.getDisplayInstruction() == 1 || newGame.getDisplayInstruction() == 2) {
			play(newGame);
		}	
		else if (newGame.getDisplayInstruction()==10) {
			game.disconnect();
		}
		else {
			endGameMessage(newGame);
		}
	}

	private void endGameMessage(GameInformation newGame) {
		if (newGame.getDisplayInstruction() == 3) {
			game.xWins();
			game.freeze();
			game.gameOver(gameInfo.getPlayerX());
		}
		else if (newGame.getDisplayInstruction() == 4) {
			game.oWins();
			game.freeze();
			game.gameOver(gameInfo.getPlayerO());
		}
		else if (newGame.getDisplayInstruction() == 5) {
			game.tie();
			game.freeze();
			game.gameOver();
		}
	}

	public void namePlayer(GameInformation gameInfo) {
		if (gameInfo.getPlayer().equals("X")) {
			game.setPlayerMessage("X");
			gameInfo.setPlayerX(game.getXPlayer());
			game.setCharacter("X");
			game.setPlayerName(game.getXPlayer());
		}
		else {
			game.setPlayerMessage("O");
			gameInfo.setPlayerO(game.getOPlayer());
			game.setCharacter("O");
			game.setPlayerName(game.getOPlayer());
		}
		
	}

	public void display(String string) {
		game.setPlayerName(string);
		
	}

	public void setNames(GameInformation newGame) {
		game.setOPlayer(newGame.getPlayerO());
		game.setXPlayer(newGame.getPlayerX());
		
	}

	public void play(GameInformation newGame) {
		gameInfo = newGame;
		if (newGame.getDisplayInstruction() == 1) {
			if (newGame.getPlayer().equals(game.getCharacter())){
				game.unfreeze(game.getXPlayer());
			}
			else {
				game.freeze(game.getXPlayer());
			}
		}
		else
		{
			if (newGame.getPlayer().equals(game.getCharacter())){
				game.unfreeze(game.getOPlayer());
			}
			else {
				game.freeze(game.getOPlayer());
			}
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void updateDisplay(GameInformation newGame) {
		char[][] board = newGame.getBoard();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				char character = board[i][j];
				if (character == 'X')
					game.setButtonText(i, j, "X");
				if (character == 'O')
					game.setButtonText(i, j, "O");
			}
		}
		
	}

	public void updateBoard(char[][] board) {
		game.setButtonText(board);
		
	}

	public void waitingForConnect() {
		game.waiting();
		game.freeze();
	}

	public void connected() {
		game.connected();
		
	}
	
	public void playerDisconnect() {
		game.disconnect();
	}
}
