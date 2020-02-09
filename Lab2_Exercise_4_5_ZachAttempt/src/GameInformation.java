import java.io.Serializable;

public class GameInformation implements Serializable{

	private boolean abort = false;
	private String player;
	private String playerX, playerO;
	private char[][] board = new char[3][3];
	private int displayInstruction; //0 enter nameplayer
									//1 choose square on board
									//2 display x wins message
									//3 display o wins message
									//4 display tie message
	
	public GameInformation() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = ' ';
			}
		}
		setPlayerX(null);
		setPlayerO(null);
		setDisplayInstruction(0);
	}

	public GameInformation(String c) {
		setPlayer(c);
	}

	public GameInformation(boolean b) {
		// TODO Auto-generated constructor stub
	}

	public GameInformation(int i) {
		setDisplayInstruction(i);
	}

	public int getDisplayInstruction() {
		return displayInstruction;
	}

	public void setDisplayInstruction(int displayInstruction) {
		this.displayInstruction = displayInstruction;
	}

	public String getPlayerX() {
		return playerX;
	}

	public void setPlayerX(String playerX) {
		this.playerX = playerX;
	}

	public String getPlayerO() {
		return playerO;
	}

	public void setPlayerO(String playerO) {
		this.playerO = playerO;
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}
	
	public void setBoard(int i, int j, char c) {
		board[i][j] = c;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public void setAbort(boolean abort) {
		this.abort = abort;
	}
	
	public boolean getAbort() {
		return abort;
	}
}
