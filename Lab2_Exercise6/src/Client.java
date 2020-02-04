
/**
 * An object used to store information about a client
 * @author Riley Berry
 * @version 1.0
 * @since 2019-11-15
 *
 */
public class Client {
	/**
	 * The unique identification number of the client
	 */
	private int id;
	/**
	 * The first name of the client
	 */
	private String fName;
	/**
	 * The last name of the client
	 */
	private String lName;
	/**
	 * The address of the client
	 */
	private String address;
	/**
	 * The postal code of the client
	 * Must be in the format A1A 1A1 where A is any letter, 1 is any number
	 */
	private String postalCode;
	/**
	 * The phone number of the client
	 * Must be in the format ###-###-#### Where # is any number
	 */
	private String phoneNum;
	/**
	 * The type of client 
	 * C for Commercial, R for Residential
	 */
	private String clientType;
	
	/**
	 * The constructor for a client
	 * All member variables must be filled except for the ID
	 * @param fName The first name of the client
	 * @param lName The last name of the client
	 * @param address The address of the client
	 * @param postalCode The postal code of the client
	 * @param phoneNum The phone number of the client
	 * @param clientType The type of client
	 */
	public Client(String fName, String lName, String address, String postalCode, String phoneNum, String clientType) {

		this.fName = fName;
		this.lName = lName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNum = phoneNum;
		this.clientType = clientType ;
	}
	/**
	 * Constructor for a client where the ID is known
	 * 
	 * @param id unique identifier for the client
	 * @param fName The first name of the client
	 * @param lName The last name of the client
	 * @param address The address of the client
	 * @param postalCode The postal code of the client
	 * @param phoneNum The phone number of the client
	 * @param clientType The type of client
	 */
	public Client(int id,String fName, String lName, String address, String postalCode, String phoneNum, String clientType) {
		this(fName, lName, address,postalCode,phoneNum, clientType);
		this.id = id;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	
	
	public String toString() {
		String st = id+ ": "+fName +" " + lName + " " + clientType;
		return st;
		
	}
	

}
