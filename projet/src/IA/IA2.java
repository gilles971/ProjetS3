package IA;

import java.util.ArrayList;
import java.util.Collections;

public class IA3 {
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
public Case deplacement() {
	Random rand = new Random();
	int aleatoire = rand.nextInt(4)+1;
	int aleatoire2;

	switch aleatoire{
		case 1: return "d e";


		case 2: return "d o";


		case 3: return "d n";


		case 4: return "d s";
	}	
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

	labyrinth[currentX][currentY].setVisite(true);

	//Si on sent une odeur infame
	if(message.contains("odeur")){
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
	if(message.contains("courant")){
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
	
	//Si on a raté, on perd une flèche
	if(message.contains("rate")) {
		this.arrows --;
	}
	
	
	//On va regarder le nombre de dangersPuit et dangersWumpus
	int compteurPuit=0;
	int xPuit=-1;
	int yPuit=-1;
	int compteurWumpus=0;
	int xWumpus=-1;
	int yWumpus=-1;
	
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			if(labyrinth[i][j].getDangersPuit()){
				compteurPuit++;
				xPuit=i;
				yPuit=j;
			}
			if(labyrinth[i][j].getDangersWumpus()){
				compteurWumpus++;
				xWumpus=i;
				yWumpus=j;
			}
		}
	}
	
	//Si il n'y a qu'un seul dangers, alors le puit est à cette position
	if(compteurPuit == 1){
		labyrinth[xPuit][yPuit].setPuit(true);
	}
	
	//Si il n'y a qu'un seul dangers, alors le puit est à cette position
	if(compteurWumpus == 1){
		labyrinth[xWumpus][yWumpus].setWumpus(true);
	}

}

/**
*Méthode appelé pour jouer
*/
public String jouer(int x, int y, ArrayList<String> message){

	this.currentX=x;
	this.currentY=y;
	boolean wumpusTrouve=false;
	Case wumpusAcote=null;

	//Met à jour le plateau en fonction du message
	this.miseAJour(message);

	//On cherche si un wumpus a été trouvé
	wumpusTrouve = this.presenceWumpus();
	
	if(wumpusTrouve){//Si un wumpus à été trouvé
	
		//On cherche si il y a un wumpus à cote
		wumpusAcote = this.wumpusProche();
		if(wumpusAcote != null){//Si un wumpus est à cote
			if (currentY-1 >= 0) {
				if(labyrinth[x][y-1] == wumpusAcote){	//Case de gauche
					return "t o";
				}
			}
			if (currentY+1 <= 4) {
				if(labyrinth[x][y+1] == wumpusAcote){	//Case de droite
					return "t e";
				}
			}
			if (currentX-1 >= 0) {
				if(labyrinth[x-1][y] == wumpusAcote){	//Case du bas
					return "t s";
				}
			}
			if (currentX+1 <= 4) {
				if(labyrinth[x+1][y] == wumpusAcote){ //Case du haut
					return "t n";
				}
			}
			return "oups";
		}else{//Si le wumpus n'est pas à cote
			//On cherche la position du wumpus et on cherche un deplacement qui se rapproche de lui
			ArrayList<Integer> posWumpus = positionWumpus(); 
			Case move = this.deplacement();
			if(move != null){
				return move;
			}else{
				return "oups";
			}
		}
		
	
	}else{//Si le wumpus n'a pas été trouvé	
		//Cherche la case avec le deplacement le plus viable
		Case move = this.deplacement();
		
		if(move != null){
			return move;
		}else{
			return "oups";
		}
	}
}


/**
 * Renvoie vrai si on a trouvé le wumpus, faux sinon
 */
public boolean presenceWumpus(){
	
	boolean test=false;
	
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			if(labyrinth[i][j].getWumpus()){
				test = true;
			}
		}
	}
	
	return test;
}

/**
 * Renvoie la position du wumpus
 */
public ArrayList<Integer> positionWumpus(){
	
	ArrayList<Integer> listPosition = new ArrayList<Integer>();
	
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			if(labyrinth[i][j].getWumpus()){
				listPosition.add(i);
				listPosition.add(j);
			}
		}
	}

	return listPosition;
}

/**
 * Renvoie la case où est présent le wumpus, null si il n'est pas à cote 
 */
public Case wumpusProche(){
	
	ArrayList<Case> listCellAdja = getCelluleAdjacente();
	
	for(Case c : listCellAdja){
		if(c.getWumpus()){
			return c;
		}
	}
	return null;
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
 *Fonction de test pour afficher les dangers de Puit		
 */
		public void afficheLabPuit(){
			for (int i=0; i<boundX; i++) {
				System.out.print("\n");
				for (int j=0; j<boundY; j++) {
					System.out.print(this.labyrinth[i][j].getDangersPuit()+ " ");
				}
			}
		}

/**
*Fonction de test pour afficher les dangers de Wumpus		
*/
		public void afficheLabWumpus(){
			for (int i=0; i<boundX; i++) {
				System.out.print("\n");
				for (int j=0; j<boundY; j++) {
					System.out.print(this.labyrinth[i][j].getDangersWumpus()+ " ");
				}
			}
		}
		
}