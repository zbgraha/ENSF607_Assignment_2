package clientSide;


import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import customerAndActions.Customer;

/**Class that contains all of the main UI elements for the client management system
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
 *
 */
public class ClientManagementScreen extends JFrame{

	private JPanel clientInformation;
	private JPanel clientSearch;
	private JTextField clientID = new JTextField(3);
	private JTextField firstName = new JTextField(10);
	private JTextField lastName = new JTextField(10);
	private JTextField address = new JTextField(15);
	private JTextField postCode = new JTextField(6);
	private JTextField phone = new JTextField(8);
	private ButtonGroup clientType = new ButtonGroup();
	private JRadioButton rTypeButton = new JRadioButton();
	private JRadioButton cTypeButton = new JRadioButton();
	private JButton save = new JButton("Save/Modify");
	private JButton delete = new JButton("Delete");
	private JButton clear = new JButton("Clear");
	private JButton add = new JButton("Add");
	private ButtonGroup searchType = new ButtonGroup();
	private JRadioButton clientIDSearch = new JRadioButton();
	private JRadioButton lastNameSearch = new JRadioButton();
	private JRadioButton clientTypeSearch = new JRadioButton();
	private JTextField searchCriteria = new JTextField(15);
	private JButton search = new JButton("Search");
	private JButton clearSearch = new JButton("Clear Search");
	private DefaultListModel clients = new DefaultListModel();
	private JList clientList = new JList(clients);
	
	/**Constructor for the main UI of the client management system
	 * 
	 */
	public ClientManagementScreen() {
		
		setLayout(new BorderLayout());
		add("North",createTitle("Client Management Screen", 18));
		createClientInfoPanel();
		createSearchClientScreen();
		JPanel center = new JPanel(new GridLayout(1,2,10,10));
		center.add(clientSearch);
		center.add(clientInformation);
		add("Center",center);
		add("West",new JLabel("     "));
		add("East",new JLabel("     "));
		add("South", new JLabel("\n"));
		
		setClientIDSearch(true);
		rTypeButton.setSelected(true);
		clientID.setEditable(false);
		
		setSize(900,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	/**Takes a string argument and displays the string as an error message over the main UI
	 * @param message
	 */
	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message,"Error",JOptionPane.ERROR_MESSAGE);
	}
	
	/**Takes a string argument and displays the string as an information message over the main UI.
	 * @param message
	 */
	public void displaySuccessMessage(String message) {
		JOptionPane.showMessageDialog(this, message,"Error",JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**Accepts a string array containing client data and displays parts of it to the scrollable
	 * client list window in the main UI.
	 * @param clientList
	 */
	public void updateClientList(ArrayList<Customer> clientList) {
		for (Customer client: clientList) {
			clients.addElement(client);
		}
	}
	
	/**Removes all current elements in the scrollable client list window on the main UI
	 * 
	 */
	public void clearClientList() {
		clients.removeAllElements();
	}
	
	/**Removes all of the text in the client information text fields.
	 * 
	 */
	public void clear() {
		setClientID("");
		setFirstName("");
		setLastName("");
		setAddress("");
		setPostCode("");
		setPhone("");
	}
	
	public void deselectClients() {
		clientList.clearSelection();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////Action Listeners
	
	public void addClientSelectListener (ListSelectionListener listen) {
		clientList.getSelectionModel().addListSelectionListener(listen);
	}
	
	public void addSaveListener (ActionListener listen) {
		save.addActionListener(listen);
	}
	
	public void addAddListener (ActionListener listen) {
		add.addActionListener(listen);
	}
	
	public void addDeleteListener (ActionListener listen) {
		delete.addActionListener(listen);
	}
	
	public void addClearListener (ActionListener listen) {
		clear.addActionListener(listen);
	}
	
	public void addSearchListener (ActionListener listen) {
		search.addActionListener(listen);
	}
	
	public void addClearSearchListener (ActionListener listen) {
		clearSearch.addActionListener(listen);
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////Helper Functions
	
	private JPanel createTitle(String titleName, int fontSize) {
		JPanel title = new JPanel();
		JLabel label = new JLabel(titleName);
		Font font = new Font("Helvetica", Font.BOLD,fontSize);
		label.setFont(font);
		title.add(label);
		return title;
	}

	private void createClientInfoPanel() {
		clientInformation = new JPanel();
		clientInformation.setLayout(new BoxLayout(clientInformation,BoxLayout.Y_AXIS));
		clientInformation.add(createTitle("Client Information", 16));
		clientInformation.add(clientInfoJPanel(clientID,"Client ID:"));
		clientInformation.add(clientInfoJPanel(firstName,"First Name:"));
		clientInformation.add(clientInfoJPanel(lastName,"Last Name:"));
		clientInformation.add(clientInfoJPanel(address,"Address:"));
		clientInformation.add(clientInfoJPanel(postCode,"Postal Code:"));
		clientInformation.add(clientInfoJPanel(phone,"Phone Number:"));
		clientInformation.add(createClientTypeSelect());
		clientInformation.add(createClientInfoButtons());
		
		setSize (300,300);
	}
	
	private void createSearchClientScreen() {
		clientSearch = new JPanel();
		clientSearch.setLayout(new BoxLayout(clientSearch,BoxLayout.Y_AXIS));
		clientSearch.add(createTitle("Search Clients",16));
		clientSearch.add(createTitle("Select the type of search to be performed:",14));
		clientSearch.add(radioButtonMaker(searchType, clientIDSearch,"Client ID"));
		clientSearch.add(radioButtonMaker(searchType, lastNameSearch,"Last Name"));
		clientSearch.add(radioButtonMaker(searchType, clientTypeSearch,"Client Type"));
		clientSearch.add(createTitle("Enter the search parameters below:",14));
		clientSearch.add(searchParameterBox());
		clientSearch.add(createTitle("Search Results:",16));
		clientSearch.add(scrollWindowClients());
	}

	private JPanel searchParameterBox() {
		JPanel searchBox = new JPanel();
		searchBox.add(searchCriteria);
		searchBox.add(search);
		searchBox.add(clearSearch);
		return searchBox;
	}

	private JPanel radioButtonMaker(ButtonGroup group, JRadioButton button, String string) {
		JPanel radioButton = new JPanel();
		group.add(button);
		radioButton.add(button);
		radioButton.add(new JLabel(string));
		return radioButton;
	}

	private JScrollPane scrollWindowClients() {
	    JScrollPane scroll = new JScrollPane(clientList);
	    clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setSize(20, 40);
	    return scroll;
	}

	private JPanel createClientTypeSelect() {
		JPanel clientType = new JPanel(new FlowLayout());
		clientType.add(new JLabel("Client Type:"));
		clientType.add(radioButtonMaker(this.clientType = new ButtonGroup(), rTypeButton, "Residential"));
		clientType.add(radioButtonMaker(this.clientType, cTypeButton, "Commercial"));
		return clientType;
	}

	private JPanel createClientInfoButtons() {
		JPanel clientInfoButtons = new JPanel();
		clientInfoButtons.add(add);
		clientInfoButtons.add(save);
		clientInfoButtons.add(delete);
		clientInfoButtons.add(clear);
		return clientInfoButtons;
	}

	private JPanel clientInfoJPanel(JTextField textField, String string) {
		JPanel j = new JPanel();
		j.add(new JLabel(string));
		j.add(textField);
		return j;
	}
	
	private boolean validPostCode(String postCode) {
		if (postCode.length() != 7)
			return false;
		if (postCode.charAt(3) != ' ')
			return false;
		if (invalidLetters(postCode))
			return false;
		if (invalidNumbers(postCode))
			return false;
		return true;
	}
	
	private boolean invalidLetters(String postCode) {
		if (postCode.charAt(0) < 'A' || postCode.charAt(0) > 'Z')
			return true;
		if (postCode.charAt(2) < 'A' || postCode.charAt(2) > 'Z')
			return true;
		if (postCode.charAt(5) < 'A' || postCode.charAt(5) > 'Z')
			return true;
		return false;
	}

	private boolean invalidNumbers(String postCode) {
		if (postCode.charAt(1) < '0' || postCode.charAt(1) > '9')
			return true;
		if (postCode.charAt(4) < '0' || postCode.charAt(4) > '9')
			return true;
		if (postCode.charAt(6) < '0' || postCode.charAt(6) > '9')
			return true;
		return false;
	}
	
	private boolean validPhone(String phone) {
		if (phone.length() != 12) {
			return false;
		}
		for (int i = 0; i < phone.length(); i++) {
			if (i == 3 || i == 7) {
				if (phone.charAt(i) != '-') {
				return false;
				}
			}
			else if(phone.charAt(i) > '9' || phone.charAt(i) < '0') {
				return false;
			}
		}
		return true;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////Getters and Setters
	

	public String getClientID() {
		return clientID.getText();
	}

	public void setClientID(String clientID) {
		this.clientID.setText(clientID);
	}

	public String getFirstName() throws InvalidFirstNameException {
		if (firstName.getText().length() > 20)
			throw new InvalidFirstNameException();
		else
			return firstName.getText();
	}

	public void setFirstName(String firstName) {
		this.firstName.setText(firstName);
	}

	public String getLastName() throws InvalidLastNameException {
		if (lastName.getText().length() > 20)
			throw new InvalidLastNameException();
		else
			return lastName.getText();
	}

	public void setLastName(String lastName) {
		this.lastName.setText(lastName);;
	}

	public String getAddress() throws InvalidAddressException {
		if (address.getText().length() > 50)
			throw new InvalidAddressException();
		else
			return address.getText();
	}

	public void setAddress(String address) {
		this.address.setText(address);
	}

	public String getPostCode() throws InvalidPostCodeException {
		if (!validPostCode(postCode.getText().toUpperCase()))
			throw new InvalidPostCodeException();
		else
			return postCode.getText().toUpperCase();
	}

	public void setPostCode(String postCode) {
		this.postCode.setText(postCode);
	}

	public String getPhone() throws InvalidPhoneException {
		if (!validPhone(phone.getText()))
			throw new InvalidPhoneException();
		else
			return phone.getText();
	}

	public void setPhone(String phone) {
		this.phone.setText(phone);
	}

	public boolean getrTypeButton() {
		return rTypeButton.isSelected();
	}

	public void setrTypeButton(boolean select) {
		this.rTypeButton.setSelected(select);
	}

	public boolean getcTypeButton() {
		return cTypeButton.isSelected();
	}

	public void setcTypeButton(boolean select) {
		this.cTypeButton.setSelected(select);
	}

	public boolean getClientIDSearch() {
		return clientIDSearch.isSelected();
	}

	public void setClientIDSearch(boolean select) {
		this.clientIDSearch.setSelected(select);;
	}

	public boolean getLastNameSearch() {
		return lastNameSearch.isSelected();
	}

	public void setLastNameSearch(boolean select) {
		this.lastNameSearch.setSelected(select);;
	}

	public boolean getClientTypeSearch() {
		return clientTypeSearch.isSelected();
	}

	public void setClientTypeSearch(boolean select) {
		this.clientTypeSearch.setSelected(select);
	}

	public String getSearchCriteria() {
		return searchCriteria.getText();
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria.setText(searchCriteria);
	}
	
	public JList getClientList() {
		return clientList;
	}

	public void setClientList(JList clientList) {
		this.clientList = clientList;
	}

}

