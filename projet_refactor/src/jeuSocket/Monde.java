/**Documentation de la classe Monde
 *
 * @author Groupe de projet D refactorisée par l'équipe IA Wumpus
 * @version 2.0
 */

package jeuSocket;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;

public class Monde {

	private static String mondeDeBase = "Wumpus:1 Trou:1";
	private int taille;
	private ArrayList<ObjetDuMonde> contenu;
	private Case[][] plateau;

	public Monde(int taille) {	// Modification car erreur dans l'ordre des objets
		this.contenu = new ArrayList<ObjetDuMonde>();
		this.taille = taille;
		this.plateau = new Case[taille][taille];

		for( int i = 0 ; i < taille ; i++ )
			for ( int j= 0 ; j < taille ; j++ )
				this.plateau[i][j] = new Case();
	}

	public void placerObjets() {
		this.placerObjets(mondeDeBase);
	}

	public void placerObjets(String s) {
		// Class.forName("Trou").getConstructor().newInstance(); +-  https://docs.oracle.com/javase/7/docs/api/
		String[] objets = s.split("\\s+");
		for (int i=0; i<objets.length; i++) {
			String[] objQuantite = objets[i].split(":");
			System.out.println(objQuantite[0]);
			try {
				for (int j=0; j<Integer.valueOf(objQuantite[1]); j++) {
					contenu.add((ObjetDuMonde) Class.forName("jeuSocket."+objQuantite[0]).getConstructor().newInstance());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		int randX;
		int randY;
		for (ObjetDuMonde o : contenu) {
			do {
				randX = (int) (Math.random()*taille);
				randY = (int) (Math.random()*taille);
			} while (plateau[randX][randY].getObjet() != null);
			plateau[randX][randY].setObjet(o);
			setIndice(o, randX, randY);
			System.out.println("ok1 ");
		}
		System.out.println("ok2 ");
	}

	public void setIndice(ObjetDuMonde o, int x, int y) {
		plateau[x][y].getIndices().add(o);
		for (int i=1; i<plateau[x][y].getObjet().getPortee()+1; i++) {
			int currentX = x-i;
			int currentY = y;

			for(int j=0; j<i; j++) {
				if (currentX>-1 && currentY>-1) plateau[currentX][currentY].getIndices().add(o);
				currentX++;
				currentY--;
			}

			for(int j=0; j<i; j++) {
				if (currentY>-1 && currentX<taille) plateau[currentX][currentY].getIndices().add(o);
				currentX++;
				currentY++;
			}

			for(int j=0; j<i; j++) {
				if (currentX<taille && currentY<taille) plateau[currentX][currentY].getIndices().add(o);
				currentX--;
				currentY++;
			}

			for(int j=0; j<i; j++) {
				if (currentY<taille && currentX>-1) plateau[currentX][currentY].getIndices().add(o);
				currentX--;
				currentY--;
			}

		}
	}

	public void remove(int x, int y) {
		ObjetDuMonde o = plateau[x][y].getObjet();
		for (int i=1; i<o.getPortee()+1; i++) {
			int currentX = x-i;
			int currentY = y;

			for(int j=0; j<i; j++) {
				if (currentX>-1 && currentY>-1) plateau[currentX][currentY].getIndices().remove(o);
				currentX++;
				currentY--;
			}

			for(int j=0; j<i; j++) {
				if (currentY>-1 && currentX<taille) plateau[currentX][currentY].getIndices().remove(o);
				currentX++;
				currentY++;
			}

			for(int j=0; j<i; j++) {
				if (currentX<taille && currentY<taille) plateau[currentX][currentY].getIndices().remove(o);
				currentX--;
				currentY++;
			}

			for(int j=0; j<i; j++) {
				if (currentY<taille && currentX>-1) plateau[currentX][currentY].getIndices().remove(o);
				currentX--;
				currentY--;
			}
		}
		plateau[x][y].setObjet(null);
		plateau[x][y].getIndices().remove(o);
	}

	public void placerJoueur(Joueur player, int fleches) {
		int randX;
		int randY;
		do  {
			randX = (int) (Math.random()*taille);
			randY = (int) (Math.random()*taille);
		} while (plateau[randX][randY].getObjet() != null);

		Sortie exit = new Sortie();
		plateau[randX][randY].setObjet(exit);
		player.setX(randX);
		player.setY(randY);
		setIndice(exit, randX, randY);
		plateau[randX][randY].setJoueur(player);
		plateau[randX][randY].visiter();
	}

	public Case[][] getPlateau() { return plateau; }

	public int getTaille() { return taille; }

	public ArrayList<ObjetDuMonde> getContenu() { return contenu; }
}
