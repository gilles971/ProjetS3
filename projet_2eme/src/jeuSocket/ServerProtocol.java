package jeuSocket;

/*
 * cette classe sert au WumpusServer
 * elle permet de vérifier la validité des chaînes de caractères passée en entrée
 * sont standard et interprétable par le jeu.
 *
 * le protocole est d'ailleurs lié a une version customisée de partie,
 * a laquelle il fait appel si le mouvement est standard
 *
 * cette classe est en partie inspirée des classes fournies par oracle pour la mise
 * en place d'un système client serveur
 */

public class ServerProtocol {

    private Joueur j;
    private Partie p;

    public ServerProtocol () {
    	j = new Joueur("j1");
		p = new Partie(j, "hunt the wumpus");

    }

    /**
     * @param theInput la valeur du déplacement
     * @return la position actualisée et le message/indice
     * dans les grandes lignes c'est une reprise de la méthode jouer de Partie
     * (des versions antérieures de partie).
     */
    public String processInput(String theInput) {

    	if(p.getMonde() == null){
    		String[] tab = theInput.split("\\s+");

    		if(tab[0].equals("conf")
    			&& Integer.valueOf(tab[1]) > 2
    			&& Integer.valueOf(tab[2]) >= 0 && Integer.valueOf(tab[2]) < Integer.valueOf(tab[1])*Integer.valueOf(tab[1])/2
    			&& Integer.valueOf(tab[3]) >=0
    			&& Integer.valueOf(tab[4]) >=1
    			){
				p.setMonde(new Monde(Integer.valueOf(tab[1]),
									1,
									Integer.valueOf(tab[2]),
									Integer.valueOf(tab[3]),
									Integer.valueOf(tab[4])));
				p.disposerPlateau();
				p.setGrille();
				return "Paramètres enregistrés";
			}else{
				p.setMonde(new Monde(5,1,1,0,1));
				p.disposerPlateau();
				p.setGrille();
				return "Paramètres non valides, paramètres de base enregistrés";
			}
		}else{

	    	//on vérifie que la commande est standard
			if (
					theInput.equals("d n") ||
					theInput.equals("d o") ||
					theInput.equals("d e") ||
					theInput.equals("d s")
					)
					{
				//on déplace le joueur en utilisant les méthodes de partie
						p.deplacer(theInput);
					}
			else if (
					theInput.equals("t n") ||
					theInput.equals("t o") ||
					theInput.equals("t e") ||
					theInput.equals("t s")
					)
					{
				//on tire notre flèche en utilisant les méthodes de partie
						p.tirer(theInput);
					}

			//mise a jour de la fenêtre
			p.getVue().updateGrille(p.getGrille().recupAffGrille());

			//verification des conditions de victoire
			if (p.getVictoire()) {
				p.getVue().updateVictoire();
				return "Vous avez gagne !!!";
			}
			//verification de l'état de mort
			if (!(p.getJoueur().getVivant())){
				p.getVue().updateDefaite();
				String mort = p.getRaisonMort();
				if (mort.equals("Wumpus")) {
					return "Wumpus suicide ! Try again !";
				}
				if (mort.equals("trou")) {
					return "Le trou a vaincu !";
				}
			}

	        return p.getGrille().getJoueur().getCoordX()+" "+p.getGrille().getJoueur().getCoordY()+" "+p.verifMarque(); // \t
    	}
    }

}
