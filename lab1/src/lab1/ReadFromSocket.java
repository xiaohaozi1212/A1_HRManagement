package lab1;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This example demonstrates a simple Java socket client that receive users' input and send to the (echo) 
 * socket server. Then read server's feedback and print it out. 
 * This code also shows the steps to build client socket.
 */
public class ReadFromSocket {

	public static void main(String[] args) {
		try {

			// step 1: Open a socket to connect to the server.
			// server IP address/name: "localhost"
			// port number 7777
			Socket clientSocket = new Socket("localhost", 3186);
			
			//System.out.println( "Connected to " +   clientSocket.getInetAddress().getHostName());

			// step 2: create output stream and input stream to the socket.
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
					true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			//System.out.println( "I/O streams connected to the socket" );

			// step 3: communicate with the server (write to and read from the streams) 
			// according to the server's protocol.
			String userInput;
			/* BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));*/
	        Scanner keyboard = new Scanner( System.in );
	        
			System.out.println("echo from the server: " + in.readLine());
			//System.out.print("Command >>");
			
			System.out.print("Command >>");
			for(;;){
				userInput = keyboard.nextLine();
				
				if (userInput != null) {
					// send data to the server
					out.println(userInput);  
					out.flush(); // data sent immediately!
					
					if ("No".equals(userInput)) {
						out.println("No"); // to stop server/thread
						break; // stop client
						}
						else if ("STOP".equals(userInput)) {
							 // Whenever the client sends "STOP" as a request,stop server/thread
							break; // stop client
					}else if("Yes".equals(userInput)) {
						System.out.print("Command >>");
						 // continue server/thread
					}else {
						// receive data from the server
						String instr = in.readLine();
						//if receive data has letters,shows echo rather than calculation
						Matcher m=Pattern.compile(".*[a-zA-Z].*").matcher(instr);
						if(m.matches()) {
							System.out.println("echo from the server:" + instr);
						}else {
							System.out.println("Calculation result: " + instr);
						}						
						System.out.println("Continue (Yes/No)?");
						
					}
				}
			}

			// step 4: Close the streams.
			keyboard.close();
			out.close();
			in.close();

			// step 5: Close the socket.
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Exception : " + e);
		}
	}
}
