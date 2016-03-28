/** Voici la classe abstraite Ia
 * Elle sert de squelette pour toutes les IA
 */

package IA;

import java.util.ArrayList;

public abstract class Ia {
  /** nombre de flèches */
  private int arrows;
  /** limites en x */
  private int boundX;
  /** limites en y */
  private int boundY;

  /** le constructeur
   * @param bX la limite en X du plateau
   * @param bY la limite en Y du plateau
   * @param arr le nombre de flèches pour la partie en cours
   */
  public Ia(int bX, int bY, int arr) {
    arrows = arr;
    boundX = bX;
    boundY = bY;
  }

  /** la méthode de base qui donne en fonction de la situation, le coup à jouer
   * @param x la position en x actuelle
   * @param y la position en y actuelle
   * @param message l'ensemble du message découpé en mots
   * @return l'ordre à donner
   */
  public String jouer(int x, int y, ArrayList<String> message) {
    return "h";
  }
}
