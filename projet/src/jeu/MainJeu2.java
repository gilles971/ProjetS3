
package jeu;

import IA.*;
import java.util.Scanner;


public class MainJeu2 {

	public static void main(String[] args) {

		System.out.println("Bonjour et bienvenue dans le jeu de la Chasse au Wumpus cree par la groupe de projet D du groupe de TP 4-1"
				+ "de la promo 1 de l'IUT Informatique de Nantes.\n"
				+ "Le but du jeu est de ramasser tous les sacs d'or du monde sans se faire tuer par un Wumpus ou un trou !\n"
				+ "Pour tuer un Wumpus, il faut tirer une fleche sur sa case !\n"
				+ "Bon jeu !");
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nEntrez votre pseudo : ");
		String pseudo = "";
		pseudo = sc.nextLine();
		
		Joueur j = new Joueur(pseudo);		
		
		System.out.println("Monde de base selectionne par defaut compose de :\n"
				+ "1 Plateau de 5x5 cases\n"				
				+ "1 Wumpus\n"
				+ "2 Trous\n"
				+ "1 Sac d'or\n"
				+ "1 fleche \n");
                Partie2 thread1 = new Partie2(j, "hunt the wumpus");

		thread1.setMonde(new Monde(5,1,2,0,1));
		thread1.disposerPlateau();
		thread1.setGrille();
		
		//ArrayList<Integer> li =p.jouer();			//Modifié pour S3
		
		thread1.start();
		try {
			thread1.sleep(500);
			}
		catch (InterruptedException e) {
			System.out.println(e.getMessage());
			}
		Threaducteur thread2 = new Threaducteur("translate the wumpus");
		thread2.start();
		while (thread1.getState() != Thread.State.TERMINATED);
		System.out.println("\nFin de la partie.\n\nPour rejouer, veuillez relancer l'executable du jeu !");			
		
		sc.close();
	}
}
