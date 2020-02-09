package serverSide;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import customerAndActions.CustomerAndAction;

public class ThreadController implements Runnable{
	
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private ClientDataBaseManager database;;
	
	public ThreadController (ObjectInputStream socketIn, ObjectOutputStream socketOut, ClientDataBaseManager database) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		this.database = database;
	}

	@Override
	public void run() {
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
