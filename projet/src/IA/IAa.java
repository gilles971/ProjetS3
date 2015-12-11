import java.util.ArrayList;
import java.util.Collections;

public class IAa {
private Case[][] labyrinth;
private int currentX;
private int currentY;
private int arrows;
private int boundX;
private int boundY;

public IAa(int boundX, int boundY, int arrows) {
	
	labyrinth = new Case[boundX][boundY];
	
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			labyrinth[i][j] = new Case();
		}
	}
	this.arrows = arrows;
	this.boundX = boundX;
	this.boundY = boundY;
}

/**
*Envoie la case vers laquelle le déplacement est le moins dangereux
*/
public Case deplacementLePlusViable() {
	ArrayList<Case> listCellAdja = getCelluleAdjacente();
	ArrayList<Integer> listInt = new ArrayList<Integer>();
	int compteur;
	for(Case c:listCellAdja) {
		compteur = 0;
		if(!c.getSafe()) {
			if(c.getDangersPuit()) {
				compteur++;
			}
			if (c.getDangersWumpus()) {
				compteur++;
			}
			if(c.getWumpus()) {
			}
			if (c.getPuit()) {
				compteur += 10;
			}
		}
		listInt.add(compteur);
	}
	int minimum = Collections.min(listInt);

	for(int i=0; i<listInt.size(); i++){
		if(listInt.get(i) != minimum){
			listCellAdja.remove(i);
		}
	}

	return listCellAdja.get((int)(Math.random()*listCellAdja.size()));
}


/**
*Envoie la liste des cases adjacentes
*/
public ArrayList<Case> getCelluleAdjacente() {
	ArrayList<Case> list = new ArrayList<Case>();
	if (currentY-1 >= 0) {
		list.add(labyrinth[currentX][currentY-1]);
	}
	if (currentY+1 <= 4) {
		list.add(labyrinth[currentX][currentY+1]);
	}
	if (currentX-1 >= 0) {
		list.add(labyrinth[currentX-1][currentY]);
	}
	if (currentX+1 <= 4) {
		list.add(labyrinth[currentX+1][currentY]);
	}
	return list;
}

/**
*En fonction des données reçues, on met à jour la variable labyrinth
*/
public void miseAJour(ArrayList<String> message){

	labyrinth[currentX][currentY].setSafe(true);
	labyrinth[currentX][currentY].setVisite(true);

	//Si on sent une odeur infame
	if(message.contains("Odeur")){
		labyrinth[currentX][currentY].setOdeurInfame(true);
		for (int i=0; i<boundX; i++) {
			for (int j=0; j<boundY; j++) {
				//Si la case est à une distance supérieur à 2, 
				//il n'y a pas de danger
				if(	Math.abs(i -currentX)+Math.abs(j-currentY) > 2 ){
					labyrinth[i][j].setDangersWumpus(false);
				}

			}
		}
	//Si on ne sent pas d'odeur
	}else{
		for (int i=0; i<boundX; i++) {
			for (int j=0; j<boundY; j++) {
				//Si la case est à une distance inferieur ou égale à 2, 
				//il n'y a pas de danger
				if(	Math.abs(i -currentX)+Math.abs(j-currentY) <= 2 ){
					labyrinth[i][j].setDangersWumpus(false);
				}

			}
		}
	}	

	//Si on sent un courant d'air
	if(message.contains("Courant")){
		labyrinth[currentX][currentY].setCourantDair(true);
		for (int i=0; i<boundX; i++) {
			for (int j=0; j<boundY; j++) {
				//Si la case est à une distance supérieur à 1, 
				//il n'y a pas de danger
				if(	Math.abs(i -currentX)+Math.abs(j-currentY) > 1 ){
					labyrinth[i][j].setDangersPuit(false);
				}

			}
		}
	//Si on ne sent pas de courant d'air
	}else{
		for (int i=0; i<boundX; i++) {
			for (int j=0; j<boundY; j++) {
				//Si la case est à une distance inferieur ou égale à 1, 
				//il n'y a pas de danger
				if(	Math.abs(i -currentX)+Math.abs(j-currentY) <= 1 ){
					labyrinth[i][j].setDangersPuit(false);
				}

			}
		}
	}	

}

/**
*Méthode appelé pour jouer
*/
public String jouer(int x, int y, ArrayList<String> message){

	this.currentX=x;
	this.currentY=y;

	//Met à jour le plateau en fonction du message
	this.miseAJour(message);

	//Cherche la case avec le deplacement le plus viable
	Case caseDirection = this.deplacementLePlusViable();
	
	if (currentY-1 >= 0) {
		if(labyrinth[x][y-1] == caseDirection){	//Case de gauche
			return "d o";
		}
	}
	if (currentY+1 <= 4) {
		if(labyrinth[x][y+1] == caseDirection){	//Case de droite
			return "d e";
		}
	}
	if (currentX-1 >= 0) {
		if(labyrinth[x-1][y] == caseDirection){	//Case du bas
			return "d s";
		}
	}
	if (currentX+1 <= 4) {
		if(labyrinth[x+1][y] == caseDirection){ //Case du haut
			return "d n";
		}
	}
	

	return "Oups";
}

// ---------------------------------------------------------------------- //

/**
 * Retourne currentX
 * @return currentX
 */
		public int getCurrentX() {
			return currentX;
		}

/**
*Retourne currentY 
* @return
*/
		public int getCurrentY() {
			return currentY;
		}

/**
 *Affiche le tableau		
 */
		public void afficheLabPuit(){
			for (int i=0; i<boundX; i++) {
				System.out.print("\n");
				for (int j=0; j<boundY; j++) {
					System.out.print(this.labyrinth[i][j].getDangersPuit()+ " ");
				}
			}
		}
		
		public void afficheLabWumpus(){
			for (int i=0; i<boundX; i++) {
				System.out.print("\n");
				for (int j=0; j<boundY; j++) {
					System.out.print(this.labyrinth[i][j].getDangersWumpus()+ " ");
				}
			}
		}