

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An application manage the client database communications
 * @author Riley Berry
 * @version 1.0
 * @since 2019-11-15
 *
 */
public class ClientManagerModel {
	
	/**
	 * Connection for the  Database
	 */
	private Connection jdbc_connection;
	/**
	 * prepared statement to communicate with the database
	 */
	private PreparedStatement pStatement;
	/**
	 * Database name to connect with
	 */
	private String databaseName = "ClientDB";
	/**
	 * Name of table to use
	 */
	private String tableName = "ClientTable";
	/**
	 * Text file used to initially populate the database table
	 */
	private String  dataFile = "clients.txt";
	
	/**
	 * Database Connection Information
	 * Update to match local credentials
	 */
	//MODIFY THIS FOR INITIAL DATABASE CREATION
	private String connectionInfo = "jdbc:mysql://localhost:3306/clientdb",
			login = "root",
			password = "rootpassword";

	/**
	 * Default constructor
	 * Uses the database connection information to establish a connection to a database
	 */
	public ClientManagerModel() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			
		}
		catch(SQLException e) {e.printStackTrace();}
		catch(Exception e) {e.printStackTrace();}
		
	}
	/**
	 * Creates a new database based on the supplied information
	 */
	public void createDB() {
		try {
			String sql = "CREATE DATABASE "+ databaseName;
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.executeUpdate();
		} 
		catch( SQLException e)	{}
		catch (Exception e)  {}
	}
	/**
	 * Creates a table in the database
	 * Columns are: ID, FNAME, LNAME, ADDRESS, POSTALCODE, PHONENUM, CLIENTTYPE
	 * Primary key is ID, which auto increments with a new database addition
	 */
	public void createTable() {
		String sql = "CREATE TABLE " + tableName + "("+
					"ID INT NOT NULL AUTO_INCREMENT,"+
					"FNAME VARCHAR(20) NOT NULL," +
					"LNAME VARCHAR(20) NOT NULL," +
					"ADDRESS VARCHAR(50) NOT NULL," + 
					"POSTALCODE CHAR(7) NOT NULL," +
					"PHONENUM CHAR(12) NOT NULL," +
					"CLIENTTYPE CHAR(1) NOT NULL,"+
					"PRIMARY KEY (ID))";
//		 NOT NULL  IDENTITY PRIMARY KEY   AUTO_INCREMENT=1000,
		try {
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.executeUpdate();
		}
		catch(SQLException e) { e.printStackTrace();}	
		
		alterIDStart(1000);
	}
	
	/**
	 * Removes the table from the database
	 */
	public void removeTable() {
		String sql = "DROP TABLE "+tableName;
		try {
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.executeUpdate();
		}
		catch(SQLException e) {e.printStackTrace();}
	}
	
	/**
	 * Populates the database table from the supplied text file
	 */
	public void fillTable() {
		//Could make this accept a string to read from any file
		// For this application, I am purposefully restricting it
		try {
			Scanner sc = new Scanner(new FileReader(dataFile));
			while(sc.hasNext()) {
				String [] clientInfo = sc.nextLine().split(";");
				addClientToDB(new Client(clientInfo[0],clientInfo[1],clientInfo[2],
						clientInfo[3],clientInfo[4],clientInfo[5]));			
			}
			sc.close();
		} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
				
	}
	
	/**
	 * Adds a Client to the database
	 * @param c Adds a Client to the database, ID doesn't need to be filled
	 */
	public void addClient(Client c) {
		addClientToDB(c);
	}
	
	/**
	 * Search the database by the Client ID
	 * @param ID The ID to search the database for, doesn't need to be an exact match
	 * @return The list of clients matching the supplied ID or partial ID
	 */
	public ArrayList<Client> findClientListByID(int ID){
		String sql = "SELECT * FROM "+ tableName +" WHERE ID LIKE ?";
		ArrayList<Client> clientList = new ArrayList<Client>();
		ResultSet client;
		try {
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.setString(1,"%"+ ID + "%");
//			System.out.println(pStatement);
			client = pStatement.executeQuery();
			
			while(client.next()) {
				clientList.add(resultSetToClient(client));
			}
			
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return clientList;
	
		
//		String sql = "SELECT * FROM "+ tableName +" WHERE LNAME LIKE %?%";
	}
	
	/**
	 * Search the database by client last name
	 * @param name The client's last Name to search the database for, doesn't need to be an exact match
	 * @return the list of clients who's last name matches any of the supplied name
	 */
	public ArrayList<Client> findClientListByLName(String name){
		String sql = "SELECT * FROM "+ tableName +" WHERE LNAME LIKE ?";
		ArrayList<Client> clientList = new ArrayList<Client>();
		ResultSet client;
		try {
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.setString(1,"%"+ name + "%");
//			System.out.println(pStatement);
			client = pStatement.executeQuery();
			
			while(client.next()) {
				clientList.add(resultSetToClient(client));
			}
			
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return clientList;
	
		
//		String sql = "SELECT * FROM "+ tableName +" WHERE LNAME LIKE %?%";
	}
	
	
	/**
	 * Search the database by client type
	 * @param type The type of client
	 * @return the list of clients of the specified type
	 */
	public ArrayList<Client> findClientListByType(String type){
		String sql = "SELECT * FROM "+ tableName +" WHERE CLIENTTYPE LIKE ?";
		ArrayList<Client> clientList = new ArrayList<Client>();
		ResultSet client;
		try {
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.setString(1,"%"+ type + "%");
//			System.out.println(pStatement);
			client = pStatement.executeQuery();
			
			while(client.next()) {
				clientList.add(resultSetToClient(client));
			}
			
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return clientList;
	
		
//		String sql = "SELECT * FROM "+ tableName +" WHERE LNAME LIKE %?%";
	}
	
	/**
	 * Updates and existing client with the supplied information, ID cannot be changed
	 * @param c The Client Object containing the new information
	 */
	public void updateClient(Client c) {
//		Client originalClient = findClientByID(modifiedClient.getId());
		
		String sql = "UPDATE "+ tableName + " SET FNAME =?,LNAME =?,ADDRESS=?,POSTALCODE=?,PHONENUM=?,CLIENTTYPE=? WHERE ID = ?";
				
		
		try {
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.setString(1,c.getfName());
			pStatement.setString(2,c.getlName());
			pStatement.setString(3,c.getAddress());
			pStatement.setString(4,c.getPostalCode());
			pStatement.setString(5,c.getPhoneNum());
			pStatement.setString(6,c.getClientType());
			pStatement.setInt(7,c.getId());
		
			pStatement.executeUpdate();
						
		} catch (SQLException e) {e.printStackTrace();}
		
	}
	
	
//Following two methods were initially created, but only return a single client
	//Left in the code for personal reflection, not required to run the program
	
//	public Client findClientByName(String name) {
//		String sql = "SELECT * FROM "+ tableName +" WHERE FNAME = ?;";
//		ResultSet client;
//		try {
//			pStatement = jdbc_connection.prepareStatement(sql);
//			pStatement.setString(1,name);
//			client = pStatement.executeQuery();
//			
//			if (client.next()) {
//				return resultSetToClient(client);
//			}
//		} catch (SQLException e) {e.printStackTrace();}
//		return null;
//	}
	
//	public Client findClientByID(int ID) {
//	String sql = "SELECT * FROM "+ tableName +" WHERE ID = ?;";
//	ResultSet client;
//	try {
//		pStatement = jdbc_connection.prepareStatement(sql);
//		pStatement.setInt(1,ID);
//		client = pStatement.executeQuery();
//		
//		if (client.next()) {
//			return resultSetToClient(client);
//		}
//	} catch (SQLException e) {e.printStackTrace();}
//	return null;
//}
	/**
	 * Removes a client from the database using the client ID
	 * @param ID The ID of the client to remove from the database
	 */
	public void deleteClient(int ID) {
		String sql = "DELETE FROM "+ tableName +" WHERE ID =?;";
		try {
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.setInt(1, ID);
			pStatement.execute();
			
		} catch (SQLException e) {e.printStackTrace();}
		
	}
	
	//////////////////////////
	/////// Helper Methods
	

	
	private Client resultSetToClient(ResultSet client) throws SQLException {
		return  new Client(client.getInt("ID"),client.getString("FNAME"), client.getString("LNAME"),
				client.getString("ADDRESS"), client.getString("POSTALCODE"), client.getString("PHONENUM"), 
				client.getString("ClientType"));
	}
	
	
	private void alterIDStart(int startingID) {
		String sql = "ALTER TABLE "+ tableName +" AUTO_INCREMENT=?";
		
		try {
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.setInt(1,startingID);
			pStatement.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		
		
	}
	
	private void addClientToDB(Client c) {
		String sql = "INSERT INTO " +tableName + "(FNAME,LNAME,ADDRESS,POSTALCODE,PHONENUM,CLIENTTYPE) VALUES(?,?,?,?,?,?);";
		
		try {
			pStatement = jdbc_connection.prepareStatement(sql);
			pStatement.setString(1,c.getfName());
			pStatement.setString(2,c.getlName());
			pStatement.setString(3,c.getAddress());
			pStatement.setString(4,c.getPostalCode());
			pStatement.setString(5,c.getPhoneNum());
			pStatement.setString(6,c.getClientType());
			
			pStatement.executeUpdate();
			
//			System.out.println(pStatement);
//			+ databaseName+"."
			
//			System.out.println(c.getfName());
//			System.out.println(c.getlName());
//			System.out.println(c.getAddress());
//			System.out.println(c.getPostalCode());
//			System.out.println(c.getPhoneNum());
//			System.out.println(c.getClientType());
			
			
		} catch (SQLException e) {e.printStackTrace();}
		
		
	}
	
//	public static void main(String[] args) {
//		ClientManagerModel CMM = new ClientManagerModel();
//		
////		CMM.createDB();
//		CMM.removeTable();
////		
//		CMM.createTable();
//		CMM.fillTable();
//		
////		System.out.println(CMM.findClientByID(1000));
////		System.out.println(CMM.findClientByName("Bugs"));
//		
////		for (Client c:CMM.findClientListByID(100)) {
////			System.out.println(c);
////		}
//		for (Client c:CMM.findClientListByType("C")) {
//			System.out.println(c);
//		}
//		
//		System.out.println();
//		
////		for (Client c:CMM.findClientListByLName("Bu")) {
////			System.out.println(c);
////		}
//		
//
//		
//		Client testC = CMM.findClientByID(1002);
//		testC.setfName("Bob");
////		testC.setlName("Ross");
//		testC.setId(1000);
//		System.out.println(testC);
//		
//		CMM.addClient(testC);
//		
//		CMM.updateClient(testC);
//		System.out.println();
//		
//		for (Client c:CMM.findClientListByType("C")) {
//			System.out.println(c);
//		}
//		
//		
////		System.out.println(CMM.findClientListByID(100));
//		
//		
//		
//	}
	
	
}
