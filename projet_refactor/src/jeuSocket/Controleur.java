/** Cette classe est la classe Controleur qui permet de gerer
*   la modification de la fenetre
* 
*@author Groupe de projet de D
*@version 1.0
*/

package jeuSocket;


import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controleur implements KeyListener {

	// attribut de la classe
	private Vue vueFenetre;
	private Partie laPartie;
	
	// constructeur de la classe
	public Controleur(Vue vueF, Partie p) {
		this.vueFenetre = vueF;
		this.laPartie = p;
	}

	// methode demandant a la fenetre de modifier la grille au cours du jeu
	/**Constructeur de la classe Controleur
	*@param e qui represente l'evenement que le controleur controle
	*/

	public void keyPressed(KeyEvent e) {

		// affichage de la victoire dans la fenetre
		if (this.laPartie.getVictoire()) {
			this.vueFenetre.updateVictoire();
		}
		else if (!(this.laPartie.getJoueur().getVivant())){
			this.vueFenetre.updateDefaite();
		}
		else {
			this.vueFenetre.updateGrille(this.laPartie.getGrille().recupAffGrille());
		}
	}


	public void keyReleased(KeyEvent arg0) {		
	}
	public void keyTyped(KeyEvent arg0) {		
	}
	
}
