/**Documentation de la classe Wumpus
 * 
 * @author Groupe de projet de D
 * @version 1.0
 */

package jeuSocket;



public class Wumpus extends ObjetDuMonde
{
	// --------------
	// Les attributs |
	// --------------
	
	/**
	 * Permet de savoir si le wumpus est vivant ou non.
	 */
	private boolean vivant;
	
	// ----------------
	// Le constructeur |
	// ----------------
	
	/**
	 * Constructeur de la classe Wumpus
	 * Un wumpus est mortel et indique au joueur.
	 */
	public Wumpus()
	{
		super(true, true);
		this.vivant = true;
	}
	
	// -------------
	// Les methodes |
	// -------------
	
	/**
	 * @return permet de savoir si un joueur est vivant ou non.
	 */
	public boolean getVivant()
	{
		return vivant;
	}

	/**
	 * Permet de regler l'attribut vivant du Wumpus
	 * @param vivant
	 */
	public void setVivant(boolean vivant)
	{
		this.vivant = vivant;
	}

}

