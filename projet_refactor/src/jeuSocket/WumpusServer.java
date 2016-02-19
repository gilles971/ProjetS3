package jeuSocket;

/*
 * Cette classe met en place un serveur de jeu Wumpus
 * afin qu'un client de jeu normal ou spécial pour les IA s'y connecte
 *
 * cette classe est en partiet inspirée des classes fournies par oracle pour la mise
 * en place d'un système client serveur
 */

import java.net.*;
import java.io.*;

public class WumpusServer {
    public static void main(String[] args) throws IOException {

    	//il s'agit d'une simple vérification de l'utilisation de la classe
        if (args.length != 1) {
            System.err.println("Usage: java WumpusServer <port number>");
            System.exit(1);
        }

        // on récupère le numéro de port passé en paramètre
        int portNumber = Integer.parseInt(args[0]);

        //bloc type, on met en place une socket sur le port concerné
        //et on se crée des descripteur de fichier avec leurs buffer en écriture
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {

        	//on  se crée deux variables: l'netrée et la sortie
        	String inputLine = "\n", outputLine;

            // on initialise la communication avec le client
            ServerProtocol sp = new ServerProtocol();
            outputLine = "welcome to hunt the wumpus (press 'h' anytime for help)";
            out.println(outputLine);


            //on boucle tant que l'on reçoit des données dans la socket
            //on demande au protocole de nous fournir la réponse a l'entrée fournie
            //puis on l'envoie en sortie
            //si jamais l'entrée est "Bye." on quitte

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("Bye."))
                    break;

            	  System.out.println("reçu: "+inputLine);
                outputLine = sp.processInput(inputLine);
                System.out.println("envoyé: "+outputLine);
                out.println(outputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
