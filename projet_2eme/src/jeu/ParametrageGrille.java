package jeu;


public class ParametrageGrille {
	
	private Joueur joueur;
	private Monde monde;
	
	/**Constructeur de la classe ParametrageGrille
	 * 
	 * @param j - Joueur de la partie en cours
	 */
	public ParametrageGrille(Joueur j, Monde m) {
		this.joueur = j;
		this.monde = m;
	}
	
	/**Methode qui affiche les commandes a utiliser pendant une partie
	 *
	 * @return - Message d'information listant les differentes commandes realisables
	 */
	public String commandes() {
		return ("Les differentes commandes possibles sont a utiliser de la facon suivante : \n"
				+	"\"raccourci action\" [espace] \"raccourci direction\".\n"
				+ "     --> Exemple : d n veut dire \"Deplacement au nord\""
				+ "\nDeux actions sont possibles : d (raccourci pour se deplacer) et t (raccourci pour tirer)"
				+ "\nQuatre directions sont accessibles : n (nord), s (sud), e (est), o (ouest)"
				+ "\nPour afficher l'historique de vos actions, entrez la commande : \"h\"");
		}
	
	/**Methode qui affiche les coordonnees du joueur apres chaque action
	 * 
	 * @return - Message d'information sur les coordonnees du joueur
	 */
	public String positionActuelle() {
		return ("Vous vous situez sur la case (" + this.joueur.getCoordX() + ", " + this.joueur.getCoordY() + ")"
				+ "\nAppuyez sur entree pour continuer");
	}
	
	
	/** Methode permettant de creer la chaine de caracteres contenant l'affichage de la grille
	 * 
	 * @return la chaine de caracteres contenant l'affichage de la grille
	 */
	public String recupAffGrille() {
		
		int taille = this.monde.getLongueurCote();

		String caseGrille = "            0          1          2          3          4\n       ";

		int i = 0, j = 0;
		Case[][] grilleJeu = new Case[this.monde.getLongueurCote()][this.monde.getLongueurCote()];
		grilleJeu = this.monde.getPlateau();
		
		

		for (j = 0;j < taille; j++) {

			for(i = 0; i < taille; i++) {
				caseGrille = caseGrille + " _      _ ";
			}

			caseGrille = caseGrille + "\n       ";

			for(i = 0; i < taille; i++) {

				if (grilleJeu[i][j].getMarqueTrou()) {

					if ( grilleJeu[i][j].getPassageJoueur() || grilleJeu[i][j].getPresenceJoueur() ) {
						caseGrille = caseGrille + "/   ~~   \\";
					}					
					else {
						caseGrille = caseGrille + "/          \\";
					}
				}
				else {
					caseGrille = caseGrille + "/          \\";
				}
			}

			caseGrille = caseGrille + "\n" + j + "     ";

			for(i = 0; i < taille; i++) {
				
				if (grilleJeu[i][j].getPassageJoueur() && !(grilleJeu[i][j].getPresenceJoueur()) ) {

					if (grilleJeu[i][j].getMarqueWumpus()) {
						caseGrille = caseGrille + "     :-!    ";
					}	
					else {
						caseGrille = caseGrille + "            ";
					}
				}
				else if (grilleJeu[i][j].getPresenceJoueur()) {

					if (grilleJeu[i][j].getMarqueWumpus()) {
						caseGrille = caseGrille + "    J:-!    ";
					}	
					else {
						caseGrille = caseGrille + "     J      ";
					}
				}
				else {
					caseGrille = caseGrille + "            ";
				}
			}

			caseGrille = caseGrille + "\n       ";

			for(i = 0; i <taille; i++) {

				caseGrille = caseGrille + "\\_      _/";

			}
			caseGrille = caseGrille + "\n       ";
		}
		
		return caseGrille;

	}
	
}
