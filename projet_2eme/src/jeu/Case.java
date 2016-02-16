/**Documentation de la classe Case
 * 
 * @author Groupe de projet de D
 * @version 1.0
 */



package jeu;



public class Case {

	
	// Section des attibuts
	
	private boolean presenceJoueur;
	private boolean passageJoueur;
	private boolean occupe;
	private boolean marqueTrou;
	private boolean marqueWumpus;	
	private ObjetDuMonde objet;
	
	
	// Section des constructeurs
	

	/**Constructeur de la classe Case
	 * 
	 */
	public Case() {
		this.presenceJoueur = false;
		this.occupe         = false;
		this.marqueTrou     = false;
		this.marqueWumpus   = false;
		this.objet          = null ;
	}

	
	
	// Section des methodes

	

	/**Permet un acces au booleen passageJoueur
	 * @return le booleen passageJoueur qui nous dit si le joueur est passe sur la case ou non
	 */
	public boolean getPassageJoueur() {
		return passageJoueur;
	}

	/**Permet de donner la valeur souhaitee au booleen passageJoueur
	 * @param passageJoueur la valeur voulue pour passageJoueur
	 */
	public void setPassageJoueur(boolean passageJoueur) {
		this.passageJoueur = passageJoueur;
	}

	
	
	/**Permet un acces au booleen presenceJoueur
	 * @return le booleen presenceJoueur qui nous dit si le joueur est sur la case ou non
	 */
	public boolean getPresenceJoueur() {
		return this.presenceJoueur;
	}

	/**Permet de donner la valeur souhaitee au booleen presenceJoueur
	 * @param presenceJoueur la valeur voulue pour presenceJoueur
	 */
	public void setPresenceJoueur(boolean presenceJoueur) {
		this.presenceJoueur = presenceJoueur;
	}




	/**Permet un acces au booleen occupe
	 * @return la booleen occupe qui nous dit si la case est occupee ou non
	 */
	public boolean getOccupe() {
		return occupe;
	}

	/**Permet de donner la valeur souhaitee au booleen occupe
	 * @param occupe la valeur voulue pour occupe
	 */
	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}




	/**Permet un acces au booleen marqueTrou
	 * @return le booleen marqueTrou qui nous dit si la case indique la proximite d'un trou ou non
	 */
	public boolean getMarqueTrou() {
		return marqueTrou;
	}

	/**Permet de donner la valeur souhaitee au booleen marqueTrou
	 * @param marqueTrou la valeur voulue pour marqueTrou
	 */
	public void setMarqueTrou(boolean marqueTrou) {
		this.marqueTrou = marqueTrou;
	}




	/**Permet un acces au booleen marqueWumpus
	 * @return le booleen marqueWumpus qui nous dit si la case indique la proximite d'un wumpus ou non
	 */
	public boolean getMarqueWumpus() {
		return marqueWumpus;
	}

	/**Permet de donner la valeur souhaitee au booleen marqueWumpus
	 * @param marqueWumpus la valeur voulue pour marqueWumpus
	 */
	public void setMarqueWumpus(boolean marqueWumpus) {
		this.marqueWumpus = marqueWumpus;
	}



	/**Permet de donner l'ObjetDuMonde contenu dans la case
	 * @return L'objet present dans la classe ou null si elle n'est pas occupee
	 */
	public ObjetDuMonde getObjet() {
		return objet;
	}

	/**Permet d'attribuer un ObjetDuMonde a une case
	 * @param objet l'ObjetDuMonde a mettre sur la case
	 */
	public void setObjet(ObjetDuMonde objet) {
		this.objet = objet;
	}
	


}
