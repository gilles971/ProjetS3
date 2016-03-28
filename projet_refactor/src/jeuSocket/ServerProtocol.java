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
     	p = new Partie(j);

    }

    /**
     * @param theInput la valeur du déplacement
     * @return la position actualisée et le message/indice
     * dans les grandes lignes c'est une reprise de la méthode jouer de Partie
     * (des versions antérieures de partie).
     */
    public String processInput(String theInput) {

	if(p.getMonde() == null){
	    if (theInput.trim().equals("") || theInput.equals("default")) {
		p.setMonde(new Monde(5));
		p.getMonde().placerObjets();
		p.disposerPlateau();
		return "Paramètres non valides, paramètres de base enregistrés";
	    }
	    else {
		String[] theWorld = theInput.split("\\s+");
		p.setMonde(new Monde(Integer.valueOf(theWorld[0])));
		String objets = "";
		if (theWorld.length>1)
			for (int i=1; i<theWorld.length; i++)
		    	objets += theWorld[i]+" ";
		p.getMonde().placerObjets(objets);
		p.disposerPlateau();
		return "Paramètres enregistrés";
	    }
	}
	else{

	    String ret="";
	    String[] order=theInput.split("\\s+");
	    //on vérifie que la commande est standard
	    if (order[0].equals("d")) {
		//on déplace le joueur en utilisant les méthodes de partie
		ret = p.deplacer(theInput);
	    }
	    if (order[0].equals("t")) {
		//on tire notre flèche en utilisant les méthodes de partie
		ret = p.tirer(theInput);
	    }
	    if (theInput.equals("s")) {
		ret = p.sortir();
	    }
      if (theInput.equals("h")) {
		return p.help();
	    }

	    //mise a jour de la fenêtre
	    p.getVue().updateGrille(p.toString());

	    //verification des conditions de victoire
	    //if (p.gameOver()) { return "game end"; }

	    return p.getJoueur().getX()+" "+p.getJoueur().getY()+" "+ret; // \t
    	}
    }

}
