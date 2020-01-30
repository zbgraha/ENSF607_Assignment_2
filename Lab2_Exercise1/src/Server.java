
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {
	
	private Socket aSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	
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
	
	private String boolToString(boolean bool) {
		if (bool == true) {
			return "is a Palindrome.";
		}
		return "is not a Palindrome.";
	}
	

	
	public void run() {
		String line = null;
		boolean running = true;
		while(running) {
			try {
				line = socketIn.readLine();
				if(line.equals("QUIT")) {
					line = "Good Bye";
//					socketOut.println(line);
					running = false;
					
				}
				else {
					line = line.toLowerCase() + " " + boolToString(isPalindrome(line));
				}
				
				
				socketOut.println(line);
				
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
