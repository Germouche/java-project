package SimulatorPackage;
/*
 * ConwaySimulator
 * 
 * Cette classe permet de manier l'interface graphique pour afficher le jeu de la vie de Conway
 * Il s'agit d'une extension de la classe abstraite Simulator.
 * Elle contient toutes les méthodes nécessaires à afficher, passer et revenir au début.
 *
 * 11/2021
 * 
 * Copyright notice
 */

import java.awt.Color;

import EntityPackage.Cellule;
import EntityPackage.Conway;
import gui.* ;

public class ConwaySimulator extends Simulator {

    protected Conway jeu;
    
    private static int NOMBRE_COULEURS = 1;

    protected int hauteurFenetre;
    protected int tailleCarreau;


    //Constructeur
    public ConwaySimulator(GUISimulator gui, int x, int y){
        super(gui);
        this.jeu = new Conway(x, y);
        this.hauteurFenetre=gui.getPanelHeight();
        this.tailleCarreau=(int)this.hauteurFenetre/x;
    }

    //Implementation de simulable

    public void animate(){
        this.jeu.AnimateJeu();
    }

    public void restart(){
        this.gui.reset();
        this.jeu.reinitGrille();
        printElements();
    }    

    //Fonction de dessin
    public void printElements(){
        Cellule[][] grille = this.jeu.getGrille();
        for (int x = 0; x<this.jeu.getSizeX(); x++){
            for (int y = 0; y<this.jeu.getSizeY(); y++){
                int etat = grille[x][y].getEtat();
                Color couleur = intToCol(etat);
                this.gui.addGraphicalElement(new Rectangle(this.tailleCarreau*x, this.tailleCarreau*y, Color.WHITE, couleur, this.tailleCarreau));
            }
        }
    }

    public Color intToCol(int colNum){
        int rgbNum = 255 - (int) (((float)colNum/NOMBRE_COULEURS)*255.0);
        return new Color (rgbNum,rgbNum,rgbNum);
    }
}
