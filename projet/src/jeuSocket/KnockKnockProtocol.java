package jeuSocket;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     noticphis list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.net.*;
import java.io.*;

public class KnockKnockProtocol {
    private static final int WAITING = 0;
    private static final int SENTCLUE = 1;
    private static final int ANOTHER = 2;

    private static final int NUMJOKES = 4;

    private int state = WAITING;
    private int currentClue = 0;

    private String[] clues = { "c'est trop calme", "je sens un courant d'air", "raté!", "Je sens le Wumpus", "Entrez votre nom" };
    private String[] ends = { "Vous êtes tombé",
            "Miam Wumpus suicide" };
            
    private Joueur j;
    private Partie p;
    
    public KnockKnockProtocol () {
    	j = new Joueur("j1");		
		p = new Partie(j, "hunt the wumpus");
	
		p.setMonde(new Monde(5,1,2,0,1));
		p.disposerPlateau();
		p.setGrille();
    }
    
    public String processInput(String theInput) {
    	
        String theOutput = null;
		
		System.out.println(p.getJoueur().toString());
		System.out.println(p.getGrille().commandes());	
		p.verifMarque();
		
		System.out.println(p.getGrille().positionActuelle());
							
		if (
				theInput.equals("d n") ||
				theInput.equals("d o") ||
				theInput.equals("d e") ||
				theInput.equals("d s")
				)
				{
					p.deplacer(theInput);
				}				
		else if (
				theInput.equals("t n") ||
				theInput.equals("t o") ||
				theInput.equals("t e") ||
				theInput.equals("t s")
				)
				{					
					p.tirer(theInput);
				}
		/*
		else if (theInput.equals("h")){
					System.out.println("Voici l'historique de vos actions :"+"\n"+p.);
				}
			*/
			
				// mise a jour de la fenetre
		
		p.getVue().updateGrille(p.getGrille().recupAffGrille());
		
		if (p.getVictoire()) {
			p.getVue().updateVictoire();
			return "Vous avez gagne !!!";
		}
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
        
        return p.verifMarque();
    }
}