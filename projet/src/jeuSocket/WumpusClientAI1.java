package jeuSocket;

/*
 * cette classe sert a communiquer avec le WumpusServer
 * elle permet a une IA de réagir aux situations communiquée par le serveur
 * 
 * elle se lance exactement comme le Client WumpusClient, sauf qu'elle génère
 * ses sorties avec l'IA
 */

import java.util.ArrayList;

import IA.IA1;

import java.io.*;
import java.net.*;

public class WumpusClientAI1 {
    public static void main(String[] args) throws IOException {
        
    	//on vérifie que la classe est utilisée correctement
        if (args.length != 2) {
            System.err.println(
                "Usage: java WumpusClientAI1 <host name> <port number>");
            System.exit(1);
        }

     // on mémorise le numéro de port
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        IA1 artificialIntelligence = new IA1(5, 5, 1);

        try (
        		// on commence par créer nos socket avec 
        		//leur descripteur de fichier et leur buffer en écriture
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            String fromServer;
            String fromAI;

          //on lance notre boucle:
            //tant que l'on reçoit des données du serveur
            //on les donne a traiter a l'IA qui nous donne le mouvement
            //puis on les envoie au serveur
            while ((fromServer = in.readLine()) != null) {
            	fromAI = "\t";
                //System.out.println("Server: " + fromServer);
                ArrayList<String> info = new ArrayList<String>();
                
                //on patiente un peu :)
            	try {
            		Thread.currentThread().sleep(2000);
            	} catch (InterruptedException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
            	
            	//ondécoupe la String en entrée au niveau des espaces
                String[] temp = fromServer.split("\\s+");
                for (int i = 0; i<temp.length; i++) {
                    info.add(temp[i]);
                }
                //si on a gagné ou perdu on break
                if (fromServer.equals("Wumpus suicide ! Try again !") ||fromServer.equals("Vous avez gagne !!!") )
                    break;
                
                //ici on vérifie que les deux premières données sont bien des coordonées
                // si oui on demande a l'IA de jouer
                if((info.get(0).equals("0") || info.get(0).equals("1") || info.get(0).equals("2") || info.get(0).equals("3") || info.get(0).equals("4")) &&
                   (info.get(1).equals("0") || info.get(1).equals("1") || info.get(1).equals("2") || info.get(1).equals("3") || info.get(1).equals("4")))
                {
                	System.out.println("ok");
                    fromAI = artificialIntelligence.jouer();
                }                                     
                    //a moins que l'IA n'ait rien retourné, on envoie la donnée au serveur
                if (fromAI != null) {
                    //System.out.println("Client: " + fromAI);
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


