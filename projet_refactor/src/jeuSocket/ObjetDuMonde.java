/**Documentation de la classe ObjetDuMonde
 *
 * @author Groupe de projet de D refactorisée par l'équipe IA Wumpus
 * @version 2.0
 */

package jeuSocket;

public abstract class ObjetDuMonde
{
	protected int portee; //la portee a laquelle l'objet est détectable
	protected char symbole;
	protected String message;

	public ObjetDuMonde(int portee, char symbole, String message)
	{
		this.portee = portee;
		this.symbole = symbole;
		this.message = message;
	}

	public int getPortee() { return portee; }
	public char getSymbole() { return symbole; }
	public String getMessage() { return message; }

	public String interaction(Joueur j) { return ""; }
}
