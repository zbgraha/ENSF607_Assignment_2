package serverSide;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import customerAndActions.Customer;
import customerAndActions.CustomerAndAction;

/**Class with an object with functions to manage the SQL database of clients
 * @author Zachary Graham
 */
public class ClientDataBaseManager {

	public Connection jdbc_connection;
	public PreparedStatement statement;
	public String databaseName = "ClientDB", tableName = "ClientTable", dataFile = "clients.txt";
	
	// Students should configure these variables for their own MySQL environment
	// If you have not created your first database in mySQL yet, you can leave the 
	// "[DATABASE NAME]" blank to get a connection and create one with the createDB() method.
	public String connectionInfo = "jdbc:mysql://localhost:3306",  
				  login          = "root",
				  password       = "e7TOF#09swulEs";

	/**Contructor for the database manager. Establishes a connection to the database that will be used
	 * for client management.
	 * 
	 */
	public ClientDataBaseManager()
	{
		try{
			// If this throws an error, make sure you have added the mySQL connector JAR to the project
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// If this fails make sure your connectionInfo and login/password are correct
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		}
		catch(SQLException e) { e.printStackTrace(); }
		catch(Exception e) { e.printStackTrace(); }
	}
	

	/**Use the jdbc connection to create a new database in MySQL. 
	 * (e.g. if you are connected to "jdbc:mysql://localhost:3306", the database will be created at "jdbc:mysql://localhost:3306/ClientDB")
	 * @return true if a database is created
	 */
	public boolean createDB()
	{
		try {
			statement = jdbc_connection.prepareStatement("CREATE DATABASE " + databaseName);
			statement.executeUpdate();
			System.out.println("Created Database " + databaseName);
			return true;
		} 
		catch( SQLException e)
		{
			return false;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**Connects to the database that contains client information.
	 * 
	 */
	public void connectToDB() {
		try {
			jdbc_connection = DriverManager.getConnection(connectionInfo+"/"+databaseName, login, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**Create a data table inside of the database to hold tools
	 * @return true if a table is created
	 */
	public boolean createTable()
	{
		String sql = "CREATE TABLE " + tableName + "(" +
				     "ID INT(4) NOT NULL AUTO_INCREMENT, " +
				     "FIRSTNAME VARCHAR(20) NOT NULL, " + 
				     "LASTNAME VARCHAR(20) NOT NULL, " + 
				     "ADDRESS VARCHAR(50) NOT NULL, " + 
				     "POSTCODE CHAR(7) NOT NULL, " + 
				     "PHONENUMBER CHAR(12) NOT NULL, " + 
				     "CLIENTTYPE CHAR(1) NOT NULL, " +
					 "PRIMARY KEY ( id ))";
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		}
		catch(SQLException e)
		{
			return false;
		}
	}


	/**Fills the data table with all the clients from the text file 'clients.txt' if found
	 * 
	 */
	public void fillTable()
	{
		try{
			Scanner sc = new Scanner(new FileReader(dataFile));
			while(sc.hasNext())
			{
				String clientInfo[] = sc.nextLine().split(";");
				addClient(new Customer(clientInfo[0], clientInfo[1], clientInfo[2], clientInfo[3], clientInfo[4], clientInfo[5]));
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File " + dataFile + " Not Found!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	/**Add a client to the database table
	 * @param customer
	 */
	public boolean addClient(Customer customer)
	{
		String sql = "INSERT INTO " + tableName +
				" VALUES ( NULL, ?, ?, ?, ?, ?, ? );";
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getAddress());
			statement.setString(4, customer.getPostCode());
			statement.setString(5, customer.getPhone());
			statement.setString(6, customer.getType());
			statement.executeUpdate();
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateClient(Customer customer) {
		String sql = "UPDATE " + tableName +
				" SET FIRSTNAME=?, LASTNAME=?, ADDRESS=?, "
				+ "POSTCODE=?, PHONENUMBER=?, CLIENTTYPE=? "
				+ "WHERE ID = ?;";
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getAddress());
			statement.setString(4, customer.getPostCode());
			statement.setString(5, customer.getPhone());
			statement.setString(6, customer.getType());
			statement.setInt(7, customer.getId());
			statement.executeUpdate();
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}


	public boolean deleteClient(Customer customer) {
		String sql = "DELETE FROM " + tableName +
				" WHERE ID=?;";
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, customer.getId());
			statement.executeUpdate();
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
	}


	public ArrayList<Customer> searchClient(CustomerAndAction instruction) {
		String sql = "SELECT * FROM " + tableName + " WHERE " + instruction.getSearchType() + "=?";
		ArrayList<Customer> results = new ArrayList<Customer>();
		ResultSet client;
		try {
			statement = jdbc_connection.prepareStatement(sql);
			if (instruction.getSearchType().equals("ID"))
				statement.setInt(1, Integer.parseInt(instruction.getSearchCriteria()));
			else
				statement.setString(1, instruction.getSearchCriteria());
			client = statement.executeQuery();
			while(client.next())
			{
				Customer customer = new Customer();
				customer.setId(client.getInt(1));
				customer.setFirstName(client.getString(2));
				customer.setLastName(client.getString(3));
				customer.setAddress(client.getString(4));
				customer.setPostCode(client.getString(5));
				customer.setPhone(client.getString(6));
				customer.setType(client.getString(7));
				results.add(customer);
			}
			return results;
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
		
	}
	
	/**Add a client to the database table
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param postCode
	 * @param phone
	 * @param type
	 */
//	public void addClient(String firstName, String lastName, String address, String postCode, String phone, String type)
//	{
//		String sql = "INSERT INTO " + tableName +
//				" VALUES ( NULL, ?, ?, ?, ?, ?, ? );";
//		try{
//			statement = jdbc_connection.prepareStatement(sql);
//			statement.setString(1, firstName);
//			statement.setString(2, lastName);
//			statement.setString(3, address);
//			statement.setString(4, postCode);
//			statement.setString(5, phone);
//			statement.setString(6, type);
//			statement.executeUpdate();
//		}
//		catch(SQLException e)
//		{
//			e.printStackTrace();
//		}
//	}
		
	/**Delete a client in the database that matches the id argument
	 * @param id
	 */
//	public void deleteClient(int id)
//	{
//		String sql = "DELETE FROM " + tableName +
//				" WHERE ID=?;";
//		try{
//			statement = jdbc_connection.prepareStatement(sql);
//			statement.setInt(1, id);
//			statement.executeUpdate();
//		}
//		catch(SQLException e)
//		{
//			e.printStackTrace();
//		}
//	}


	/**This method should search the database table for a client matching the search parameter and return that client information.
	 * It should return null if no clients matching search parameters are found.
	 * @param searchCriteria
	 * @param searchType
	 * @return string array of client information from the database
	 */
//	public ArrayList<String> searchClient(String searchCriteria, String searchType)
//	{
//		String sql = "SELECT * FROM " + tableName + " WHERE " + searchType + "=?";
//		ArrayList<String> results = new ArrayList<String>();
//		ResultSet client;
//		try {
//			statement = jdbc_connection.prepareStatement(sql);
//			if (searchCriteria.equals("ID"))
//				statement.setInt(1, Integer.parseInt(searchCriteria));
//			else
//				statement.setString(1, searchCriteria);
//			client = statement.executeQuery();
//			while(client.next())
//			{
//				String find = client.getString(1) + ";" + client.getString(2) + ";"
//						+ client.getString(3) + ";" + client.getString(4) + ";"
//						+ client.getString(5) + ";" + client.getString(6) + ";"
//						+ client.getString(7);
//				results.add(find);
//			}
//			return results;
//		} catch (SQLException e) { e.printStackTrace(); }
//		
//		return null;
//	}

//	/**Update client information for the client that matches the ID argument given
//	 * @param id
//	 * @param firstName
//	 * @param lastName
//	 * @param address
//	 * @param postCode
//	 * @param phone
//	 * @param type
//	 */
//	public void updateClient(String id, String firstName, String lastName, String address, String postCode, String phone,
//			String type) {
//		String sql = "UPDATE " + tableName +
//				" SET FIRSTNAME=?, LASTNAME=?, ADDRESS=?, "
//				+ "POSTCODE=?, PHONENUMBER=?, CLIENTTYPE=? "
//				+ "WHERE ID = ?;";
//		try{
//			statement = jdbc_connection.prepareStatement(sql);
//			statement.setString(1, firstName);
//			statement.setString(2, lastName);
//			statement.setString(3, address);
//			statement.setString(4, postCode);
//			statement.setString(5, phone);
//			statement.setString(6, type);
//			statement.setString(7, id);
//			statement.executeUpdate();
//		}
//		catch(SQLException e)
//		{
//			e.printStackTrace();
//		}
//	}
	


}