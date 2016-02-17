/**Documentation de la classe Case
 * 
 * @author Groupe de projet de D refactorisée par l'équipe IA Wumpus
 * @version 2.0
 */
 
package jeuSocket;

import java.util.ArrayList;


public class Case {
	
	private Joueur joueur;
	private boolean visitee;
	private ObjetDuMonde objet;
	private ArrayList<ObjetDuMonde> indice;

	public Case() {
		joueur = null;
		visitee = false;
		objet = null ;
		indice = new ArrayList<ObjetDuMonde>();
	}
	
	public Joueur getJoueur() { return joueur; }
	public void setJoueur(Joueur joueur) { this.joueur = joueur; }
	
	public boolean getVisite() { return visitee; }
	public void visiter() { visitee = true; }
	
	public ArrayList<ObjetDuMonde> getIndices() { return indice; }

	public ObjetDuMonde getObjet() { return objet; }
	public void setObjet(ObjetDuMonde objet) { this.objet = objet; }
	


}
