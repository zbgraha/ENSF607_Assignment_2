

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * An application to run the Client manager application
 * @author Riley Berry
 * @version 1.0
 * @since 2019-11-15
 *
 */
public class ClientManagerGUI {

	/**
	 * The main container of the GUI
	 */
	private JFrame frmClientManagementScreen;
	/**
	 * The text field where users enter a search parameter
	 */
	private JTextField searchParamField;
	/**
	 * The button group containing the raido buttons
	 */
	private final ButtonGroup buttonGroup = new ButtonGroup();
	/**
	 * The text field where the client ID is displayed, cannot be modified by user
	 */
	private JTextField clientIDTextField;
	/**
	 * The text field displaying the modifiable first name
	 */
	private JTextField fNameTextField;
	/**
	 * The text field displaying the modifiable last name
	 */
	private JTextField lNameTextField;
	/**
	 * The text field displaying the modifiable address
	 */
	private JTextField addressTextField;
	/**
	 * The text field displaying the modifiable postal code
	 */
	private JTextField postalCodeTextField;
	/**
	 * The text field displaying the modifiable phone number
	 */
	private JTextField phoneNumTextField;
	/**
	 * The menu with the available client types
	 */
	private JComboBox<String> clientTypeComboBox;
	/**
	 * The list of search results
	 */
	private JList searchResultList;
	
	/**
	 * Enable to search by client ID
	 */
	private JRadioButton clientIDRdbtn;
	/**
	 * Enable to search by client last name
	 */
	private JRadioButton lNameRdbtn;
	/**
	 * Enable to search by client type
	 */
	private JRadioButton clientTypeRdbtn;
	
	/**
	 * Button to search based for the supplied information
	 */
	private JButton searchBtn;
	/**
	 * Clears the search area and the search query
	 */
	private JButton clearSearchBtn;
	/**
	 * Deletes the selected client, based on shown client ID
	 */
	private JButton deleteBtn;
	/**
	 * Clears the detailed client information window
	 */
	private JButton clearBtn;
	/**
	 * Updates the information of the client, based on client ID
	 */
	private JButton saveBtn;
	/**
	 * Adds a new client to the database, displayed ID will be ignored and a new one will be automatically generated
	 */
	private JButton addClientBtn;
	
	/**
	 * Types of available clients
	 */
	private final String[] clientTypes = {"-","C", "R"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientManagerGUI window = new ClientManagerGUI();
					window.frmClientManagementScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientManagerGUI() {
		initialize();
		frmClientManagementScreen.setVisible(true);
	}
	
//	public void addClientIDListener(ActionListener listener) {
//		clientIDRdbtn.addActionListener(listener);
//	}
//	
//	public void addLNameListener(ActionListener listener) {
//		lNameRdbtn.addActionListener(listener);
//	}
//	
//	public void addClientTypeListener(ActionListener listener) {
//		clientTypeRdbtn.addActionListener(listener);
//	}
	/*
	 * Clears the search result window and the search criteria text box
	 */
	public void clearSearchResults() {
		searchResultList.removeAll();//  getModel().   removeAllElements();// removeAll();
		searchResultList.setListData( (new ArrayList<Client>()).toArray());
		searchParamField.setText("");
	}
	
	/**
	 * Shows the detailed information of the client based on the selection of from the Search result window
	 */
	public void showSerchSelectionDetails() {
		try {
		Client c = (Client) searchResultList.getSelectedValue();
		setClientIDTextField(c.getId());
		setFNameTextField(c.getfName());
		setLNameTextField(c.getlName());
		setAddressTextField(c.getAddress());
		setPostalCodeTextField(c.getPostalCode());
		setPhoneNumTextField(c.getPhoneNum());
		setClientType(c.getClientType());
		}catch (Exception e) {
			clearClientInfo();
		}
		
	}
	/**
	 * Clears all of the detailed client information
	 */
	public void clearClientInfo() {
		setClientIDTextField(-1);
		setFNameTextField("");
		setLNameTextField("");
		setAddressTextField("");
		setPostalCodeTextField("");
		setPhoneNumTextField("");
		setClientType(""); // Will default to "-"
		
	}
	///////////////////////// Action Listeners
	public void addSearchBtnListener(ActionListener listener) {
		searchBtn.addActionListener(listener);
	}
	public void addClearSearchBtnListener(ActionListener listener) {
		clearSearchBtn.addActionListener(listener);
	}
	public void addDeleteBtnListener(ActionListener listener) {
		deleteBtn.addActionListener(listener);
	}
	public void addClearBtnListener(ActionListener listener) {
		clearBtn.addActionListener(listener);
	}
	public void addSaveBtnListener(ActionListener listener) {
		saveBtn.addActionListener(listener);
	}
	public void addAddClientBtnListener(ActionListener listener) {
		addClientBtn.addActionListener(listener);
	}
	
	public void addSearchResultListener(ListSelectionListener listener) {
		searchResultList.addListSelectionListener(listener);
	}
	

	////////////////// Getters and Setters
	public void setSearchParamField(String s) {
		searchParamField.setText(s);
	}
	public String getSearchParamField() {
		return searchParamField.getText();
	}

	public void setClientIDTextField(int i) {
		if (i>=0) {
			clientIDTextField.setText(String.valueOf(i));
		}
		else {
			clientIDTextField.setText("");
		}
	}
	
	
	public int getClientIDTextField() {
		return Integer.parseInt(clientIDTextField.getText());
	}
	
	public void setFNameTextField(String s) {
		fNameTextField.setText(s);
	}
	public String getFNameTextField() {
		return fNameTextField.getText();
	}
	public void setLNameTextField(String s) {
		lNameTextField.setText(s);
	}
	public String getLNameTextField() {
		return lNameTextField.getText();
	}
	public void setAddressTextField(String s) {
		addressTextField.setText(s);
	}
	public String getAddressTextField() {
		return addressTextField.getText();
	}
	public void setPostalCodeTextField(String s) {
		postalCodeTextField.setText(s);
	}
	public String getPostalCodeTextField() {
		return postalCodeTextField.getText();
	}
	public void setPhoneNumTextField(String s) {
		phoneNumTextField.setText(s);
	}
	public String getPhoneNumTextField() {
		return phoneNumTextField.getText();
	}
	
	// Violates the open-closed principle
	// Not sure on the best method on how to modify
	public void setClientType(String s) {
		if (s.equals("C")){
			clientTypeComboBox.setSelectedIndex(1);
		}
		else if (s.equals("R")) {
			clientTypeComboBox.setSelectedIndex(2);
		}
		else {
			clientTypeComboBox.setSelectedIndex(0);
		}
	}
	public String getClientType() {
		return (String) clientTypeComboBox.getSelectedItem();
	}
	

	
	public void setSearchResult(ArrayList<Client> client) {
		searchResultList.clearSelection();
		searchResultList.setListData( client.toArray()); //
		
	}
		
	public boolean getClientIDRdbtn() {
		return clientIDRdbtn.isSelected();
	}
	public boolean getLNameRdbtn() {
		return lNameRdbtn.isSelected();
	}
	public boolean getClientTypeRdbtn() {
		return clientTypeRdbtn.isSelected();
	}

	
	///////////////////////////////
	///////// Helper Methods


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClientManagementScreen = new JFrame();
		frmClientManagementScreen.setTitle("Client Management Screen");
		frmClientManagementScreen.setBounds(100, 100, 800, 500);
//		frame.setPreferredSize(new Dimension(600,500));
		frmClientManagementScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 35, 126, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmClientManagementScreen.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblSearchClients = new JLabel("Search Clients");
		lblSearchClients.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchClients.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblSearchClients = new GridBagConstraints();
		gbc_lblSearchClients.gridwidth = 4;
		gbc_lblSearchClients.fill = GridBagConstraints.BOTH;
		gbc_lblSearchClients.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearchClients.gridx = 1;
		gbc_lblSearchClients.gridy = 1;
		frmClientManagementScreen.getContentPane().add(lblSearchClients, gbc_lblSearchClients);
		
		JLabel lblClientInformation = new JLabel("Client Information");
		lblClientInformation.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblClientInformation = new GridBagConstraints();
		gbc_lblClientInformation.insets = new Insets(0, 0, 5, 5);
		gbc_lblClientInformation.gridx = 8;
		gbc_lblClientInformation.gridy = 1;
		frmClientManagementScreen.getContentPane().add(lblClientInformation, gbc_lblClientInformation);
		
		JLabel lblSelectTypeOf = new JLabel("Select type of search to be performed");
		GridBagConstraints gbc_lblSelectTypeOf = new GridBagConstraints();
		gbc_lblSelectTypeOf.gridwidth = 4;
		gbc_lblSelectTypeOf.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSelectTypeOf.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectTypeOf.gridx = 1;
		gbc_lblSelectTypeOf.gridy = 2;
		frmClientManagementScreen.getContentPane().add(lblSelectTypeOf, gbc_lblSelectTypeOf);
		
		clientIDRdbtn = new JRadioButton("Client ID");
		clientIDRdbtn.setSelected(true);
		buttonGroup.add(clientIDRdbtn);
		GridBagConstraints gbc_clientIDRdbtn = new GridBagConstraints();
		gbc_clientIDRdbtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_clientIDRdbtn.gridwidth = 4;
		gbc_clientIDRdbtn.insets = new Insets(0, 0, 5, 5);
		gbc_clientIDRdbtn.gridx = 1;
		gbc_clientIDRdbtn.gridy = 3;
		frmClientManagementScreen.getContentPane().add(clientIDRdbtn, gbc_clientIDRdbtn);
		
		JLabel lblClientId = new JLabel("Client ID:");
		GridBagConstraints gbc_lblClientId = new GridBagConstraints();
		gbc_lblClientId.anchor = GridBagConstraints.EAST;
		gbc_lblClientId.insets = new Insets(0, 0, 5, 5);
		gbc_lblClientId.gridx = 7;
		gbc_lblClientId.gridy = 3;
		frmClientManagementScreen.getContentPane().add(lblClientId, gbc_lblClientId);
		
		clientIDTextField = new JTextField();
		clientIDTextField.setEditable(false);
		GridBagConstraints gbc_ClientIDTextField = new GridBagConstraints();
		gbc_ClientIDTextField.insets = new Insets(0, 0, 5, 5);
		gbc_ClientIDTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ClientIDTextField.gridx = 8;
		gbc_ClientIDTextField.gridy = 3;
		frmClientManagementScreen.getContentPane().add(clientIDTextField, gbc_ClientIDTextField);
		clientIDTextField.setColumns(10);
		
		lNameRdbtn = new JRadioButton("Last Name");
		buttonGroup.add(lNameRdbtn);
		GridBagConstraints gbc_lastNameRdbtn = new GridBagConstraints();
		gbc_lastNameRdbtn.gridwidth = 4;
		gbc_lastNameRdbtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameRdbtn.insets = new Insets(0, 0, 5, 5);
		gbc_lastNameRdbtn.gridx = 1;
		gbc_lastNameRdbtn.gridy = 4;
		frmClientManagementScreen.getContentPane().add(lNameRdbtn, gbc_lastNameRdbtn);
		
		JLabel lblFirstName = new JLabel("First Name:");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.EAST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 7;
		gbc_lblFirstName.gridy = 4;
		frmClientManagementScreen.getContentPane().add(lblFirstName, gbc_lblFirstName);
		
		fNameTextField = new JTextField();
		GridBagConstraints gbc_fNameTextField = new GridBagConstraints();
		gbc_fNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_fNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fNameTextField.gridx = 8;
		gbc_fNameTextField.gridy = 4;
		frmClientManagementScreen.getContentPane().add(fNameTextField, gbc_fNameTextField);
		fNameTextField.setColumns(10);
		
		clientTypeRdbtn = new JRadioButton("Client Type");
		buttonGroup.add(clientTypeRdbtn);
		GridBagConstraints gbc_clientTypeRdbtn = new GridBagConstraints();
		gbc_clientTypeRdbtn.gridwidth = 4;
		gbc_clientTypeRdbtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_clientTypeRdbtn.insets = new Insets(0, 0, 5, 5);
		gbc_clientTypeRdbtn.gridx = 1;
		gbc_clientTypeRdbtn.gridy = 5;
		frmClientManagementScreen.getContentPane().add(clientTypeRdbtn, gbc_clientTypeRdbtn);
		
		JLabel lblLastName = new JLabel("Last Name:");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 7;
		gbc_lblLastName.gridy = 5;
		frmClientManagementScreen.getContentPane().add(lblLastName, gbc_lblLastName);
		
		lNameTextField = new JTextField();
		GridBagConstraints gbc_lNameTextField = new GridBagConstraints();
		gbc_lNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_lNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lNameTextField.gridx = 8;
		gbc_lNameTextField.gridy = 5;
		frmClientManagementScreen.getContentPane().add(lNameTextField, gbc_lNameTextField);
		lNameTextField.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address:");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.EAST;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 7;
		gbc_lblAddress.gridy = 6;
		frmClientManagementScreen.getContentPane().add(lblAddress, gbc_lblAddress);
		
		addressTextField = new JTextField();
		GridBagConstraints gbc_addressTextField = new GridBagConstraints();
		gbc_addressTextField.insets = new Insets(0, 0, 5, 5);
		gbc_addressTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressTextField.gridx = 8;
		gbc_addressTextField.gridy = 6;
		frmClientManagementScreen.getContentPane().add(addressTextField, gbc_addressTextField);
		addressTextField.setColumns(10);
		
		JLabel lblEnterTheSearch = new JLabel("Enter the search parameter below:");
		GridBagConstraints gbc_lblEnterTheSearch = new GridBagConstraints();
		gbc_lblEnterTheSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEnterTheSearch.gridwidth = 4;
		gbc_lblEnterTheSearch.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterTheSearch.gridx = 1;
		gbc_lblEnterTheSearch.gridy = 7;
		frmClientManagementScreen.getContentPane().add(lblEnterTheSearch, gbc_lblEnterTheSearch);
		
		JLabel lblPostalCode = new JLabel("Postal Code: ");
		GridBagConstraints gbc_lblPostalCode = new GridBagConstraints();
		gbc_lblPostalCode.anchor = GridBagConstraints.EAST;
		gbc_lblPostalCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblPostalCode.gridx = 7;
		gbc_lblPostalCode.gridy = 7;
		frmClientManagementScreen.getContentPane().add(lblPostalCode, gbc_lblPostalCode);
		
		postalCodeTextField = new JTextField();
		GridBagConstraints gbc_postalCodeTextField = new GridBagConstraints();
		gbc_postalCodeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_postalCodeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_postalCodeTextField.gridx = 8;
		gbc_postalCodeTextField.gridy = 7;
		frmClientManagementScreen.getContentPane().add(postalCodeTextField, gbc_postalCodeTextField);
		postalCodeTextField.setColumns(10);
		
		searchParamField = new JTextField();
		GridBagConstraints gbc_searchParamField = new GridBagConstraints();
		gbc_searchParamField.gridwidth = 2;
		gbc_searchParamField.anchor = GridBagConstraints.NORTHWEST;
		gbc_searchParamField.insets = new Insets(0, 0, 5, 5);
		gbc_searchParamField.fill = GridBagConstraints.BOTH;
		gbc_searchParamField.gridx = 1;
		gbc_searchParamField.gridy = 8;
		frmClientManagementScreen.getContentPane().add(searchParamField, gbc_searchParamField);
		searchParamField.setColumns(10);
		
		searchBtn = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 3;
		gbc_btnSearch.gridy = 8;
		frmClientManagementScreen.getContentPane().add(searchBtn, gbc_btnSearch);
		
		
		clearSearchBtn = new JButton("Clear Search");
		GridBagConstraints gbc_btnClearSearch = new GridBagConstraints();
		gbc_btnClearSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearSearch.gridx = 4;
		gbc_btnClearSearch.gridy = 8;
		frmClientManagementScreen.getContentPane().add(clearSearchBtn, gbc_btnClearSearch);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
		gbc_lblPhoneNumber.anchor = GridBagConstraints.EAST;
		gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhoneNumber.gridx = 7;
		gbc_lblPhoneNumber.gridy = 8;
		frmClientManagementScreen.getContentPane().add(lblPhoneNumber, gbc_lblPhoneNumber);
		
		phoneNumTextField = new JTextField();
		GridBagConstraints gbc_phoneNumTextField = new GridBagConstraints();
		gbc_phoneNumTextField.insets = new Insets(0, 0, 5, 5);
		gbc_phoneNumTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneNumTextField.gridx = 8;
		gbc_phoneNumTextField.gridy = 8;
		frmClientManagementScreen.getContentPane().add(phoneNumTextField, gbc_phoneNumTextField);
		phoneNumTextField.setColumns(10);
		
		JLabel lblSearchResults = new JLabel("Search Results");
		lblSearchResults.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblSearchResults = new GridBagConstraints();
		gbc_lblSearchResults.gridwidth = 4;
		gbc_lblSearchResults.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearchResults.gridx = 1;
		gbc_lblSearchResults.gridy = 9;
		frmClientManagementScreen.getContentPane().add(lblSearchResults, gbc_lblSearchResults);
		
		JLabel lblClientType = new JLabel("Client Type");
		GridBagConstraints gbc_lblClientType = new GridBagConstraints();
		gbc_lblClientType.anchor = GridBagConstraints.EAST;
		gbc_lblClientType.insets = new Insets(0, 0, 5, 5);
		gbc_lblClientType.gridx = 7;
		gbc_lblClientType.gridy = 9;
		frmClientManagementScreen.getContentPane().add(lblClientType, gbc_lblClientType);
		
		clientTypeComboBox = new JComboBox(clientTypes);
		clientTypeComboBox.setSelectedIndex(0);
		GridBagConstraints gbc_clientTypeComboBox = new GridBagConstraints();
		gbc_clientTypeComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_clientTypeComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_clientTypeComboBox.gridx = 8;
		gbc_clientTypeComboBox.gridy = 9;
		
		frmClientManagementScreen.getContentPane().add(clientTypeComboBox, gbc_clientTypeComboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 10;
		frmClientManagementScreen.getContentPane().add(scrollPane, gbc_scrollPane);
		
		searchResultList = new JList();
		scrollPane.setViewportView(searchResultList);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 7;
		gbc_panel.gridy = 10;
		frmClientManagementScreen.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		deleteBtn = new JButton("Delete Client");
		panel.add(deleteBtn);
		
		clearBtn = new JButton("Clear Information");
		panel.add(clearBtn);
		
		JLabel label_2 = new JLabel("");
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("");
		panel.add(label_3);
		
		saveBtn = new JButton("Save Changes");
		panel.add(saveBtn);
		
		addClientBtn = new JButton("Add Client");
		panel.add(addClientBtn);
		
		JLabel label = new JLabel("");
		panel.add(label);
		
		JLabel label_1 = new JLabel("");
		panel.add(label_1);
		
		
		

	}

}
