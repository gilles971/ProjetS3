package IA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class IA1 {
private Case[][] labyrinth;
private int arrows;
private int boundX;
private int boundY;

public IA1(int boundX, int boundY, int arrows) {
	
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
*Méthode appelé pour jouer
*/
public String jouer(){

	Random rand = new Random();
	int aleatoire = rand.nextInt(5)+1;
	int aleatoire2;

	switch (aleatoire){
		case 1: return "d e";


		case 2: return "d o";


		case 3: return "d n";


		case 4: return "d s";


		case 5: aleatoire2 = rand.nextInt(4)+1;
				switch (aleatoire2){
					case 1: return "t e";

					case 2: return "t o";

					case 3: return "t n";

					case 4: return "t s";

					default: return "oups";
				}
		default: return "oups";


	}	
}


// ---------------------------------------------------------------------- //
}