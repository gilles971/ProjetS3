/**Documentation de la classe Sac
 * 
 * @author Groupe de projet de D refactorisée par l'équipe IA Wumpus
 * @version 2.0
 */

package jeuSocket;

public class Sac extends ObjetDuMonde
{
        public Sac()
        {
                super(0, ' ', "");
        }

        
        public void interaction(Joueur j) {
                if (j.alive()) j.addScore(100);
        }
}
