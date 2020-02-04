

/**
 * An application to run the Client manager application
 * @author Riley Berry
 * @version 1.0
 * @since 2019-11-15
 *
 */
public class ClientManagerApp {
	
	public static void main (String[] args) {
		
		ClientManagerController CMC = new ClientManagerController (new ClientManagerModel(), new ClientManagerGUI());
		
//		Uncomment the lines below as appropriate to create the database and the table
		
		// To create database, uncomment the below line, and modify credentials 
		// and path in ClientManagerModel Object
//		CMC.getModel().createDB();
		
		// To delete a modified table
//		CMC.getModel().removeTable(); 
		
		// To make a new table
//		CMC.getModel().createTable();
//		CMC.getModel().fillTable();
	}

}
