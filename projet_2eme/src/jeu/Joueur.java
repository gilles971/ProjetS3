/**Documentation de la classe Joueur
 * 
 * @author Groupe de projet de D
 * @version 1.0
 */

package jeu;



public class Joueur {
	
	
// Section des attributs
	
	private int coordX;
	private int coordY;
	private String pseudo;
	private boolean vivant;
	private int nbFleches;

	
// Section des constructeurs
	
	public Joueur(String sonPseudo) {
		this.pseudo = sonPseudo;
		this.vivant = true;
		this.coordX = 0;
		this.coordY = 0;
	}

	
// Section des methodes
	
	/**
	 * @return true si joueur vivant, false sinon
	 */
	public boolean getVivant() {
		return vivant;
	}
	/**
	 * @param vivant le boolean attribue a l'attribut vivant
	 */
	public void setVivant(boolean vivant) {
		this.vivant = vivant;
	}

	
	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	/**
	 * @param pseudo the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	
	/**
	 * @return coordX
	 */
	public int getCoordX() {
		return coordX;
	}
	/**
	 * @param coordX la nouvelle coordonnee X du joueur
	 */
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	
	/**
	 * @return coordY
	 */
	public int getCoordY() {
		return coordY;
	}
	/**
	 * @param coordY la nouvelle coordonnee Y du joueur
	 */
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	
	
	/**
	 * @return le nombre de fleche(s) que possède le joueur
	 */
	public int getNbFleches() {
		return nbFleches;
	}
	/**
	 * @param nbFleches le nouveau nombre de fleche(s)
	 */
	public void setNbFleches(int nbFleches) {
		this.nbFleches = nbFleches;
	}

	
	/**Permet d'afficher les informations du joueur
	 * @return la chaine de caracteres contenant les informations
	 */
	public String toString() {

		String ret = "...\n...\n...\n\nAttention ! Toi, "+ this.pseudo + ", tu viens d'entrer dans l'antre du wumpus !\n"
				+ "Tu es positionne sur la case de coordonnees (" + this.coordX +"," + this.coordY + ").\n"
						+ "N'oublie pas que tu ne possede que " + this.nbFleches;
		
		if (this.nbFleches > 1) {
			
			ret += " fleches dans ton carquois !\n";
		}
		else {
			
			ret +=  " seule fleche dans ton carquois !\n";
		}
		
		// ret += "\nAppuyer sur la touche Entree pour continuer";
		
		return ret;
	}

	
	

}