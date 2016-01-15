package jeuSocket;
/**
*Cette classe est la classe Vue du Jeu de la chasse au Wumpus
*
*@author Groupe de projet de D
*@version 1.0
*/


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Vue extends JFrame {

	// attributs de la classe
	private JTextArea grille;
	private Partie laPartie;
	
	// constructeur de la classe
	/**Constructeur de la classe vue
	*@param titre qui represente le titre qui sera donnee a la vue
	*@param p qui represente la partie initialise
	*/
	public Vue(String titre, Partie p) {
		super(titre);
		
		this.setAlwaysOnTop(true);
		
		this.laPartie = p;
		
		JPanel panneau = new JPanel();
		panneau.setBackground(Color.BLACK);
		this.getContentPane().add(panneau);	
		
		this.grille = new JTextArea("\n\n\n\n\n       Bienvenue a vous, " + this.laPartie.getJoueur().getPseudo() + " !\n"
									+ "Si vous etes pret a relever le defi,\nappuyer sur la touche Entree pour\n"
									+ "       afficher la grille de jeu...");
		this.grille.setOpaque(false);
		this.grille.setBackground(Color.BLACK);
		this.grille.setFont(new Font("Times New Romans", Font.BOLD, 15));
		this.grille.setForeground(Color.WHITE);
		this.grille.setEditable(false);
		this.grille.addKeyListener(new Controleur(this, this.laPartie));
		panneau.add(grille);
		
		this.setPreferredSize(new Dimension(350, 450));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	/**methode qui permet une mise a jour de la grille de jeu affichee dans la fenetre 
	*/
	public void updateGrille(String up) {
		this.grille.setText(up);
		this.grille.setFont(new Font("Arial", Font.BOLD, 15));
		this.grille.setForeground(Color.green);
	}
	
	/**methode qui permet a la vue de se modifier lors de la victoire du joueur
	*/
	public void updateVictoire() {
		this.grille.setText("\n\n\n\n\n\n\nVictoire !!!");
		this.grille.setForeground(Color.RED);
		this.grille.setFont(new Font("Arial", Font.BOLD, 20));
	}
	
	/**methode qui permet a la vue de se modifier lors de la defaite du joueur
	*/
	public void updateDefaite() {
		this.grille.setText("\n\n\n\n\n\n\nGame Over...");
		this.grille.setForeground(Color.WHITE);
		this.grille.setFont(new Font("Arial", Font.BOLD, 20));
	}

}
