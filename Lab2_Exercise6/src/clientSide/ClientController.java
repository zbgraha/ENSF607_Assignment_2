package clientSide;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import customerAndActions.Customer;
import customerAndActions.CustomerAndAction;

/**A controller that ties the UI for a client management system to its back
 * end functionality.
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
 *
 */
public class ClientController {

	private ClientManagementScreen UI;
	private ConnectionWaitingScreen wait;
	private Client client;
	
	/**Accepts a Client management object and its related database object and instantiates
	 * the listeners between them
	 * @param database
	 * @param UI
	 */
	public ClientController(ClientManagementScreen UI, ConnectionWaitingScreen wait) {
		this.UI = UI;
		this.wait = wait;
		
		UI.addSaveListener(new SaveListener());
		UI.addAddListener(new AddListener());
		UI.addDeleteListener(new DeleteListener());
		UI.addClearListener(new ClearListener());
		UI.addSearchListener(new SearchListener());
		UI.addClearSearchListener(new ClearSearchListener());
		UI.addClientSelectListener(new ClientSelectListener());
	}
	
	public void addCloseWindowListener() {
		UI.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        try {
					client.getSocketOut().writeObject(new CustomerAndAction(5));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
		wait.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	wait.setVisible(false);
		    }
		});
	}
	
	/**A listener that implements functionality for when a user makes a selection from the client
	 * list in the main UI. Parses and saves the client ID from the selection, conducts a database search,
	 * and populates the rightmost window with the client's information.
	 * @author Zachary Graham
	 *
	 */
	class ClientSelectListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (UI.getClientList().getSelectedValue() != null) {
				Customer c = (Customer) UI.getClientList().getSelectedValue();
				UI.setClientID(Integer.toString(c.getId()));
				UI.setFirstName(c.getFirstName());
				UI.setLastName(c.getLastName());
				UI.setAddress(c.getAddress());
				UI.setPostCode(c.getPostCode());
				UI.setPhone(c.getPhone());
				if (c.getType().equals("R"))
					UI.setrTypeButton(true);
				else
					UI.setcTypeButton(true);
			}
		}	
	}
	
	/**Listener that contains functionality for when a user presses the add button from the client information window.
	 * Saves the input in each of the boxes and adds a client to the database.
	 * @author Zachary Graham
	 *
	 */
	class AddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String type = "";
			if (UI.getcTypeButton())
				type = "C";
			else if (UI.getrTypeButton())
				type = "R";
			try {
				CustomerAndAction instruction = new CustomerAndAction(1);
				Customer c = new Customer();
				c.setFirstName(UI.getFirstName());
				c.setLastName(UI.getLastName());
				c.setAddress(UI.getAddress());
				c.setPhone(UI.getPhone());
				c.setPostCode(UI.getPostCode());
				c.setType(type);
				instruction.addCustomer(c);
				try {
					client.getSocketOut().writeObject(instruction);
				} catch (IOException e1) {
					UI.displayErrorMessage("I/O Exception");
				}
			}catch (InvalidFirstNameException ex) {
				UI.displayErrorMessage("First name must be 20 characters or less");
			} catch (InvalidLastNameException e1) {
				UI.displayErrorMessage("Last name must be 20 characters or less");
			} catch (InvalidAddressException e1) {
				UI.displayErrorMessage("Address must be 50 characters or less");
			} catch (InvalidPostCodeException e1) {
				UI.displayErrorMessage("Postal Code must follow the format: 'A1A 1A1'");
			} catch (InvalidPhoneException e1) {
				UI.displayErrorMessage("Phone number must follow the format: '111-111-1111'");
			}
		}	
	}
	
	/**Listener that takes the text fields from the UI client information window, and updates the matching fields
	 * for the client that matches the selected ID in the UI when the save/modify button is pushed.
	 * @author Zachary Graham
	 *
	 */
	class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String type = "";
			if (UI.getcTypeButton())
				type = "C";
			else if (UI.getrTypeButton())
				type = "R";
			try {
				CustomerAndAction instruction = new CustomerAndAction(4);
				Customer c = new Customer();
				c.setId(Integer.parseInt(UI.getClientID()));
				c.setFirstName(UI.getFirstName());
				c.setLastName(UI.getLastName());
				c.setAddress(UI.getAddress());
				c.setPhone(UI.getPhone());
				c.setPostCode(UI.getPostCode());
				c.setType(type);
				instruction.addCustomer(c);
				try {
					client.getSocketOut().writeObject(instruction);
				} catch (IOException e1) {
					UI.displayErrorMessage("I/O Exception");
				}
			}catch (InvalidFirstNameException ex) {
				UI.displayErrorMessage("First name must be 20 characters or less");
			} catch (InvalidLastNameException e1) {
				UI.displayErrorMessage("Last name must be 20 characters or less");
			} catch (InvalidAddressException e1) {
				UI.displayErrorMessage("Address must be 50 characters or less");
			} catch (InvalidPostCodeException e1) {
				UI.displayErrorMessage("Postal Code must follow the format: 'A1A 1A1'");
			} catch (InvalidPhoneException e1) {
				UI.displayErrorMessage("Phone number must follow the format: '111-111-1111'");
			}
		}	
	}
	
	/**Takes the client ID from the client information field in the main UI and deletes the client with matching ID in the database
	 * @author Zachary Graham
	 *
	 */
	class DeleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CustomerAndAction instruction = new CustomerAndAction(2);
				Customer customer = new Customer();
				customer.setId(Integer.parseInt(UI.getClientID()));
				instruction.addCustomer(customer);
				client.getSocketOut().writeObject(instruction);
			}catch (Exception ex) {
				UI.displayErrorMessage("Operation not Completed");
			}
		}		
	}
	
	/**Listener that deletes all of the text in the main UI client information text fields when
	 * the clear button is pressed.
	 * @author Zachary Graham
	 *
	 */
	class ClearListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			UI.clear();
		}		
	}
	
	/**Listener that creates a search query for the database to return clients that match the parameters entered
	 * in the client search field of the main UI. Modifies the search query type depending on if last name, client
	 * type, or client ID is selected
	 * @author Zachary Graham
	 *
	 */
	class SearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String query = UI.getSearchCriteria();
			String search = typeSelector();
			CustomerAndAction instruction = new CustomerAndAction(3);
			instruction.setSearchCriteria(query);
			instruction.setSearchType(search);
			try {
				client.getSocketOut().writeObject(instruction);
			} catch (IOException e1) {
				UI.displayErrorMessage("I/O Exception");
				e1.printStackTrace();
			}
		}
		
		/**Returns a string keyword to be passed to the database search function that specifies
		 * which column of the database that the search keywords should be applied to.
		 * @return string that matches column in database column
		 */
		private String typeSelector() {
			if (UI.getClientIDSearch())
				return "ID";
			if (UI.getLastNameSearch())
				return "LASTNAME";
			if (UI.getClientTypeSearch())
				return "CLIENTTYPE";
			return null;
		}		
	}
	
	/**Deletes all infromation in the client list scrollable window when the clear button is selected
	 * in the client search window
	 * @author Zachary Graham
	 *
	 */
	class ClearSearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			UI.clearClientList();
		}	
	}

	public void process(CustomerAndAction instruction) {
		if (instruction.getAction() == 5) {
			System.out.println("Unreachable Code");
		}
		
		else if (instruction.getAction() == 1) {
			UI.displaySuccessMessage("Client Successfully Added!");
		}
		
		else if (instruction.getAction() == 2) {
			UI.displaySuccessMessage("Client Successfully Deleted!");
		}
		
		else if (instruction.getAction() == 3) {
			if (instruction.getCustomerList().size() == 0)
				UI.displayErrorMessage("Your Search Returned no Results!");
			else {
				UI.clearClientList();
				UI.updateClientList(instruction.getCustomerList());
			}
		}
		
		else if (instruction.getAction() == 4) {
			UI.displaySuccessMessage("Client Successfully Updated!");
		}
		
		else if (instruction.getAction() == 6)
			System.out.println("Operation not Successful");
		
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void waitingForConnection() {
		UI.setVisible(false);
	}

	public void connected() {
		wait.setVisible(false);
		UI.setVisible(true);
	}
}
 