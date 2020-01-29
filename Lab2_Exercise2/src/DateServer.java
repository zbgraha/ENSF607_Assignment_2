package inlab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateServer {
	private BufferedReader socketInput;
	private PrintWriter socketOutput;
	private ServerSocket serverSocket;
	private Socket aSocket;

	/**
	 * Construct a Server with Port 9090
	 */
	public DateServer() {
		try {
			serverSocket = new ServerSocket(9090);
			System.out.println("Date Server is now running.");
			aSocket = serverSocket.accept();
			socketInput = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOutput = new PrintWriter(aSocket.getOutputStream(), true);
		} catch (IOException e) {
		}
	}

	/**
	 * Get input from Client.
	 * 
	 * @throws IOException
	 */
	public void getUserInput() throws IOException {
		StringBuffer line = null;
		while (true) {
			line = new StringBuffer(socketInput.readLine());
			if (line != null) {
				if (line.toString().equals("QUIT")) {
					break;
				}
				if (line.toString().equals("DATE")) {
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
					socketOutput.println( sdf.format(cal.getTime()));
				} else if (line.toString().equals("TIME")) {
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					socketOutput.println( sdf.format(cal.getTime()));
				}else {
					socketOutput.println("Wrong input, please try again");
				}
			}
		}
		socketInput.close();
		socketOutput.close();
		serverSocket.close();
	}

	/**
	 * Run the Server.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		DateServer ds = new DateServer();
		ds.getUserInput();
	}
}