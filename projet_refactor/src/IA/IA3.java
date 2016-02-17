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
private int well;

public IA3(int boundX, int boundY, int arrows, int well) {
	
	labyrinth = new Case[boundX][boundY];
	
	int compteur=0;
	
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			labyrinth[i][j] = new Case(compteur);
			compteur++;
		}
	}
	this.arrows = arrows;
	this.boundX = boundX;
	this.boundY = boundY;
	this.well = well;
}

/**
*Envoie la case vers laquelle le déplacement est le moins dangereux
*/
public Case deplacementLePlusViable() {
	ArrayList<Case> listCellAdja = getCelluleAdjacente();
	ArrayList<Double> listInt = new ArrayList<Double>();
	Double compteur;
	//Pour toutes les cases adjacentes à l'actuelle
	for(Case c:listCellAdja) {
		compteur = 0.0;
		
		if(c.getVisite()){
			compteur += 1.0;	//Si on l'a déjà visité, on évite d'y aller
		}else{
			if(c.getDangersPuit()) {
				compteur += 1.5;		//Si il y a un dangers de puit
			}
			if (c.getDangersWumpus()) {
				compteur += 1.0;		//Si il y a un dangers de wumpus
			}
			if(c.getWumpus()) {
				compteur +=10.0;	//Si il y a wumpus, on n'y va pas
			}
			if (c.getPuit()) {
				compteur += 10.0;	//Si il y a un puit, on y va pas
			}
		}
		listInt.add(compteur);
	}
	//On cherche le plus petit nombre trouvé
	Double minimum = Collections.min(listInt);

	
	//On cherche quelles cases ont reçu ce score
	for(int i=listCellAdja.size()-1; i>=0; i--){
		if(listInt.get(i) != minimum){
			listCellAdja.remove(i);
		}
	}
	
	
	//On retourne une des cases qui a eu le plus petit score
	return listCellAdja.get((int)(Math.random()*listCellAdja.size()));
}

/**
*Envoie la case vers laquelle le déplacement est le moins dangereux et qui se rapproche du wumpus
*/
public Case deplacementChasseur(ArrayList<Integer> posWumpus) {
	
	//Si le wumpus est à droite
	if(currentX < posWumpus.get(0)){
			return labyrinth[currentX+1][currentY];
	}else{
	//Si le wumpus est à gauche	
		if(currentX > posWumpus.get(0)){
			return labyrinth[currentX-1][currentY];	
		}
	}
	//Si le wumpus est en bas
	if(currentY < posWumpus.get(1)){
			return labyrinth[currentX][currentY+1];
	}else{
	//Si le wumpus est en haut	
		if(currentY < posWumpus.get(1)){
			return labyrinth[currentX][currentY-1];
		}
	}
	return null;
}

/**
*Envoie la liste des cases adjacentes
*/
public ArrayList<Case> getCelluleAdjacente() {
	ArrayList<Case> list = new ArrayList<Case>();
	//On envoie les cases que si elles ne sont pas à l'exterieur du labyrinthe
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
	//labyrinth[currentX][currentY].incrNbrVisite(0.75);
	
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

	boolean allWells = false;

	//Si on sent un courant d'air
	if(message.contains("courant")){
		labyrinth[currentX][currentY].setCourantDair(true);

		//Si il y a un seul puit
		if(this.well == 1){
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
	
	//Si on a 4 dangers autour d'une case, elle a un puit
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			//Si la case est à une distance supérieur à 1, 
			//il n'y a pas de danger
			if(	labyrinth[i+1][j].getCourantDair() == true 
				&& labyrinth[i-1][j].getCourantDair() == true
				&& labyrinth[i][j+1].getCourantDair() == true
				&& labyrinth[i][j-1].getCourantDair() == true){labyrinth[i][j].setPuit(true);}
		}
	}


	//Si on a raté, on perd une flèche
	if(message.contains("rate")) {
		this.arrows --;
	}
	
	
	//On va regarder le nombre de dangersPuit et dangersWumpus
	int compteurPuit=0;
	ArrayList<Integer> listPuit = new ArrayList<Integer>();

	//int xPuit=-1;
	//int yPuit=-1;
	int compteurWumpus=0;
	int xWumpus=-1;
	int yWumpus=-1;
	
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			//On incrémente le nombre de dangers puit
			if(labyrinth[i][j].getDangersPuit()){
				compteurPuit++;
				//xPuit=i;
				//yPuit=j;
				listPuit.add(i);
				listPuit.add(j);
			}
			//On incrémente le nombre de dangers wumpus
			if(labyrinth[i][j].getDangersWumpus()){
				compteurWumpus++;
				xWumpus=i;
				yWumpus=j;
			}
		}
	}
	
	//Si il y a le nombre de dangers égal au nombre de puit, on les met en tant que dangers
	if(compteurPuit == this.well){
		for(int l=0; l<this.well*2; l=l+2){
			labyrinth[l][l+1].setPuit(true);
		}	
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

	//On met à jour notre position
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
				if(labyrinth[x][y-1].getId() == wumpusAcote.getId()){	//Case du haut
					return "t n";
				}
			}
			if (currentY+1 <= 4) {
				if(labyrinth[x][y+1].getId() == wumpusAcote.getId()){	//Case du bas
					return "t s";
				}
			}
			if (currentX-1 >= 0) {
				if(labyrinth[x-1][y].getId() == wumpusAcote.getId()){	//Case de gauche
					return "t o";
				}
			}
			if (currentX+1 <= 4) {
				if(labyrinth[x+1][y].getId() == wumpusAcote.getId()){ //Case de droite
					return "t e";
				}
			}
			return "oups";
		}else{//Si le wumpus n'est pas à cote
			//On cherche la position du wumpus et on cherche un deplacement qui se rapproche de lui
			ArrayList<Integer> posWumpus = positionWumpus(); 
			Case caseDirection = this.deplacementChasseur(posWumpus);
			if(caseDirection != null){
				return messageAEnvoyer(caseDirection);
			}else{
				return "oups";
			}
		}
		
	
	}else{//Si le wumpus n'a pas été trouvé	
		//Cherche la case avec le deplacement le plus viable
		Case caseDirection = this.deplacementLePlusViable();
		
		if(caseDirection != null){
			return messageAEnvoyer(caseDirection);
		}else{
			return "oups";
		}
	}
}

/**
 * Genere le message à partir de la case
 */
public String messageAEnvoyer(Case caseDirection){
	
	if (currentY-1 >= 0) {
		if(labyrinth[currentX][currentY-1].getId() == caseDirection.getId()){	//Case du haut
			return "d n";
		}
	}
	if (currentY+1 <= 4) {
		if(labyrinth[currentX][currentY+1].getId() == caseDirection.getId()){	//Case du bas
			return "d s";
		}
	}
	if (currentX-1 >= 0) {
		if(labyrinth[currentX-1][currentY].getId() == caseDirection.getId()){	//Case de gauche
			return "d o";
		}
	}
	if (currentX+1 <= 4) {
		if(labyrinth[currentX+1][currentY].getId() == caseDirection.getId()){ //Case de droite
			return "d e";
		}
	}

	return "Oups";
	
}


/**
 * Renvoie vrai si on a trouvé le wumpus, faux sinon
 */
public boolean presenceWumpus(){
	
	boolean test=false;
	
	//Parcours le labyrinthe
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			//Si on a trouvé le wumpus
			if(labyrinth[i][j].getWumpus()){
				//On renvoie vrai
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
	
	//Parcours le labyrinthe
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			//Si on a trouvé le wumpus
			if(labyrinth[i][j].getWumpus()){
				//On renvoie sa position
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
	
	//On récupère les cases adjacentes
	ArrayList<Case> listCellAdja = getCelluleAdjacente();
	
	for(Case c : listCellAdja){
		//si le wumpus est à coter
		if(c.getWumpus()){
			return c;
		}
	}
	return null;
}

// ---------------------------------------------------------------------- //

//Classes de tests

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
			for (int i=0; i<boundY; i++) {
				System.out.print("\n");
				for (int j=0; j<boundX; j++) {
					System.out.print(this.labyrinth[j][i].getDangersPuit()+ "\t");
				}
			}
		}

/**
*Fonction de test pour afficher les dangers de Wumpus		
*/
		public void afficheLabWumpus(){
			for (int i=0; i<boundY; i++) {
				System.out.print("\n");
				for (int j=0; j<boundX; j++) {
					System.out.print(this.labyrinth[j][i].getDangersWumpus()+ "\t");
				}
			}
		}
		
}