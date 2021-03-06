package IA;

import java.util.ArrayList;
import java.util.Collections;

public class IaPrudente implements Ia {
    private int well;
    private int currentX;
    private int currentY;
    private int arrows;
    private int boundX;
    private int boundY;
    private int turn;
    private int totalTurn;
    private boolean nouveauCourant;

    //Les infos que possède l'IA et qui sont mises à jour durant l'execution
    private Case[][] labyrinth;
    private ArrayList<Integer> posSortie;
    private boolean wumpusTue;


    public IaPrudente(int boundX, int boundY, int arrows, int well, int t) {

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
	this.posSortie = new ArrayList<Integer>();
	this.wumpusTue = false;
	this.totalTurn = t;
	this.turn = 0;
    }

    /**
     *Envoie la case vers laquelle le déplacement est le moins dangereux
     */
    public Case deplacementLePlusViable() {
	ArrayList<Case> listCellAdja = getCelluleAdjacente();
	ArrayList<Double> listInt = new ArrayList<Double>();
	Double compteur;
	Double pDangersWumpus = this.pourcentageDangersWumpus();
	//Pour toutes les cases adjacentes à l'actuelle
	for(Case c:listCellAdja) {
	    compteur = 0.0;

	    if(c.getVisite()){
		compteur += 2.0/5.0;	//Si on l'a déjà visité, on évite d'y aller
		//Si on entre dans un nouveau courant d'air, on preferera aller en arrière plutot que de tenter un dangers
	   	 if(nouveauCourant){
			compteur -= 1.0/5.0;
	   	 }
	    }else{
		if (currentY-1 >= 0) {
		    if(labyrinth[currentX][currentY-1].getId() == c.getId()){	//Case du haut
			if(c.getDangersPuit()) {
			    compteur += this.pourcentageDangersPuit(currentX, currentY-1);		//Si il y a un dangers de puit
			}
			if (c.getDangersWumpus()) {
			    compteur += pDangersWumpus;		//Si il y a un dangers de wumpus
			}
		    }
		}
		if (currentY+1 < this.boundY) {
		    if(labyrinth[currentX][currentY+1].getId() == c.getId()){	//Case du bas
			if(c.getDangersPuit()) {
			    compteur += this.pourcentageDangersPuit(currentX, currentY+1);		//Si il y a un dangers de puit
			}
			if (c.getDangersWumpus()) {
			    compteur += pDangersWumpus;		//Si il y a un dangers de wumpus
			}
		    }
		}
		if (currentX-1 >= 0) {
		    if(labyrinth[currentX-1][currentY].getId() == c.getId()){	//Case de gauche
			if(c.getDangersPuit()) {
			    compteur += this.pourcentageDangersPuit(currentX-1, currentY);		//Si il y a un dangers de puit
			}
			if (c.getDangersWumpus()) {
			    compteur += pDangersWumpus;		//Si il y a un dangers de wumpus
			}
		    }
		}
		if (currentX+1 < this.boundX) {
		    if(labyrinth[currentX+1][currentY].getId() == c.getId()){ //Case de droite
			if(c.getDangersPuit()) {
			    compteur += this.pourcentageDangersPuit(currentX+1, currentY);		//Si il y a un dangers de puit
			}
			if (c.getDangersWumpus()) {
			    compteur += pDangersWumpus;		//Si il y a un dangers de wumpus
			}
		    }
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
     *Envoie la case vers laquelle le déplacement se rapproche de la posistion voulue
     */
    public Case deplacementChasseur(ArrayList<Integer> posWumpus) {

	//Si la posistion voulue est à droite
	if(currentX < posWumpus.get(0)){
	    return labyrinth[currentX+1][currentY];
	}else{
	    //Si la posistion voulue est à gauche
	    if(currentX > posWumpus.get(0)){
		return labyrinth[currentX-1][currentY];
	    }
	}
	//Si la posistion voulue est en bas
	if(currentY < posWumpus.get(1)){
	    return labyrinth[currentX][currentY+1];
	}else{
	    //Si la posistion voulue est en haut
	    if(currentY < posWumpus.get(1)){
		return labyrinth[currentX][currentY-1];
	    }
	}
	return null;
    }


    /**
     *En fonction des données reçues, on met à jour la variable labyrinth
     */
    public void miseAJour(ArrayList<String> message){

	//On met à zéro nouveauCourant et on le met à vrai si on entre dans une case non visité et qui contient un courant d'air
	this.nouveauCourant = false;
	if(labyrinth[currentX][currentY].getVisite() == false && message.contains("courant")){
		this.nouveauCourant = true;
	}

	labyrinth[currentX][currentY].setVisite(true);
	//labyrinth[currentX][currentY].incrNbrVisite(0.75);

	//Si le wumpus a été tué, on l'enregistre
	if(message.contains("tue")){
	    this.wumpusTue = true;
	}

	//Si on est sur la case de la sortie, on l'enregistre
	if(turn == 0){
	    posSortie.clear();
	    posSortie.add(currentX);
	    posSortie.add(currentY);
	}

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

	    //Si on ne sent pas de courant d'air
	}else{
	    if (currentY-1 >= 0 ) {
		labyrinth[currentX][currentY-1].setDangersPuit(false);	//Case du haut
	    }
	    if (currentY+1 < this.boundY) {
		labyrinth[currentX][currentY+1].setDangersPuit(false);	//Case du bas
	    }
	    if (currentX-1 >= 0) {
		labyrinth[currentX-1][currentY].setDangersPuit(false);	//Case de gauche
	    }
	    if (currentX+1 < this.boundX) {
		labyrinth[currentX+1][currentY].setDangersPuit(false); //Case de droite
	    }
	}


	//Si on a raté, on perd une flèche
	if(message.contains("tir")) {
	    this.arrows --;
	}


	//On va regarder le nombre de dangersWumpus
	int compteurWumpus=0;
	int xWumpus=-1;
	int yWumpus=-1;

	for (int i=0; i<boundX; i++) {
	    for (int j=0; j<boundY; j++) {
		//On incrémente le nombre de dangers wumpus
		if(labyrinth[i][j].getDangersWumpus()){
		    compteurWumpus++;
		    xWumpus=i;
		    yWumpus=j;
		}
	    }
	}

	//Si il n'y a qu'un seul dangers, alors le wumpus est à cette position
	if(compteurWumpus == 1){
	    labyrinth[xWumpus][yWumpus].setWumpus(true);
	}
    }

    /**
     *Méthode appelé pour jouer -----------------------------------------------------------------------------------------------
     */
    public String jouer(int x, int y, ArrayList<String> message){

	//On met à jour notre position
	this.currentX=x;
	this.currentY=y;
	boolean wumpusTrouve=false;
	Case wumpusAcote=null;

	//Met à jour le plateau en fonction du message
	this.miseAJour(message);

	this.turn++;
	
	//Si le nombre de tour a dépassé la limite
	if(turn > totalTurn){
	    if(currentX == posSortie.get(0) && currentY == posSortie.get(1)){
		return "s";
	    }else{
			Case caseDirection = this.deplacementChasseur(posSortie);
		if(caseDirection != null){
		    return messageAEnvoyer(caseDirection);
		}else{
		    return "oups";
		}
	    }
	}else{
		//Si le wumpus a été tué, on va vers la sortie
		if(wumpusTue){

		    if(currentX == posSortie.get(0) && currentY == posSortie.get(1)){
			return "s";
		    }else{
				Case caseDirection = this.deplacementChasseur(posSortie);
			if(caseDirection != null){
			    return messageAEnvoyer(caseDirection);
			}else{
			    return "oups";
			}
		    }
		    //Sinon, on le cherche et on le tue
		}else{

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
			    if (currentY+1 < this.boundY) {
				if(labyrinth[x][y+1].getId() == wumpusAcote.getId()){	//Case du bas
				    return "t s";
				}
			    }
			    if (currentX-1 >= 0) {
				if(labyrinth[x-1][y].getId() == wumpusAcote.getId()){	//Case de gauche
				    return "t o";
				}
			    }
			    if (currentX+1 < this.boundX) {
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
	}
    }


    // -----------------------------------------------------------------------------------------------------------------------------

    /**
     *Envoie la liste des cases adjacentes
     */
    public ArrayList<Case> getCelluleAdjacente() {
	ArrayList<Case> list = new ArrayList<Case>();
	//On envoie les cases que si elles ne sont pas à l'exterieur du labyrinth
	if (currentY-1 >= 0) {
	    list.add(labyrinth[currentX][currentY-1]);
	}
	if (currentY+1 < this.boundY) {
	    list.add(labyrinth[currentX][currentY+1]);
	}
	if (currentX-1 >= 0) {
	    list.add(labyrinth[currentX-1][currentY]);
	}
	if (currentX+1 < this.boundX) {
	    list.add(labyrinth[currentX+1][currentY]);
	}


	return list;
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
	if (currentY+1 < this.boundY) {
	    if(labyrinth[currentX][currentY+1].getId() == caseDirection.getId()){	//Case du bas
		return "d s";
	    }
	}
	if (currentX-1 >= 0) {
	    if(labyrinth[currentX-1][currentY].getId() == caseDirection.getId()){	//Case de gauche
		return "d o";
	    }
	}
	if (currentX+1 < this.boundX) {
	    if(labyrinth[currentX+1][currentY].getId() == caseDirection.getId()){ //Case de droite
		return "d e";
	    }
	}

	return "oups";

    }


    /**
     * Renvoie vrai si on a trouvé le wumpus, faux sinon
     */
    public boolean presenceWumpus(){

	boolean test=false;

	//Parcours le labyrinth
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

	//Parcours le labyrinth
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


    /**
     * Renvoie le pourcentage de dangers puit, correspondant au nombre de puit sur le nombre de dangers
     */
    public Double pourcentageDangersPuit(int posX, int posY){

	Double nbDangers = 0.0;
	Double nbrCourantDair = 0.0;
	ArrayList listeDesId = new ArrayList();
	Case enDouble = null;	


	if(labyrinth[posX][posY].getDangersPuit()){

	    if (posY-1 >= 0) {
		if(labyrinth[posX][posY-1].getCourantDair()){
			nbDangers++;
			nbrCourantDair++;
		    if(posY-2 >= 0){
			if(labyrinth[posX][posY-2].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX][posY-2].getId())){
					enDouble = labyrinth[posX][posY-2];
				}else{
					listeDesId.add(labyrinth[posX][posY-2].getId());
				}
			}
		    }
		    if(posX-1 >= 0){
			if(labyrinth[posX-1][posY-1].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX-1][posY-1].getId())){
					enDouble = labyrinth[posX-1][posY-1];
				}else{
					listeDesId.add(labyrinth[posX-1][posY-1].getId());
				}
			}
		    }
		    if(posX+1 < this.boundX){
			if(labyrinth[posX+1][posY-1].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX+1][posY-1].getId())){
					enDouble = labyrinth[posX+1][posY-1];
				}else{
					listeDesId.add(labyrinth[posX+1][posY-1].getId());
				}
			}
		    }
		}
	    }
	    if (posY+1 < this.boundY) {
		if(labyrinth[posX][posY+1].getCourantDair()){
			nbDangers++;
			nbrCourantDair++;
		    if(posY+2 < this.boundY){
			if(labyrinth[posX][posY+2].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX][posY+2].getId())){
					enDouble = labyrinth[posX][posY+2];
				}else{
					listeDesId.add(labyrinth[posX][posY+2].getId());
				}
			}
		    }
		    if(posX-1 >= 0){
			if(labyrinth[posX-1][posY+1].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX-1][posY+1].getId())){
					enDouble = labyrinth[posX-1][posY+1];
				}else{
					listeDesId.add(labyrinth[posX-1][posY+1].getId());
				}
			}
		    }
		    if(posX+1 < this.boundX){
			if(labyrinth[posX+1][posY+1].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX+1][posY+1].getId())){
					enDouble = labyrinth[posX+1][posY+1];
				}else{
					listeDesId.add(labyrinth[posX+1][posY+1].getId());
				}
			}
		    }
		}
	    }
	    if (posX-1 >= 0) {
		if(labyrinth[posX-1][posY].getCourantDair()){
			nbDangers++;
			nbrCourantDair++;
		    if(posY-1 >= 0){
			if(labyrinth[posX-1][posY-1].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX-1][posY-1].getId())){
					enDouble = labyrinth[posX-1][posY-1];
				}else{
					listeDesId.add(labyrinth[posX-1][posY-1].getId());
				}	    
			}
		    }
		
		    if(posY+1 < this.boundY){
			if(labyrinth[posX-1][posY+1].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX-1][posY+1].getId())){
					enDouble = labyrinth[posX-1][posY+1];
				}else{
					listeDesId.add(labyrinth[posX-1][posY+1].getId());
				}
			}
		    }
		    if(posX-2 >= 0){
			if(labyrinth[posX-2][posY].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX-2][posY].getId())){
					enDouble = labyrinth[posX-2][posY];
				}else{
					listeDesId.add(labyrinth[posX-2][posY].getId());
				}
			}
		    }
		}
	    }
	    if (posX+1 < this.boundX) {
		if(labyrinth[posX+1][posY].getCourantDair()){
			nbDangers++;
			nbrCourantDair++;
		    if(posY-1 >= 0){
			if(labyrinth[posX+1][posY-1].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX+1][posY-1].getId())){
					enDouble = labyrinth[posX+1][posY-1];
				}else{
					listeDesId.add(labyrinth[posX+1][posY-1].getId());
				}
			}
		    }
		    if(posY+1 < this.boundY){
			if(labyrinth[posX+1][posY+1].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX+1][posY+1].getId())){
					enDouble = labyrinth[posX+1][posY+1];
				}else{
					listeDesId.add(labyrinth[posX+1][posY+1].getId());
				}
			}
		    }
		    if(posX+2 < this.boundX){
			if(labyrinth[posX+2][posY].getDangersPuit()){
			    nbDangers++;
				if(listeDesId.contains(labyrinth[posX+2][posY].getId())){
					enDouble = labyrinth[posX+2][posY];
				}else{
					listeDesId.add(labyrinth[posX+2][posY].getId());
				}
			}
		    }
		}
	    }
		 //On renvoie une valeur en fonction du nombre de courant d'air autour de la case
		 if(nbrCourantDair == 1){
		    return 1/nbDangers;
		 }
		 if(nbrCourantDair == 2){
			if(enDouble == null){
				return 4.0/5.0;
			}else{
				if(enDouble.getDangersPuit()){
					return 1.0/2.0;
				}else{
					return 4.0/5.0;
				}
			}
		 }
		 if(nbrCourantDair == 3){
			return 4.0/5.0;
		 }
		 if(nbrCourantDair == 4){
			return 1.0;
	 	}


	 	return 0.0;

	 }else{
	 	return 0.0;
	 }

    }
    

    /**
     * Renvoie le pourcentage de dangers wumpus, correspondant à 1 (le nombre de wumpus) sur le nombre de dangers
     */
    public Double pourcentageDangersWumpus(){

	Double nbDangers = 0.0;

	//Parcours le labyrinth
	for (int i=0; i<boundX; i++) {
	    for (int j=0; j<boundY; j++) {
			if(labyrinth[i][j].getDangersWumpus() == true){
		    	nbDangers++;
			}
	    }
	}

		if(nbDangers == 0){
			return 0.0;
		}else{
			return 1.0/nbDangers;
		}

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
