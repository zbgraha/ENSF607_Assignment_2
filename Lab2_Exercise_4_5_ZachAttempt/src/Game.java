import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game implements Runnable{
	
	private ObjectOutputStream socketOutPlayerX, socketOutPlayerO;
	private ObjectInputStream socketInPlayerX, socketInPlayerO;
	private Board boardCalc;
	private GameInformation gameInfo;
	
	public Game (ObjectInputStream socketInPlayerX2, ObjectOutputStream socketOutPlayerX2, ObjectInputStream socketInPlayerO2, ObjectOutputStream socketOutPlayerO2) {
		socketInPlayerX = socketInPlayerX2;
		socketOutPlayerX = socketOutPlayerX2;
		socketInPlayerO = socketInPlayerO2;
		socketOutPlayerO = socketOutPlayerO2;
		boardCalc = new Board();
	}

	@Override
	public void run() {
		GameInformation newGameX = new GameInformation("X");
		GameInformation newGameO = new GameInformation("O");
		try {
			socketOutPlayerX.writeObject(newGameX);
			socketOutPlayerO.writeObject(newGameO);
			while(newGameX.getPlayerX() == null && newGameO.getPlayerO() == null) {
				newGameO = (GameInformation) socketInPlayerO.readObject();
				newGameX = (GameInformation) socketInPlayerX.readObject();
			}
			GameInformation newGame = newGameX;
			newGame.setPlayerO(newGameO.getPlayerO());
			newGame.setDisplayInstruction(1);
			socketOutPlayerO.writeObject(newGame);
			socketOutPlayerX.writeObject(newGame);
			gameInfo = newGame;
		} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setGameInfo(gameInfo);
		gameInfo.setDisplayInstruction(1);
		try {
			socketOutPlayerO.writeObject(gameInfo);
			socketOutPlayerX.writeObject(gameInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				gameInfo = (GameInformation) socketInPlayerX.readObject();
				if (gameInfo.getDisplayInstruction()==10) {
					socketOutPlayerO.writeObject(gameInfo);
					socketOutPlayerX.writeObject(gameInfo);
					break;
				}
				updateBoard();
				if (gameOver()) {
					socketOutPlayerO.writeObject(gameInfo);
					socketOutPlayerX.writeObject(gameInfo);
					break;
				}
				gameInfo.setPlayer("O");
				socketOutPlayerO.writeObject(gameInfo);
				socketOutPlayerX.writeObject(gameInfo);
				System.out.println(gameInfo.getDisplayInstruction());
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				gameInfo = (GameInformation) socketInPlayerO.readObject();
				if (gameInfo.getDisplayInstruction()==10) {
					socketOutPlayerX.writeObject(gameInfo);
					socketOutPlayerO.writeObject(gameInfo);
					break;
				}
				updateBoard();
				if (gameOver()) {
					break;
				}
				gameInfo.setPlayer("X");
				socketOutPlayerO.writeObject(gameInfo);
				socketOutPlayerX.writeObject(gameInfo);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
		
	private void updateBoard() {
		boardCalc.setBoard(gameInfo.getBoard());
		boardCalc.display();
	}

	private boolean gameOver() {
		try {
			if (boardCalc.xWins()) {
				gameInfo.setDisplayInstruction(3);
				socketOutPlayerX.writeObject(gameInfo);
				socketOutPlayerO.writeObject(gameInfo);
				return true;
			}
			if (boardCalc.oWins()) {
				gameInfo.setDisplayInstruction(4);
				socketOutPlayerX.writeObject(gameInfo);
				socketOutPlayerO.writeObject(gameInfo);
				return true;
			}
			if (boardCalc.isFull()) {
				gameInfo.setDisplayInstruction(5);
				socketOutPlayerX.writeObject(gameInfo);
				socketOutPlayerO.writeObject(gameInfo);
				return true;
			}
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public GameInformation getGameInfo() {
		return gameInfo;
	}

	public void setGameInfo(GameInformation gameInfo) {
		this.gameInfo = gameInfo;
	}

}
