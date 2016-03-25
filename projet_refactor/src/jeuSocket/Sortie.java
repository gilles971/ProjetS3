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
                super(0, 'S', "Vous pouvez sortir avec 's'");
        }

        public String interaction(Joueur j) {
                //if (j.alive()) j.addScore(50);
                return "c'est la sortie";
        }

}
