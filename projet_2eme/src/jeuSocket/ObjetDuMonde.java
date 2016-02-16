/**Documentation de la classe ObjetDuMonde
 * 
 * @author Groupe de projet de D
 * @version 1.0
 */

package jeuSocket;


public abstract class ObjetDuMonde
{
	// Les attributs
	
	
	/**
	 * Permet d'indiquer si l'objet tuera le joueur ou non 
	 */
	protected boolean mortel;
	
	/**
	 * Permet d'indiquer si on affichera dans les cases environnantes un messages pour prevenir du danger ou non
	 */
	protected boolean indique;
	
	
	
	// Le constructeur
	
	/**
	 * Constructeur de la classe ObjetDuMonde (reutilise dans les sous-classes)
	 * @param mortel permet de dire si l'objet instancie sera mortel pour le joueur
	 * @param indique permet de dire si on indiquera au joueur sa presence dans les cases environnantes.
	 */
	public ObjetDuMonde(boolean mortel, boolean indique)
	{
		this.mortel = mortel;
		this.indique = indique;
	}


	
	/**
	 * @return permet de dire si l'objet en question est mort ou pas.
	 */
	public boolean getMortel()
	{
		return mortel;
	}


	/**
	 * Permet de regler l'attribut mortel d'un objet
	 * @param mortel
	 */
	public void setMortel(boolean mortel)
	{
		this.mortel = mortel;
	}


	/**
	 * @return permet de savoir si l'objet sera indique ou non.
	 */
	public boolean getIndique()
	{
		return indique;
	}


	/**
	 * Permet de regler l'attribut indique d'un objet du monde 
	 * @param indique la valeur que l'on souhaite attribuer a l'objet
	 */
	public void setIndique(boolean indique)
	{
		this.indique = indique;
	}
}
