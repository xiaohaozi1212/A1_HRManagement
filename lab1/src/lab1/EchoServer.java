package lab1;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class EchoServer {
	// step 1: Open a socket to accept the connection.
	// step 2: create output stream and input stream to the socket.
	// step 3: read from the streams and return result or echo the error message
	// step 4: Close the socket and waiting for another connection.
	
	public static void main(String[] args) {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		String dt = "Simple Calculator(" + ft.format(dNow) + ")";
		System.out.println(dt);

		// System.out.println("EchoServer started.");

		try {
			// step 1: Open a socket to accept the connection.
			ServerSocket s = new ServerSocket(3186);//need s.close();
			Socket clientSocket = s.accept();//need clientSocket.close();


			 System.out.println("Connected to: " + clientSocket.getInetAddress()
			 + " at port: " + clientSocket.getLocalPort());

			// step 2: create output stream and input stream to the socket.
			
			// InputStream - InputStreamReader - BufferedReader
			InputStream is = clientSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			//need in.readLine()
			//BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			//OutputStream - OutputStr eamWriter - PrintWriter
			OutputStream ops = clientSocket.getOutputStream();
			OutputStreamWriter opsw = new OutputStreamWriter(ops);
			PrintWriter out = new PrintWriter(opsw);
			//need out.println(userInput);  
			//need out.flush();
			//need out.close();
			
			
			//or you can use :
			//PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			//PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			// step 3: read from the streams and return result or echo the error message
			// print + flush
			out.println(dt);//here the data out put to the client
			out.flush();

			//for (;;) {
			while(true) {
			String str = in.readLine();

				if (str == null) {
					break;
				} else {
					// write (send) back to the client wthe result or error message
					// adding the Echo string in front of it
					// out.println("Echo: " + str);
					// out.flush();
					if (str.trim().equals("No")) {
						System.out.println("STOP command received");
						break;
					} else if (str.trim().equals("STOP")) {
						System.out.println("STOP command received");
						break;
					} else if (str.trim().equals("Yes")) {

					} else {
						System.out.println("client requested calculation:" + str);

						// split string
						String[] tokens = str.split(" ");
						String d1 = tokens[0];
						String d2 = tokens[1];
						String d3 = tokens[2];
						String valueRespond = null;
						double value1 = Double.valueOf(d1.toString());
						double value2 = Double.valueOf(d3.toString());
						double value3;
						int value4;

						// if multiply
						if (d2.equals("*")) {
							value3 = value1 * value2;
							// valueRespond = value1 * value2 + "";
							if (((int) value3 * 10) == (int) (value3 * 10)) {
								value4 = (int) value3;
								valueRespond = value4 + "";
							} else {
								valueRespond = value3 + "";
							}

						}
						// if add
						else if (d2.equals("+")) {
							value3 = value1 + value2;
							// valueRespond = value1 + value2 + "";
							if (((int) value3 * 10) == (int) (value3 * 10)) {
								value4 = (int) value3;
								valueRespond = value4 + "";
							} else {
								valueRespond = value3 + "";
							}
						}
						// if minus
						else if (d2.equals("-")) {
							value3 = value1 - value2;
							// valueRespond = value1 + value2 + "";
							if (((int) value3 * 10) == (int) (value3 * 10)) {
								value4 = (int) value3;
								valueRespond = value4 + "";
							} else {
								valueRespond = value3 + "";
							}
						} // if divide
						else if (d2.equals("/")) {
							if (d3.equals("0")) {
								valueRespond = "a number cannot be divided by 0";
							} else {
								value3 = value1 / value2;
								// valueRespond = value1 + value2 + "";
								if (((int) value3 * 10) == (int) (value3 * 10)) {
									value4 = (int) value3;
									valueRespond = value4 + "";
								} else {
									valueRespond = value3 + "";
								}
							}
						} else {
							valueRespond = "Calculator should only be +,-,*,/";
						}

						// write (send) back to the client
						out.println(valueRespond);
						out.flush();

					}

				}
			}

			// step 4: Close the socket and waiting for another connection.
			clientSocket.close();
			in.close();
			out.close();
			s.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

	}
}
