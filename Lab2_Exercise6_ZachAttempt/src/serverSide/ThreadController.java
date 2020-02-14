package serverSide;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import customerAndActions.CustomerAndAction;
/**
 * An object to manage each connection from a client with a new thread
 * @author Riley Berry and Zachary Graham
 * @version 1.0
 * @since 2020-02-13
 *
 */

public class ThreadController implements Runnable{
	
	/**
	 * Socket output stream to send Objects to the client instance
	 */
	private ObjectOutputStream socketOut;
	/**
	 * Socket intput stream to receive objects to the client
	 */
	private ObjectInputStream socketIn;
	/**
	 * The database manager used to interact with the database
	 */
	private ClientDataBaseManager database;;
	
	/**
	 * Constuctor for the thread controller
	 * @param socketIn Object input stream from the client socket connection
	 * @param socketOut Object output stream from the client socket connection
	 * @param database The database manager used to interact with the customer database
	 */
	public ThreadController (ObjectInputStream socketIn, ObjectOutputStream socketOut, ClientDataBaseManager database) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		this.database = database;
	}

	/**
	 * Method implemented by the runnable interface to send and receive commands from the client
	 */
	@Override
	public void run() {
		while (true) {
			try {
				socketOut.writeObject(new CustomerAndAction(99));
				break;
			} catch (Exception e) {
				
			}
		}
		System.out.println("Accepting User Input!\n");
		while (true) {
			try {
				CustomerAndAction instruction  = (CustomerAndAction) socketIn.readObject();
				
				if (instruction.getAction() == 5) {
					System.out.println("Thread Destroyed");
					break;
				}
				
				else if (instruction.getAction() == 1) {
					if (database.addClient(instruction.getCustomerList().get(0)))
						socketOut.writeObject(instruction);
					else
						socketOut.writeObject(new CustomerAndAction(6));
				}
				
				else if (instruction.getAction() == 2) {
					if (database.deleteClient(instruction.getCustomerList().get(0)))
						socketOut.writeObject(instruction);
					else
						socketOut.writeObject(new CustomerAndAction(6));
				}
				
				else if (instruction.getAction() == 3) {
					instruction.setCustomerList(database.searchClient(instruction));
					if (instruction.getCustomerList() != null)
						socketOut.writeObject(instruction);
					else
						socketOut.writeObject(new CustomerAndAction(6));
				}
				
				else if (instruction.getAction() == 4) {
					if (database.updateClient(instruction.getCustomerList().get(0)))
						socketOut.writeObject(instruction);
					else
						socketOut.writeObject(new CustomerAndAction(6));
				}
				
				else if (instruction.getAction() == 6) {
					System.out.println("Error");
					socketOut.writeObject(instruction);
				}
					
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}

}
