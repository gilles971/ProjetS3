/** Voici la classe abstraite Ia
 * Elle sert de squelette pour toutes les IA
 */

package IA;

import java.util.ArrayList;

public interface Ia {

  /** la méthode de base qui donne en fonction de la situation, le coup à jouer
   * @param x la position en x actuelle
   * @param y la position en y actuelle
   * @param message l'ensemble du message découpé en mots
   * @return l'ordre à donner
   */
  public String jouer(int x, int y, ArrayList<String> message);
}
