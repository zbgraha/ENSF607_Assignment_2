

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * An object used to manage interactions between the GUI and the database model
 * @author Riley Berry
 * @version 1.0
 * @since 2019-11-15
 *
 */
public class ClientManagerController {

	/**
	 * The Client Manager Model used to communicate with the database
	 */
	private ClientManagerModel model;
	/**
	 * The ClientManagerGUI used to create a GUI and record actions from user input
	 */
	private ClientManagerGUI view;
	
	
	/**
	 * Constructor for the controller object.
	 * Creates an instance of the model and viewer, initializes the required parameters and links the listeners to the GUI
	 * @param model The model object to be used to interact with the database
	 * @param view the GUI object used to interact with the user
	 */
	public ClientManagerController(ClientManagerModel model, ClientManagerGUI view) {
		/**
		 * The model used to interact with the database
		 */
		this.model =model;
		/**
		 * the GUI object used to interact with the user
		 */
		this.view = view;
		
		linkActionListeners();
		linkListListeners();
		
//		
//		// If database is already created, an exception is caught in the ClientManagerModel
//		model.createDB();
//
//		// Comment out the following lines of code if DB persistence is desired after the first run
//		model.removeTable();
//		model.createTable();
//		model.fillTable();
	}


	public ClientManagerModel getModel() {
		return this.model;
	}

	private void linkListListeners() {
		
		view.addSearchBtnListener(new SearchBtnListener());
		view.addClearSearchBtnListener((ActionEvent e) -> {	view.clearSearchResults();	});
		view.addDeleteBtnListener((ActionEvent e) -> { model.deleteClient(view.getClientIDTextField()); });
		// Shouldn't need,user cannot adjust Client ID
		view.addClearBtnListener((ActionEvent e) ->{view.clearClientInfo();});
		view.addSaveBtnListener(new SaveButtonListener());
		view.addAddClientBtnListener(new AddClientButtonListener());
			
	}
	
	private void linkActionListeners() {
	
//		view.addSearchResultListener(new SearchResultSelectionListener());
		view.addSearchResultListener((ListSelectionEvent e)->{view.showSerchSelectionDetails();});
		
	}
	
	
	
	private Client inputToClient() {
		Client c;
		int iD;
		try {
			iD = view.getClientIDTextField();
		}catch(NumberFormatException e){
			iD=0;
		}
		
		String fName = view.getFNameTextField();
		String lName = view.getLNameTextField();
		String address = view.getAddressTextField();
		String postalCode = view.getPostalCodeTextField().toUpperCase();
		String phoneNum =view.getPhoneNumTextField();
		String clientType = view.getClientType();
		
		if (verifyUserInput(fName,lName,address,postalCode,phoneNum,clientType)) {
			c = new Client(iD,fName,lName,address,postalCode,phoneNum,clientType);
			return c;
		}
		
		return null;
	}
	
	
	
	private boolean verifyUserInput(String fName, String lName, String address,
			String postalCode, String phoneNum,	String clientType) {
		try {
	
			if (verifyFNameFormat(fName) && verifyLNameFormat(lName) && verifyAddressFormat(address)&&
					verifyPostalCodeFormat(postalCode)&& verifyPhnoneNumFormat(phoneNum)&&
					verifyClientType(clientType)) {
				return true;
			}	
		}
		catch(NameFormatException e1) {}
		catch(AddressFormatException e1) {}
		catch (PostalCodeException e1) {}
		catch(PhoneNumException e1) {}
		catch(ClientTypeException e1) {};
		

		return false;
	}

	//Might be better to implement an interface with constants for the options
	
	// Generate Error windows if input is invalid
	private boolean verifyClientType(String clientType) throws ClientTypeException {
		if (clientType.contentEquals("C") || clientType.contentEquals("R")) {
			return true;
		}
		else {
			throw new ClientTypeException();
		}
	}


	private boolean verifyFNameFormat(String fName) throws NameFormatException {
		if (fName.length() <=20 && fName.length()>0) {
			return true;
		}
		else {
			throw new NameFormatException();
		}		
	}
	
	private boolean verifyLNameFormat(String lName) throws NameFormatException {
		if (lName.length() <=20 && lName.length()>0) {
			return true;
		}
		else {
			throw new NameFormatException();
		}
				
	}
	
	private boolean verifyAddressFormat(String address) throws AddressFormatException {
		if (address.length() <=50 && address.length()>0) {
			return true;
		}
		else {
			throw new AddressFormatException();
		}
		
	}
	
	private boolean verifyPostalCodeFormat(String postalCode) throws PostalCodeException {
		String regEx = "[A-Z]\\d[A-Z]\\s\\d[A-Z]\\d";
		Pattern pattern = Pattern.compile(regEx);
		
		if (postalCode.length() ==7 && pattern.matcher(postalCode).matches()) {
			return true;
		}
		else {
			throw new PostalCodeException();
		}
		

		
	}
	
	private boolean verifyPhnoneNumFormat(String PhoneNum) throws PhoneNumException {	
		
		String regEx = "\\d{3}-\\d{3}-\\d{4}";
		Pattern pattern = Pattern.compile(regEx);
		if (PhoneNum.length() ==12 && pattern.matcher(PhoneNum).matches()) {
			return true;
		}
		else {
			throw new PhoneNumException();
		}

		

	}
	
	
	/**
	 * sub-class used to link the front end button to the save command if all inputs are valid
	 * Updates an existing client
	 * @author Riley Berry
	 * @version 1.0
	 * @since 2019-11-15
	 *
	 */
	public class SaveButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Client c = inputToClient();
			if (c != null) {
				model.updateClient(c);
			}
		}
	}
	/**
	 * sub-class used to link the front end button to the add command if all inputs are valid
	 * Creates a new client
	 * @author Riley Berry
	 * @version 1.0
	 * @since 2019-11-15
	 *
	 */
	public class AddClientButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Client c = inputToClient();
			if (c != null) {
				model.addClient(c);
			}
		}
	}
	
	/**
	 * When the search button is pressed, it will determine what type of search to perform based on radio button group
	 * 
	 * @author Riley Berry
	 * @version 1.0
	 * @since 2019-11-15
	 *
	 * As designed violates the open-closed principle. Not sure the best way to change this 
	 */
	public class SearchBtnListener implements ActionListener{

		String searchParam;
		ArrayList <Client> clients;
		@Override
		public void actionPerformed(ActionEvent e) {
			searchParam = view.getSearchParamField();

			if(view.getClientIDRdbtn()) {
				try {
					clients = model.findClientListByID(Integer.parseInt(searchParam));
				}catch(NumberFormatException e1) {
					view.setSearchParamField("ID must be an Integer");
					// Generate a pop-up instead?
				}
			}
			else if(view.getClientTypeRdbtn()) {
				clients = model.findClientListByType(searchParam.toUpperCase());
			}
			
			else if (view.getLNameRdbtn()) {
				clients = model.findClientListByLName(searchParam);
			}
			if(clients != null) {
				view.setSearchResult(clients);
			}
		}
	}
//	/**
//	 * 
//	 * @author Riley Berry
//	 * @version 1.0
//	 * @since 2019-11-15
//	 *
//	 */
//	public class SearchResultSelectionListener implements ListSelectionListener{
//
//		@Override
//		public void valueChanged(ListSelectionEvent e) {
//			view.showSerchSelectionDetails();		
//		};
//	}
	
/////////////////////////////////////////
	///////// Custom Exceptions
	
	
	/**
	 * Exception thrown when the postal code input does not follow the required format
	 * @author Riley Berry
	 * @version 1.0
	 * @since 2019-11-15
	 *
	 */
	@SuppressWarnings("serial")
	public class PostalCodeException extends Exception{
		public PostalCodeException() {
			super();		
			JOptionPane.showMessageDialog(null,	"Postal Code format must be A1A 1A1, where A is a letter and 1 is a digit",
					"Invalid Postal Code", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Exception thrown when the phone number does not follow the required format
	 * @author Riley Berry
	 * @version 1.0
	 * @since 2019-11-15
	 *
	 */
	@SuppressWarnings("serial")
	public class PhoneNumException extends Exception{
		public PhoneNumException() {
			super();		
			JOptionPane.showMessageDialog(null,	"Phone Number format must be ###-###-####, where # is any digit",
					"Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Exception thrown when a name does not follow the required format
	 * @author Riley Berry
	 * @version 1.0
	 * @since 2019-11-15
	 *
	 */
	@SuppressWarnings("serial")
	public class NameFormatException extends Exception{
		public NameFormatException() {
			super();		
			JOptionPane.showMessageDialog(null,	"Names have a max length of 20 characters and cannot be empty",
					"Name Format", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Exception thrown when the address does not follow the required format
	 * @author Riley Berry
	 * @version 1.0
	 * @since 2019-11-15
	 *
	 */
	@SuppressWarnings("serial")
	public class AddressFormatException extends Exception{
		public AddressFormatException() {
			super();		
			JOptionPane.showMessageDialog(null,	"Address have a max length of 50 characters and cannot be empty",
					"Address Format", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Exception thrown when the client type does not follow the required format
	 * @author Riley Berry
	 * @version 1.0
	 * @since 2019-11-15
	 *
	 */
	@SuppressWarnings("serial")
	public class ClientTypeException extends Exception{
		public ClientTypeException() {
			super();		
			JOptionPane.showMessageDialog(null,	"Must Select a Client Type",
					"Invalid Client Type", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
}

































