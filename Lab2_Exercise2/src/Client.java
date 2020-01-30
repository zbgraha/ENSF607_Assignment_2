import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private BufferedReader stdIn;
	
	public Client (String serverName, int portNumber) {
		
		try {
			aSocket = new Socket (serverName, portNumber);
			//keyboard input stream
			stdIn = new BufferedReader (new InputStreamReader (System.in));
			//socket input stream
			socketIn = new BufferedReader (new InputStreamReader (aSocket.getInputStream()));
			socketOut = new PrintWriter((aSocket.getOutputStream()), true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void communicate () {
		String line = "abc";
		String response = "";
		
		while (!line.equals("QUIT")) {
			
			try {
				System.out.println("Please select an option (DATE/TIME)");
				line = stdIn.readLine();//read line from the user (i.e. keyboard)
				line = line.toUpperCase();
				socketOut.println(line);
				response = socketIn.readLine(); //read response from the socket
				if (response != null) {
					System.out.println(response);
				}
				
			}catch (IOException e) {
				e.getStackTrace();
			}
			
		}
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		}catch (IOException e) {
			e.getStackTrace();
		}
		
	}
	public static void main (String [] args) throws IOException{
		Client aClient = new Client ("localhost", 9090);
		aClient.communicate();
	}

}