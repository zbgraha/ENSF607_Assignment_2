package exercise4_5_FrontEnd;

import exercise4_5_Shared.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JScrollBar;
import java.awt.GridBagConstraints;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

public class TicTacToeViewer implements Constants {

	private JFrame frmRileysTictactoeGame;
//	private final ButtonGroup ticButtonGroup = new ButtonGroup();
	private JTextField server;
	private JTextField port;
	private JButton restartButton;
	private JTextField playerName;
	private JTextField playerMark;
	private JTextField opponentName;
	private JTextArea messageBox;
	private TicButton [] ticButtonList;
	private JButton connectButton;
	
	

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TicTacToeViewer window = new TicTacToeViewer();
//					window.frmRileysTictactoeGame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	

	/**
	 * Create the application.
	 */
	public TicTacToeViewer() {
		ticButtonList= new TicButton [9];
		initialize();
		frmRileysTictactoeGame.setVisible(true);
	}
	
	public int getTicButtonListLength() {
		return ticButtonList.length;
	}

	public void isActive() {
		System.out.println(frmRileysTictactoeGame.isEnabled());
//		frmRileysTictactoeGame.updateWindow();
		
	}
	
	
	
	
	////////////////////
	//////// Getters and Setters

	public String getPopupMessage(String question) {
		
		String ans = JOptionPane.showInputDialog(frmRileysTictactoeGame,question,null, JOptionPane.PLAIN_MESSAGE);
		
		return ans;
	}
	
	public boolean getYesNoDialogBox(String message) {
		int ans =  JOptionPane.showConfirmDialog(null,message);
		
//		System.out.println(ans);
		
		if(ans == 0) {
			return true;
		}
		else
			return false;
		
	}
	
	public String getServer() {
		return server.getText();
	}
	
	public void setServer(String newServer) {
		server.setText(newServer);
	}
	
	public int getPort() {
		try {
			int newPort = Integer.parseInt(port.getText());
			return  newPort;
		}catch(NumberFormatException e) {
			// give error message
		}
		return 9899;
	}
	
	public void setPort(int newPort) {
		port.setText(newPort+"");
	}
	
	public void setPlayerName(String name) {
		playerName.setText(name);
	}
	
	public String getPlayerName() {
		return playerName.getText();
	}
	
	public void setOpponentName(String name) {
		opponentName.setText(name);
	}
	
	public String getOpponentName() {
		return opponentName.getText();
	}
	
	public void setPlayerMark(char mark) {
		playerMark.setText(mark + "");
	}
	
	public char getPlayerMark() {
		return playerMark.getText().charAt(0);
	}
	
	
	public void addMessage(String newMsg) {
		String msg = messageBox.getText();
		msg += "\n"+newMsg;
		messageBox.setText(msg);
	}
	
	
	//////////////////////////////////////////////
	//////////////// Object Methods
	
	public void clearMessage() {
		messageBox.setText("");
	}
	
	public void updateBoard(char[][] board) {
		if (ticButtonList !=null) {
			for(TicButton button: ticButtonList) {
				button.setText(board[button.getRowNum()][button.getColNum()]+"");
			}
		}	
	}
	
	public void clearBoard() {
		if (ticButtonList !=null) {
			for(TicButton button: ticButtonList) {
				button.setText(SPACE_CHAR+"");
			}
		}	
	}
	
	///////////////////////////////////
	///// Attaching Action Listeners
	public void addRestartButtonListener(ActionListener l) {
		restartButton.addActionListener(l);
	}
	
	public void addTicButtonListeners(ActionListener l, int index) {
		ticButtonList[index].addActionListener(l);
	}
	
	public void addConnectButtonListener(ActionListener l) {
		connectButton.addActionListener(l);
	}
	
	
	
///////////////////////////////////
	//////// Auto generated Code
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRileysTictactoeGame = new JFrame();
		frmRileysTictactoeGame.setTitle("Riley's Tic-Tac-Toe Game");
		frmRileysTictactoeGame.setBounds(100, 100, 539, 414);
		frmRileysTictactoeGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRileysTictactoeGame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(285, 68, 228, 200);
		frmRileysTictactoeGame.getContentPane().add(scrollPane);
		
		messageBox = new JTextArea();
		messageBox.setLineWrap(true);
		messageBox.setText("Welcome to a new Game!");
		messageBox.setEditable(false);
		scrollPane.setViewportView(messageBox);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 68, 265, 200);
		frmRileysTictactoeGame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(3, 3, 0, 0));
		
		TicButton ticBox0_0 = new TicButton(SPACE_CHAR,0,0);
		panel.add(ticBox0_0);
		ticButtonList[0]= ticBox0_0;
		
		TicButton ticBox0_1 = new TicButton(SPACE_CHAR,0,1);
		panel.add(ticBox0_1);
		ticButtonList[1]= ticBox0_1;
		TicButton ticBox0_2 = new TicButton(SPACE_CHAR,0,2);
		panel.add(ticBox0_2);
		ticButtonList[2]= ticBox0_2;
		TicButton ticBox1_0 = new TicButton(SPACE_CHAR,1,0);
		panel.add(ticBox1_0);
		ticButtonList[3]= ticBox1_0;
		TicButton ticBox1_1 = new TicButton(SPACE_CHAR,1,1);
		panel.add(ticBox1_1);
		ticButtonList[4]= ticBox1_1;
		TicButton ticBox1_2 = new TicButton(SPACE_CHAR,1,2);
		panel.add(ticBox1_2);
		ticButtonList[5]= ticBox1_2;
		TicButton ticBox2_0 = new TicButton(SPACE_CHAR,2,0);
		panel.add(ticBox2_0);
		ticButtonList[6]= ticBox2_0;
		TicButton ticBox2_1 = new TicButton(SPACE_CHAR,2,1);
		panel.add(ticBox2_1);
		ticButtonList[7]= ticBox2_1;
		TicButton ticBox2_2 = new TicButton(SPACE_CHAR,2,2);
		panel.add(ticBox2_2);
		ticButtonList[8]= ticBox2_2;
		
		restartButton = new JButton("Restart");
		restartButton.setBounds(93, 279, 91, 23);
		frmRileysTictactoeGame.getContentPane().add(restartButton);
		
		JLabel lblNewLabel = new JLabel("Tic-Tac-Toe");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(196, 11, 155, 46);
		frmRileysTictactoeGame.getContentPane().add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(44, 313, 210, 58);
		frmRileysTictactoeGame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblName = new JLabel("Your Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblName);
		
		playerName = new JTextField("Not entered");
		playerName.setEditable(false);
		panel_1.add(playerName);
		
		JLabel lblNewLabel_2 = new JLabel("Your Mark");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblNewLabel_2);
		
		playerMark = new JTextField("  ");
		playerMark.setEditable(false);
		panel_1.add(playerMark);
		
		JLabel lblNewLabel_4 = new JLabel("Opponent Name");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblNewLabel_4);
		
		opponentName = new JTextField("Not Connected");
		opponentName.setEditable(false);
		panel_1.add(opponentName);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(295, 313, 218, 58);
		frmRileysTictactoeGame.getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		connectButton = new JButton("Connect To Server");
		panel_2.add(connectButton, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_6 = new JLabel("Server");
		panel_3.add(lblNewLabel_6);
		
		server = new JTextField();
		panel_3.add(server);
		server.setText("localhost");
		server.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		panel_3.add(lblPort);
		
		port = new JTextField();
		panel_3.add(port);
		port.setText("9899");
		port.setColumns(10);
	}





}
