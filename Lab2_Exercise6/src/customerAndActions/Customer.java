package customerAndActions;

import java.io.Serializable;

/**A class that contains a constructor for an object that contains client information.
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
 *
 */
public class Customer implements Serializable{


	
	/**
	 * ID used as the primary key for the customer
	 */
	private int id;
	/**
	 * Customer's first name
	 */
	private String firstName;
	/**
	 * Customer's last name
	 */
	private String lastName;
	/**
	 * Address of customer
	 */
	private String address;
	/**
	 * Postal code of customer
	 */
	private String postCode;
	/**
	 * Customer primary phone number
	 */
	private String phone;
	/**
	 * Type of customer 
	 * C for Commercial, R for Residential
	 */
	private String type;
	/**
	 * serial version ID for the customer object class
	 */
	private static final long serialVersionUID = -8074658753714705509L;

	
/**
 * Default constructor for client, sets all values to null
 */
	public Customer() {
		id = 0;
		firstName = null;
		lastName = null;
		address = null;
		postCode = null;
		phone = null;
		type = null;
	}
	/**Client object used by the back end to sort client data
	 * @param firstName The first name of the client
	 * @param lastName The last name of the client
	 * @param address The address of the client
	 * @param postCode The postal code of the client
	 * @param phone The phone number of the client
	 * @param type The type of client
	 */
	public Customer(String firstName, String lastName, String address, String postCode, String phone, String type)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postCode = postCode;
		this.phone = phone;
		this.type = type;
	}
	/**
	 * Client object used by the back end to sort client data
	 * @param id customer identification number
	 * @param firstName The first name of the client
	 * @param lastName The last name of the client
	 * @param address The address of the client
	 * @param postCode The postal code of the client
	 * @param phone The phone number of the client
	 * @param type The type of client
	 */
	public Customer(int id, String firstName, String lastName, String address, String postCode, String phone, String type)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postCode = postCode;
		this.phone = phone;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString()
	{
		String client = this.id + " " + this.firstName + " " + this.lastName + " " + this.type;
		return client;
	}
}
