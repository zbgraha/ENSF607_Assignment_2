package customerAndActions;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * An object to manage each connection from a client with a new thread
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
 *
 */
public class CustomerAndAction implements Serializable{

	private ArrayList<Customer> customerList;
	private int action;
	private String searchCriteria;
	private String searchType;
	//ACTIONS:
	//	0 - Connecting
	//	1 - addClient
	//	2 - deleteClient
	//	3 - searchClient
	//	4 - updateClient
	//	5 - terminateThread
	
	public CustomerAndAction() {
		customerList = new ArrayList<Customer>();
		action = 0;
	}
	
	public CustomerAndAction(int action) {
		customerList = new ArrayList<Customer>();
		this.action = action;
	}
	
	public CustomerAndAction(ArrayList<Customer> customerList, int action) {
		this.customerList = customerList;
		this.action = action;
	}
	
	public CustomerAndAction(Customer customer, int action) {
		customerList = new ArrayList<Customer>();
		customerList.add(customer);
		this.action = action;
	}
	
	public int getAction() {
		return action;
	}
	
	public void setAction(int action) {
		this.action = action;
	}
	
	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}
	
	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public void addCustomer(Customer customer) {
		if (customerList == null)
			customerList = new ArrayList<Customer>();
		customerList.add(customer);
	}
}
