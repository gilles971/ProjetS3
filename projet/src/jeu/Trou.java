/**Documentation de la classe Trou
 * 
 * @author Groupe de projet de D
 * @version 1.0
 */

package jeu;


public class Trou extends ObjetDuMonde
{
	// Le constructeur
	
	/**
	 * Le constructeur de la classe Trou
	 * Un puits tue le joueur et est indique a celui-ci.
	 */
	public Trou()
	{
		super(true, true);
	}
}