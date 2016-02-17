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

import java.io.*;
import java.net.*;

public class WumpusClient {
    public static void main(String[] args) throws IOException {
        
    	//on vérifie que la classe est utilisée correctement
        if (args.length != 2) {
            System.err.println(
                "Usage: java WumpusClient <host name> <port number>");
            System.exit(1);
        }

        // on mémorise le numéro de port
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
        		// on commence par créer nos socket avec 
        		//leur descripteur de fichier et leur buffer en écriture
            Socket wSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(wSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(wSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            //on lance notre boucle:
            //tant que l'on reçoit des données du serveur
            //on les affiche et on lit l'entrée standard
            //qu'on envoie ensuite au serveur
            //on sort si on envoie "Bye."
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                
                fromUser = stdIn.readLine();
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

