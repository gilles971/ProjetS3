/**Documentation de la classe Partie
 *
 * @author Groupe de projet de D refactorisée par l'équipe IA Wumpus
 * @version 2.0
 */

package jeuSocket;

import java.util.ArrayList;
import java.util.Scanner;


public class Partie {

	private String historique;
	private int compteur;
	private int score;
	private Joueur joueur;
	private	Monde monde;
	private Vue vueFenetre;

	public Partie(Joueur j) {
		this.joueur = j;
		this.historique = "";
		this.compteur = 0;
		this.monde = null;
		this.vueFenetre = new Vue("Jeu de la chasse au Wumpus", (Partie) this);
		//this.pointJoueur=0;
	}

	public Vue getVue() { return this.vueFenetre; }

	public Joueur getJoueur() { return joueur; }

	public Monde getMonde() { return monde; }
	public void setMonde(Monde monde) { this.monde = monde; }

	public void interactionJoueurObjet(){
		ObjetDuMonde o = monde.getPlateau()[joueur.getX()][joueur.getY()].getObjet();
		if (o != null)
			o.interaction(joueur);
	}


	public String tirer(String com) {
		int x = joueur.getX();
		int y = joueur.getY();
		String ret = "Vous n'avez rien touche avec votre tir";
		//int nbfleches = joueur.getNbFleches();
		//int i, j;

		if ( com.equals("t n")) { y--; }
		if ( com.equals("t o")) { x--; }
		if ( com.equals("t e")) { x++; }
		if ( com.equals("t s")) { y++; }

		if (this.autoriserTir(x , y))
		{
			joueur.tirer();

			if (monde.getPlateau()[x][y].getObjet() instanceof Wumpus) {
				joueur.addScore(200);
				monde.remove(x, y);
				ret = "Le wumpus est tue par votre tir";
			}

			this.historique = historique + "\n" + compteur + com;
			this.compteur++;
			return ret;
		}

		return "Impossible";
	}

	public boolean autoriserDeplacement(int x, int y) {

		boolean ret = false;
		int joueurX = this.joueur.getX();
		int joueurY = this.joueur.getY();

		if (((Math.abs(joueurX - x)) == 1 && joueurY == y)
			|| (joueurX == x && (Math.abs(joueurY - y)) == 1))
		{
			ret = true;
		}

		if ( (x > this.monde.getTaille()-1) || (x < 0)
			|| (y > this.monde.getTaille()-1) || (y < 0) ) {
			ret = false;
		}

		return ret;
	}

	public boolean autoriserTir(int x, int y) {
		return joueur.getNbFleches()>0 && autoriserDeplacement(x, y);
	}

	public String deplacer(String com) {

		String ret ="";

		Case[][] plateau = this.monde.getPlateau();

		int x = this.joueur.getX();
		int y = this.joueur.getY();

		if ( com.equals("d n")) { y--; }
		if ( com.equals("d o")) { x--; }
		if ( com.equals("d e")) { x++; }
		if ( com.equals("d s")) { y++; }

		if (autoriserDeplacement(x, y))
		{
			plateau[joueur.getX()][joueur.getY()].setJoueur(null);

			joueur.setX(x);
			joueur.setY(y);

			plateau[x][y].setJoueur(joueur);
			plateau[x][y].visiter();

			interactionJoueurObjet();
			if (monde.getPlateau()[x][y].getObjet() instanceof Sac) {
				monde.remove(x, y);
			}

			this.historique = historique + "\n" + compteur + com;
			this.compteur++;
			for (ObjetDuMonde o : monde.getPlateau()[x][y].getIndices()) {
				ret += o.getMessage()+" ";
			}
			System.out.println(joueur.getScore());
			return ret;
		}

		return "deplacement impossible";
	}

	public void sortir() {
		int x = joueur.getX();
		int y = joueur.getY();
		if (monde.getPlateau()[x][y].getObjet() instanceof Sortie) {
			victoire();
			monde.getPlateau()[x][y].setJoueur(null);
		}
		else {
			System.out.println("vous ne pouvez pas sortir");
		}
	}

	public void disposerPlateau() {

		Monde monde = this.getMonde();

		//monde.placerObjets();
		monde.placerJoueur(joueur, 1);

		joueur.setNbFleches(); //*** a mettre eventuellement nb fleche différent
	}

	public boolean defaite() { return !joueur.alive(); }

	public void victoire() {	if (joueur.alive()) joueur.addScore(50);}

	public boolean gameOver() {
		if(monde.getPlateau()[joueur.getX()][joueur.getY()].getJoueur() == null) return true;
		return defaite();
	}

	public String commandes() {
		return ("Les differentes commandes possibles sont a utiliser de la facon suivante : \n"
				+	"\"raccourci action\" [espace] \"raccourci direction\".\n"
				+ "     --> Exemple : d n veut dire \"Deplacement au nord\""
				+ "\nDeux actions sont possibles : d (raccourci pour se deplacer) et t (raccourci pour tirer)"
				+ "\nQuatre directions sont accessibles : n (nord), s (sud), e (est), o (ouest)"
				+ "\nPour afficher l'historique de vos actions, entrez la commande : \"h\"");
	}

	public String toString() {

		String ret="";
		int t = monde.getTaille();

		for (int i=0; i<t; i++) { ret += "    "+i; }
		ret += "\n  ";

		for (int i=0; i<t; i++) { ret += " _ _ "; }
		ret += "\n";

		for (int i=0; i<t; i++) {
			ret += i+" ";
			for (int j=0; j<t; j++) {
				ret += "/";
				for (int k=0; k<3; k++) {
					if (monde.getPlateau()[j][i].getJoueur() != null && k==2) { ret += "J"; }
					else {
						if (monde.getPlateau()[j][i].getIndices().size()<=k
								|| !monde.getPlateau()[j][i].getVisite()) {
							ret+= " ";
						}
						else {
							ret += new Character(monde.getPlateau()[j][i].getIndices().get(k).getSymbole());
						}
					}
				}
				ret += "\\";
			}
			ret += "\n  ";

			for (int j=0; j<t; j++) {
				ret += "\\_ _/";
			}
			ret += "\n";
		}
		return ret;
	}
}
