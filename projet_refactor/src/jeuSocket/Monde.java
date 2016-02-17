/**Documentation de la classe Monde
 * 
 * @author Groupe de projet de D refactorisée par l'équipe IA Wumpus
 * @version 2.0
 */

package jeuSocket;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;

public class Monde {
	
	private static String mondeDeBase = "jeuSocket.Wumpus.class:1 jeuSocket.Trou.class:1";
	private int taille;
	private ArrayList<ObjetDuMonde> contenu;
	private Case[][] plateau;

	public Monde(int taille) {	// Modification car erreur dans l'ordre des objets
		this.taille = taille;
		this.contenu = new ArrayList<ObjetDuMonde>();
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
			System.out.println(objQuantite[0])
			try {
				for (int j=0; j<Integer.valueOf(objQuantite[1]); j++) {
					contenu.add((ObjetDuMonde) Class.forName(objQuantite[0]).getConstructor().newInstance());
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
		
		int randX = (int) Math.random()*taille;
		int randY = (int) Math.random()*taille;
		for (ObjetDuMonde o : contenu) {
			while (plateau[randX][randY].getObjet() != null) {
				randX = (int) Math.random()*taille;
				randY = (int) Math.random()*taille;
			}
			plateau[randX][randY].setObjet(o);
			setIndice(o, randX, randY);
		}
	}
	
	public void setIndice(ObjetDuMonde o, int x, int y) {
		plateau[x][y].getIndices().add(o);
		for (int i=1; i<plateau[x][y].getObjet().getPortee()+1; i++) {
			int currentX = x-i;
			int currentY = y;
			
			for(int j=0; j<i; j++) {
				plateau[currentX][currentY].getIndices().add(o);
				currentX++;
				currentY--;
			}
			
			for(int j=0; j<i; j++) {
				plateau[currentX][currentY].getIndices().add(o);
				currentX++;
				currentY++;
			}
			
			for(int j=0; j<i; j++) {
				plateau[currentX][currentY].getIndices().add(o);
				currentX--;
				currentY++;
			}
			
			for(int j=0; j<i; j++) {
				plateau[currentX][currentY].getIndices().add(o);
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
				plateau[currentX][currentY].getIndices().remove(o);
				currentX++;
				currentY--;
			}
			
			for(int j=0; j<i; j++) {
				plateau[currentX][currentY].getIndices().remove(o);
				currentX++;
				currentY++;
			}
			
			for(int j=0; j<i; j++) {
				plateau[currentX][currentY].getIndices().remove(o);
				currentX--;
				currentY++;
			}
			
			for(int j=0; j<i; j++) {
				plateau[currentX][currentY].getIndices().remove(o);
				currentX--;
				currentY--;
			}
		}
		plateau[x][y].setObjet(null);
		plateau[x][y].getIndices().remove(o);
	}
	
	public void placerJoueur(Joueur player, int fleches) {
		int randX = (int) Math.random()*taille;
		int randY = (int) Math.random()*taille;
		while (plateau[randX][randY].getObjet() != null) {
			randX = (int) Math.random()*taille;
			randY = (int) Math.random()*taille;
		}
		
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
