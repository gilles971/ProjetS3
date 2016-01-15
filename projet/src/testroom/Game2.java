package testroom;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Game2 {
	public static void main(String[] args) {
		String hostName = args[0];
		int portNumberIn = 7;//Integer.parseInt(args[1]);
		int portNumbeOut = 8;

		try {
		    Socket echoSocket = new Socket(hostName, portNumber);
		    PrintWriter out =
		        new PrintWriter(echoSocket.getOutputStream(), true);
		    BufferedReader in =
		        new BufferedReader(
		            new InputStreamReader(echoSocket.getInputStream()));
		    BufferedReader stdIn =
		        new BufferedReader(
		            new InputStreamReader(System.in));
		} catch (Exception e) { e.printStackTrace(); }
	}
}
