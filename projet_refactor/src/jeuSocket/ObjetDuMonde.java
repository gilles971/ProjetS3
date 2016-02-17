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
	//dans l'idéal avec du fonctionnel/truc du genre ou pourrait lier les objets a leur intercation avec le joueur ex: mortel, tp, Points

	public ObjetDuMonde(int portee, char symbole, String message)
	{
		this.portee = portee;
		this.symbole = symbole;
		this.message = message;
	}

	public int getPortee() { return portee; }
	public int getSymbole() { return sybole; }
	public int getMessage() { return message; }
	
	public void interaction(Joueur j) {}
}
