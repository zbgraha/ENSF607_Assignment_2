
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

/**The server side of an application that determines if a word is a palindrome.
 * @author Riley Berry and Zachary Graham
 *
 */
public class Server {
	
	private Socket aSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	/**Constructor for the server
	 * @param port
	 */
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	/**Creates the sockets for the client side to use
	 * @throws IOException
	 */
	public void initializeSocket() throws IOException{
		aSocket = this.serverSocket.accept();
		
		socketIn = new BufferedReader(new InputStreamReader (aSocket.getInputStream()));
		socketOut = new PrintWriter(aSocket.getOutputStream(), true);
		System.out.println("Server connected");
	}
	
	public void closeSockets() throws IOException{
		socketIn.close();
		socketOut.close();
		
	}
	
	/**Accepts a word as an argument and determines whether the word is a palindrome
	 * @param word to be reviewed
	 * @return true is the word is a palindrome
	 */
	private boolean isPalindrome(String word) {
		
		for (int i=0, j = word.length()-1; i<=j; i++, j--) {
			// won't loop for the middle index of an uneven length word
			// this is fine because both indices would be pointing to the same 
			//letter, which will always be the same
			if(word.charAt(i) != word.charAt(j)) {
				return false;
			}
		}
		return true;
	}
	
	/**Accepts a boolean value and returns a string indicating the value of the boolean
	 * @param bool - boolean value
	 * @return "is a palindrome" if the parameter is true; "is not a palindrome" if the parameter is false
	 */
	private String boolToString(boolean bool) {
		if (bool == true) {
			return "is a Palindrome.";
		}
		return "is not a Palindrome.";
	}

	/**Listens to the socketIn variable for user input from the client side.
	 * If the user inputs a null value into the socket, the server shuts down
	 * Otherwise, the server sends the string in the socket to private methods to determine
	 * if it is a palindrome and sends the appropriate message back to the client.
	 * 
	 */
	public void run() {
		String line = null;
		boolean running = true;
		
		while(running) {
			try {
				line = socketIn.readLine();
				if(line == null) {
//					line = "Good Bye";
//					socketOut.println(line);
					running = false;	
				}
				else {
					line = line.toLowerCase() + " " + boolToString(isPalindrome(line));
					socketOut.println(line);
				}
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args)throws IOException{
		
		Server myServer = new Server(8099);
		System.out.println("Server is now running");
		
		try {
			myServer.initializeSocket();
			
			myServer.run();
			myServer.closeSockets();
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Server has been terminated");
		
		
		
		
	}
}
