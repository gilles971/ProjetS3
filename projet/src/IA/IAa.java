import java.util.ArrayList;
import java.util.Collections;

public class IAa {
private Case[][] labyrinth;
private int currentX;
private int currentY;
private int arrows;

public IAa(int boundX, int boundY, int arrows) {
	for (int i=0; i<boundX; i++) {
		for (int j=0; j<boundY; j++) {
			labyrinth[i][j] = new Case();
		}
	}
	this.arrows = arrows;
}

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
				compteur += 10;
			}
			if (c.getPuit()) {
				compteur += 10;
			}
		}
		listInt.add(compteur);
	}
	int minimum = Collections.min(listInt);
	
}


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

}

