package SimulatorPackage;
/*
 * SchellingSimulator
 * 
 * Cette classe permet de manier l'interface graphique pour afficher le modèle de Schelling
 * Il s'agit d'une extension de la classe ImmigrationSimulator, qui override les méthodes qui changent 
 * d'un jeu à l'autre
 * Elle contient toutes les méthodes nécessaires à afficher, passer et revenir au début.
 * 
 * 11/2021
 * 
 * Copyright notice
 */

import java.util.*;

import EntityPackage.Schelling;
import java.awt.Color;
import gui.* ;

public class SchellingSimulator extends ImmigrationSimulator {

    private static int NOMBRE_COULEURS = 5;

    private Color[] couleurs;

    public SchellingSimulator(GUISimulator gui, int x, int y) {
        super(gui, x, y);
        this.jeu = new Schelling(x, y);
        this.couleurs = RandomListColors();
    }

    @Override
    public void animate() {
        super.animate();
    }

    @Override
    public Color intToCol(int colNum){
        return colNum==0?Color.BLACK:this.couleurs[colNum-1];
    }

    public Color[] RandomListColors(){
        Color[] liste_couleurs = new Color[NOMBRE_COULEURS];
        for (int i=0; i<NOMBRE_COULEURS; i++){
            Random random = new Random();
            final float hue = random.nextFloat();
            final float saturation = 0.4f;//1.0 for brilliant, 0.0 for dull
            final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
            liste_couleurs[i] = Color.getHSBColor(hue, saturation, luminance);
        }
        return liste_couleurs;
    }  

}
