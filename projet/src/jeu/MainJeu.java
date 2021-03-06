
package jeu;
import java.util.Scanner;

import jeu.*;
import java.util.ArrayList;

public class MainJeu {

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
		Partie p = new Partie(j, "hunt the wumpus");
		
		System.out.println("Monde de base selectionne par defaut compose de :\n"
				+ "1 Plateau de 5x5 cases\n"				
				+ "1 Wumpus\n"
				+ "2 Trous\n"
				+ "1 Sac d'or\n"
				+ "1 fleche \n");
		
		p.setMonde(new Monde(5,1,2,0,1));
		p.disposerPlateau();
		p.setGrille();
		
		ArrayList<Integer> liste = p.jouer();			//Modifié pour S3
		
		System.out.println("\nFin de la partie.\n\nPour rejouer, veuillez relancer l'executable du jeu !");			
		
		sc.close();
	}
}
