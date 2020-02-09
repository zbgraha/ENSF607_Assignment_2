package customerAndActions;

import java.io.Serializable;

/**A class that contains a constructor for an object that contains client information.
 * @author zacha
 *
 */
public class Customer implements Serializable{

	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String postCode;
	private String phone;
	private String type;

	
	/**Client object used by the backend to sort client data
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param postCode
	 * @param phone
	 * @param type
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
	public Customer(String firstName, String lastName, String address, String postCode, String phone, String type)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postCode = postCode;
		this.phone = phone;
		this.type = type;
	}
	
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
