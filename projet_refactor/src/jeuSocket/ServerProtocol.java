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
			/*p.setMonde(new Monde(10));
			p.getMonde().placerObjets(theInput);
			p.disposerPlateau();
			return "Paramètres enregistrés";
			*/
			p.setMonde(new Monde(5));
			p.getMonde().placerObjets();
			p.disposerPlateau();
			return "Paramètres non valides, paramètres de base enregistrés";
			//*/
		}else{

			String ret="";

	    	//on vérifie que la commande est standard
			if (
					theInput.equals("d n") ||
					theInput.equals("d o") ||
					theInput.equals("d e") ||
					theInput.equals("d s")
					)
					{
				//on déplace le joueur en utilisant les méthodes de partie
						ret = p.deplacer(theInput);
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
      else if (theInput.equals("s")) {
            p.sortir();
      }

			//mise a jour de la fenêtre
			p.getVue().updateGrille(p.toString());

			//verification des conditions de victoire
			if (p.gameOver()) { return "game end"; }

	        return p.getJoueur().getX()+" "+p.getJoueur().getY()+" "+ret; // \t
    	}
    }

}
