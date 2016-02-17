/**Documentation de la classe Sortie
 * 
 * @author Équipe IA Wumpus
 * @version 1.0
 */

package jeuSocket;

public class Sortie extends ObjetDuMonde
{
        public Sortie()
        {
                super(0, "S", "Vous pouvez sortir avec 's'");
        }
        
        public void interaction(Joueur j) {
                if (j.alive()) j.addScore(50);
        }

}
