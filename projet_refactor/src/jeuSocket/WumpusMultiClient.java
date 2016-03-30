package jeuSocket;

/*
 * cette classe sert a se connecter au serveur de jeu wumpus
 *
 * il s'agit d'une invite de commande qui envoie l'entrée terminal au serveur
 * elle sert a jouer normalement
 *
 * cette classe est en partiet inspirée des classes fournies par oracle pour la mise
 * en place d'un système client serveur
 *
 */

import IA.Ia;
import java.io.*;
import java.net.*;
import java.lang.reflect.InvocationTargetException;

public class WumpusMultiClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                "Usage: java WumpusClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
            Socket wSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(wSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(wSocket.getInputStream()));
        
            String fromServer;
            String fromUser;
            ClientProtocol cp = new ClientProtocol();
            cp.setup();

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);

                fromUser = cp.processOrPlay(fromServer);
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
                if (fromUser.equals("Bye."))
                    break;
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
