package jeuSocket;

/*
 * cette classe sert a communiquer avec le WumpusServer
 * elle permet a une IA de réagir aux situations communiquée par le serveur
 *
 * elle se lance exactement comme le Client WumpusClient, sauf qu'elle génère
 * ses sorties avec l'IA
 */

import java.util.ArrayList;

import IA.IaTemeraire;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WumpusClientAiTemeraire {
    public static void main(String[] args) throws IOException {

    	//on vérifie que la classe est utilisée correctement
        if (args.length != 3) {
            System.err.println(
                "Usage: java KnockNnockClient <host name> <port number> <turn number>");
            System.exit(1);
        }

     // on mémorise le numéro de port
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        int tours = Integer.parseInt(args[2]);

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

            System.out.println("configurez\n");
            Scanner sctmp = new Scanner(System.in);
            String tmpOrder = sctmp.nextLine();
            sctmp.close();


        String[] objets = tmpOrder.split("\\s+");
        int dim = Integer.valueOf(objets[0]);
        int wumpus=1;
        int trou=1;
        int sac=0;
        for(int i=1; i<objets.length; i++) {
            String[] objQuantite = objets[i].split(":");
            if (objQuantite[0].equals("Wumpus")) wumpus = Integer.valueOf(objQuantite[1]);
            if (objQuantite[0].equals("Sac")) sac = Integer.valueOf(objQuantite[1]);
            if (objQuantite[0].equals("Trou")) trou = Integer.valueOf(objQuantite[1]);
        }
        IaTemeraire artificialIntelligence = new IaTemeraire(dim, dim, wumpus, trou, tours);


          //on lance notre boucle:
            //tant que l'on reçoit des données du serveur
            //on les donne a traiter a l'IA qui nous donne le mouvement
            //puis on les envoie au serveur
            while ((fromServer = in.readLine()) != null) {
            	fromAI = tmpOrder;
                //System.out.println("Server: " + fromServer);
                ArrayList<String> info = new ArrayList<String>();

                //on patiente un peu :)
            	try {
            		Thread.currentThread().sleep(2000);
            	} catch (InterruptedException e) {
            		e.printStackTrace();
            	}

            	//ondécoupe la String en entrée au niveau des espaces
                String[] temp = fromServer.split("\\s+");
                for (int i = 0; i<temp.length; i++) {
                    info.add(temp[i]);
                }
                //si on a gagné ou perdu on break
                //if (fromServer.equals("Wumpus suicide ! Try again !") ||fromServer.equals("Vous avez gagne !!!") )
                  //  break;

                //ici on vérifie que les deux premières données sont bien des coordonées
                // si oui on demande a l'IA de jouer

                try {
                  int x = Integer.valueOf(info.get(0));
                  int y = Integer.valueOf(info.get(1));
                  if ( x > -1 && y > -1){
                  	System.out.println("ok");
                    fromAI = artificialIntelligence.jouer(x, y, info);
                  }
                } catch (NumberFormatException e) {
                  e.printStackTrace();
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
