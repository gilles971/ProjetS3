package project;

import java.util.ArrayList;
import java.util.Collections;

public class IA {
private CaseIA[][] labyrinth;
private int currentX;
private int currentY;
private int arrows;
private int boundX;
private int boundY;

public IAa(int boundX, int boundY, int arrows) {
	
	labyrinth = new boundX[CaseIA][boundY];
	
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			labyrinth[i][j] = new CaseIA();
		}
	}
	this.arrows = arrows;
	this.boundX = boundX;
	this.boundY = boundY;
}

/**
*Envoie la CaseIA vers laquelle le déplacement est le moins dangereux
*/
public CaseIA deplacementLePlusViable() {
	ArrayList<CaseIA> listCellAdja = getCelluleAdjacente();
	ArrayList<Integer> listInt = new ArrayList<Integer>();
	int compteur;
	//Pour toutes les CaseIAs adjacentes à l'actuelle
	for(CaseIA c:listCellAdja) {
		compteur = 0;
		
		if(c.getVisite()){
			compteur += 1.5;	//Si on l'a déjà visité, on évite d'y aller
		}else{
			if(c.getDangersPuit()) {
				compteur++;		//Si il y a un dangers de puit
			}
			if (c.getDangersWumpus()) {
				compteur++;		//Si il y a un dangers de wumpus
			}
			if(c.getWumpus()) {
				compteur +=10;	//Si il y a wumpus, on n'y va pas
			}
			if (c.getPuit()) {
				compteur += 10;	//Si il y a un puit, on y va pas
			}
		}
		listInt.add(compteur);
	}
	//On cherche le plus petit nombre trouvé
	int minimum = Collections.min(listInt);

	//On cherche quelles CaseIAs ont reçu ce score
	for(int i=0; i<listInt.size(); i++){
		if(listInt.get(i) != minimum){
			listCellAdja.remove(i);
		}
	}

	//On retourne une des CaseIAs qui a eu le plus petit score
	return listCellAdja.get((int)(Math.random()*listCellAdja.size()));
}

/**
*Envoie la CaseIA vers laquelle le déplacement est le moins dangereux et qui se rapproche du wumpus
*/
public CaseIA deplacementChasseur(ArrayList<Integer> posWumpus) {
	
	//Si la CaseIA en bas se rapproche
	if(Math.abs(posWumpus.get(0)-(currentX)) + Math.abs(posWumpus.get(1)-(currentY)) >  
		Math.abs(posWumpus.get(0)-(currentX-1)) + Math.abs(posWumpus.get(1)-(currentY))){
		return labyrinth[currentX-1][currentY];
	}
	//Si la CaseIA en haut se rapproche
	if(Math.abs(posWumpus.get(0)-(currentX)) + Math.abs(posWumpus.get(1)-(currentY)) >  
		Math.abs(posWumpus.get(0)-(currentX+1)) + Math.abs(posWumpus.get(1)-(currentY))){
		return labyrinth[currentX+1][currentY];
	}
	//Si la CaseIA à gauche se rapproche
	if(Math.abs(posWumpus.get(0)-(currentX)) + Math.abs(posWumpus.get(1)-(currentY)) >  
		Math.abs(posWumpus.get(0)-(currentX)) + Math.abs(posWumpus.get(1)-(currentY-1))){
		return labyrinth[currentX][currentY-1];
	}
	//Si la CaseIA à droite se rapproche
	if(Math.abs(posWumpus.get(0)-(currentX)) + Math.abs(posWumpus.get(1)-(currentY)) >  
		Math.abs(posWumpus.get(0)-(currentX)) + Math.abs(posWumpus.get(1)-(currentY+1))){
		return labyrinth[currentX-1][currentY+1];
	}
	return null;
}

/**
*Envoie la liste des CaseIAs adjacentes
*/
public ArrayList<CaseIA> getCelluleAdjacente() {
	ArrayList<CaseIA> list = new ArrayList<CaseIA>();
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
	if(message.contains("Odeur")){
		labyrinth[currentX][currentY].setOdeurInfame(true);
		for (int i=0; i<boundX; i++) {
			for (int j=0; j<boundY; j++) {
				//Si la CaseIA est à une distance supérieur à 2, 
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
				//Si la CaseIA est à une distance inferieur ou égale à 2, 
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
				//Si la CaseIA est à une distance supérieur à 1, 
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
				//Si la CaseIA est à une distance inferieur ou égale à 1, 
				//il n'y a pas de danger
				if(	Math.abs(i -currentX)+Math.abs(j-currentY) <= 1 ){
					labyrinth[i][j].setDangersPuit(false);
				}

			}
		}
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
	CaseIA wumpusAcote=null;

	//Met à jour le plateau en fonction du message
	this.miseAJour(message);

	//On cherche si un wumpus a été trouvé
	wumpusTrouve = this.presenceWumpus();
	
	if(wumpusTrouve){//Si un wumpus à été trouvé
	
		//On cherche si il y a un wumpus à cote
		wumpusAcote = this.wumpusProche();
		if(wumpusAcote != null){//Si un wumpus est à cote
			if (currentY-1 >= 0) {
				if(labyrinth[x][y-1] == wumpusAcote){	//CaseIA de gauche
					System.out.println("IA joue: t o");
					return "t o";
				}
			}
			if (currentY+1 <= 4) {
				if(labyrinth[x][y+1] == wumpusAcote){	//CaseIA de droite
					System.out.println("IA joue: t e");
					return "t e";
				}
			}
			if (currentX-1 >= 0) {
				if(labyrinth[x-1][y] == wumpusAcote){	//CaseIA du bas
					System.out.println("IA joue: t s");
					return "t s";
				}
			}
			if (currentX+1 <= 4) {
				if(labyrinth[x+1][y] == wumpusAcote){ //CaseIA du haut
					System.out.println("IA joue: t n");
					return "t n";
				}
			}
			return "oups";
		}else{//Si le wumpus n'est pas à cote
			//On cherche la position du wumpus et on cherche un deplacement qui se rapproche de lui
			ArrayList<Integer> posWumpus = positionWumpus(); 
			CaseIA CaseIADirection = this.deplacementChasseur(posWumpus);
			if(CaseIADirection != null){
				return messageAEnvoyer(CaseIADirection);
			}else{
				return "oups";
			}
		}
		
	
	}else{//Si le wumpus n'a pas été trouvé	
		//Cherche la CaseIA avec le deplacement le plus viable
		CaseIA CaseIADirection = this.deplacementLePlusViable();
		
		if(CaseIADirection != null){
			return messageAEnvoyer(CaseIADirection);
		}else{
			return "oups";
		}
	}
}

/**
 * Genere le message à partir de la CaseIA
 */
public String messageAEnvoyer(CaseIA CaseIADirection){
	
	if (currentY-1 >= 0) {
		if(labyrinth[currentX][currentY-1] == CaseIADirection){	//CaseIA de gauche
			System.out.println("IA joue: d o");
			return "d o";
		}
	}
	if (currentY+1 <= 4) {
		if(labyrinth[currentX][currentY+1] == CaseIADirection){	//CaseIA de droite
			System.out.println("IA joue: d e");
			return "d e";
		}
	}
	if (currentX-1 >= 0) {
		if(labyrinth[currentX-1][currentY] == CaseIADirection){	//CaseIA du bas
			System.out.println("IA joue: d s");
			return "d s";
		}
	}
	if (currentX+1 <= 4) {
		if(labyrinth[currentX+1][currentY] == CaseIADirection){ //CaseIA du haut
			System.out.println("IA joue: d n");
			return "d n";
		}
	}

	return "Oups";
	
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
 * Renvoie la CaseIA où est présent le wumpus, null si il n'est pas à cote 
 */
public CaseIA wumpusProche(){
	
	ArrayList<CaseIA> listCellAdja = getCelluleAdjacente();
	
	for(CaseIA c : listCellAdja){
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