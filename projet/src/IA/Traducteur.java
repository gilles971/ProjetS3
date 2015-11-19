import java.util.Scanner; 

public class Traducteur {

Scanner sc;
String com;

public Traducteur() {
	sc = new Scanner(System.in);
}		

	public int scanEntreeDeplacement() {
		com = sc.nextLine();
		com = sc.nextLine();
		if (com.equals("Vous sentez un courant d'air !!")) {
			return 1;
		}
		if (com.equals("\nVous sentez une odeur infame...")) {
			return 2;
		}

		if (com.equals("Vous etes deja passe par la.")) {
			return 3;
		}
		if (com.equals("C'est trop calme...")) {
			return 4;
		}
		return 0;
	}

	public int scanEntreeTire() {
		com = sc.nextLine();
		if (com.equals("Rate ! Il vous reste 0 fleches\n")) {
			return 1;
		}
		if (com.equals("Rate ! Il vous reste 1 fleches\n")) {
			return 2;
		}
		if (com.equals("Rate ! Il vous reste 2 fleches\n")) {
			return 3;
		}
		if (com.equals("\nVous n'avez plus de fleche pour tirer.\n")) {
			return 4;
		}
		if (com.equals("Vous ne pouvez pas tirer ici !\n")) {
			return 5;
		}
		if (com.equals("En plein entre les deux yeux !\nFelicitation vous avez tue le  wumpus !\n")) {
			return 6;
		}
			return 0;
	}


	//public void envoieSortie(String str) {
}



