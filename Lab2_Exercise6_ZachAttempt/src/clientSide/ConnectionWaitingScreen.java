package clientSide;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectionWaitingScreen extends JFrame {
	
	private JLabel waiting;
	private JLabel warning;

	public ConnectionWaitingScreen() {
		waiting = new JLabel("Waiting for Connection...");
		warning = new JLabel("Closing this Window will Not Cancel Connection");
		setLayout(new BorderLayout());
		add("North",createTitle("Client Management Screen", 18));
		add("South", warning);
		this.add("Center", waiting);
		setSize(300,100);
		
		this.setVisible(true);
	}
	
	private JPanel createTitle(String titleName, int fontSize) {
		JPanel title = new JPanel();
		JLabel label = new JLabel(titleName);
		Font font = new Font("Helvetica", Font.BOLD,fontSize);
		label.setFont(font);
		title.add(label);
		return title;
	}
}
