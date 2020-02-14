package clientSide;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * An object to create e label
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
 *
 */
public class ConnectionWaitingScreen extends JFrame {
	
	/**
	 * label for waiting message
	 */
	private JLabel waiting;
	/**
	 * label for waring message
	 */
	private JLabel warning;

	/**
	 * creates a connection waiting screen
	 */
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
	
	/**
	 * title for the warning screen
	 * @param titleName label title
	 * @param fontSize 
	 * @return Jpanel with the created title
	 */
	private JPanel createTitle(String titleName, int fontSize) {
		JPanel title = new JPanel();
		JLabel label = new JLabel(titleName);
		Font font = new Font("Helvetica", Font.BOLD,fontSize);
		label.setFont(font);
		title.add(label);
		return title;
	}
}
