/**Documentation de la classe Trou
 * 
 * @author Groupe de projet de D refactorisée par l'équipe IA Wumpus
 * @version 2.0
 */

package jeuSocket;

public class Trou extends ObjetDuMonde
{
	public Trou()
	{
		super(1, '~', "il y a un courant d'air");
	}
	
	public void interaction(Joueur j) {
		j.kill();
	}
}