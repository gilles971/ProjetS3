/**Documentation de la classe Joueur
 *
 * @author Groupe de projet de D refactorisée par l'équipe IA Wumpus
 * @version 2.0
 */

package jeuSocket;

public class Joueur {

	private String pseudo;
	private int nbFleches;
	private int score;
	private boolean alive;
	private int x, y;

	public Joueur(String sonPseudo) {
		this.pseudo = sonPseudo;
		score = 0;
		alive = true;
	}

	public String getPseudo() { return pseudo; }

	public int getNbFleches() { return nbFleches; }
	public void setNbFleches() { setNbFleches(1); }
	public void setNbFleches(int nbFleches) { this.nbFleches = nbFleches; }
	public void tirer() { nbFleches--; }

	public int getScore() { return score; }
	public void addScore(int value) { score += value; }

	public boolean alive() { return alive; }
	public void kill() { alive = false; }

	public int getX() { return x; }
	public void setX(int x) { this.x = x; }

	public int getY() { return y; }
	public void setY(int y) { this.y = y; }

	public String toString() {
		return pseudo+" dispose d'encore "+nbFleches+" fleches et de "+score+" points";
	}
}
