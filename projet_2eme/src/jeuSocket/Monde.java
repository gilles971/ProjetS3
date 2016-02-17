/**Documentation de la classe Monde
 *
 * @author Groupe de projet de D
 * @version 1.0
 */

package jeuSocket;




public class Monde {
	private int longueurCote;
	private int nbSac;
	private int nbWumpus;
	private int nbTrous;
	private int nbFleches;
	private Case[][] plateau;

	/**Constructeur de la classe Monde
	 *
	 * @param taille - Longueur d'un cote du plateau
	 * @param sac - Nombre de sacs a ramasser
	 * @param wumpus - Nombre de monstres sur la carte
	 * @param trous - Nombre de pieges sur la carte
	 * @param fleches - Nombre de fleches disponibles pour abattre le monstre
	 */
	public Monde(int taille, int wumpus, int trous,int sac, int fleches) {	// Modification car erreur dans l'ordre des objets
		this.longueurCote = taille;
		this.nbSac = sac;
		this.nbWumpus = wumpus;
		this.nbTrous = trous;
		this.nbFleches = fleches;
		this.plateau = new Case[taille][taille];


		int i, j;

		for( i = 0 ; i < taille ; i++ ) {

			for ( j= 0 ; j < taille ; j++ ) {

				this.plateau[i][j] = new Case();
			}

		}

	}


	/**Placement des objets sur les cases du monde
	 *
	 */
	public void placerObjets() {


		int i, j, k, randomX, randomY;
		ObjetDuMonde w = new Wumpus();
		ObjetDuMonde t = new Trou();
		ObjetDuMonde s = new Sac();

		for (i=0;i<this.nbWumpus;i++) {


			randomX = (int)(Math.random()*this.longueurCote);
			randomY = (int)(Math.random()*this.longueurCote);


			if (this.plateau[randomX][randomY].getOccupe()) {	//Un seul wumpus possible

				while (this.plateau[randomX][randomY].getOccupe()) {


					randomX = (int)(Math.random()*this.longueurCote);
					randomY = (int)(Math.random()*this.longueurCote);

				}
			}
			if(nbWumpus>0){	   // Ligne ajouté pour la cas : 0 objet
			this.plateau[randomX][randomY].setObjet(w);
			this.plateau[randomX][randomY].setOccupe(true);
			System.out.println();
			System.out.println("Un wumpus en "+randomX+"-"+randomY+".");
			}
		}

		for (j=0;j<this.nbTrous;j++) {

			randomX = (int)(Math.random()*this.longueurCote);
			randomY = (int)(Math.random()*this.longueurCote);

			if (this.plateau[randomX][randomY].getOccupe()) {

				while (this.plateau[randomX][randomY].getOccupe()) {


					randomX = (int)(Math.random()*this.longueurCote);
					randomY = (int)(Math.random()*this.longueurCote);

				}

			}

			if(this.nbTrous>0){	// Ligne ajouté pour la cas : 0 objet
			this.plateau[randomX][randomY].setObjet(t);
			this.plateau[randomX][randomY].setOccupe(true);
			System.out.println();
			System.out.println("Un trou en "+randomX+"-"+randomY+".");
			}
		}

		for (k=0;k<this.nbSac;k++) {

			randomX = (int)(Math.random()*this.longueurCote);
			randomY = (int)(Math.random()*this.longueurCote);

			if (this.plateau[randomX][randomY].getOccupe()) {

				while (this.plateau[randomX][randomY].getOccupe()) {


					randomX = (int)(Math.random()*this.longueurCote);
					randomY = (int)(Math.random()*this.longueurCote);

				}
			}

			if(this.nbSac > 0){	// Ligne ajouté pour la cas : 0 objet
			this.plateau[randomX][randomY].setObjet(s);
			this.plateau[randomX][randomY].setOccupe(true);
			System.out.println();
			System.out.println("Un sac en "+randomX+"-"+randomY+".");
			}
		}
	}


	/**Marquage des cases par des indices de presence en fonction de l'objet detecte sur la case voisine
	 *
	 */
	public void marquerCases() {

		int i, j , k;
		ObjetDuMonde w = new Wumpus();
		ObjetDuMonde t = new Trou();

		for (i=0;i<(this.nbWumpus+this.nbTrous);i++) {

			for (j=0;j< this.longueurCote ;j++) {

				for ( k=0 ; k< this.longueurCote ; k++) {

				w = this.plateau[j][k].getObjet();
				t = this.plateau[j][k].getObjet();

				if (w instanceof Wumpus) {

					if ((j+1) <= (longueurCote-1)) {
						this.plateau[j+1][k].setMarqueWumpus(true);
					}

					if ((j+2) <= (longueurCote-1)) {
						this.plateau[j+2][k].setMarqueWumpus(true);
					}

					if ((j-1) >= 0) {
						this.plateau[j-1][k].setMarqueWumpus(true);
					}

					if ((j-2) >= 0) {
						this.plateau[j-2][k].setMarqueWumpus(true);
					}

					if ((k+1) <= (longueurCote-1)) {
						this.plateau[j][k+1].setMarqueWumpus(true);
					}

					if ((k+2) <= (longueurCote-1)) {
						this.plateau[j][k+2].setMarqueWumpus(true);
					}

					if ((k-1) >= 0) {
						this.plateau[j][k-1].setMarqueWumpus(true);
					}

					if ((k-2) >= 0) {
						this.plateau[j][k-2].setMarqueWumpus(true);
					}

					if ((j+1) <= (longueurCote-1) && (k+1) <= (longueurCote-1)) {
						this.plateau[j+1][k+1].setMarqueWumpus(true);
					}

					if ((j+1) <= (longueurCote-1) && (k-1) >= 0) {
						this.plateau[j+1][k-1].setMarqueWumpus(true);
					}

					if ((j-1) >= 0 && (k+1) <= (longueurCote-1)) {
						this.plateau[j-1][k+1].setMarqueWumpus(true);
					}

					if ((j-1) >= 0 && (k-1) >= 0) {
						this.plateau[j-1][k-1].setMarqueWumpus(true);
					}

				}

				else if (t instanceof Trou) {

					if ((j+1) <= (longueurCote-1)) {
						this.plateau[j+1][k].setMarqueTrou(true);
					}
					if ((j-1) >= 0) {
						this.plateau[j-1][k].setMarqueTrou(true);
					}
					if ((k+1) <= (longueurCote-1)) {
						this.plateau[j][k+1].setMarqueTrou(true);
					}

					if ((k-1) >= 0) {
						this.plateau[j][k-1].setMarqueTrou(true);
					}
				}

				}
			}
		}
	}


	/**Placement du joueur sur une case
	 *
	 * @param player - Joueur a placer au debut de la partie
	 */
	public void placerJoueur(Joueur player) {

		int randomX, randomY;

		randomX = (int)(Math.random()*this.longueurCote);
		randomY = (int)(Math.random()*this.longueurCote);

		if (this.plateau[randomX][randomY].getOccupe()) {

			while (this.plateau[randomX][randomY].getOccupe()) {


				randomX = (int)(Math.random()*this.longueurCote);
				randomY = (int)(Math.random()*this.longueurCote);

			}
		}

		this.plateau[randomX][randomY].setPresenceJoueur(true);
		this.plateau[randomX][randomY].setPassageJoueur(true);
		this.plateau[randomX][randomY].setObjet(new Sortie());
	}


	/**Accesseur de l'attribut plateau
	 *
	 * @return - Le plateau genere
	 */
	public Case[][] getPlateau() {
		return plateau;
	}
	public void setPlateau(Case[][] plateau) {
		this.plateau = plateau;
	}


	/**
	 * @return la longueur d'un cote du monde
	 */
	public int getLongueurCote() {
		return longueurCote;
	}
	/**
	 *
	 * @param longueurCote la nouvelle longueur d'un cote
	 */
	public void setLongueurCote(int longueurCote) {
		this.longueurCote = longueurCote;
	}

	/**
	 * @return le nombre de sac(s) du monde
	 */
	public int getNbSac() {
		return nbSac;
	}
	/**
	 *
	 * @param nbSac le nouveau nombre de sac(s) du monde
	 */
	public void setNbSac(int nbSac) {
		this.nbSac = nbSac;
	}

	/**
	 * @return le nombre de wumpus du monde
	 */
	public int getNbWumpus() {
		return nbWumpus;
	}
	/**
	 *
	 * @param nbWumpus le nouveau nombre de wumpus du monde
	 */
	public void setNbWumpus(int nbWumpus) {
		this.nbWumpus = nbWumpus;
	}

	/**
	 * @return le nombre de trou(s) du monde
	 */
	public int getNbTrous() {
		return nbTrous;
	}
	/**
	 *
	 * @param nbTrous le nouveau nombre de trou(s) du monde
	 */
	public void setNbTrous(int nbTrous) {
		this.nbTrous = nbTrous;
	}

	/**
	 * @return le nombre de fleche(s) du monde
	 */
	public int getNbFleches() {
		return nbFleches;
	}
	/**
	 *
	 * @param nbFleches le nouveau nombre de fleche(s) du monde
	 */
	public void setNbFleches(int nbFleches) {
		this.nbFleches = nbFleches;
	}


}
