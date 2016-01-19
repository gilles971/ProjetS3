package IA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class IA1 {
private int arrows;
private int boundX;
private int boundY;

public IA1(int boundX, int boundY, int arrows) {

	this.arrows = arrows;
	this.boundX = boundX;
	this.boundY = boundY;
}


/**
*Méthode appelé pour jouer
*/
public String jouer(){
	try {
		Thread.currentThread().sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Random rand = new Random();
	int aleatoire = rand.nextInt(5)+1;
	int aleatoire2;

	//On envoie un mouvement (4 chances sur 5) ou un tir (1 chance sur 5) aléatoirement

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
}
