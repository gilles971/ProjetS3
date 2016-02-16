/**Documentation de la classe Partie
 * 
 * @author Groupe de projet de D
 * @version 1.0
 */

package jeuSocket;

import IA.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Partie extends Thread {
	
	// Section des attributs	
	
	private String historique;
	private int compteur;
	private boolean victoire;
	private boolean wumpusTuer;
	private int nbSacRamasses;
	private Joueur joueur;		
	private	Monde monde;		
	private String raisonMort;
	private ParametrageGrille grille;
	private Vue vueFenetre;



	public int getNbPoint()
	{
		int point=0;
		if(this.getVictoire()==true)
								{
								point += 24;
								}
        if(this.wumpusTuer)
        						{
        						point += 24;
        						}
        if(this.nbSacRamasses!=0 && this.monde.getNbSac() != 0)
        						{
        						point+= (24/this.monde.getNbSac())*this.getNbSacRamasses();
        						}

     point -= this.compteur;
     return point;
	}
	
	// Section des constructeurs
	
	/**Constructeur de la classe Partie permet d'initialiser la grille de jeu
*@param j qui represente le joueur de la partie
*/
	public Partie(Joueur j, String name) {
		super(name);
		this.joueur = j;
		this.historique = "";
		this.nbSacRamasses = 0; 
		this.compteur = 0;
		this.victoire = false;
		this.wumpusTuer= false;
		this.monde = null;				
		this.raisonMort = null;
		this.vueFenetre = new Vue("Jeu de la chasse au Wumpus", (Partie) this);
		this.grille = new ParametrageGrille(this.joueur, this.monde);
		this.pointJoueur=0;
	}	
	
	// Section des getters and setters
	
	/**methode qui permet de recuperer la Vue
*@return la vue de la partie
*/
	public Vue getVue() {
		return this.vueFenetre;
	}
	
	/**methode qui permet de recuperer la valeur de l'attribut victoire
*@return true si le joueur a gagne et false dans le cas ou le joueur a perdu ou s'il n'a pas encore gagne
*/
	public boolean getVictoire() {
		return this.victoire;
	}

	/**methode qui permet de changer la valeur de l'attribut victoire
*@param saVictoire qui represente la nouvelle valeur de l'attribut
*/
	public void setVictoire(boolean saVictoire) {
		this.victoire = saVictoire;
	}

	

	/**methode qui permet de recuperer le nombre de sacs d'or ramasses
 * @return le nombre de sacs d'or ramasses
 */
	public int getNbSacRamasses() {
		return nbSacRamasses;
	}
	
	/**methode qui permet de changer le nombre de sacs d'or ramasses
 * @param nbSacRamasse qui represente le nombre de sacs d'or ramasses
 */
	public void setNbSacRamasses(int nbSacRamasses) {
		this.nbSacRamasses = nbSacRamasses;
	}

	
	
	/**methode qui permet de recuperer le joueur
 * @return le joueur
 */
	public Joueur getJoueur() {
		return joueur;
	}

	/**methode qui permet de changer le joueur
 * @param joueur qui represente le joueur
 */
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	
	
	/**methode qui permet de recuperer le monde
 * @return le monde
 */
	public Monde getMonde() {
		return monde;
	}

	/**methode qui permet de changer le monde
 * @param monde qui represente le monde
 */
	public void setMonde(Monde monde) {
		this.monde = monde;
	}

	

	/**methode qui permet de recuperer la raison de la mort du joueur
 * @return la raison de la mort du joueur
 */
	public String getRaisonMort() {
		return raisonMort;
	}

	/**methode qui permet de changer la raison de la mort du joueur
 * @param raisonMort qui represente la raison de la mort du joueur
 */
	public void setRaisonMort(String raisonMort) {
		this.raisonMort = raisonMort;
	}
	
	
	/**methode qui permet de recuperer l'attribut permettant le parametrage de la grille de jeu
	 * @return l'attribut permettant le parametrage de la grille de jeu
	 */
	public ParametrageGrille getGrille() {
		return grille;
	}

	/**methode setter remaniee qui permet de l'attribut grille de la Partie
	 */
	public void setGrille() {
		this.grille =  new ParametrageGrille(this.joueur, this.monde);
	}	
	
	
	
	
	// Section des méthodes 
	

	/**methode qui permet de savoir si la case ou se trouve le joueur est occupee, et si c'est le cas
	 *  les modifications dans les classes seront faites 
	*/
	public void caseJoueurOccupee(){
			
		Case[][] plat = this.monde.getPlateau();
			
		int temp = 0;
			
		if (plat[this.joueur.getCoordX()][this.joueur.getCoordY()].getOccupe()) {
				
			if(plat[this.joueur.getCoordX()][this.joueur.getCoordY()].getObjet() instanceof Wumpus) {
					
				System.out.println("\nMais... mais c'est un Wumpus ?! *miam miam*");
				this.joueur.setVivant(false);
				this.raisonMort = "Wumpus";
			}
				
				
			if(plat[this.joueur.getCoordX()][this.joueur.getCoordY()].getObjet() instanceof Trou) {
					
				System.out.println("\nVous etes tombe dans un trou ...");
				this.joueur.setVivant(false);
				this.raisonMort = "trou";
			}
				
				
			if(plat[this.joueur.getCoordX()][this.joueur.getCoordY()].getObjet() instanceof Sac) {
					
				this.nbSacRamasses += 1;
					
				System.out.println("\nVous avez trouvez un sac d'or ! Vous en avez " + this.nbSacRamasses +" sur "
						+ this.monde.getNbSac() + ".\n");
					
				plat[this.joueur.getCoordX()][this.joueur.getCoordY()].setOccupe(false);
					
				temp = this.monde.getNbSac();
					
				if ( temp == this.nbSacRamasses) {
						
					this.setVictoire(true);
				}
			}
		}
	}
		
	
	
	/**methode qui permet au joueur de tirer une fleche
	 *  @return vrai si la fleche a bien ete tiree et faux dans le cas contraire 
	 */	
	public boolean tirer(String com) {
		
		boolean ret = false;
		Case[][] plat = this.monde.getPlateau();
		int nbfleches = this.joueur.getNbFleches();
		int i, j;
		
		int x = this.joueur.getCoordX();
		int y = this.joueur.getCoordY();
		
		
		if ( com.equals("t n"))
		{
			y--;
		}
		else if ( com.equals("t o"))
		{
			x--;
		}
		else if ( com.equals("t e"))
		{
			x++;
		}
		else if ( com.equals("t s"))
		{
			y++;
		}
		
		
		if (this.autoriserTir(x , y)) 
		{
			if ( nbfleches > 0) 
			{				
				this.joueur.setNbFleches(nbfleches - 1);
				this.historique = historique + "\n" + "Au tour " + this.compteur + " Vous avez fait un tir en (" + x + "," + y + ")";
				this.compteur++;
			
				if (plat[x][y].getOccupe()) 
				{				
					if (plat[x][y].getObjet() instanceof Wumpus)
					{					
						System.out.println("En plein entre les deux yeux !\nFelicitation vous avez tue le  wumpus !\n");
						plat[x][y].setObjet(null);
						plat[x][y].setOccupe(false);
						this.wumpusTuer=true;   
						
						for( i = 0 ; i < this.monde.getLongueurCote() ; i++ ) 
						{
							for( j = 0 ; j < this.monde.getLongueurCote() ; j++ ) 
							{								
								if ( plat[i][j].getMarqueWumpus())
								{									
									plat[i][j].setMarqueWumpus(false);
								}
							}
						}						
						this.monde.setPlateau(plat);						
					}
				} 			
				else 
				{
					System.out.println("Rate ! Il vous reste "+this.joueur.getNbFleches()+" fleches\n");
				}			
			} 
			else 
			{			
				System.out.println("\nVous n'avez plus de fleche pour tirer.\n");
			}
		}
		else 
		{			
			System.out.println("Vous ne pouvez pas tirer ici !\n");
		}
		
		ret = true;
		
		return ret;
	}
		

	
	/**methode qui permet de savoir si le deplacement demande est possible ou non
	 *  @return vrai si le deplacement est possible et faux dans le cas contraire
	 */
	public boolean autoriserDeplacement(int x, int y) {
		
		boolean ret = false;
		int joueurX = this.joueur.getCoordX();
		int joueurY = this.joueur.getCoordY();
		
		if ( ((Math.abs(joueurX - x)) == 1) && (((joueurY - y) == 0)) ||
			 (((joueurX - x) == 0) && ((Math.abs(joueurY - y)) == 1)) )
		{		
			ret = true;
		}		
		
		if ( (x > this.monde.getLongueurCote()-1) || (x < 0) 
			|| (y > this.monde.getLongueurCote()-1) || (y < 0) ) {			
			ret = false;
		}
		
		return ret;		
	}
	
	
	
	/**methode qui permet de savoir si le tire demande est autorise
	 *  @return vrai si le tire demande est possible et faux dans le cas contraire
	 */
	public boolean autoriserTir(int x, int y) {
		
		boolean ret = false;
		
		int joueurX = this.joueur.getCoordX();
		int joueurY = this.joueur.getCoordY();
		
		if ( ((Math.abs(joueurX - x)) == 1) && (((joueurY - y) == 0)) ||
			 (((joueurX - x) == 0) && ((Math.abs(joueurY - y)) == 1)) )
		{		
			ret = true;
		}		
		
		if ( (x > this.monde.getLongueurCote()-1) || (x < 0) 
			|| (y > this.monde.getLongueurCote()-1) || (y < 0) ) {			
			ret = false;
		}
		
		return ret;		
	}



	/**methode permettant le deplacement du joueur
	 *  @return vrai si le deplacement du joueur a bien eux lieu et faux dans le cas contraire 
	 */
	public boolean deplacer(String com) {
		
		boolean ret = false;
		this.localiserJoueur();
		Case[][] plateau = this.monde.getPlateau();

		int x = this.joueur.getCoordX();
		int y = this.joueur.getCoordY();
				
		if ( com.equals("d n"))
		{
			y--;
		}
		else if ( com.equals("d o"))
		{
			x--;
		}
		else if ( com.equals("d e"))
		{
			x++;
		}
		else if ( com.equals("d s"))
		{
			y++;
		}		
		
		if (autoriserDeplacement(x , y))
		{			
			this.historique = historique + "\n" + "Au tour " + this.compteur + " Vous vous etes deplace en (" + x + "," + y + ")";
			this.compteur++;
			
			plateau[this.joueur.getCoordX()][this.joueur.getCoordY()].setPresenceJoueur(false);
			plateau[this.joueur.getCoordX()][this.joueur.getCoordY()].setPassageJoueur(true);
			
			this.joueur.setCoordX(x);
			this.joueur.setCoordY(y);
			
			plateau[this.joueur.getCoordX()][this.joueur.getCoordY()].setPresenceJoueur(true);
			
			this.localiserJoueur();
			
			this.verifMarque();			
			this.monde.setPlateau(plateau);				
			this.caseJoueurOccupee();		
		} 		
		else 
		{			
			System.out.println("`\nErreur ! Ce deplacement n'est pas possible.\n");
			this.verifMarque();
		}
		ret = true;
		
		this.localiserJoueur();
		
		return ret;
	}
	
	

	/**methode qui permet verifier si la case est marquer
	 * c'est a dire si un piege se trouve a proximite et 
	 * d'afficher les messages au joueur
	 */
	public String verifMarque() {
		
		Case[][] plat = this.monde.getPlateau();
		String ret = "";
		
		System.out.println();
		if (plat[this.joueur.getCoordX()][this.joueur.getCoordY()].getMarqueTrou()) {
			
			ret = ret + "Vous sentez un courant d'air !! ";
		}		
		if (plat[this.joueur.getCoordX()][this.joueur.getCoordY()].getMarqueWumpus()) {
			
			ret = ret + "Vous sentez une odeur infame... ";
		}
		if ( (plat[this.joueur.getCoordX()][this.joueur.getCoordY()].getPassageJoueur()) &&
			 !this.historique.equals("") // evite l'affichage de l'info avant meme que le joueur ait fait un seul deplacement
		   ) {
			
			ret = ret + "Vous etes deja passe par la. ";
		}
		if ( !(plat[this.joueur.getCoordX()][this.joueur.getCoordY()].getMarqueWumpus()) &&
			 !(plat[this.joueur.getCoordX()][this.joueur.getCoordY()].getMarqueTrou()) ) {
				
			return "C'est trop calme... ";
		}
		return ret;
	}
	
	
	
	/**methode qui permet de savoir ou se trouve le joueur
	 */
	public void localiserJoueur() {		
		int i,j;		
		Case[][] plat = this.monde.getPlateau();
		
		for ( i = 0 ; i < this.monde.getLongueurCote() ; i++ ) {
			for ( j = 0 ; j < this.monde.getLongueurCote() ; j++ ) {				
				if (plat[i][j].getPresenceJoueur()) {						
					this.joueur.setCoordX(i);
					this.joueur.setCoordY(j);
				}				
			}
		}		
		this.monde.setPlateau(plat);
	}
	
	
	
	/**methode qui permet de disposer le plateau de jeu
	 */	
	public void disposerPlateau() {
		
		Monde monde = this.getMonde();
		
		monde.placerObjets();		
		monde.marquerCases();		
		monde.placerJoueur(this.joueur);
		
		this.localiserJoueur();
		
		this.joueur.setNbFleches(monde.getNbFleches());
		
		this.setMonde(monde);	
	}


}