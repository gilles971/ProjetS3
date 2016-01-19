package jeuSocket;

/*
 * cette classe sert a communiquer avec le WumpusServer
 * elle permet a une IA de réagir aux situations communiquée par le serveur
 * 
 */

import java.util.ArrayList;

import IA.IA3;




import java.io.*;
import java.net.*;

public class WumpusClientAI3 {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java KnockNnockClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        IA3 artificialIntelligence = new IA3(5, 5, 1);

        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromAI;

            while ((fromServer = in.readLine()) != null) {
            	fromAI = "\t";
                System.out.println("Server: " + fromServer);
                ArrayList<String> info = new ArrayList<String>();
                
            	try {
            		Thread.currentThread().sleep(2000);
            	} catch (InterruptedException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
                String[] temp = fromServer.split("\\s+");
                for (int i = 0; i<temp.length; i++) {
                    info.add(temp[i]);
                }
                if (fromServer.equals("Vous avez perdu"))
                    break;
                
                if((info.get(0).equals("0") || info.get(0).equals("1") || info.get(0).equals("2") || info.get(0).equals("3") || info.get(0).equals("4")) &&
                   (info.get(1).equals("0") || info.get(1).equals("1") || info.get(1).equals("2") || info.get(1).equals("3") || info.get(1).equals("4")))
                {
                	System.out.println("ok");
                    fromAI = artificialIntelligence.jouer(Integer.parseInt(info.get(0)), 
                                                          Integer.parseInt(info.get(1)), 
                                                          info);//stdIn.readLine();
                }                                     
                /*else 
                {
                    fromAI = " ";
                }*/
                    
                if (fromAI != null) {
                    System.out.println("Client: " + fromAI);
                    out.println(fromAI);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}

