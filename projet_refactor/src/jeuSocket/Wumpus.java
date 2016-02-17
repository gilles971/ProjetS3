/**Documentation de la classe Wumpus
 * 
 * @author Groupe de projet de D refactorisée par l'équipe IA Wumpus
 * @version 2.0
 */

package jeuSocket;

public class Wumpus extends ObjetDuMonde
{
	public Wumpus()
	{
		super(2, "!", "Vous sentez une odeur infame...");
	}
	
	public void interaction(Joueur j) {
		j.kill();
	}
}

