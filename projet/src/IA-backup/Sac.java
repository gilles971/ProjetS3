/**Documentation de la classe Sac
 * 
 * @author Groupe de projet de D
 * @version 1.0
 */

package jeu;


public class Sac extends ObjetDuMonde
{
        // --------------
        // Les attributs |
        // --------------

        /**
         * Permet de savoir si le sac a ete ramasse ou non. 
         */
        private boolean ramasse;




        // ----------------
        // Le constructeur |
        // ----------------

        /**
         * Le constructeur de la classe SacDOr
         * Un sac d'or n'est pas mortel et n'est pas indique.
         */
        public Sac()
        {
                super(false, false);
                this.ramasse = false;
        }




        // -------------
        // Les methodes |
        // -------------

        public boolean getRamasse()
        {
                return ramasse;
        }

        public void setRamasse(boolean ramasse)
        {
                this.ramasse = ramasse;
        }
}
